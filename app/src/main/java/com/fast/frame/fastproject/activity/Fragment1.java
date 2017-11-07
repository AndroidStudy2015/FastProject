package com.fast.frame.fastproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fast.core.fast_core.net.RestClient;
import com.fast.core.fast_core.net.callback.ISuccess;
import com.fast.frame.fastproject.R;
import com.fast.frame.fastproject.adapter.CacheInterceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apple on 2017/10/21.
 */

public class Fragment1 extends Fragment {
    private String TAG = "text";
    private TextView mTv;
    private OkHttpClient mClient;
    private String mUrl111;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        mUrl111 = "http://192.168.252.128:8080/bkdy/front/card!httptest.do";

        //缓存文件夹
        File cacheFile = new File(getContext().getExternalCacheDir().toString(), "huancun.txt");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        final Cache cache = new Cache(cacheFile, cacheSize);
        Button bt = (Button) view.findViewById(R.id.bt);
        mTv = (TextView) view.findViewById(R.id.tv);
        mClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new CacheViolenceInterceptor())
                .cache(cache)
                .build();
//        storeStringToCachesDir(getContext(), "cachesDir", "mayyyyyyyyyyyyyyy.txt");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCache2();

            }
        });


        return view;
    }
    private void testCache2() {

        RestClient.builder().url(mUrl111).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG,response);
                Log.e(TAG, "==========================================================================================");

            }
        }).build().getWithCache();

    }

    private void testCache1() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                //官方的一个示例的url
                String url = "http://publicobject.com/helloworld.txt";
                String url111 = "http://192.168.252.128:8080/bkdy/front/card!httptest.do";
                String url1 = "https://www.baikanmovie.com/front/cinema!getCinemaHotList.do?para.size=999&mechanicalCode=11111111";

                Request request = new Request.Builder()
                        .url(url111)

                        .addHeader("Cache-Control","max-age=30")
                        .build();
                Call call1 = mClient.newCall(request);
                Response response1 = null;
                try {

                    //第一次网络请求
                    response1 = call1.execute();

                    final Response finalResponse = response1;
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                mTv.setText(finalResponse.body().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
                    Log.e(TAG, "testCache: response1 :" + response1.body().string());
                    Log.e(TAG, "testCache: response1 cache :" + response1.cacheResponse());
                    Log.e(TAG, "testCache: response1 network :" + response1.networkResponse());
                    Log.e(TAG, "==========================================================================================");
                    response1.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    private void testCache() {
        //缓存文件夹
        File cacheFile = new File(getContext().getExternalCacheDir().toString(), "huancun.txt");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        final Cache cache = new Cache(cacheFile, cacheSize);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new CacheInterceptor())
                        .cache(cache)
                        .build();
                //官方的一个示例的url
                String url = "http://publicobject.com/helloworld.txt";
                String url1 = "https://www.baikanmovie.com/front/cinema!getCinemaHotList.do?para.size=999&mechanicalCode=11111111";

                Request request = new Request.Builder()
                        .url(url1)
                        .build();
                Call call1 = client.newCall(request);
                Response response1 = null;
                try {

                    //第一次网络请求
                    response1 = call1.execute();
                    Log.i(TAG, "testCache: response1 :" + response1.body().string());
                    Log.i(TAG, "testCache: response1 cache :" + response1.cacheResponse());
                    Log.i(TAG, "testCache: response1 network :" + response1.networkResponse());
                    Log.i(TAG, "==========================================================================================");
                    Log.i(TAG, "==========================================================================================");
                    Log.i(TAG, "==========================================================================================");
                    Log.i(TAG, "==========================================================================================");
                    response1.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Response response2 = null;
                Call call12 = client.newCall(request);

                try {
                    //第二次网络请求
                    response2 = call12.execute();
                    Log.i(TAG, "testCache: response2 :" + response2.body().string());
                    Log.i(TAG, "testCache: response2 cache :" + response2.cacheResponse());
                    Log.i(TAG, "testCache: response2 network :" + response2.networkResponse());
                    Log.i(TAG, "testCache: response1 equals response2:" + response2.equals(response1));
                    Log.i(TAG, "==========================================================================================");
                    Log.i(TAG, "==========================================================================================");
                    Log.i(TAG, "==========================================================================================");

                    response2.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Call call13 = client.newCall(request);

                try {
                    //第二次网络请求
                    Response response3 = call13.execute();
                    Log.i(TAG, "testCache: response3 :" + response3.body().string());
                    Log.i(TAG, "testCache: response3 cache :" + response3.cacheResponse());
                    Log.i(TAG, "testCache: response3 network :" + response3.networkResponse());
                    Log.i(TAG, "testCache: response3 equals response2:" + response3.equals(response2));
                    Log.i(TAG, "******************************************************");
                    Log.i(TAG, "******************************************************");
                    Log.i(TAG, "******************************************************");

                    response3.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * 数据存储到缓存中:data/data/包名/caches/
     *
     * @param context  上下文
     * @param content  要保存的内容Sting
     * @param fileName 保存内容的文件名称
     *                 具体代码与storeStringToFilesDir是一摸一样的，仅仅是改了个目录context.getCacheDir()
     *                 除了目录不同就是，储存在缓存里的文件，当内存不足时，会自动释放掉，
     *                 也可以点击清除缓存，来释放
     */
    public static void storeStringToCachesDir(Context context, String content, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(context, "存储数据到CachesDir成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "存储数据到CachesDir失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从私有文件夹中数读取据：data/data/包名/caches/
     *
     * @param file data/data/包名/caches/下的文件
     * @return String内容
     * 代码与readStringFromFilesDir完全一样
     */
    public static String readStringFromCachesDir(File file) {

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String content = "";
            String tmp;
            while ((tmp = br.readLine()) != null) {
                content += tmp;
            }
            br.close();
            fis.close();
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "读取缓存失败，不存在此文件，请核对文件路径、文件名";
        } catch (Exception e) {
            e.printStackTrace();
            return "读取缓存失败";
        }


    }
}
