package test.guo.com.imagehelper.loaderfactory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.util.Util;

import test.guo.com.imagehelper.R;
import test.guo.com.imagehelper.options.LoaderOptions;
import test.guo.com.imagehelper.paramannotation.ImageType;
import test.guo.com.imagehelper.utils.Preconditions;

/**
 * Created by guo on 2017/9/8.
 * load image by Glide
 */

public class GlideFactory implements ImageFactory {
    private RequestManager mRequestManager;
    private Glide glide;
    private Context mContext;

    public GlideFactory(Context context) {
        mContext = context;
        mRequestManager = Glide.with(context);
        glide = Glide.get(context);
    }

    @Override
    public void load(LoaderOptions options) {
        if (options.getSource() != null) {
            DrawableTypeRequest request = mRequestManager.load(options.getSource());
            if (options.getErrorResource() != 0) {
                request.error(options.getErrorResource());
            }
            if (options.getPlaceHolder() != null)
                request.placeholder((int) options.getPlaceHolder());
            ImageType type = options.getImageType();
            if (type == ImageType.BITMAP)
                request.asBitmap().into((ImageView) options.getTargetView());
            else if (type == ImageType.GIF)
                request.asGif().into(new GlideDrawableImageViewTarget((ImageView) options.getTargetView(), 1));
            else
                request.into((ImageView) options.getTargetView());
        }
    }

    @Override
    public void clearMemory() {
        glide.clearMemory();
        glide.clearDiskCache();
    }
}
