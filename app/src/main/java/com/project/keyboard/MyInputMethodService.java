package com.project.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;


public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private CustomKeyboard[] keyboardsArray;
    private int currentKeyboardIdx;
    private CustomKeyboard currentKeyboard;
    private boolean isOnceShiftClicked = false;
    private boolean isTwiceShiftClicked = false;
    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboardsArray = new CustomKeyboard[]{
                new CustomKeyboard(this, R.xml.keys_layout1),
                new CustomKeyboard(this, R.xml.keys_layout2),
                new CustomKeyboard(this, R.xml.keys_layout3),
                new CustomKeyboard(this, R.xml.keys_layout4),
                new CustomKeyboard(this, R.xml.keys_layout5)
        };
        currentKeyboardIdx = 0;
        currentKeyboard = keyboardsArray[0];
        keyboardView.setKeyboard(currentKeyboard);
        keyboardView.setOnKeyboardActionListener(this);
        makeCapitalLettersIfEmptyInput();


        return keyboardView;
    }

    private void makeCapitalLettersIfEmptyInput(){
        InputConnection inputConnection = getCurrentInputConnection();

        CharSequence selectedText2 = inputConnection.getTextBeforeCursor(1, 0);
        if (TextUtils.isEmpty(selectedText2) && !caps) {
            capsClicked();
            isOnceShiftClicked = true;
        }
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    private void setNewKeyboard(CustomKeyboard keyboard){
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        currentKeyboard = keyboard;
        currentKeyboard.setShifted(caps);
        keyboardView.invalidateAllKeys();
    }

    private void capsClicked(){
        caps = !caps;
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
                    if(isOnceShiftClicked) {
                        isTwiceShiftClicked = true;
                        isOnceShiftClicked = false;
                    }
                    else if(isTwiceShiftClicked) {
                        isTwiceShiftClicked = false;
                        isOnceShiftClicked = false;
                        capsClicked();
                    }
                    else {
                        isOnceShiftClicked = true;
                        capsClicked();
                    }
                        break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                    break;
                case CustomKeyboard.NEXT:
                    int nextIndex = (currentKeyboardIdx + 1) % keyboardsArray.length;
                    setNewKeyboard(keyboardsArray[nextIndex]);
                    currentKeyboardIdx = nextIndex;
                    break;
                case CustomKeyboard.BACK:
                    int prevIndex = currentKeyboardIdx - 1;
                    if (currentKeyboardIdx == 0 )
                        prevIndex = keyboardsArray.length - 1;
                    setNewKeyboard(keyboardsArray[prevIndex]);
                    currentKeyboardIdx = prevIndex;
                    break;
                default :
                    char code = (char) primaryCode;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);
                    if(isOnceShiftClicked){
                        capsClicked();
                        isOnceShiftClicked = false;
                    }
            }
            makeCapitalLettersIfEmptyInput();
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        currentKeyboard.setImeOptions(getResources(), attribute.imeOptions);
        for (CustomKeyboard ck : keyboardsArray) {
            ck.setImeOptions(getResources(), attribute.imeOptions);
        }
        super.onStartInputView(attribute, restarting);
        keyboardView.invalidateAllKeys();
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