package com.project.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodSubtype;
import static com.project.keyboard.ThemeActivity.THEME_KEY;
import static com.project.keyboard.ThemeActivity.VIEW_KEY;


public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static Context appContext;

    private KeyboardView keyboardView;
    private CustomKeyboard[] keyboardsArray;
    private int currentKeyboardIdx;
    private CustomKeyboard currentKeyboard;
    private boolean isOnceShiftClicked = false;
    private boolean isTwiceShiftClicked = false;
    private boolean caps;
    SharedPreferences sharedPreferences;

    @Override
    public void onInitializeInterface(){
//        shouldOfferSwitchingToNextInputMethod();
        super.onInitializeInterface();
        appContext = getApplicationContext();
    }


    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        mWordSeparators = getResources().getString(R.string.word_separators);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public View onCreateInputView() {

        String nrOfTheme =  sharedPreferences.getString(VIEW_KEY, "DARK_BLUE");

        switch(nrOfTheme){
            case "DARK_BLUE":
                keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_yellow_dark_blue, null);
                break;
            case "LIGHT_BLUE":
                keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_dark, null);
                break;
            case "LIGHT":
                keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_light, null);
                break;
        }


        String nrOfView =  sharedPreferences.getString(THEME_KEY, "MEDIUM_SIZE");
        switch(nrOfView) {
            case "SMALL_SIZE":

                keyboardsArray = new CustomKeyboard[]{
                        new CustomKeyboard(this, R.xml.small_keys_layout1),
                        new CustomKeyboard(this, R.xml.small_keys_layout2),
                        new CustomKeyboard(this, R.xml.small_keys_layout3)
                };
                currentKeyboardIdx = 0;
                currentKeyboard = keyboardsArray[0];
                keyboardView.setKeyboard(currentKeyboard);
                keyboardView.setOnKeyboardActionListener(this);
                break;
            case "MEDIUM_SIZE":
                keyboardsArray = new CustomKeyboard[]{
                        new CustomKeyboard(this, R.xml.medium_keys_layout1),
                        new CustomKeyboard(this, R.xml.medium_keys_layout2),
                        new CustomKeyboard(this, R.xml.medium_keys_layout3),
                        new CustomKeyboard(this, R.xml.medium_keys_layout4),
                        new CustomKeyboard(this, R.xml.medium_keys_layout5)
                };

                currentKeyboardIdx = 0;
                currentKeyboard = keyboardsArray[0];
                keyboardView.setKeyboard(currentKeyboard);
                keyboardView.setOnKeyboardActionListener(this);
                break;
            case "LARGE_SIZE":
                keyboardsArray = new CustomKeyboard[]{
                        new CustomKeyboard(this, R.xml.large_keys_layout1),
                        new CustomKeyboard(this, R.xml.large_keys_layout2),
                        new CustomKeyboard(this, R.xml.large_keys_layout3),
                        new CustomKeyboard(this, R.xml.large_keys_layout4),
                        new CustomKeyboard(this, R.xml.large_keys_layout5),
                        new CustomKeyboard(this, R.xml.large_keys_layout6),
                        new CustomKeyboard(this, R.xml.large_keys_layout7),
                        new CustomKeyboard(this, R.xml.large_keys_layout8),
                        new CustomKeyboard(this, R.xml.large_keys_layout9),
                };
                currentKeyboardIdx = 0;
                currentKeyboard = keyboardsArray[0];
                keyboardView.setKeyboard(currentKeyboard);
                keyboardView.setOnKeyboardActionListener(this);
                break;
        }
        return keyboardView;
    }

    private void makeCapitalLettersIfEmptyInput(EditorInfo attr) {
        EditorInfo ei = getCurrentInputEditorInfo();
        int capsTmp = 0;
        if (ei != null && ei.inputType != InputType.TYPE_NULL) {
            capsTmp = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
        }
        keyboardView.setShifted(caps || capsTmp != 0);
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    public void setNewKeyboard(CustomKeyboard keyboard){
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        currentKeyboard = keyboard;
        currentKeyboard.setShifted(caps);
        keyboardView.invalidateAllKeys();
    }

    private void capsClicked(){
        caps = !caps;
        keyboardView.setShifted(caps || !keyboardView.isShifted());

//        currentKeyboard.setShifted(caps);
        keyboardView.invalidateAllKeys();
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch(primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
//                    switchToNextInputMethod(false);
                    if (isOnceShiftClicked) {
                        isTwiceShiftClicked = true;
                        isOnceShiftClicked = false;
                    }
                    else if (isTwiceShiftClicked) {
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
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_ENTER));
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
                    if(Character.isLetter(code) && keyboardView.isShifted()){
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);
                    if(isOnceShiftClicked){
                        capsClicked();
                        isOnceShiftClicked = false;
                    }
            }
                if(!(isOnceShiftClicked && isTwiceShiftClicked))
                    makeCapitalLettersIfEmptyInput(getCurrentInputEditorInfo());
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        currentKeyboard.setImeOptions(getResources(), attribute.imeOptions);
        for (CustomKeyboard ck : keyboardsArray) {
            ck.setImeOptions(getResources(), attribute.imeOptions);
        }
        super.onStartInputView(attribute, restarting);

        makeCapitalLettersIfEmptyInput(attribute);
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);

        // Restart the InputView to apply right theme selected.
        setInputView(onCreateInputView());

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

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
    }

    @Override
    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
//        keyboardView.setSubtypeOnSpaceKey(subtype);
//        switch(subtype.getLocale()) {
//            case "fa_IR":
////                setLatinKeyboard(mSymbolsKeyboard);
//                break;
//            case "en_US":
////                setLatinKeyboard(mQwertyKeyboard);
//                break;
//        };
    }

}