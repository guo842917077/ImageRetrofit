package test.guo.com.imagehelper;


import android.view.View;
import test.guo.com.imagehelper.paramannotation.LoadType;
import test.guo.com.imagehelper.paramannotation.Path;
import test.guo.com.imagehelper.paramannotation.TargetLoader;
import test.guo.com.imagehelper.paramannotation.TargetView;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface ImageLoader {
    @LoadType(loader = TargetLoader.GLIDE)
    <T> void load(@Path T source, @TargetView View targetView);
}
