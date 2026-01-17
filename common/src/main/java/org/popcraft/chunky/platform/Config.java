package org.popcraft.chunky.platform;

import java.nio.file.Path;

public interface Config {
    Path getDirectory();

    int getVersion();

    String getLanguage();

    boolean getContinueOnRestart();

    boolean isForceLoadExistingChunks();

    boolean isSilent();

    void setSilent(boolean silent);

    int getUpdateInterval();

    void setUpdateInterval(int updateInterval);

    int getMaxConcurrentChunks();

    void setMaxConcurrentChunks(int maxConcurrentChunks);

    int getChunkGenerationDelay();

    void setChunkGenerationDelay(int chunkGenerationDelay);

    void reload();
}
