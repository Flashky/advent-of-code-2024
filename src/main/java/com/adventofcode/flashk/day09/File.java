package com.adventofcode.flashk.day09;

import lombok.Getter;


public class File {

    private final int id;
    @Getter
    private int startBlockIndex;
    private int endBlockIndex;
    @Getter
    private final int size;

    public File(int fileId, int startBlockIndex, int size) {
        this.id = fileId;
        this.startBlockIndex = startBlockIndex;
        this.endBlockIndex = startBlockIndex + size - 1;
        this.size = size;
    }

    public void move(int startBlockIndex) {
        this.startBlockIndex = startBlockIndex;
        this.endBlockIndex = startBlockIndex + size - 1;
    }

    public long checksum() {
        long result = 0;
        for(int i = startBlockIndex; i <= endBlockIndex; i++) {
            result += (long) id * i;
        }
        return result;
    }
}
