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

package com.hazelcast.jet.dag.tap;

import com.hazelcast.core.MultiMap;
import com.hazelcast.jet.container.ContainerDescriptor;
import com.hazelcast.jet.data.DataWriter;
import com.hazelcast.jet.impl.dag.tap.sink.HazelcastMultiMapPartitionWriter;

/**
 * A sink which uses a Hazelcast {@code MultiMap} as output.
 */
public class MultiMapSink extends MapSink {

    /**
     * Constructs a sink with the given multimap name.
     *
     * @param name of the map to use as the output
     */
    public MultiMapSink(String name) {
        super(name);
    }

    /**
     * Constructs a sink with the given multimap instance.
     *
     * @param multiMap the multimap instance to be used as the output
     */
    public MultiMapSink(MultiMap multiMap) {
        super(multiMap.getName());
    }

    @Override
    protected DataWriter getPartitionWriter(ContainerDescriptor containerDescriptor, int partitionId) {
        return new HazelcastMultiMapPartitionWriter(containerDescriptor, partitionId, getName());
    }

}
