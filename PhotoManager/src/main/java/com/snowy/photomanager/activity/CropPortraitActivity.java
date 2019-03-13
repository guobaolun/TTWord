package com.snowy.photomanager.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.snowy.common.activity.BaseActivity;
import com.snowy.photomanager.R;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;

import java.io.File;

public class CropPortraitActivity extends BaseActivity implements View.OnClickListener {


    public static final String IMAGE_PATH = "imagePath";


    private UCropView mUCropView;
    private GestureCropImageView mGestureCropImageView;
    private OverlayView mOverlayView;


    private Bitmap.CompressFormat mCompressFormat;
    private final static int mCompressQuality = 90;

    String savePath; // 存放路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savePath = getApplicationContext().getFilesDir().getAbsolutePath();
        String imagePath = getIntent().getStringExtra(IMAGE_PATH);
        setImageData(imagePath);
    }

    @Override
    protected int onContentView() {
        return R.layout.activity_crop_portrait;
    }

    @Override
    protected void initView() {
        ImageButton backIb = findViewById(R.id.back_ib);
        backIb.setOnClickListener(this);

        Button okBt = findViewById(R.id.ok_bt);
        okBt.setOnClickListener(this);


        mUCropView = findViewById(com.yalantis.ucrop.R.id.ucrop);
        mGestureCropImageView = mUCropView.getCropImageView();
        mOverlayView = mUCropView.getOverlayView();

        mGestureCropImageView.setTransformImageListener(mImageListener);

        //TODO
//        mGestureCropImageView.setOnBitmapLoadListener(new MyOnBitmapLoadListener());

    }


    private void setImageData(String path) {

        String destinationFileName = savePath + "/IMG_" + System.currentTimeMillis() + "." + path.substring(path.lastIndexOf(".") + 1);
//        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME+"."+path.substring(path.lastIndexOf(".") + 1);
        System.out.println("============= "+destinationFileName);
        Uri inputUri = Uri.fromFile(new File(path));
        Uri outputUri = Uri.fromFile(new File(destinationFileName));

        processOptions(path);

        try {
            mGestureCropImageView.setImageUri(inputUri, outputUri);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    private void processOptions(String path) {

        if (path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".jpeg")) {
            mCompressFormat = Bitmap.CompressFormat.JPEG;
        } else {
            mCompressFormat = Bitmap.CompressFormat.PNG;
        }

        // setMaxBitmapSize()           //设置裁剪图片的最大尺寸
        mGestureCropImageView.setMaxBitmapSize(0);
        //setMaxScaleMultiplier         //设置最大缩放比例
        mGestureCropImageView.setMaxScaleMultiplier(10);
        //setImageToCropBoundsAnimDuration  //设置图片在切换比例时的动画
        mGestureCropImageView.setImageToWrapCropBoundsAnimDuration(500);
        //setFreeStyleCropEnabled       //设置自由缩放裁剪模式
        mOverlayView.setFreestyleCropMode(OverlayView.FREESTYLE_CROP_MODE_DISABLE);
        //setDimmedLayerColor           //设置暗图层颜色
        mOverlayView.setDimmedColor(ContextCompat.getColor(getApplicationContext(), R.color.crop_color_dimmed));
        //setCircleDimmedLayer          //设置圆圈暗层
        mOverlayView.setCircleDimmedLayer(true);
        //setShowCropFrame              //设置是否展示矩形裁剪框
        mOverlayView.setShowCropFrame(true);
        //setCropFrameColor             //设置裁剪框颜色
        mOverlayView.setCropFrameColor(-1);
        //setCropFrameStrokeWidth       //设置裁剪框线宽度
        mOverlayView.setCropFrameStrokeWidth(2);
        //setShowCropGrid               //设置显示裁剪栅格
        mOverlayView.setShowCropGrid(false);
        //setCropGridRowCount           //设置横线的数量
        mOverlayView.setCropGridRowCount(2);
        //setCropGridColumnCount        //设置竖线的数量
        mOverlayView.setCropGridColumnCount(2);
        //setCropGridColor              //设置裁剪框横竖线的颜色
        mOverlayView.setCropGridColor(ContextCompat.getColor(getApplicationContext(), R.color.crop_color_grid));
        //setCropGridStrokeWidth        //设置裁剪框横竖线的宽度
        mOverlayView.setCropGridStrokeWidth(2);
        //useSourceImageAspectRatio     //使用源图像的纵横比
        mGestureCropImageView.setTargetAspectRatio(1f);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_ib) {
            finish();

        } else if (id == R.id.ok_bt) {
            cropAndSaveImage();

        }
    }


//    class MyOnBitmapLoadListener implements OnBitmapLoadListener {
//
//        @Override
//        public void onBitmapLoaded(@NonNull final Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String imageInputPath, @Nullable String imageOutputPath) {
//            final float width = bitmap.getWidth();
//            final float height = bitmap.getHeight();
//
//            ViewTreeObserver vto2 = mGestureCropImageView.getViewTreeObserver();
//            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    float imageViewHeight = mGestureCropImageView.getHeight();
//                    float imageViewWidth = mGestureCropImageView.getWidth();
//                    if (width / imageViewWidth < height / imageViewHeight) {
//                        mGestureCropImageView.zoomInImage(imageViewHeight / height);
//                    } else {
//                        mGestureCropImageView.zoomInImage(imageViewWidth / width);
//                    }
//                }
//            });
//
//
//        }
//
//        @Override
//        public void onFailure(@NonNull Exception bitmapWorkerException) {
//
//        }
//    }


    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
        @Override
        public void onRotate(float currentAngle) {
//            setAngleText(currentAngle);
        }

        @Override
        public void onScale(float currentScale) {
//            setScaleText(currentScale);
        }

        @Override
        public void onLoadComplete() {
            mUCropView.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
//            mBlockingView.setClickable(false);
//            mShowLoader = false;
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onLoadFailure(@NonNull Exception e) {
//            setResultError(e);
            finish();
        }

    };


    protected void cropAndSaveImage() {
        supportInvalidateOptionsMenu();

        mGestureCropImageView.cropAndSaveImage(mCompressFormat, mCompressQuality, new BitmapCropCallback() {

            @Override
            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
//                Toast.makeText(getApplicationContext(),resultUri.getPath(),Toast.LENGTH_SHORT).show();

//                Uri compressImageUri = Uri.fromFile(new File(resultUri.getPath()));
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(compressImageUri);
//                sendBroadcast(intent);
                Intent intent = new Intent();
                intent.putExtra(IMAGE_PATH, resultUri.getPath());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onCropFailure(@NonNull Throwable t) {
//                setResultError(t);
                Toast.makeText(getApplicationContext(), "裁剪失败", Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }


    //setCompressionFormat          //压缩格式
    //setCompressionQuality         //设置压缩质量
    //setAllowedGestures            //设置允许的手势
    //setOvalDimmedLayer            //设置裁剪窗口是否为椭圆
    //setCircleDimmedLayer          //设置圆圈暗层
    //setMaxScaleMultiplier         //设置最大缩放比例
    //setImageToCropBoundsAnimDuration  //设置图片在切换比例时的动画
    // setMaxBitmapSize()           //设置裁剪图片的最大尺寸
    //setDimmedLayerColor           //设置暗图层颜色
    //setShowCropFrame              //设置是否展示矩形裁剪框
    //setCropFrameColor             //设置裁剪框颜色
    //setCropFrameStrokeWidth       //设置裁剪框宽度
    //setShowCropGrid               //设置显示裁剪栅格
    //setCropGridRowCount           //设置横线的数量
    //setCropGridColumnCount        //设置竖线的数量
    //setCropGridColor              //设置裁剪框横竖线的颜色
    //setCropGridStrokeWidth        //设置裁剪框横竖线的宽度
    //setToolbarColor               //设置工具栏的颜色
    //setStatusBarColor             //设置状态栏颜色
    //setActiveWidgetColor          //设置活动小部件颜色
    //setToolbarWidgetColor         //设置工具栏小部件颜色
    //setToolbarTitle               //设置工具栏标题
    //setToolbarCancelDrawable      //取消设置工具栏CancelDrawable
    //setToolbarCropDrawable        //设置工具栏CropDrawable
    //setLogoColor                  //设置标志颜色
    //setHideBottomControls         //设置隐藏底部控件
    //setFreeStyleCropEnabled       //设置自由缩放裁剪模式
    //setAspectRatioOptions         //设置宽高比选项
    //setRootViewBackgroundColor    //设置根视图背景颜色
    //setRootViewBackgroundColor    //设置根视图背景颜色
    //withAspectRatio               //动态的设置图片的宽高比
    //useSourceImageAspectRatio     //使用源图像的纵横比
    //withMaxResultSize             //最大结果尺寸
}
