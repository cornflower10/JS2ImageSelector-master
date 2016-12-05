package com.yuyh.imgsel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

public class JSObject {
	private JsCallAndroid jsCallAndroid;
	/*
     * 绑定的object对象
     * */
	public JSObject(JsCallAndroid jsCallAndroid){
		this.jsCallAndroid = jsCallAndroid;
	}

	/*
     * JS调用android的方法
     * @JavascriptInterface仍然必不可少
     *
     * */
	@JavascriptInterface
	public void  JsCallAndroid(){
//		Toast.makeText(context, "JsCallAndroid", Toast.LENGTH_SHORT).show();
		jsCallAndroid.call();
	}

	interface  JsCallAndroid{
		void call();
	}
}
