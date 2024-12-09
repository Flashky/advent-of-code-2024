package com.adventofcode.flashk.day09;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DiskFragmenter {

    private final Deque<Integer> diskDeque = new ArrayDeque<>();

    public DiskFragmenter(List<String> inputs) {

        char[] diskMap = inputs.get(0).toCharArray();
        int fileId = 0;
        int lastBlockIndex = 0;

        for(int i = 0; i < diskMap.length; i++) {

            int blockSize = Character.getNumericValue(diskMap[i]);

            for(int blockIndex = lastBlockIndex; blockIndex < lastBlockIndex + blockSize; blockIndex++) {
                if(i % 2 == 0) {
                    diskDeque.add(fileId);
                } else {
                    diskDeque.add(-1);
                }
            }

            lastBlockIndex += blockSize;
            if(i % 2 == 0) {
                fileId++;
            }
        }

    }

    public long solve() {

        long result = 0;
        int index = 0;
        while(!diskDeque.isEmpty()) {
            int current = diskDeque.pollFirst();
            if(current != -1) {
                result += (long) index * current;
                index++;
            } else if(diskDeque.peekLast() != null){
                diskDeque.addFirst(diskDeque.pollLast());
            }
        }

        return result;

    }



}
