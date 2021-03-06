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

package com.hazelcast.jet.impl.container;

import com.hazelcast.jet.impl.statemachine.StateMachineEvent;
import com.hazelcast.jet.impl.statemachine.StateMachineOutput;
import com.hazelcast.jet.impl.util.BasicCompletableFuture;

public class RequestPayLoad<SI extends StateMachineEvent, SO extends StateMachineOutput> {
    private final BasicCompletableFuture<SO> future;
    private final Object payLoad;
    private final SI event;

    public RequestPayLoad(SI event, BasicCompletableFuture<SO> future, Object payLoad) {
        this.event = event;
        this.payLoad = payLoad;
        this.future = future;
    }

    public BasicCompletableFuture<SO> getFuture() {
        return future;
    }

    public SI getEvent() {
        return event;
    }

    public Object getPayLoad() {
        return payLoad;
    }
}
