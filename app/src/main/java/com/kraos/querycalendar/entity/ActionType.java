package com.kraos.querycalendar.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 编辑类型
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/12/17 10:40
 */
public enum ActionType {
    SIZE(0),
    COLOR(1),
    BOLD(2),
    UNORDERED_LIST(3),
    JUSTIFY_CENTER(4),
    BLOCK_QUOTE(5),
    UNDERLINE(6);

    private int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static final Map<Integer, ActionType> actionTypeMap = new HashMap<>();

    static {
        for (ActionType actionType : values()) {
            actionTypeMap.put(actionType.getValue(), actionType);
        }
    }

}
