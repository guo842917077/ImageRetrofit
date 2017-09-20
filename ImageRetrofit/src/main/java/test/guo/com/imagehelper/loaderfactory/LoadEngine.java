package test.guo.com.imagehelper.loaderfactory;

import android.content.Context;

import test.guo.com.imagehelper.ImageHelper;
import test.guo.com.imagehelper.options.LoaderOptions;


/**
 * Created by guo on 2017/9/14.
 */

public class LoadEngine {
    private Context mContext;
    private ImageFactory mLoadFactory;
    private ImageHelper mHelper;

    public LoadEngine(Context context, ImageHelper helper) {
        this.mContext = context;
        this.mHelper = helper;
    }

    public void load(LoaderOptions option) {
        mLoadFactory = mHelper.getImageEngine();
        if (mLoadFactory == null) {
            //default load way
            mLoadFactory = new GlideFactory(mContext);
            this.mHelper.setLoadEngine(mLoadFactory);
        }
        mLoadFactory.load(option);
    }

    /**
     * chose a way to load
     */

    public ImageFactory getLoadFactory() {
        return mLoadFactory;
    }

}
