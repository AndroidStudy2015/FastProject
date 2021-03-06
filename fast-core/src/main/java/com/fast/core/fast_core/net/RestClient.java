package com.fast.core.fast_core.net;

import android.content.Context;

import com.fast.core.fast_core.net.callback.IDownloadIncomplete;
import com.fast.core.fast_core.net.callback.IDownloading;
import com.fast.core.fast_core.net.callback.IError;
import com.fast.core.fast_core.net.callback.IFailure;
import com.fast.core.fast_core.net.callback.IRequest;
import com.fast.core.fast_core.net.callback.ISuccess;
import com.fast.core.fast_core.net.callback.RequestCallbacks;
import com.fast.core.fast_core.net.download.DownloadHandler;
import com.fast.core.fast_core.ui.loader.FastLoader;
import com.fast.core.fast_core.ui.loader.LoaderStyle;
import com.fast.core.fast_core.utils.log.FastLogger;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by apple on 2017/9/17.
 * 进行请求的具体实现类
 */

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    private final File FILE;

    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IDownloading DOWN_LOADING;
    private final IDownloadIncomplete DOWNLOAD_INCOMPLETE;


    public RestClient(String url, Map<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      LoaderStyle fastLoaderStyle,
                      Context context,
                      File file,
                      String download_dir,
                      String extension,
                      String name,
                      IDownloading downLoading,
                      IDownloadIncomplete download_incomplete) {
        URL = url;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        PARAMS.putAll(params);
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        BODY = body;
        LOADER_STYLE = fastLoaderStyle;
        CONTEXT = context;
        FILE = file;
        DOWN_LOADING = downLoading;
        DOWNLOAD_INCOMPLETE = download_incomplete;

    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();

        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
//        这里可以判断网络不存在，停止请求，弹出吐司
//          还可以显示加载圈圈
        if (LOADER_STYLE != null) {
            FastLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case GET_WITH_CACHE:
                call = service.getWithCache(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;

            default:
                break;
        }

//        不直接用retrofit的成功、失败回调，要再次封装一层自己的回调 class RequestCallBack implements retrofit.Callback
        if (call != null) {
            call.enqueue(getRequestCallBack());
        }
        // TODO: 2017/10/19  参数清空
        PARAMS.clear();
        FastLogger.e("qwe", PARAMS.size() + "");
    }

    private Callback<String> getRequestCallBack() {

        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }
    public final void getWithCache() {
        request(HttpMethod.GET_WITH_CACHE);
    }
    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {//要使用post_raw的话，一定要是null
                throw new RuntimeException("要使用post_raw的话，PARAMS一定要是null,params must be null");
            }
            request(HttpMethod.POST_RAW);
        }

    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("要使用put_raw的话，PARAMS一定要是null,params must be null");
            }
            request(HttpMethod.PUT_RAW);

        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR, DOWN_LOADING, DOWNLOAD_INCOMPLETE)
                .handleDownload();
    }
}
/*用法示例：
private void testCache2() {

        RestClient.builder()
                .url(mUrl111)
                 .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e(TAG,response);
                        Log.e(TAG, "==========================================================================================");

                    }
                }).build().getWithCache();//带缓冲

    }*/