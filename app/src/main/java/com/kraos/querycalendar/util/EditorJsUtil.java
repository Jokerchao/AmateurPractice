package com.kraos.querycalendar.util;

import android.os.Build;
import android.webkit.WebView;

/**
 * Description: 加载对应js脚本的工具类
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/12/17 10:53
 */
public class EditorJsUtil {

    private WebView mWebView;

    public EditorJsUtil(WebView mWebView) {
        this.mWebView = mWebView;
    }

    public void fontSize(WebView mWebView,int fontSize){
        load("javascript:fontSize(" + fontSize + ")");
    }

    public void color(WebView mWebView,String color){
        load("javascript:foreColor('" + color + "')");
    }

    public void bold(WebView mWebView){
        load("javascript:bold()");
    }

    public void unorderedList(WebView mWebView){
        load("javascript:insertUnorderedList()");
    }

    public void justifyCenter(WebView mWebView){
        load("javascript:justifyCenter()");
    }

    public void justifyLeft(WebView mWebView) {
        load("javascript:justifyLeft()");
    }

    public void insertHtml(WebView mWebView,String htmlContent) {
        load("javascript:pasteHTML('" + htmlContent + "')");
    }

    public void underline(WebView mWebView){
        load("javascript:underline()");
    }

    private void load(String trigger) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.mWebView.evaluateJavascript(trigger, null);
        } else {
            this.mWebView.loadUrl(trigger);
        }
    }
}
