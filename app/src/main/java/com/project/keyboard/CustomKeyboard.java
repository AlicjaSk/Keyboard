package com.project.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.view.inputmethod.EditorInfo;

public class CustomKeyboard extends Keyboard {

    public static final int NEXT = -10;
    public static final int BACK = -11;
    boolean isSearchIME = false;

    private Key mEnterKey;
    public CustomKeyboard(Context context, int xmlLayoutResId)
    {
        super(context, xmlLayoutResId);
    }

    void setImeOptions(Resources res, int options) {
        if (mEnterKey == null) {
            return;
        }
        switch (options & (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            case EditorInfo.IME_ACTION_SEARCH:
                mEnterKey.icon = res.getDrawable(R.drawable.search);
                int [] codes = new int [] {Keyboard.KEYCODE_DONE};
                mEnterKey.codes = codes;
                isSearchIME = true;
                break;
            case EditorInfo.IME_ACTION_DONE:
                mEnterKey.icon = res.getDrawable(R.drawable.done);
                mEnterKey.codes = new int [] {Keyboard.KEYCODE_DONE};;
                isSearchIME = false;
                break;
            default:
                mEnterKey.icon = res.getDrawable(R.drawable.enter);
                mEnterKey.label = null;
                isSearchIME = false;
                break;

        }

    }
    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new Key(res, parent, x, y, parser);
        mEnterKey = key;
//        if (key.codes[0] == 10) {
//
//        } else if (key.codes[0] == ' ') {
//            mSpaceKey = key;
//        } else if (key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
//            mModeChangeKey = key;
//            mSavedModeChangeKey = new LatinKey(res, parent, x, y, parser);
//        } else if (key.codes[0] == LatinKeyboardView.KEYCODE_LANGUAGE_SWITCH) {
//            mLanguageSwitchKey = key;
//            mSavedLanguageSwitchKey = new LatinKey(res, parent, x, y, parser);
//        }
        return key;
    }

}
