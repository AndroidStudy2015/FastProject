package com.fast.core.fast_core.ui.picture.select_multi_pictures.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bk.ydtv.fast_core.R;
import com.fast.core.fast_core.ui.picture.common.utils.CunZhi;
import com.fast.core.fast_core.ui.picture.one_picture_crop.entry.PictureCrop;
import com.fast.core.fast_core.ui.picture.select_multi_pictures.adapter.FirstAdapter;

import java.io.File;
import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    ArrayList<String> mSelectImagPathList = new ArrayList<>();
    public static final int OPEN_PICTURE_PICKER_ACTIVITY = 0x001;
    private FirstAdapter mFirstAdapter;
    private Button btOpenCamera;
    private File mCameraTempImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        initData();
        btOpenCamera = (Button) findViewById(R.id.bt_open_camera);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

//        以下的三步：是为了确保选择图片Activity加载大量图片，内存吃紧，会把现在的这个Activity内存回收，导致回到本Activity是重新走了onCreate方法
//        导致没有收到照片，但是由于他们选择的图片已经存在了CunZhi.mSelectPathList里了，这里可以直接加载
        mSelectImagPathList.clear();
        mSelectImagPathList.addAll(CunZhi.mSelectPathList);
        mFirstAdapter = new FirstAdapter(this, mSelectImagPathList);

        mRecyclerView.setAdapter(mFirstAdapter);
        mFirstAdapter.setOnItemClickListener(new FirstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == mSelectImagPathList.size()) {
                    Intent intent = new Intent(FirstActivity.this, PicturePickerActivity.class);
                    startActivityForResult(intent, OPEN_PICTURE_PICKER_ACTIVITY);
                } else {
                    Toast.makeText(FirstActivity.this, position + "", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 打开摄像头
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //系统常量， 启动相机的关键


                String saveDir = Environment.getExternalStorageDirectory()
                        + "/camera_temp";
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                mCameraTempImageFile = new File(saveDir, System.currentTimeMillis() + "temp_img.jpg");

                Uri imageUri = Uri.fromFile(mCameraTempImageFile);
                //指定照片保存路径（SD卡），temp_img.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, PictureCrop.PIC_TAKE_PHOTO); // 参数常量为自定义的request code, 在取返回结果时有用


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        ArrayList<String> mTempYuLanLists = intent.getStringArrayListExtra("mTempYuLanLists");
        Log.e("onResume", "onResume" + mTempYuLanLists);

        if (mTempYuLanLists != null) {
            Log.e("onResume", mTempYuLanLists.size() + "");

//            mSelectImagPathList.clear();


            mSelectImagPathList.addAll(CunZhi.mSelectPathList);
            mFirstAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //        打开相机，相机拍照后
        if (resultCode == RESULT_OK && requestCode == PictureCrop.PIC_TAKE_PHOTO) {
            CunZhi.mSelectPathList.add(mCameraTempImageFile.getAbsolutePath());
            mSelectImagPathList.clear();

            mSelectImagPathList.addAll(CunZhi.mSelectPathList);

            mFirstAdapter.notifyDataSetChanged();

//            //  根据Uri.fromFile(file)方法即可将path转为uri
            Uri resultUri = Uri.fromFile(mCameraTempImageFile);

            //广播刷新相册(注意：如果一直用的是覆盖同一个文件名的话，刷新时会没有效果，他认为这个文件我已经刷新过了)
            Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intentBc.setData(resultUri);
            this.sendBroadcast(intentBc);
        }


        if (resultCode == PicturePickerActivity.RESULT_CODE_SELECTED_PATH_LIST) {
            mSelectImagPathList.clear();


            mSelectImagPathList.addAll(CunZhi.mSelectPathList);
            mFirstAdapter.notifyDataSetChanged();
        }


        if (resultCode == PicturePickerActivity.RESULT_CODE_SELECTED_PATH_LIST_FINISH) {
            mSelectImagPathList.clear();


            mSelectImagPathList.addAll(CunZhi.mSelectPathList);
            mFirstAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        CunZhi.mSelectPathList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CunZhi.mSelectPathList.clear();
    }


}
