package com.fast.core.fast_core.ui.picture.one_picture_crop.entry;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by apple on 2017/11/8.
 */

public class PictureCrop {


    public static final int PIC_PICKER_REQUEST_CODE = 1000;
    public static final String PIC_PICKER_RESULT_URI = "resultUri";
    public static final int PIC_TAKE_PHOTO = 1700;

    /**
     * 在Fragment里面打开，传递Fragment.this
     * 这是应为Fragment有自己的startActivityForResult和对应的onActivityResult
     * 不然的话，会接收不到结果的
     *
     * @param fragment
     */
    public static void start(@NonNull Fragment fragment) {
        start(fragment, PIC_PICKER_REQUEST_CODE);
    }

    private static void start(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getActivity(), SinglePicturePickerActivity.class), requestCode);
    }


    /**
     * 在Activity里面打开，传递Activity.this
     * 这是应为activity有自己的startActivityForResult和对应的onActivityResult
     * 不然的话，会接收不到结果的
     *
     * @param activity
     */
    public static void start(@NonNull Activity activity) {
        start(activity, PIC_PICKER_REQUEST_CODE);
    }

    private static void start(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, SinglePicturePickerActivity.class), requestCode);
    }

//    用法示例：

//   PictureCrop.start(Fragment1.this);//从Fragment里启动
//                       or
//   PictureCrop.start(Activit1.this);//从Activity里启动

//    处理回调结果
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PictureCrop.PIC_PICKER_REQUEST_CODE) {
//            //   得到裁剪后的图片的uri
//            Uri uri = Uri.parse(data.getStringExtra(PictureCrop.PIC_PICKER_RESULT_URI));
//            //   显示照片
//            Glide.with(getContext()).load(uri).into(mImageView);
//            //   上传服务器
//            // TODO: 2017/11/8 ............
//        }
////        裁剪失败的情况在PicturePickerActivity里处理了，这里不用管了
//    }


}
