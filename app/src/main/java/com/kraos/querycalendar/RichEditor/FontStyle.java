package com.kraos.querycalendar.RichEditor;

import com.google.gson.annotations.SerializedName;

/**
 * Description:
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/12/18 10:41
 */
public class FontStyle {
    @SerializedName("font-size")
    private int fontSize;
    @SerializedName("font-foreColor")
    private String fontForeColor;
    @SerializedName("font-bold")
    private String fontBold;
    @SerializedName("list-style")
    private String listStyle;
    @SerializedName("text-align")
    private String textAlign;
    @SerializedName("font-underline")
    private String fontUnderline;
    @SerializedName("font-block")
    private String fontBlock;

    public int getFontSize() {
        return fontSize;
    }

    public String getTextColor() {
        return fontForeColor;
    }

    public boolean isBold() {
        return "bold".equals(fontBold);
    }

    public boolean isUnorderList() {
        return "unordered".equals(listStyle);
    }

    public boolean isTextAlignCenter() {
        return "center".equals(textAlign);
    }

    public boolean isUnderline() {
        return "underline".equals(fontUnderline);
    }

    public boolean isFontBlock() {
        return "blockquote".equals(fontBlock);
    }

}
