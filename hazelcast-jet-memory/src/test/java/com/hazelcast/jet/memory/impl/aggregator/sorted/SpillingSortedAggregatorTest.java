package com.hazelcast.jet.memory.impl.aggregator.sorted;


import com.hazelcast.jet.io.impl.IOContextImpl;
import com.hazelcast.jet.io.tuple.Tuple2;
import com.hazelcast.jet.io.IOContext;
import com.hazelcast.jet.io.tuple.Tuple;
import com.hazelcast.jet.memory.BaseMemoryTest;
import com.hazelcast.jet.memory.impl.aggregator.NonAssociativeSumFunctor;
import com.hazelcast.jet.memory.impl.aggregator.SumFunctor;
import com.hazelcast.jet.memory.impl.binarystorage.comparator.StringComparator;
import com.hazelcast.jet.memory.impl.memory.impl.DefaultMemoryContext;
import com.hazelcast.jet.memory.impl.operations.DefaultContainersPull;
import com.hazelcast.jet.memory.impl.operations.tuple.Tuple2Factory;
import com.hazelcast.jet.memory.impl.operations.tuple.TupleKeyElementsWriter;
import com.hazelcast.jet.memory.impl.operations.tuple.TupleKeyElementsReader;
import com.hazelcast.jet.memory.impl.operations.tuple.TupleValueElementsReader;
import com.hazelcast.jet.memory.impl.operations.tuple.TupleValueElementsWriter;
import com.hazelcast.jet.memory.spi.binarystorage.BinaryComparator;
import com.hazelcast.jet.memory.spi.binarystorage.sorted.OrderingDirection;
import com.hazelcast.jet.memory.spi.memory.MemoryChainingType;
import com.hazelcast.jet.memory.spi.operations.ElementsReader;
import com.hazelcast.jet.memory.spi.operations.ElementsWriter;
import com.hazelcast.jet.memory.spi.operations.OperationFactory;
import com.hazelcast.jet.memory.spi.operations.aggregator.sorting.SortedAggregator;
import com.hazelcast.jet.memory.spi.operations.functors.BinaryFunctor;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(HazelcastSerialClassRunner.class)
@Category(QuickTest.class)
public class SpillingSortedAggregatorTest extends BaseMemoryTest {
    private SortedAggregator<Tuple> aggregator;
    private IOContext ioContext = new IOContextImpl();
    private final ElementsWriter<Tuple> keyWriter = new TupleKeyElementsWriter();
    private final ElementsWriter<Tuple> valueWriter = new TupleValueElementsWriter();

    protected long heapSize() {
        return 256 * 1024 * 1024;
    }

    protected long blockSize() {
        return 128 * 1024;
    }

    @Before
    public void setUp() throws Exception {
        init();
    }

    private void initAggregator(BinaryComparator binaryComparator) throws IOException {
        initAggregator(binaryComparator, null);
    }

    private void initAggregator(BinaryComparator binaryComparator,
                                BinaryFunctor binaryFunctor) throws IOException {
        memoryContext = new DefaultMemoryContext(
                heapMemoryPool,
                nativeMemoryPool,
                blockSize(),
                useBigEndian()
        );

        aggregator = OperationFactory.getSortedAggregator(
                memoryContext,
                ioContext,
                MemoryChainingType.HEAP,
                2,//partitionCount
                1024,//spillingBufferSize
                binaryComparator,
                keyWriter,
                valueWriter,
                new DefaultContainersPull<Tuple>(
                        new Tuple2Factory(),
                        1024
                ),
                binaryFunctor,
                Files.createTempDirectory("hazelcast-jet-spilling").toString(),
                OrderingDirection.ASC,
                65536,//spillingChunkSize
                true,
                true
        );
    }

    private void insertElements(ElementsReader<Tuple> keyReader,
                                ElementsReader<Tuple> valueReader,
                                Tuple<String, String> tuple,
                                int start,
                                int end) throws Exception {
        for (int i = end; i >= start; i--) {
            tuple.setKey(0, String.valueOf(i));
            tuple.setValue(1, String.valueOf(i));
            if (!aggregator.putRecord(
                    tuple,
                    keyReader,
                    valueReader
            )) {
                doSpilling(i);
                aggregator.putRecord(
                        tuple,
                        keyReader,
                        valueReader
                );
            }
        }
    }

    private void doSpilling(int index) {
        long t = System.currentTimeMillis();
        System.out.println("Start spilling i=" + index);

        aggregator.startSpilling();
        do {
        } while (!aggregator.spillNextChunk());
        aggregator.finishSpilling();

        System.out.println(
                "End spilling index=" + index + " spillingTime=" + (System.currentTimeMillis() - t)
        );
    }

    @Test
    public void testString2String() throws Exception {
        initAggregator(new StringComparator());

        ElementsReader<Tuple> keyReader = new TupleKeyElementsReader();
        ElementsReader<Tuple> valueReader = new TupleValueElementsReader();
        Tuple<String, String> tuple = new Tuple2<String, String>();

        int CNT = 10_000_000;
        long t = System.currentTimeMillis();
        insertElements(keyReader, valueReader, tuple, 1, CNT);
        System.out.println("InsertionTime=" + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();

        aggregator.startSorting();

        do {
        } while (!aggregator.sort());

        System.out.println("SortingTime=" + (System.currentTimeMillis() - t));

        Iterator<Tuple> iterator = aggregator.iterator();

        long time = System.currentTimeMillis();

        String previous = null;

        int iterations_count = 0;

        while (iterator.hasNext()) {
            Tuple tt = iterator.next();

            if (previous != null) {
                assertTrue("iterations_count=" + iterations_count, ((String) tt.getKey(0)).compareTo(previous) > 0);
            }

            previous = (String) tt.getKey(0);
            iterations_count++;
        }

        assertEquals(CNT, iterations_count);
        System.out.println("SelectionTime=" + (System.currentTimeMillis() - time));
    }

    @Test
    public void testString2StringMultiValue() throws Exception {
        initAggregator(new StringComparator());
        ElementsReader<Tuple> keyReader = new TupleKeyElementsReader();
        ElementsReader<Tuple> valueReader = new TupleValueElementsReader();
        Tuple<String, String> tuple = new Tuple2<String, String>();
        int VALUES_CNT = 10;
        int KEYS_CNT = 1_000_000;

        byte[] markers = new byte[KEYS_CNT];
        Arrays.fill(markers, (byte) 0);
        long t = System.currentTimeMillis();

        for (int i = 1; i <= KEYS_CNT; i++) {
            if (i == 100186) {
                int a = 1;
            }
            tuple.setKey(0, String.valueOf(i));
            for (int ii = 0; ii < VALUES_CNT; ii++) {
                tuple.setValue(0, String.valueOf(ii));
                if (!aggregator.putRecord(
                        tuple,
                        keyReader,
                        valueReader
                )) {
                    doSpilling(i);

                    aggregator.putRecord(
                            tuple,
                            keyReader,
                            valueReader
                    );
                }
            }
        }
        System.out.println("InsertionTime=" + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        aggregator.startSorting();

        do {
        } while (!aggregator.sort());

        System.out.println("SortingTime=" + (System.currentTimeMillis() - t));

        Iterator<Tuple> iterator = aggregator.iterator();

        int value_offset = 0;
        String previous = null;
        int iterations_count = 0;

        while (iterator.hasNext()) {
            Tuple tt = iterator.next();
            String key = (String) tt.getKey(0);

            if (value_offset == 0) {
                if (previous != null) {
                    assertTrue(((String) tt.getKey(0)).compareTo(previous) > 0);
                }

                previous = key;
                value_offset++;
            } else {
                assertEquals(key, previous);

                if (value_offset < VALUES_CNT - 1) {
                    value_offset++;
                } else if (value_offset == VALUES_CNT - 1) {
                    value_offset = 0;
                }

                previous = key;
            }

            iterations_count++;
        }

        assertEquals(KEYS_CNT * VALUES_CNT, iterations_count);
    }

    @Test
    public void testString2StringAssociativeFunctor() throws Exception {
        initAggregator(new StringComparator(), new SumFunctor());
        testFunctor(10_000_000, 10);
    }

    @Test
    public void testString2StringNonAssociativeFunctor() throws Exception {
        initAggregator(new StringComparator(), new NonAssociativeSumFunctor());
        testFunctor(1_000_000, 10);
    }

    private void testFunctor(int keyCount, int valuesCount) throws Exception {
        Tuple<String, Integer> tuple = new Tuple2<String, Integer>();
        ElementsReader<Tuple> keyReader = new TupleKeyElementsReader();
        ElementsReader<Tuple> valueReader = new TupleValueElementsReader();

        long t = System.currentTimeMillis();
        for (int i = 1; i <= keyCount; i++) {
            tuple.setKey(0, String.valueOf(i));
            for (int ii = 0; ii < valuesCount; ii++) {
                tuple.setValue(0, 1);
                if (!aggregator.putRecord(
                        tuple,
                        keyReader,
                        valueReader
                )) {
                    doSpilling(i);

                    aggregator.putRecord(
                            tuple,
                            keyReader,
                            valueReader
                    );
                }
            }
        }

        System.out.println("InsertionTime=" + (System.currentTimeMillis() - t));

        long time = System.currentTimeMillis();
        aggregator.startSorting();
        do {
        } while (!aggregator.sort());
        System.out.println("SortingTime=" + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        Iterator<Tuple> iterator = aggregator.iterator();
        String previous = null;
        int iterations_count = 0;
        while (iterator.hasNext()) {
            Tuple tt = iterator.next();
            if (previous != null) {
                assertTrue(((String) tt.getKey(0)).compareTo(previous) > 0);
            }

            assertEquals(valuesCount, tt.getValue(0));
            previous = (String) tt.getKey(0);
            iterations_count++;
        }

        assertEquals(keyCount, iterations_count);
        System.out.println("SelectionTime=" + (System.currentTimeMillis() - time));
    }

    @After
    public void tearDown() throws Exception {
        aggregator.dispose();
        cleanUp();
    }
}
