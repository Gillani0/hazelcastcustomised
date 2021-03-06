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

package com.hazelcast.jet.memory.impl.operations.tuple;

import com.hazelcast.jet.io.tuple.Tuple;
import com.hazelcast.jet.memory.spi.operations.ElementsReader;

public class TupleKeyElementsReader implements ElementsReader<Tuple> {
    private Tuple source;
    private int keyIndex;

    @Override
    public Object next() {
        try {
            return source.getKey(keyIndex);
        } finally {
            keyIndex++;
        }
    }

    @Override
    public boolean hasNext() {
        return keyIndex < source.keySize();
    }

    @Override
    public int size() {
        return source.keySize();
    }

    @Override
    public void setSource(Tuple source) {
        this.keyIndex = 0;
        this.source = source;
    }
}
