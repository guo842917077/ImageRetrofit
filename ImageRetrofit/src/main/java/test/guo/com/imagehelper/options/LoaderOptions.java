package test.guo.com.imagehelper.options;

import android.view.View;

import java.lang.reflect.Type;

import test.guo.com.imagehelper.paramannotation.ImageAs;
import test.guo.com.imagehelper.paramannotation.ImageType;
import test.guo.com.imagehelper.paramannotation.TargetLoader;

/**
 * Created by Administrator on 2017/9/8.
 * 配置ImageLoader需要用到的参数  站位图
 */

public class LoaderOptions {
    private TargetLoader mTargetLoader = TargetLoader.GLIDE;
    private Object mSource;
    private View mTargetView;
    private ImageType mImageType = ImageType.DRAWABLE;
    private Object mPlaceHolder;//占位符
    private int mErrorResource;//错误图片
    private int hashCode;
    private Type[] mParamsType;

    public Type[] getParamsType() {
        return mParamsType;
    }

    public void setParamsType(Type[] mParamsType) {
        this.mParamsType = mParamsType;
    }



    public int getErrorResource() {
        return mErrorResource;
    }

    public void setErrorResource(int errorResource) {
        this.mErrorResource = errorResource;
    }


    public Object getPlaceHolder() {
        return mPlaceHolder;
    }

    public void setPlaceHolder(Object mPlaceHolder) {
        this.mPlaceHolder = mPlaceHolder;
    }


    public ImageType getImageType() {
        return mImageType;
    }

    public void setImageType(ImageType mImageType) {
        this.mImageType = mImageType;
    }


    public View getTargetView() {
        return mTargetView;
    }

    public void setTargetView(View mTargetView) {
        this.mTargetView = mTargetView;
    }

    public LoaderOptions() {

    }


    public LoaderOptions(TargetLoader mTargetLoader, Object mSource) {
        this.mTargetLoader = mTargetLoader;
        this.mSource = mSource;
    }

    public TargetLoader getTargetLoader() {
        return mTargetLoader;
    }

    public void setTargetLoader(TargetLoader mTargetLoader) {
        this.mTargetLoader = mTargetLoader;
    }

    public Object getSource() {
        return mSource;
    }

    public void setSource(Object mSource) {
        this.mSource = mSource;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {

        }
        return hashCode;
    }
}
