package com.kraos.querycalendar.interfaces;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private static final String TAG = "ProxyHandler";

    private final ISubject subject;
    public ProxyHandler(ISubject subject){
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(TAG, "invoke: before");
        Object result = method.invoke(subject,args);
        Log.i(TAG, "invoke: after");
        return result;
    }
}
