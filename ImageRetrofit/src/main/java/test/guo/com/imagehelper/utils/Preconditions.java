package test.guo.com.imagehelper.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.ObjectStreamException;
import java.util.Collection;

/**
 * Created guo Administrator on 2017/9/14.
 */

public class Preconditions {
    public static <T> T checkNotNull(T arg) {
        return checkNotNull(arg, "Argument must not be null");
    }

    public static <T> T checkNotNull(T arg, String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }

    public static String checkNotEmpty(String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }


    public static <T extends Collection<Y>, Y> T checkNotEmpty(T collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Must not be empty.");
        }
        return collection;
    }

    public static <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("Load method declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Load method interfaces must not extend other interfaces.");
        }
    }


    public static IllegalArgumentException ArgumentException(Object obj, Object value) {
        return new IllegalArgumentException(obj + "set a invalidate value : " + value);
    }
}
