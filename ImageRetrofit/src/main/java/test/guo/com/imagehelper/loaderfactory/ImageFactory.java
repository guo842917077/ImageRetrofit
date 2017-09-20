package test.guo.com.imagehelper.loaderfactory;

import test.guo.com.imagehelper.options.LoaderOptions;

/**
 * Created by guo on 2017/9/8.
 */

public interface ImageFactory {
    /**
     * load image。call this method in LoadEngine
     * @param options
     */
    void load(LoaderOptions options);

    void clearMemory();

}
