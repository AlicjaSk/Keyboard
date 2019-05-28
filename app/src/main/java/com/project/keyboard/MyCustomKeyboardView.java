package com.project.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.lang.reflect.Field;

public class MyCustomKeyboardView extends KeyboardView {
    public MyCustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setKeyTextSize(int size){
        try {
            Field f = this.getClass().getSuperclass().getDeclaredField("mKeyTextSize");
            f.setAccessible(true);
            f.set(this, size);
            f.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setKeyTextSize(100);
        this.invalidateAllKeys();
    }
}
