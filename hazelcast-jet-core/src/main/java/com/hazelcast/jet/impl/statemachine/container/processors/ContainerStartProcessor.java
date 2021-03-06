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

package com.hazelcast.jet.impl.statemachine.container.processors;

import com.hazelcast.jet.impl.container.ProcessingContainer;
import com.hazelcast.jet.impl.Dummy;
import com.hazelcast.jet.impl.container.ContainerPayLoadProcessor;

public class ContainerStartProcessor implements ContainerPayLoadProcessor<Dummy> {
    private final ProcessingContainer container;

    public ContainerStartProcessor(ProcessingContainer container) {
        this.container = container;
    }

    @Override
    public void process(Dummy payLoad) throws Exception {
        this.container.start();
    }
}
