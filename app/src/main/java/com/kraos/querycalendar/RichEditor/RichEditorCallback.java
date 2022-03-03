package com.kraos.querycalendar.RichEditor;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.kraos.querycalendar.entity.ActionType;

/**
 * Description:
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/12/18 10:19
 */
public abstract class RichEditorCallback {
    private static final String TAG = "RichEditorCallback";

    private Gson gson = new Gson();
    private FontStyle mFontStyle = new FontStyle();
    private OnGetHtmlListener onGetHtmlListener;
    private String html;

    @JavascriptInterface
    public void returnHtml(String html) {
        this.html = html;
        if (onGetHtmlListener != null) {
            onGetHtmlListener.getHtml(html);
        }
    }

    @JavascriptInterface
    public void updateCurrentStyle(String currentStyle) {
        FontStyle fontStyle = gson.fromJson(currentStyle, FontStyle.class);
        if (fontStyle != null) {
            updateStyle(fontStyle);
        }
    }

    private void updateStyle(FontStyle fontStyle){
        Log.d(TAG, "updateStyle:getTextColor(): " +fontStyle.getTextColor());
        Log.d(TAG, "updateStyle:MgetTextColor(): " +mFontStyle.getTextColor());

        if (mFontStyle.getFontSize() != fontStyle.getFontSize()) {
            notifyFontStyleChange(ActionType.SIZE, String.valueOf(fontStyle.getFontSize()));
        }

        if (mFontStyle.getTextColor() == null || !mFontStyle.getTextColor()
                .equals(fontStyle.getTextColor())) {
            if (!TextUtils.isEmpty(fontStyle.getTextColor())) {
                notifyFontStyleChange(ActionType.COLOR, fontStyle.getTextColor());
            }
        }

        if (mFontStyle.isBold() != fontStyle.isBold()) {
            notifyFontStyleChange(ActionType.BOLD, String.valueOf(fontStyle.isBold()));
        }

        if (mFontStyle.isUnorderList() != fontStyle.isUnorderList()) {
            notifyFontStyleChange(ActionType.UNORDERED_LIST, String.valueOf(fontStyle.isUnorderList()));
        }

        if (mFontStyle.isTextAlignCenter() != fontStyle.isTextAlignCenter()) {
            notifyFontStyleChange(ActionType.JUSTIFY_CENTER, String.valueOf(fontStyle.isTextAlignCenter()));
        }

//        if (mFontStyle.isUnderline() != fontStyle.isUnderline()) {
//            notifyFontStyleChange(ActionType.UNDERLINE, String.valueOf(fontStyle.isUnderline()));
//        }

        if (mFontStyle.isFontBlock() != fontStyle.isFontBlock()) {
            notifyFontStyleChange(ActionType.BLOCK_QUOTE, String.valueOf(fontStyle.isFontBlock()));
        }

        mFontStyle = fontStyle;
    }

    /**
     * 菜单栏状态改变
     * @param type
     * @param value
     */
    public abstract void notifyFontStyleChange(ActionType type, String value);

    public interface OnGetHtmlListener {
        void getHtml(String html);
    }

    public OnGetHtmlListener getOnGetHtmlListener() {
        return onGetHtmlListener;
    }

    public void setOnGetHtmlListener(OnGetHtmlListener onGetHtmlListener) {
        this.onGetHtmlListener = onGetHtmlListener;
    }
}
