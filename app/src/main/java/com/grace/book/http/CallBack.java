package com.grace.book.http;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Types;
import com.grace.book.App;
import com.grace.book.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by chenxb on 2017/2/1.
 */
public abstract class CallBack<T> {
    public Type type;

    public CallBack() {
        type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(String message);

    public void showFailMsg(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showFailMsg(Context context, String message) {
        ToastUtils.showErrorToasty(context, message);
    }
}
