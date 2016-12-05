package com.yuyh.imgsel;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

//    @BindView(R.id.wv)
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
//        ButterKnife.bind(this);
        wv = (WebView) findViewById(R.id.wv);
        initWebview();
    }

    private void initWebview() {
        WebSettings settings = wv.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        wv.setVerticalScrollbarOverlay(true);
        wv.setWebViewClient(new MyWebViewClient());

        initView();

        /**
         * //wView.loadUrl("file:///android_asset/index.html");

         -----打开本包内asset目录下的index.html文件
         //wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");

         -----打开本地sd卡内的index.html文件
         //wView.loadUrl("http://wap.baidu.com");

         -----打开指定URL的html文件
         */
        wv.loadUrl("file:///android_asset/jstest.html");
    }


//    private class MyWebViewClient extends WebViewClient {
//
//        @Override
//        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
//            Log.d("url",url);
//            if (!(url.startsWith("http") || url.startsWith("https"))) {
//                return true;
//            }
//            if(url.contains("http://58.211.161.180:8080/alipay/orderInfo")) {
////                payPresenter.aliPay();
//                return  true;
//            }else {
//                view.loadUrl(url);
//            }
//            return true;
//
//        }
//    }
    JSObject jsobject;
    MyWebChromeClient chromeClient;
    MyWebViewClient  WVClient;
    private void initView() {
        // TODO Auto-generated method stub

        WVClient = new MyWebViewClient();
        chromeClient = new MyWebChromeClient();
        jsobject = new JSObject(new JSObject.JsCallAndroid() {
            @Override
            public void call() {
                Intent intent = new Intent(WebViewActivity.this,MainActivity.class);
                 startActivityForResult(intent,1);
            }
        });

//        webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSavePassword(false);
//
//        //支持多种分辨率，需要js网页支持
//        webSettings.setUserAgentString("mac os");
//        webSettings.setDefaultTextEncodingName("utf-8");
//
//        //显示本地js网页
//        mWebView.loadUrl(StringUrl.TEST_NET);


        wv.setWebViewClient(WVClient);
        wv.setWebChromeClient(chromeClient);

        //注意第二个参数JsTest，这个是JS网页调用Android方法的一个类似ID的东西
        wv.addJavascriptInterface(jsobject, "JsTest");

        //调用JS网页
//        callJSBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                mHandler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        //调用JS中的 函数，当然也可以不传参
//                        mWebView.loadUrl("javascript:androidCallJS('顺便传个参数给JS！')");
//                    }
//                });
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //调用JS中的 函数，当然也可以不传参
                      String path =  data.getExtras().getString("path");
                        String imagePath =   LocalFileContentProvider.getPath(path);
                        Log.d("path",imagePath);
//                        getContentResolver().openAssetFileDescriptor(Uri.parse(imagePath), AssetFileDescriptor.).;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("path",imagePath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        wv.loadUrl("javascript:androidCallJS("+jsonObject+")");
//                        wv.loadUrl("javascript:androidCallJS('+""+')");
                    }
                });
            }
        }



    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            finish();
        }
    }
    }
