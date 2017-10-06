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

package com.hazelcast.jet.config;


import com.hazelcast.jet.impl.util.JetUtil;

import java.io.Serializable;
import java.util.Properties;

import static com.hazelcast.util.Preconditions.checkTrue;

/**
 * Config for JET application
 */
public class JetApplicationConfig implements Serializable {
    /**
     * Default port to be used by JET for server listening
     */
    public static final int DEFAULT_PORT = 6701;

    /**
     * While binding listening port JET sorts out available ports.
     * This parameter represents  default step of the sorting out
     */
    public static final int PORT_AUTO_INCREMENT = 1;

    /**
     * Represents default connection checking interval
     */
    public static final int DEFAULT_CONNECTIONS_CHECKING_INTERVAL_MS = 100000;

    /**
     * Represents default value for timeout when socket accepted as broken
     */
    public static final int DEFAULT_CONNECTIONS_SILENCE_TIMEOUT_MS = 1000;

    /**
     * Represents default number of attempts to create localization directories
     */
    public static final int DEFAULT_APP_ATTEMPTS_COUNT = 100;

    /**
     * Represents default value for localization process chunks to be used
     */
    public static final int DEFAULT_FILE_CHUNK_SIZE_BYTES = 1024;

    /**
     * Default chunk size for data passed between JET-containers
     */
    public static final int DEFAULT_CHUNK_SIZE = 256;

    /**
     * Represents default value for any futures in system
     */
    public static final int DEFAULT_JET_SECONDS_TO_AWAIT = 1200;

    /**
     * Represents default value for TCP-buffer
     */
    public static final int DEFAULT_TCP_BUFFER_SIZE = 1024;

    /**
     * Default size for the queues used to pass data between containers
     */
    private static final int DEFAULT_QUEUE_SIZE = 65536;

    /**
     * Default packet-size to be used during transportation process
     */
    private static final int DEFAULT_SHUFFLING_BATCH_SIZE_BYTES = 256;

    /**
     * Default Input/Output threads count
     */
    private static final int DEFAULT_IO_THREADS_COUNT = 5;

    /**
     * 1 Gigabyte
     */
    private static final long DEFAULT_MAX_HEAP_SIZE_IN_BYTES = JetUtil.GIGABYTE;

    /**
     * 4 Gigabytes
     */
    private static final long DEFAULT_MAX_NATIVE_SIZE_IN_BYTES = 4L * JetUtil.GIGABYTE;

    /**
     * 128 Gigabytes
     */
    private static final long DEFAULT_MAX_DISK_SIZE_IN_BYTES = 128L * JetUtil.GIGABYTE;

    /**
     * 16 Megabytes
     */
    private static final long DEFAULT_MEMORY_BLOCK_SIZE = 16 * JetUtil.MEGABYTE;

    private final Properties properties;

    private String localizationDirectory;

    private int resourceFileChunkSize = DEFAULT_FILE_CHUNK_SIZE_BYTES;

    private int defaultApplicationDirectoryCreationAttemptsCount = DEFAULT_APP_ATTEMPTS_COUNT;

    private int jetSecondsToAwait = DEFAULT_JET_SECONDS_TO_AWAIT;

    private int containerQueueSize = DEFAULT_QUEUE_SIZE;

    private int chunkSize = DEFAULT_CHUNK_SIZE;

    private int maxProcessingThreads = -1;

    private int ioThreadCount = DEFAULT_IO_THREADS_COUNT;

    private int defaultTCPBufferSize = DEFAULT_TCP_BUFFER_SIZE;

    private int shufflingBatchSizeBytes = DEFAULT_SHUFFLING_BATCH_SIZE_BYTES;

    private long maxHeapSizeInBytes = DEFAULT_MAX_HEAP_SIZE_IN_BYTES;

    private long maxNativeSizeInBytes = DEFAULT_MAX_NATIVE_SIZE_IN_BYTES;

    private long maxDiskSizeInBytes = DEFAULT_MAX_DISK_SIZE_IN_BYTES;

    private long memoryBlockSize = DEFAULT_MEMORY_BLOCK_SIZE;

    private String name;

    public JetApplicationConfig() {
        this.name = null;
        this.properties = new Properties();
    }

    public JetApplicationConfig(String name) {
        this.name = name;
        this.properties = new Properties();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceFileChunkSize() {
        return resourceFileChunkSize;
    }

    public void setResourceFileChunkSize(byte resourceFileChunkSize) {
        this.resourceFileChunkSize = resourceFileChunkSize;
    }

    public String getLocalizationDirectory() {
        return localizationDirectory;
    }

    public void setLocalizationDirectory(String localizationDirectory) {
        this.localizationDirectory = localizationDirectory;
    }

    public int getDefaultApplicationDirectoryCreationAttemptsCount() {
        return defaultApplicationDirectoryCreationAttemptsCount;
    }

    public void setDefaultApplicationDirectoryCreationAttemptsCount(int defaultApplicationDirectoryCreationAttemptsCount) {
        this.defaultApplicationDirectoryCreationAttemptsCount = defaultApplicationDirectoryCreationAttemptsCount;
    }

    public int getJetSecondsToAwait() {
        return jetSecondsToAwait;
    }

    public void setJetSecondsToAwait(int jetSecondsToAwait) {
        this.jetSecondsToAwait = jetSecondsToAwait;
    }

    public int getContainerQueueSize() {
        return containerQueueSize;
    }

    public void setContainerQueueSize(int containerQueueSize) {
        checkTrue(Integer.bitCount(containerQueueSize) == 1, "containerQueueSize should be power of 2");
        this.containerQueueSize = containerQueueSize;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int tupleChunkSize) {
        this.chunkSize = tupleChunkSize;
    }

    public int getMaxProcessingThreads() {
        return maxProcessingThreads <= 0 ? Runtime.getRuntime().availableProcessors() : maxProcessingThreads;
    }

    public void setMaxProcessingThreads(int maxProcessingThreads) {
        this.maxProcessingThreads = maxProcessingThreads;
    }

    public int getShufflingBatchSizeBytes() {
        return shufflingBatchSizeBytes;
    }

    public void setShufflingBatchSizeBytes(int shufflingBatchSizeBytes) {
        this.shufflingBatchSizeBytes = shufflingBatchSizeBytes;
    }

    public Properties getProperties() {
        return properties;
    }

    public int getIoThreadCount() {
        return ioThreadCount;
    }

    public void setIoThreadCount(int ioThreadCount) {
        this.ioThreadCount = ioThreadCount;
    }

    public int getDefaultTCPBufferSize() {
        return defaultTCPBufferSize;
    }

    public void setDefaultTCPBufferSize(int defaultTCPBufferSize) {
        this.defaultTCPBufferSize = defaultTCPBufferSize;
    }

    public long getMaxHeapSizeInBytes() {
        return maxHeapSizeInBytes;
    }

    public void setMaxHeapSizeInBytes(long maxHeapSizeInBytes) {
        this.maxHeapSizeInBytes = maxHeapSizeInBytes;
    }

    public long getMaxNativeSizeInBytes() {
        return maxNativeSizeInBytes;
    }

    public void setMaxNativeSizeInBytes(long maxNativeSizeInBytes) {
        this.maxNativeSizeInBytes = maxNativeSizeInBytes;
    }

    public long getMaxDiskSizeInBytes() {
        return maxDiskSizeInBytes;
    }

    public void setMaxDiskSizeInBytes(long maxDiskSizeInBytes) {
        this.maxDiskSizeInBytes = maxDiskSizeInBytes;
    }
}
