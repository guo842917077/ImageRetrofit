package test.guo.com.imagehelper.analyze;

import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import test.guo.com.imagehelper.options.LoaderOptions;
import test.guo.com.imagehelper.paramannotation.ErrorResource;
import test.guo.com.imagehelper.paramannotation.ImageAs;
import test.guo.com.imagehelper.paramannotation.LoadType;
import test.guo.com.imagehelper.paramannotation.Path;
import test.guo.com.imagehelper.paramannotation.PlaceHolder;
import test.guo.com.imagehelper.paramannotation.TargetView;
import test.guo.com.imagehelper.paramshandler.OptionsHandler;

/**
 * Created by guo on 2017/9/8.
 * 解析Loader的注解，转换成对应的LoaderOptions
 */

public class AnalyzeLoader {
    private LoaderOptions mOptions;
    private OptionsHandler[] mOptionHandlers;
    private int mParameterAnnotationsLength;//parameter annotation count

    /***
     * analyze the annotations
     *
     * @param method
     */
    public void analyzeLoader(Method method) {
        synchronized (AnalyzeLoader.class) {
            mOptions = new LoaderOptions();
            /**
             * parameterAnnotationsArray[1]：表示注解
             * parameterAnnotationsArray[0][0] 表示代理对象
             */
            final Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();
            final Type[] parameterTypes = method.getGenericParameterTypes();
            //参数的类型 debug
            for (int i = 0; i < parameterTypes.length; i++) {
                Log.d("tag", "type class : " + parameterTypes[i]);
            }
            mParameterAnnotationsLength = parameterAnnotationsArray.length;
            //方法注解
            Annotation[] methodAnnotations = method.getAnnotations();
            mOptionHandlers = new OptionsHandler[mParameterAnnotationsLength];
            for (int i = 0; i < methodAnnotations.length; i++) {
                Annotation annotation = methodAnnotations[i];
                if (annotation instanceof LoadType) {
                    LoadType type = (LoadType) methodAnnotations[i];
                    mOptions.setTargetLoader(type.loader());
                } else if (annotation instanceof ImageAs) {
                    //图片加载的方式
                    ImageAs imageAs = (ImageAs) methodAnnotations[i];
                    mOptions.setImageType(imageAs.value());
                }
            }
            //解析所有参数注解
            for (int p = 0; p < mParameterAnnotationsLength; p++) {
                Type parameterType = parameterTypes[p];
                Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
                /**
                 * 将OptionHandler按照顺序保存在数组中
                 */
                mOptionHandlers[p] = parseParameter(p, parameterType, parameterAnnotations);
            }
            //拿到所有参数的类型
            mOptions.setParamsType(parameterTypes);
        }
    }

    /**
     * @param args
     * @return
     */
    public LoaderOptions initLoaderOptions(Object... args) {
        for (int p = 0; p < mParameterAnnotationsLength; p++) {
            mOptionHandlers[p].apply(mOptions, args[p]);
        }
        return mOptions;
    }

    /**
     * 解析参数上的注解
     *
     * @param p             索引
     * @param parameterType 参数类型
     * @param annotations   参数注解
     * @return
     */
    private OptionsHandler<?> parseParameter(
            int p, Type parameterType, Annotation[] annotations) {
        OptionsHandler<?> result = null;
        for (Annotation annotation : annotations) {
            OptionsHandler<?> annotationAction = parseParameterAnnotationToOptionHandler(
                    p, parameterType, annotation);
            if (annotationAction == null) {
                continue;
            }
            result = annotationAction;
        }

        return result;
    }

    /**
     * 将每一个注解保存成一个OptionHandler，
     * 顺序是方法参数中注解声明的顺序，这个顺序同样是参数的顺序
     *
     * @param p
     * @param type
     * @param annotation
     * @return
     */
    private OptionsHandler<?> parseParameterAnnotationToOptionHandler(
            int p, Type type, Annotation annotation) {
        if (annotation instanceof TargetView) {
            return new OptionsHandler.TargetViewAnalyze<View>();
        } else if (annotation instanceof Path) {
            return new OptionsHandler.PathTypeAnalyze<Object>();
        } else if (annotation instanceof ErrorResource) {
            return new OptionsHandler.ErrorImage<Integer>();
        } else if (annotation instanceof PlaceHolder) {
            return new OptionsHandler.PlaceHolderImage<Object>();
        }
        // TODO: 2017/9/8  listener,CacheStrategy,thumbnail,con
        return null;
    }
}
