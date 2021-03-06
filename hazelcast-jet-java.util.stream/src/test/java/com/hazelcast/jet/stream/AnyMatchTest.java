/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazelcast.jet.stream;

import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@Category(QuickTest.class)
@RunWith(HazelcastParallelClassRunner.class)
public class AnyMatchTest extends JetStreamTestSupport {

    @Test
    public void testAnyMatchTrue_whenSourceMap() {
        IStreamMap<String, Integer> map = getStreamMap(instance);
        fillMap(map);

        boolean found = map.stream()
                .anyMatch(m -> m.getValue() > COUNT/2);


        assertEquals(true, found);
    }

    @Test
    public void testAnyMatchFalse_whenSourceMap() {
        IStreamMap<String, Integer> map = getStreamMap(instance);
        fillMap(map);

        boolean found = map.stream()
                .anyMatch(m -> m.getValue() > COUNT);


        assertEquals(false, found);
    }

    @Test
    public void testAnyMatchTrue_whenIntermediateOperation() {
        IStreamMap<String, Integer> map = getStreamMap(instance);
        fillMap(map);

        boolean found = map.stream()
                .map(Map.Entry::getValue)
                .anyMatch(m -> m > COUNT/ 2);


        assertEquals(true, found);
    }

    @Test
    public void testAnyMatchFalse_whenIntermediateOperation() {
        IStreamMap<String, Integer> map = getStreamMap(instance);
        fillMap(map);

        boolean found = map.stream()
                .map(Map.Entry::getValue)
                .anyMatch(m -> m > COUNT);


        assertEquals(false, found);
    }

    @Test
    public void testAnyMatch_whenSourceList() {
        IStreamList<Integer> list = getStreamList(instance);
        fillList(list);

        boolean found = list.stream()
                .anyMatch(l -> l > COUNT/2);

        assertEquals(true, found);
    }

}
