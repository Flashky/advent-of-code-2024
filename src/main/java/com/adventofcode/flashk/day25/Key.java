package com.adventofcode.flashk.day25;

import com.adventofcode.flashk.common.Array2DUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class Key {

    @Getter
    private int[] heights = new int[5];

    public Key(char[][] keyMap) {
        char[][] trasposedMap = Array2DUtil.transpose(keyMap);

        for(int i = 0; i < trasposedMap.length; i++) {
            String rowValue = new String(trasposedMap[i]);
            heights[i] = StringUtils.countMatches(rowValue, "#")-1;
        }
    }


}
