package test.guo.com.imagehelper.paramshandler;

import android.util.Log;
import android.view.View;

import java.io.File;

import test.guo.com.imagehelper.options.LoaderOptions;
import test.guo.com.imagehelper.paramannotation.ImageType;
import test.guo.com.imagehelper.paramannotation.TargetLoader;
import test.guo.com.imagehelper.utils.Preconditions;

/**
 * Created by guo on 2017/9/8.
 * OptionsHadnler对应的是每一个LoaderOptions的参数，它负责给options对象进行赋值
 */

public abstract class OptionsHandler<T> {
    public abstract void apply(LoaderOptions options, T value);

    public static final class TargetViewAnalyze<T> extends OptionsHandler<T> {

        @Override
        public void apply(LoaderOptions options, T value) {
            options.setTargetView((View) value);
        }
    }

    public static final class PathTypeAnalyze<T> extends OptionsHandler<T> {

        @Override
        public void apply(LoaderOptions options, T value) {
            options.setSource(value);
        }
    }


    public static final class ErrorImage<T> extends OptionsHandler<T> {

        @Override
        public void apply(LoaderOptions options, T value) {
            Log.d("TAG", "type value instance " + value.getClass());
            options.setErrorResource((Integer) value);
        }
    }

    public static final class PlaceHolderImage<T> extends OptionsHandler<T> {

        @Override
        public void apply(LoaderOptions options, T value) {
            Log.d("TAG", "type value instance " + value.getClass());
            options.setPlaceHolder(value);
        }
    }
}
