package com.fast.core.fast_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.fast.core.fast_core.app.Fast;
import com.fast.core.fast_core.net.callback.IDownloadIncomplete;
import com.fast.core.fast_core.net.callback.IDownloading;
import com.fast.core.fast_core.net.callback.IRequest;
import com.fast.core.fast_core.net.callback.ISuccess;
import com.fast.core.fast_core.utils.file.FileUtil;
import com.fast.core.fast_core.utils.log.FastLogger;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by apple on 2017/4/2
 */

final class SaveFileTask extends AsyncTask<Object, Integer, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IDownloading DOWN_LOADING;
    IDownloadIncomplete DOWNLOAD_INCOMPLETE;
    private long mFileLength;

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS, IDownloading DOWN_LOADING, IDownloadIncomplete DOWNLOAD_INCOMPLETE) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.DOWN_LOADING = DOWN_LOADING;
        this.DOWNLOAD_INCOMPLETE = DOWNLOAD_INCOMPLETE;
    }


    @Override
    protected void onProgressUpdate(Integer... progeress) {//主线程，更新UI
        super.onProgressUpdate(progeress);
//        在这里加载一个progressbar，显示进度
        if (DOWN_LOADING != null) {
            DOWN_LOADING.onDownloading(progeress[0]);
        }
    }

    @Override
    protected File doInBackground(Object... params) {//子线程，请求数据，写入本地数据
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();

        mFileLength = body.contentLength();

        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "apk";
        }

        FileUtil.ProgressCallBack progressCallBack = new FileUtil.ProgressCallBack() {

            @Override
            public void onProgressCallBack(int progress) {
                publishProgress(progress);

            }
        };
        if (name == null) {
            return FileUtil.writeToDisk(body, is, downloadDir, extension.toUpperCase(), extension, progressCallBack);
        } else {
            return FileUtil.writeToDisk(body, is, downloadDir, name, progressCallBack);
        }

    }

    @Override
    protected void onPostExecute(File file) {//主线程。更新UI
        super.onPostExecute(file);
        //        中途网断了，会使得文件下载不完全
        if (file.length() < mFileLength) {
            FastLogger.e("SaveFileTask", "可能是中途网断了，使得文件下载不完全");
            if (DOWNLOAD_INCOMPLETE != null) {
                DOWNLOAD_INCOMPLETE.onDownloadIncomplete();
            }
        } else {
            if (SUCCESS != null) {
                SUCCESS.onSuccess(file.getPath());
            }
            if (REQUEST != null) {
                REQUEST.onRequestEnd();
            }

            autoInstallApk(file);
        }
    }

    private void autoInstallApk(File file) {
        //方法有问题，并没有把extension加上，所以用的时候，要直接在name后面加上.apk，不然的话下面的判断验证不过去
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            //必须添加这句话，因为后面使用的时候getApplicationContext.startActivity
            //如果是Activity.startActivity的话可以不添加FLAG_ACTIVITY_NEW_TASK
            //使用getApplicationContext.startActivity开启Activity有一个好处是，
            //即使是现在这个Activity在后台了，也会执行打开另一个activity的效果
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Fast.getApplicationContext().startActivity(install);
        }
    }


}
