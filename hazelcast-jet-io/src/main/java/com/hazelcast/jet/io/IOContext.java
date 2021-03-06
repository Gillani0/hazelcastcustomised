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

package com.hazelcast.jet.io;

/**
 * Context to work with Input/Output system
 */
public interface IOContext {
    /**
     * @param typeID - identifier of type;
     * @return - object which represents type;
     */
    DataType getDataType(byte typeID);

    /**
     * @param object - object;
     * @return - object which represents type of @param object;
     */
    DataType getDataType(Object object);

    /**
     * @param dataType -
     */
    void registerDataType(DataType dataType);

    /**
     * @return - factory to construct objectReader
     */
    ObjectReaderFactory getObjectReaderFactory();

    /**
     * @return - factory to construct objectWriter
     */
    ObjectWriterFactory getObjectWriterFactory();
}

