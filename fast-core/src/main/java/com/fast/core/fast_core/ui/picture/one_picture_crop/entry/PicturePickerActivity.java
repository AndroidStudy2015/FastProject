package com.fast.core.fast_core.ui.picture.one_picture_crop.entry;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bk.ydtv.fast_core.R;
import com.fast.core.fast_core.ui.picture.one_picture_crop.adapter.PicturePickerAdapter;
import com.fast.core.fast_core.ui.picture.one_picture_crop.adapter.PopupWindowSelectDirAdapter;
import com.fast.core.fast_core.ui.picture.one_picture_crop.bean.FolderBean;
import com.fast.core.fast_core.ui.picture.one_picture_crop.utils.ComparatorUtils;
import com.fast.core.fast_core.utils.file.FileUtil;
import com.fast.core.fast_core.utils.log.FastLogger;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * 这个类里面，会看到各种为null，集合为0，file为null的判断，主要是因为不同的手机在文件系统上很奇葩，
 * 例如：你明明通过一个文件找到了父文件夹，但这个父文件夹也可能为null
 */

public class PicturePickerActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mPicPickerRecyclerView;

    private ProgressDialog mProgressDialog;

    List<FolderBean> mFolderBeanList = new ArrayList<>();

    /**
     * 图片文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs = new ArrayList<>();

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();
    /**
     * 存放所有图片文件夹的集合
     */
    private List<String> mDirPathList = new ArrayList<>();


    private PopupWindowSelectDirAdapter mPopupWindowSelectDirAdapter;

    /**
     * 存放所有图片path的集合
     */
    List<String> allImgPathList = new ArrayList<>();

    private TextView mTvSelectDir;
    private ImageView mSjxIcon;
    private RelativeLayout mRlSelectDir;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRlBottom;
    private View mPopTop;
    private View mRootview;
    private RecyclerView mRcvSelectDir;
    private PicturePickerAdapter mPicturePickerAdapter;


    private final int SHOW_ALL_IMG = 0x002;
    private final int SHOW_IMG_DIR_IMG = 0x003;
    private final int INIT_POP_VIEW = 0x004;

    private ImageView mIvBack;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {

                case SHOW_ALL_IMG: //默认显示所有图片，按时间降序排列的

                    mProgressDialog.dismiss();
                    mImgs.clear();

                    mImgs.addAll(allImgPathList);
                    mPicturePickerAdapter.notifyDataSetChanged();
                    mPicPickerRecyclerView.scrollToPosition(0);

                    break;
                case SHOW_IMG_DIR_IMG://显示所选文件夹内的照片

                    mProgressDialog.dismiss();
                    mImgs.clear();
                    List<String> newImgDir = ComparatorUtils.orderByDate(mImgDir);
                    if (newImgDir == null) {
                        return;
                    }
                    mImgs.addAll(newImgDir);

//                 ★ 不要在这里去setAdapter，会报异常no adpter,skip....
//                    解决方法：在findview之后紧接着设置layoutmanager，
//                  设置一个空adapter（空adapter指的是adapter所需数据为空，记得初始化list）
//                    在有了数据之后notifyDataSetChanged即可
                    mPicturePickerAdapter.notifyDataSetChanged();
                    mPicPickerRecyclerView.scrollToPosition(0);

                    break;

                case INIT_POP_VIEW://走到这里，说明pop所显示的文件夹数据已经准备好了，开始准备view
                    initPopupWindowView();
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_picker);


        initView();
        setClickListener();
        getImages();

    }

    private void setClickListener() {
        mRlSelectDir.setOnClickListener(this);
        mIvBack.setOnClickListener(this);


    }


    private void initView() {

        mRlBottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        mTvSelectDir = (TextView) findViewById(R.id.tv_select_dir);

        mRlSelectDir = (RelativeLayout) findViewById(R.id.rl_select_dir);
        mSjxIcon = (ImageView) findViewById(R.id.sjx);
        mIvBack = (ImageView) findViewById(R.id.iv_back);

//       高仿微信，小三角触摸变灰色
        mRlSelectDir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mSjxIcon.setImageResource(R.drawable.sjx_hei);
                        mTvSelectDir.setTextColor(getResources().getColor(R.color.selected_text_color));
                        break;
                    case MotionEvent.ACTION_UP:
                        mSjxIcon.setImageResource(R.drawable.sjx_bai);
                        mTvSelectDir.setTextColor(Color.WHITE);
                        break;

                }

                return false;
            }
        });


        mPicPickerRecyclerView = (RecyclerView) findViewById(R.id.pic_picker_recyclerview);
        mPicturePickerAdapter = new PicturePickerAdapter(PicturePickerActivity.this, mImgs);
        mPicPickerRecyclerView.setAdapter(mPicturePickerAdapter);//为了避免弄no adpater，这里先设置一个空数据的adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PicturePickerActivity.this, 3);
        mPicPickerRecyclerView.setLayoutManager(gridLayoutManager);

        mPicturePickerAdapter.setOnPicturePickerItemClickLisnter(new PicturePickerAdapter.OnPicturePickerItemClickLisnter() {


            // TODO: 2017/8/28  
            @Override
            public void onImageClick(ImageView picPickerImagerView, int position) {
                if (position == 0) {
//                    打开摄像头
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //系统常量， 启动相机的关键
                    startActivityForResult(openCameraIntent, PictureCrop.PIC_TAKE_PHOTO); // 参数常量为自定义的request code, 在取返回结果时有用

                } else {
                    //  根据Uri.fromFile(file)方法即可将path转为uri
                    Uri sourceUri = Uri.fromFile(new File(mImgs.get(position - 1)));
                    openUcrop(sourceUri);
                }
            }


        });


    }

    /**
     * 传递图片的URI给Ucrop去裁剪
     * @param sourceUri
     */
    private void openUcrop(Uri sourceUri) {
        //               创建裁剪照片之后保存的路径，也是先用path--->file--->Uri
        String saveDir = Environment.getExternalStorageDirectory()
                + "/crop";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        Uri destinationUri = Uri.fromFile(new File(saveDir, System.currentTimeMillis() + "_pic_crop.jpg"));
        UCrop.of(sourceUri, destinationUri)
//                        .withAspectRatio(16, 9)
                .withMaxResultSize(900, 900)
                .start(PicturePickerActivity.this);
    }


    private static final String TAG = "PicturePickerActivity";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        打开相机，相机拍照后
        if (resultCode == RESULT_OK && requestCode ==PictureCrop.PIC_TAKE_PHOTO) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
           File cameraPic = FileUtil.saveBitmap(bm,"/acq",100);

            //  根据Uri.fromFile(file)方法即可将path转为uri
            Uri sourceUri = Uri.fromFile(cameraPic);

            //广播刷新相册
            Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intentBc.setData(sourceUri);
            this.sendBroadcast(intentBc);

            openUcrop(sourceUri);
        }




//
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            data.putExtra(PictureCrop.PIC_PICKER_RESULT_URI, resultUri.toString());
            setResult(RESULT_OK, data);
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            FastLogger.e(TAG, cropError.toString());
            Toast.makeText(PicturePickerActivity.this, "图片裁剪失败", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PicturePickerActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED + " DESC");//按照时间降序排列

                while (mCursor.moveToNext()) {
                    // 1. 获取所有的图片的路径，放到allImgPathList集合中
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    allImgPathList.add(path);

                    //2. 找到所有含有图片的文件夹
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();

                    //利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        mDirPathList.add(dirPath);
                    }

                }
                mCursor.close();
                //hashSet置空，释放内存
                mDirPaths = null;
                // 3. 通知Handler扫描全部图片，发送allImgPathList
                mHandler.sendEmptyMessage(SHOW_ALL_IMG);

                //4. 初始化所有文件夹数据
                initPopupWindowData();
                mHandler.sendEmptyMessage(INIT_POP_VIEW);


            }
        }).start();

    }


    /**
     * 初始化PopupWindow视图
     */
    private void initPopupWindowView() {
        //设置contentView
        View contentView = LayoutInflater.from(PicturePickerActivity.this).inflate(R.layout.popuplayout, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, mPicPickerRecyclerView.getHeight(), true);
        mPopupWindow.setContentView(contentView);

        mPopTop = contentView.findViewById(R.id.ll_pop_top);
        mPopTop.setOnClickListener(this);
        //显示PopupWindow
//        mRootview = LayoutInflater.from(PicturePickerActivity.this).inflate(R.layout.activity_picture_picker, null);
        mRootview = mPicPickerRecyclerView;
        //外部是否可以点击
        mPopupWindow.setFocusable(false);//必须Focusable设置为false，setOutsideTouchable设置为false点击外部才不会消失
        // http://blog.csdn.net/qq402164452/article/details/53353798
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(false);

        //设置动画所对应的style
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);


//        目录选择RecyclerView
        mRcvSelectDir = (RecyclerView) contentView.findViewById(R.id.rcv_select_dir);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRcvSelectDir.setLayoutManager(manager);
        mPopupWindowSelectDirAdapter = new PopupWindowSelectDirAdapter(this, mFolderBeanList);
        mRcvSelectDir.setAdapter(mPopupWindowSelectDirAdapter);

        mPopupWindowSelectDirAdapter.setOnItemClickListener(new PopupWindowSelectDirAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(File mImgDir1) {
                if (mImgDir1 != null) {
                    mImgDir = mImgDir1;
                    mTvSelectDir.setText(mImgDir1.getName());
                    mHandler.sendEmptyMessage(SHOW_IMG_DIR_IMG);
                } else {//为null是人为制造的那个folderBean，他的dir为null
                    mHandler.sendEmptyMessage(SHOW_ALL_IMG);
                    mTvSelectDir.setText("所有图片");

                }
                mPopupWindow.dismiss();
            }

        });

    }


    /**
     * 准备PopupWindow列表要显示的数据
     */
    private void initPopupWindowData() {
        for (int i = 0; i < mDirPathList.size(); i++) {
            File fileDir = new File(mDirPathList.get(i));

            List<String> imgPaths = ComparatorUtils.orderByDate(fileDir);

            if (imgPaths == null || imgPaths.size() == 0) {//华为异常
                continue;
            }
            FolderBean folderBean = new FolderBean();
            folderBean.setFolderFile(fileDir);
            String folderPath = mDirPathList.get(i);
            folderBean.setFolderName(folderPath.substring(folderPath.lastIndexOf('/') + 1, folderPath.length()));
            folderBean.setImagCount(imgPaths.size());
            folderBean.setFolderFirstPic(imgPaths.get(0));
            folderBean.setFolderPath(folderPath);
            mFolderBeanList.add(folderBean);
        }
        mDirPathList = null;//置空
        ComparatorUtils.orderByImgCount(mFolderBeanList);

//        下面是要把所有的照片组成一个folderBean，但是这个folderBean没有统一的FolderFile和FolderPath，这里设置为null
        FolderBean folderBean = new FolderBean();
        folderBean.setFolderFile(null);
        folderBean.setFolderName("所有照片");
        folderBean.setImagCount(allImgPathList.size());
        folderBean.setFolderFirstPic(allImgPathList.get(0));
        folderBean.setFolderPath(null);

//        把这个FolderBean放在了集合的第一个
        mFolderBeanList.add(0, folderBean);

    }

    private void showPopupWindow() {
        mPopupWindow.showAtLocation(mRootview, Gravity.BOTTOM, 0, mRlBottom.getHeight());
    }


    @Override
    public void onClick(View v) {


        int id = v.getId();

        if (id == R.id.rl_select_dir) {

            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                showPopupWindow();
            }

        } else if (id == R.id.ll_pop_top) {
            mPopupWindow.dismiss();

        } else if (id == R.id.iv_back) {
            finish();

        }

    }


    @Override
    public void onBackPressed() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
/**
 * 4.如果是点击返回按钮，那么不更新正式的set，等下次进来这个Activity，还是把未更新的正式set赋给了临时set
 * 这一套，主要是解决了，点击返回，不处理选择的情况（与点击完成按钮不同）
 */
            super.onBackPressed();

        }
    }


}
