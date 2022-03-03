package com.kraos.querycalendar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.RichEditor.SoftKeyBoardListener;
import com.kraos.querycalendar.entity.ActionType;
import com.kraos.querycalendar.RichEditor.RichEditorCallback;
import com.kraos.querycalendar.util.RichEditorJsUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/12/11 15:58
 */
public class RichEditorTestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RichEditorTestActivity";
    private WebView mWebView;
    private String htmlContent = "<p>Hello World</p>";
    private RichEditorCallback mRichEditorCallback;

    private LinearLayout llRichEditor;
    private LinearLayout llRichEditorFontSize;
    private LinearLayout llRichEditorFontColor;
    private FrameLayout flRichEditorFontSize;
    private FrameLayout flRichEditorFontColor;
    private FrameLayout flRichEditorBold;
    private FrameLayout flRichEditorUnorderedList;
    private FrameLayout flRichEditorJustifyCenter;
    private FrameLayout flRichEditorUnderline;
    private TextView tvRichEditorFsSmall;
    private TextView tvRichEditorFsDefault;
    private TextView tvRichEditorFsLarge;
    private ImageView ivRichEditorFontSize;
    private ImageView ivRichEditorFontColor;
    private ImageView ivRichEditorBold;
    private ImageView ivRichEditorUnorderedList;
    private ImageView ivRichEditorJustifyCenter;
    private ImageView ivRichEditorBlockQuote;
    private ImageView ivRichEditorFcBlack;
    private ImageView ivRichEditorFcBlue;
    private ImageView ivRichEditorFcRed;
    private ImageView ivRichEditorFcGreen;
    private EditText etTest;

    private List<ActionType> mActionTypeList = Arrays.asList(ActionType.SIZE,
            ActionType.COLOR, ActionType.BOLD, ActionType.UNORDERED_LIST,
            ActionType.JUSTIFY_CENTER, ActionType.BLOCK_QUOTE, ActionType.UNDERLINE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_editor_test);
        initView();
        initKeyBoardListener();
//        etTest.requestFocus();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mWebView.requestFocus();
//                    }
//                });
//            }
//        });
    }

    private void initKeyBoardListener() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                llRichEditor.setVisibility(View.VISIBLE);
            }

            @Override
            public void keyBoardHide(int height) {
                llRichEditor.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        llRichEditor = findViewById(R.id.ll_rich_editor);
        llRichEditorFontSize = findViewById(R.id.ll_rich_editor_font_size);
        llRichEditorFontColor = findViewById(R.id.ll_rich_editor_font_color);
        flRichEditorFontSize = findViewById(R.id.fl_rich_editor_size);
        flRichEditorFontColor = findViewById(R.id.fl_rich_editor_font_color);
        flRichEditorBold = findViewById(R.id.fl_rich_editor_bold);
        flRichEditorUnorderedList = findViewById(R.id.fl_rich_editor_unordered_list);
        flRichEditorJustifyCenter = findViewById(R.id.fl_rich_editor_justify_center);
        flRichEditorUnderline = findViewById(R.id.fl_rich_editor_blockquote);
        ivRichEditorFontSize = findViewById(R.id.iv_rich_editor_size);
        tvRichEditorFsSmall = findViewById(R.id.tv_rich_editor_fs_small);
        tvRichEditorFsDefault = findViewById(R.id.tv_rich_editor_fs_default);
        tvRichEditorFsLarge = findViewById(R.id.tv_rich_editor_fs_large);
        ivRichEditorFontColor = findViewById(R.id.iv_rich_editor_font_color);
        ivRichEditorBold = findViewById(R.id.iv_rich_editor_bold);
        ivRichEditorUnorderedList = findViewById(R.id.iv_rich_editor_unordered_list);
        ivRichEditorJustifyCenter = findViewById(R.id.iv_rich_editor_justify_center);
        ivRichEditorBlockQuote = findViewById(R.id.iv_rich_editor_blockquote);
        ivRichEditorFcBlack = findViewById(R.id.iv_rich_editor_fc_black);
        ivRichEditorFcBlue = findViewById(R.id.iv_rich_editor_fc_blue);
        ivRichEditorFcRed = findViewById(R.id.iv_rich_editor_fc_red);
        ivRichEditorFcGreen = findViewById(R.id.iv_rich_editor_fc_green);
        etTest = findViewById(R.id.et_test);
        mWebView = findViewById(R.id.wv_container);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new CustomWebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mRichEditorCallback = new MRichEditorCallback();
        mWebView.addJavascriptInterface(mRichEditorCallback, "MRichEditor");
        mWebView.loadUrl("file:///android_asset/richEditor.html");
        initOnClickListener();

    }

    class MRichEditorCallback extends RichEditorCallback {
        @Override
        public void notifyFontStyleChange(final ActionType type, final String value) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (type) {
                        case SIZE:
                            if (value != null) {
                                if(value.equals("15")||value.equals("17")||value.equals("23")){
                                    enableFsMenu(value);
                                }else{
                                    RichEditorJsUtil.fontSize(mWebView,17);
                                }
                            }
                            break;
                        case COLOR:
                            if (value.equals("rgb(0, 0, 0)")) {
                                enableFcBlack(true);
                            }
                            if (value.equals("rgb(0, 0, 255)")) {
                                enableFcBlue(true);
                            }
                            if (value.equals("rgb(255, 0, 0)")) {
                                enableFcRed(true);
                            }
                            if (value.equals("rgb(0, 255, 0)")) {
                                enableFcGreen(true);
                            }
                            break;
                        case BOLD:
                            if (value != null) {
                                if (value.equals("true")) {
                                    ivRichEditorBold.setSelected(true);
                                } else {
                                    ivRichEditorBold.setSelected(false);
                                }
                            }
                            break;
                        case UNORDERED_LIST:
                            if (value != null) {
                                if (value.equals("true")) {
                                    ivRichEditorUnorderedList.setSelected(true);
                                } else {
                                    ivRichEditorUnorderedList.setSelected(false);
                                }
                            }
                            break;
                        case JUSTIFY_CENTER:
                            if (value != null) {
                                if (value.equals("true")) {
                                    ivRichEditorJustifyCenter.setSelected(true);
                                } else {
                                    ivRichEditorJustifyCenter.setSelected(false);
                                }
                            }
                            break;
                        case BLOCK_QUOTE:
                            if (value != null) {
                                if (value.equals("true")) {
                                    ivRichEditorBlockQuote.setSelected(true);
                                } else {
                                    ivRichEditorBlockQuote.setSelected(false);
                                }
                            }
                            break;
                    }
                }
            });
        }
    }

    private void initOnClickListener() {
        int[] viewIds = new int[]{R.id.iv_rich_editor_size, R.id.iv_rich_editor_font_color, R.id.iv_rich_editor_bold, R.id.iv_rich_editor_unordered_list,
                R.id.iv_rich_editor_justify_center, R.id.iv_rich_editor_blockquote, R.id.iv_rich_editor_fc_black, R.id.iv_rich_editor_fc_blue, R.id.iv_rich_editor_fc_red, R.id.iv_rich_editor_fc_green, R.id.tv_rich_editor_fs_small,
                R.id.tv_rich_editor_fs_default, R.id.tv_rich_editor_fs_large};
        for (int viewId : viewIds) {
            findViewById(viewId).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_rich_editor_size) {
//            RichEditorJsUtil.fontSize(mWebView,40);
            enableFontSizeMenu(!ivRichEditorFontSize.isSelected());
        } else if (i == R.id.iv_rich_editor_font_color) {
//            RichEditorJsUtil.color(mWebView,"blue");
            enableFontColorMenu(!ivRichEditorFontColor.isSelected());
        } else if (i == R.id.iv_rich_editor_bold) {
            RichEditorJsUtil.bold(mWebView);
            if (ivRichEditorBold.isSelected()) {
                ivRichEditorBold.setSelected(false);
            } else {
                ivRichEditorBold.setSelected(true);
            }
//            RichEditorJsUtil.refreshHtml(mWebView);
        } else if (i == R.id.iv_rich_editor_unordered_list) {
            RichEditorJsUtil.unorderedList(mWebView);
            if (ivRichEditorUnorderedList.isSelected()) {
                ivRichEditorUnorderedList.setSelected(false);
            } else {
                ivRichEditorUnorderedList.setSelected(true);
            }
        } else if (i == R.id.iv_rich_editor_justify_center) {
            if (ivRichEditorJustifyCenter.isSelected()) {
                RichEditorJsUtil.justifyLeft(mWebView);
                ivRichEditorJustifyCenter.setSelected(false);
            } else {
                RichEditorJsUtil.justifyCenter(mWebView);
                ivRichEditorJustifyCenter.setSelected(true);
            }
        } else if (i == R.id.iv_rich_editor_blockquote) {
//            RichEditorJsUtil.underline(mWebView);
            if (ivRichEditorBlockQuote.isSelected()) {
                RichEditorJsUtil.formatBlockPara(mWebView);
                ivRichEditorBlockQuote.setSelected(false);
            } else {
                RichEditorJsUtil.formatBlockquote(mWebView);
                ivRichEditorBlockQuote.setSelected(true);
            }
        } else if (i == R.id.iv_rich_editor_fc_black) {
            RichEditorJsUtil.color(mWebView, "rgb(0,0,0)");
//            enableFcBlack(true);
//            if(ivRichEditorFcBlack.isSelected()){
//                ivRichEditorFcBlack.setSelected(false);
//            }else{
//                ivRichEditorFcBlack.setSelected(true);
//            }
        } else if (i == R.id.iv_rich_editor_fc_blue) {
            RichEditorJsUtil.color(mWebView, "rgb(0,0,255)");
//            enableFcBlue(true);

        } else if (i == R.id.iv_rich_editor_fc_red) {
            RichEditorJsUtil.color(mWebView, "rgb(255,0,0)");
//            enableFcRed(true);

        } else if (i == R.id.iv_rich_editor_fc_green) {
            RichEditorJsUtil.color(mWebView, "rgb(0,255,0)");
//            enableFcGreen(true);
        } else if (i == R.id.tv_rich_editor_fs_small) {
            RichEditorJsUtil.fontSize(mWebView, 15);
        } else if (i == R.id.tv_rich_editor_fs_default) {
            RichEditorJsUtil.fontSize(mWebView, 17);
        } else if (i == R.id.tv_rich_editor_fs_large) {
            RichEditorJsUtil.fontSize(mWebView, 23);
        }
    }


    public void enableFontSizeMenu(boolean isShow) {
        ivRichEditorFontSize.setSelected(isShow);
        llRichEditorFontSize.setVisibility(isShow ? View.VISIBLE : View.GONE);
        flRichEditorFontColor.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorBold.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorUnorderedList.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorJustifyCenter.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorUnderline.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    public void enableFontColorMenu(boolean isShow) {
        ivRichEditorFontColor.setSelected(isShow);
        llRichEditorFontColor.setVisibility(isShow ? View.VISIBLE : View.GONE);
        flRichEditorFontSize.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorBold.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorUnorderedList.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorJustifyCenter.setVisibility(isShow ? View.GONE : View.VISIBLE);
        flRichEditorUnderline.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    public void enableFcBlack(boolean isSelected) {
        ivRichEditorFcBlack.setSelected(isSelected);
        ivRichEditorFcBlue.setSelected(!isSelected);
        ivRichEditorFcRed.setSelected(!isSelected);
        ivRichEditorFcGreen.setSelected(!isSelected);
    }

    public void enableFcBlue(boolean isSelected) {
        ivRichEditorFcBlue.setSelected(isSelected);
        ivRichEditorFcBlack.setSelected(!isSelected);
        ivRichEditorFcRed.setSelected(!isSelected);
        ivRichEditorFcGreen.setSelected(!isSelected);
    }

    public void enableFcRed(boolean isSelected) {
        ivRichEditorFcBlack.setSelected(!isSelected);
        ivRichEditorFcBlue.setSelected(!isSelected);
        ivRichEditorFcRed.setSelected(isSelected);
        ivRichEditorFcGreen.setSelected(!isSelected);
    }

    public void enableFcGreen(boolean isSelected) {
        ivRichEditorFcBlack.setSelected(!isSelected);
        ivRichEditorFcBlue.setSelected(!isSelected);
        ivRichEditorFcRed.setSelected(!isSelected);
        ivRichEditorFcGreen.setSelected(isSelected);
    }

    public void enableFsMenu(String fontSize) {
        tvRichEditorFsSmall.setBackgroundResource(fontSize.equals("15")?R.drawable.bg_circle_fs_menu:R.color.bg_fs_selected);
        tvRichEditorFsDefault.setBackgroundResource(fontSize.equals("17")?R.drawable.bg_circle_fs_menu:R.color.bg_fs_selected);
        tvRichEditorFsLarge.setBackgroundResource(fontSize.equals("23")?R.drawable.bg_circle_fs_menu:R.color.bg_fs_selected);
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    view.evaluateJavascript("javascript:setPlaceholder('" + "" + "')", null);
                } else {
                    view.loadUrl("javascript:setPlaceholder('" + "" + "')");
                }
            }
        }
    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, RichEditorTestActivity.class);
        context.startActivity(intent);
    }
}
