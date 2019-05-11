package com.project.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard firstPartKeyboard;
    private Keyboard secondPartKeyboard;
    private Keyboard thirdPartKeyboard;
    private Keyboard fourthPartKeyboard;
    private Keyboard fifthPartKeyboard;
    private Keyboard currentKeyboard;


    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        firstPartKeyboard = new Keyboard(this, R.xml.keys_layout);
        secondPartKeyboard = new Keyboard(this, R.xml.keys_layout2);
        thirdPartKeyboard = new Keyboard(this, R.xml.keys_layout3);
        fourthPartKeyboard = new Keyboard(this, R.xml.key_layout4);
        fifthPartKeyboard = new Keyboard(this, R.xml.key_layout5);
        currentKeyboard = firstPartKeyboard;
        keyboardView.setKeyboard(currentKeyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    private void setNewKeyboard(Keyboard keyboard){
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        currentKeyboard = keyboard;
        currentKeyboard.setShifted(caps);
        keyboardView.invalidateAllKeys();
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch(primaryCode) {
                case Keyboard.KEYCODE_DELETE :
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    currentKeyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case -10:
                    setNewKeyboard(secondPartKeyboard);
                    break;
                case -11:
                    setNewKeyboard(firstPartKeyboard);
                    break;
                case -12:
                    setNewKeyboard(thirdPartKeyboard);
                    break;
                case -13:
                    setNewKeyboard(secondPartKeyboard);
                    break;
                case -14:
                    setNewKeyboard(fourthPartKeyboard);
                    break;
                case -15:
                    setNewKeyboard(thirdPartKeyboard);
                    break;
                case -16:
                    setNewKeyboard(fifthPartKeyboard);
                    break;
                case -17:
                    setNewKeyboard(fourthPartKeyboard);
                    break;
                case -9:
                    break;
                default :
                    char code = (char) primaryCode;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);
            }
        }

    }


    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}