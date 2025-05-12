package com.kraos.querycalendar.generics;

/**
 * @author kraos
 * @date 2025/5/12
 */
public interface Eater<T extends Food> {
    void eat(T food);
}
