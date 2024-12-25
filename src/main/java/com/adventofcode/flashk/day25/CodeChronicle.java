package com.adventofcode.flashk.day25;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeChronicle {

    private Set<Lock> locks = new HashSet<>();
    private Set<Key> keys = new HashSet<>();
    //private List<Lock> locks = new ArrayList<>();
    //private List<Key> keys = new ArrayList<>();

    public CodeChronicle(List<String> inputs) {
        // Recorre la lista cogiendo elementos de 7 en 7, los convierte a char[][] y crea un lock o una llave

        int i = 0;
        char[][] map = new char[7][5];
        boolean isLock = false;


        for(String row : inputs) {

            if(i == 0) {
                isLock = "#####".equals(row);
            }

            if(i == 7) {

                if(isLock) {
                    locks.add(new Lock(map));
                } else {
                    keys.add(new Key(map));
                }

                map = new char[7][5];
                i = 0;

            } else {
                map[i++] = row.toCharArray();
            }

        }

        if(isLock) {
            locks.add(new Lock(map));
        } else {
            keys.add(new Key(map));
        }
    }

    public long solveA() {
        long count = 0;
        for(Lock lock : locks) {
            for(Key key :keys) {
                if(lock.canFit(key)) {
                    count++;
                }
            }
        }
        return count;
    }
}
