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

package com.hazelcast.jet.impl.actor.shuffling.io;

import com.hazelcast.jet.data.io.ProducerInputStream;
import com.hazelcast.jet.io.ObjectWriter;
import com.hazelcast.jet.io.ObjectWriterFactory;
import com.hazelcast.nio.ObjectDataOutput;

import java.io.IOException;

public class SenderObjectWriter implements ObjectWriter<ProducerInputStream<Object>> {
    private final ObjectWriterFactory objectWriterFactory;

    public SenderObjectWriter(ObjectWriterFactory objectWriterFactory) {
        this.objectWriterFactory = objectWriterFactory;
    }

    @Override
    public void writeType(ProducerInputStream<Object> object,
                          ObjectDataOutput objectDataOutput,
                          ObjectWriterFactory objectWriterFactory) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void writePayLoad(ProducerInputStream<Object> object,
                             ObjectDataOutput objectDataOutput,
                             ObjectWriterFactory objectWriterFactory) throws IOException {
        write(object, objectDataOutput, objectWriterFactory);
    }

    @Override
    public void write(ProducerInputStream<Object> inputStream,
                      ObjectDataOutput objectDataOutput,
                      ObjectWriterFactory objectWriterFactory) throws IOException {
        objectDataOutput.writeInt(inputStream.size());

        for (Object object : inputStream) {
            this.objectWriterFactory.getWriter(object).write(object, objectDataOutput, objectWriterFactory);
        }
    }
}
