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

package com.hazelcast.jet.memory.api.binarystorage;

import com.hazelcast.jet.memory.api.memory.management.MemoryBlock;

/**
 * Each Memory block has a header:
 * <pre>
 * ----------------------------------------------------------------------
 * |            Key-value storage base address (8 bytes)                |
 * ----------------------------------------------------------------------
 * </pre>
 * </pre>
 */
public interface StorageHeader {
    /**
     * Return address of the storage by the corresponding slot's address;
     *
     * @return address of the storage;
     */
    long getBaseStorageAddress();

    /**
     * Set corresponding memory manager for the storage;
     *
     * @param memoryBlock - corresponding memory manager;
     */
    void setMemoryBlock(MemoryBlock memoryBlock);

    /**
     * Allocated header for the corresponding
     * memoryAccessor by corresponding address;
     */
    void allocatedHeader();

    /**
     * @param baseAddress - address of the slot;
     */
    void setBaseStorageAddress(long baseAddress);

    /**
     * @return total size acquired by header;
     */
    int getSize();

    /**
     * Reset header for current memory block
     */
    void resetHeader();
}
