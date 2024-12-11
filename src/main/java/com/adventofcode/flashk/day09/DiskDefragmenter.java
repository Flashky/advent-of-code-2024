package com.adventofcode.flashk.day09;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DiskDefragmenter {

    private final Deque<File> files = new ArrayDeque<>();
    private final List<Unallocated> unallocateds = new ArrayList<>();

    public DiskDefragmenter(List<String> inputs){

        char[] diskMap = inputs.getFirst().toCharArray();
        int fileId = 0;
        int blockIndex = 0;

        for(int i = 0; i < diskMap.length; i++) {

            int blockSize = Character.getNumericValue(diskMap[i]);

            if(i % 2 == 0) {
                files.add(new File(fileId++, blockIndex, blockSize));
            } else {
                unallocateds.add(new Unallocated(blockIndex, blockSize));
            }

            blockIndex += blockSize;

        }

    }

    public long solve() {
        long result = 0;
        while(!files.isEmpty()) {
            File file = files.pollLast();
            result += reallocate(file);

        }
        return result;
    }

    private long reallocate(File file) {

        for(Unallocated unallocated : unallocateds) {
            if(unallocated.canAllocate(file)) {
                unallocated.allocate(file);
                break;
            }
        }

        return file.checksum();
    }
}
