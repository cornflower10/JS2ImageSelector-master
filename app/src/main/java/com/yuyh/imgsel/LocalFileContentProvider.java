package com.yuyh.imgsel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class LocalFileContentProvider extends ContentProvider {
    private static final String URI_PREFIX = "content://com.yuyh.imgsel";

    public static String getPath(String url){
        Uri uri =  Uri.parse(url);
        return uri.isAbsolute()?url:URI_PREFIX+url;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {

        File file = new File(uri.getPath());
        ParcelFileDescriptor parcel = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        return parcel;
    }

    @Override
    public AssetFileDescriptor openAssetFile (Uri uri, String mode) throws FileNotFoundException{
//        AssetManager am = getContext().getAssets();
        String path = uri.getPath().substring(1);
        Log.i("111111111111", "111111111111---->"+path);
        File file = new File(path);
        if (file.exists()) {
            Log.i("222222", "222222---->"+path);
            Uri turi = Uri.parse(URI_PREFIX+"/"+path);
            return super.openAssetFile(turi, mode);
        }
//        try {
//            AssetFileDescriptor afd = am.openFd(path);
//            return afd;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return super.openAssetFile(uri, mode);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String s, String[] as) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentvalues) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public Cursor query(Uri uri, String[] as, String s, String[] as1, String s1) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    @Override
    public int update(Uri uri, ContentValues contentvalues, String s, String[] as) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

}