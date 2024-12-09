package com.adventofcode.flashk.day09;

import lombok.Getter;

public class Unallocated {

    private int startBlockIndex;
    private int size;

    public Unallocated(int startBlockIndex, int size) {
        this.startBlockIndex = startBlockIndex;
        this.size = size;
    }

    public boolean canAllocate(File file) {
        return this.size >= file.getSize() && this.startBlockIndex <= file.getStartBlockIndex();
    }

    public void allocate(File file) {

        if(canAllocate(file)) {
            file.move(this.startBlockIndex);
            this.startBlockIndex += file.getSize();
            this.size -= file.getSize();
        }
    }

}

