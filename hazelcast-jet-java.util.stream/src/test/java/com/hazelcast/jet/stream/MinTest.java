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

import static org.junit.Assert.assertEquals;

@Category(QuickTest.class)
@RunWith(HazelcastParallelClassRunner.class)
public class MinTest extends JetStreamTestSupport {

    @Test
    public void testMin_whenSourceMap() throws Exception {
        IStreamMap<String, Integer> map = getStreamMap(instance);
        fillMap(map);

        int result = map.stream()
                .map(e -> e.getValue())
                .min((o1, o2) -> o1.compareTo(o2))
                .get();

        assertEquals(0, result);
    }

    @Test
    public void testMin_whenSourceList() throws Exception {
        IStreamList<Integer> list = getStreamList(instance);
        fillList(list);

        long result = list
                .stream()
                .min((o1, o2) -> o1.compareTo(o2))
                .get();

        assertEquals(0, result);
    }


}
