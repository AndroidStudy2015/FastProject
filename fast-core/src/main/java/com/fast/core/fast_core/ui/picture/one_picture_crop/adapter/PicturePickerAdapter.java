package com.fast.core.fast_core.ui.picture.one_picture_crop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bk.ydtv.fast_core.R;
import com.fast.core.fast_core.ui.picture.one_picture_crop.utils.ImageUtils;

import java.util.List;

/**
 * Created by apple on 2017/8/18.
 */

public class PicturePickerAdapter extends RecyclerView.Adapter<PicturePickerAdapter.FirstViewHolder> {
    private Context mContext;
    private List<String> imgs;
    OnPicturePickerItemClickLisnter onPicturePickerItemClickLisnter;

    public void setOnPicturePickerItemClickLisnter(OnPicturePickerItemClickLisnter onPicturePickerItemClickLisnter) {
        this.onPicturePickerItemClickLisnter = onPicturePickerItemClickLisnter;
    }

    public PicturePickerAdapter(Context context, List<String> imgs) {
        mContext = context;
        this.imgs = imgs;
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new FirstViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(final FirstViewHolder holder, final int position) {
//        ImageUtils.loadImage(mContext, absolutePath + "/" + imgs.get(position), holder.picPickerImagerView);

        if (position==0){
            ImageUtils.loadImage(mContext, R.drawable.back, holder.picPickerImagerView);

    }else {
        ImageUtils.loadImage(mContext, imgs.get(position-1), holder.picPickerImagerView);}
        holder.picPickerImagerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPicturePickerItemClickLisnter != null) {
                    onPicturePickerItemClickLisnter.onImageClick(holder.picPickerImagerView, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size()+1;
    }


    class FirstViewHolder extends RecyclerView.ViewHolder {


        private ImageView picPickerImagerView;

        public FirstViewHolder(View itemView) {
            super(itemView);
            picPickerImagerView = (ImageView) itemView.findViewById(R.id.pic_picker_imageview);


            int screenWidth = ImageUtils.getScreenWidth(mContext);
            ViewGroup.LayoutParams layoutParams = picPickerImagerView.getLayoutParams();
            layoutParams.width = screenWidth / 3;
            layoutParams.height = screenWidth / 3;
            picPickerImagerView.setLayoutParams(layoutParams);
        }
    }


    public interface OnPicturePickerItemClickLisnter {

        void onImageClick(ImageView picPickerImagerView, int position);

    }
}
