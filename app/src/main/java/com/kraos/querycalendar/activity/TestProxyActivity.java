package com.kraos.querycalendar.activity;

import static java.lang.reflect.Proxy.newProxyInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.interfaces.ISubject;
import com.kraos.querycalendar.interfaces.ProxyHandler;
import com.kraos.querycalendar.interfaces.RealCall;

import java.lang.reflect.Proxy;

public class TestProxyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_proxy);

        final RealCall realCall = new RealCall();
        final ProxyHandler proxyHandler = new ProxyHandler(realCall);

        final ISubject proxySubject  = (ISubject) Proxy.newProxyInstance(
                realCall.getClass().getClassLoader(), realCall.getClass().getInterfaces(), proxyHandler);

        proxySubject.request();
        proxySubject.response("123456");
    }
}