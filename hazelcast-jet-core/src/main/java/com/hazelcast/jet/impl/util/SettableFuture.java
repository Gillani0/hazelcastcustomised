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

package com.hazelcast.jet.impl.util;

import javax.annotation.Nullable;

/**
 * Future implementation which can be re-used
 * <pre>
 *     SettableFuture future = object.getFuture();
 *     future.get();
 *     future.reset();
 *     SettableFuture future = object.getFuture();
 *     future.get();
 * </pre>
 *
 * @param <V> - type of the result value
 */
public final class SettableFuture<V> extends AbstractFuture<V> {
    /**
     * Explicit private constructor, use the {@link #create} factory method to
     * create instances of {@code SettableFuture}.
     */
    private SettableFuture() {
    }

    /**
     * Creates a new {@code SettableFuture} in the default state.
     */
    public static <V> SettableFuture<V> create() {
        return new SettableFuture<V>();
    }

    /**
     * Sets the value of this future.  This method will return {@code true} if
     * the value was successfully set, or {@code false} if the future has already
     * been set or cancelled.
     *
     * @param value the value the future should hold.
     * @return true if the value was successfully set.
     */
    @Override
    public boolean set(@Nullable V value) {
        return super.set(value);
    }

    public void reset() {
        super.reset();
    }

    /**
     * Sets the future to having failed with the given exception. This exception
     * will be wrapped in an {@code ExecutionException} and thrown from the {@code
     * get} methods. This method will return {@code true} if the exception was
     * successfully set, or {@code false} if the future has already been set or
     * cancelled.
     *
     * @param throwable the exception the future should hold.
     * @return true if the exception was successfully set.
     */
    @Override
    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }
}
