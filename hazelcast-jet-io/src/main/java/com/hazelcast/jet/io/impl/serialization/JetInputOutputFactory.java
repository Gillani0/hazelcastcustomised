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

package com.hazelcast.jet.io.impl.serialization;

import com.hazelcast.internal.memory.MemoryManager;
import com.hazelcast.jet.io.serialization.JetDataInput;
import com.hazelcast.jet.io.serialization.JetDataOutput;
import com.hazelcast.jet.io.serialization.JetInputFactory;
import com.hazelcast.jet.io.serialization.JetOutputFactory;
import com.hazelcast.jet.io.serialization.JetSerializationService;

public class JetInputOutputFactory implements
        JetInputFactory, JetOutputFactory {
    @Override
    public JetDataInput createInput(MemoryManager memoryManager,
                                    JetSerializationService service,
                                    boolean useBigEndian) {
        return new JetObjectDataInput(memoryManager, service, useBigEndian);
    }

    @Override
    public JetDataOutput createOutput(MemoryManager memoryManager,
                                      JetSerializationService service,
                                      boolean useBigEndian) {
        return new JetObjectDataOutput(memoryManager, service, useBigEndian);
    }
}

