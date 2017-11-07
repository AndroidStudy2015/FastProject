package com.fast.core.fast_core.net.download;

import android.os.AsyncTask;

import com.fast.core.fast_core.net.RestCreator;
import com.fast.core.fast_core.net.callback.IDownloadIncomplete;
import com.fast.core.fast_core.net.callback.IDownloading;
import com.fast.core.fast_core.net.callback.IError;
import com.fast.core.fast_core.net.callback.IFailure;
import com.fast.core.fast_core.net.callback.IRequest;
import com.fast.core.fast_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2017/4/2
 */

public final class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IDownloadIncomplete DOWNLOAD_INCOMPLETE;
    private final IError ERROR;
    private final IDownloading DOWN_LODING;


    public DownloadHandler(String url,
                           IRequest request,
                           String downDir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           IDownloading downLoading,
                           IDownloadIncomplete downloadIncomplete) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.DOWN_LODING=downLoading;
        this.DOWNLOAD_INCOMPLETE=downloadIncomplete;

    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator
                .getRestService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();

//                            保存文件到本地
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS,DOWN_LODING,DOWNLOAD_INCOMPLETE);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });
    }
}
