package com.adventofcode.flashk.day25;

import com.adventofcode.flashk.common.Array2DUtil;
import org.apache.commons.lang3.StringUtils;

public class Lock {

    private int[] pins = new int[5];

    public Lock(char[][] lockMap) {

        char[][] trasposedMap = Array2DUtil.transpose(lockMap);

        for(int i = 0; i < trasposedMap.length; i++) {
            String rowValue = new String(trasposedMap[i]);
            pins[i] = StringUtils.countMatches(rowValue, "#")-1;
        }
    }

    public boolean canFit(Key key) {

        boolean fit = true;
        int[] heights = key.getHeights();
        int i = 0;

        while(fit && i < 5) {
            int spaceLeft = 5 - pins[i];
            if(spaceLeft < heights[i]){
                fit = false;
            }
            i++;
        }

        return fit;
    }
}
