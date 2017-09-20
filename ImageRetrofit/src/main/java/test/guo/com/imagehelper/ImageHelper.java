package test.guo.com.imagehelper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import test.guo.com.imagehelper.analyze.AnalyzeLoader;
import test.guo.com.imagehelper.loaderfactory.ImageFactory;
import test.guo.com.imagehelper.loaderfactory.LoadEngine;
import test.guo.com.imagehelper.options.LoaderOptions;
import test.guo.com.imagehelper.utils.Preconditions;

/**
 * Created by guo on 2017/9/7.
 * todo 1.使用builder方式直接配置options
 */

public class ImageHelper {
    private final Map<Method, AnalyzeLoader> mLoaderCache = new LinkedHashMap<>();
    private volatile static ImageHelper mInstance;


    private ImageFactory mImageFactory;

    private ImageHelper() {

    }

    public static ImageHelper getInstance() {
        if (mInstance == null) {
            synchronized (ImageHelper.class) {
                if (mInstance == null) {
                    mInstance = new ImageHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * @param params 加载方式
     * @return
     */
    public <T> T createEngine(Activity activity, Class params) {
        return (T) create(activity, params);
    }

    public <T> T createEngine(Fragment fragment, Class params) {
        return (T) create(fragment.getActivity(), params);
    }

    public <T> T createEngine(Context context, Class params) {
        return (T) create(context, params);
    }

    public <T> T createEngine(FragmentActivity activity, Class params) {
        return (T) create(activity, params);
    }


    private <T> T create(final Context context, @NonNull Class<T> request) {
        Preconditions.validateServiceInterface(request);
        return (T) Proxy.newProxyInstance(request.getClassLoader(), new Class<?>[]{request}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 解析方法上的注解并保存成OptionsHandler
                 * 将analyzeLoader缓存起来
                 */
                AnalyzeLoader analyzeLoader = getAnalyzeLoader(method);
                /**
                 * OptionsHandler数组和args参数的顺序是一致的，将参数和options关联
                 */
                LoaderOptions options = analyzeLoader.initLoaderOptions(args);
                LoadEngine engine = createLoadEngine(context);
                /**
                 * 选择加载的方式
                 */
                engine.load(options);
                return proxy;
            }
        });
    }

    /**
     * create  load engine
     *
     * @param context
     * @return
     */
    private LoadEngine createLoadEngine(Context context) {
        return new LoadEngine(context, this);
    }

    /**
     * 从缓存中获取Loader
     *
     * @param key
     * @return
     */
    private AnalyzeLoader getAnalyzeLoader(Method key) {
        AnalyzeLoader loader;
        synchronized (ImageHelper.this) {
            loader = mLoaderCache.get(key);
            if (loader == null) {
                loader = new AnalyzeLoader();
                loader.analyzeLoader(key);
                mLoaderCache.put(key, loader);
            }
        }
        return loader;
    }

    public ImageFactory getImageEngine() {
        return mImageFactory;
    }

    public void clearMemory() {
        mImageFactory.clearMemory();
    }

    public void setLoadEngine(ImageFactory mImageFactory) {
        this.mImageFactory = mImageFactory;
    }

}
