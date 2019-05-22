package com.project.keyboard;

import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class StaticHelper {
    public static InputMethodManager getMyKeyboardManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) MyInputMethodService.getAppContext()
                .getSystemService(INPUT_METHOD_SERVICE);
        return inputMethodManager;
    }
}
