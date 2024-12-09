package com.adventofcode.flashk.day09;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DiskFragmenter {

    private static final char EMPTY = '.';
    private char[] diskMap;
    private char[] disk;
    private int lastBlockIndex = 0;
    private Deque<Integer> diskDeque = new ArrayDeque<>();

    public DiskFragmenter(List<String> inputs) {
        diskMap = inputs.get(0).toCharArray();
        disk = new char[diskMap.length*9];

        int fileId = 0;

        for(int i = 0; i < diskMap.length; i++) {
            int blockSize = Character.getNumericValue(diskMap[i]);
            for(int blockIndex = lastBlockIndex; blockIndex < lastBlockIndex + blockSize; blockIndex++) {
                if(i % 2 == 0) {
                    disk[blockIndex] = Character.forDigit(fileId, 10);
                    diskDeque.add(fileId);
                } else {
                    disk[blockIndex] = EMPTY;
                    diskDeque.add(-1);
                }
            }

            lastBlockIndex += blockSize;
            if(i % 2 == 0) {
                fileId++;
            }
        }

    }

    public long solveA() {


        long result = 0;
        int index = 0;
        while(!diskDeque.isEmpty()) {
            int current = diskDeque.pollFirst();
            if(current != -1) {
                result += index * current;
                index++;
            } else if(diskDeque.peekLast() != null){
                diskDeque.addFirst(diskDeque.pollLast());
            }
        }

        return result;

    }

    public long solveB() {

        return 0;
    }
    /*
    private long checksum() {
        long result = 0;
        int i = 0;
        while(disk[i] != EMPTY) {
            result += ((long) Character.getNumericValue(disk[i])*(long)i);
            i++;
        }

        return result;
    }

    private void moveToLeft(int blockIndex) {

        char fileId = disk[blockIndex];
        boolean emptyFound = false;
        int i = 0;
        while(!emptyFound && i < blockIndex) {

            if(disk[i] == EMPTY) {
                emptyFound = true;
                disk[blockIndex] = EMPTY;
                disk[i] = fileId;
            }

            i++;
        }
    }
    */


}
