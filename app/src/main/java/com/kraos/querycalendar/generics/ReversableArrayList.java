package com.kraos.querycalendar.generics;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author kraos
 * @date 2025/5/12
 */
public class ReversableArrayList<T> extends ArrayList<T> {
    public void reverse() {
        Collections.reverse(this);
    }
}
