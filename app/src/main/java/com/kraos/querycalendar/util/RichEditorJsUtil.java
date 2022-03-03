package com.kraos.querycalendar.util;

import android.os.Build;
import android.webkit.WebView;

/**
 * Description: 加载对应js脚本的工具类
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/12/17 10:53
 */
public class RichEditorJsUtil {

    public static void fontSize(WebView mWebView,int fontSize){
        load(mWebView,"javascript:fontSize(" + fontSize + ")");
    }

    public static void color(WebView mWebView,String color){
        load(mWebView,"javascript:foreColor('" + color + "')");
    }

    public static void bold(WebView mWebView){
        load(mWebView,"javascript:bold()");
    }

    public static void unorderedList(WebView mWebView){
        load(mWebView,"javascript:insertUnorderedList()");
    }

    public static void justifyCenter(WebView mWebView){
        load(mWebView,"javascript:justifyCenter()");
    }

    public static void justifyLeft(WebView mWebView){
        load(mWebView,"javascript:justifyLeft()");
    }

    public static void insertHtml(WebView mWebView,String htmlContent) {
        load(mWebView,"javascript:pasteHTML('" + htmlContent + "')");
    }

    public static void formatBlockquote(WebView mWebView) {
        load(mWebView,"javascript:formatBlock('blockquote')");
        load(mWebView,"javascript:backColor('rgb(245,246,250)')");
    }

    public static void formatBlockPara(WebView mWebView) {
        load(mWebView,"javascript:formatPara('p')");
        load(mWebView,"javascript:backColor('rgb(250,250,250)')");
    }

    public static void refreshHtml(WebView mWebView) {
        load(mWebView,"javascript:refreshHTML()");
    }

    public static void underline(WebView mWebView){
        load(mWebView,"javascript:underline()");
    }

    private static void load(WebView mWebView, String trigger) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(trigger, null);
        } else {
            mWebView.loadUrl(trigger);
        }
    }
}
