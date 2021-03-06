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

package com.hazelcast.jet.memory.api.binarystorage.iterator;

/**
 * Represents API to iterate over records for the current slot;
 */
public interface BinaryRecordIterator {
    /**
     * @return true if there are also elements to fetch, false in opposite;
     */
    boolean hasNext();

    /**
     * @return address to the next record;
     */
    long next();

    /**
     * Reset current state of iterator
     *
     * @param slotAddress - address of the slot;
     * @param sourceId    - number of the source;
     */
    void reset(long slotAddress, int sourceId);
}
