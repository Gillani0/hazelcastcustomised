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

package com.hazelcast.jet.impl.executor;

/**
 * Represents abstract thread executor;
 */
public interface AbstractExecutor {
    /**
     * Name of the executor;
     *
     * @return the name of the executor
     */
    String getName();

    /**
     * Send wakeUp signal to all workers;
     */
    void wakeUp();

    /**
     * Synchronously shutdown executor;
     *
     * @throws Exception if exception during shutdown
     */
    void shutdown() throws Exception;
}
