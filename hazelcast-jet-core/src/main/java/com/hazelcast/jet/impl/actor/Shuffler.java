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

package com.hazelcast.jet.impl.actor;

/**
 * This is an abstract interface for each shuffler in the system
 */
public interface Shuffler {
    /**
     * Flush current buffer of Shuffler
     *
     * @return amount of bytes has been flushed
     */
    int flush();

    /**
     * Check if last buffer has been flushed
     * @return true if last data has been flushed
     *         false - if last data hasn't been flushed
     */
    boolean isFlushed();
}
