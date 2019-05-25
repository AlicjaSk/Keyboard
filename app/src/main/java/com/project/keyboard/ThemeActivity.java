package com.project.keyboard;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;



public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String THEME_KEY = "theme_key";
    public static final String VIEW_KEY = "view_key";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        ImageButton largeSizeBtn = findViewById(R.id.large_size_imageButton);
        ImageButton smallSizeBtn = findViewById(R.id.small_size_imageButton);
        ImageButton mediumSizeBtn = findViewById(R.id.medium_size_imageButton);

        ImageButton darkBlueThemeBtn = findViewById(R.id.dark_theme);
        ImageButton blueYellowThemeBtn = findViewById(R.id.blue_yellow_theme);
        ImageButton lightThemeBtn = findViewById(R.id.light_theme);
        ImageButton greenPinkThemeBtn = findViewById(R.id.green_pink_theme);

        largeSizeBtn.setOnClickListener(this);
        smallSizeBtn.setOnClickListener(this);
        mediumSizeBtn.setOnClickListener(this);

        darkBlueThemeBtn.setOnClickListener(this);
        blueYellowThemeBtn.setOnClickListener(this);
        lightThemeBtn.setOnClickListener(this);
        greenPinkThemeBtn.setOnClickListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String sizeSelected = "Rozmiar ustawiony";
        String themeSelected = "Motyw ustawiony";

        View oPopup = findViewById(R.id.o_popup);
        View ePopup = findViewById(R.id.e_popup);
        View sPopup = findViewById(R.id.s_popup);
        View aPopup = findViewById(R.id.a_popup);
        View lPopup = findViewById(R.id.l_popup);
        View zPopup = findViewById(R.id.z_popup);
        View cPopup = findViewById(R.id.c_popup);
        View nPopup = findViewById(R.id.n_popup);

        int backgroundColorForPopup = R.color.green;


        switch (view.getId()) {
            case R.id.large_size_imageButton:
                editor.putString(THEME_KEY, "LARGE_SIZE").apply();
                Toast.makeText(this, sizeSelected, Toast.LENGTH_SHORT).show();
                break;
            case R.id.small_size_imageButton:
                editor.putString(THEME_KEY, "SMALL_SIZE").apply();
                Toast.makeText(this, sizeSelected, Toast.LENGTH_SHORT).show();

                break;
            case R.id.medium_size_imageButton:
                editor.putString(THEME_KEY, "MEDIUM_SIZE").apply();
                Toast.makeText(this, sizeSelected, Toast.LENGTH_SHORT).show();

                break;
            case R.id.dark_theme:
                editor.putString(VIEW_KEY, "DARK").apply();
                Toast.makeText(this, themeSelected, Toast.LENGTH_SHORT).show();

                break;
            case R.id.blue_yellow_theme:
                editor.putString(VIEW_KEY, "BLUE_YELLOW").apply();
                Toast.makeText(this, themeSelected, Toast.LENGTH_SHORT).show();
                backgroundColorForPopup = R.color.blue;
                break;
            case R.id.light_theme:
                editor.putString(VIEW_KEY, "LIGHT").apply();
                Toast.makeText(this, themeSelected, Toast.LENGTH_SHORT).show();
                backgroundColorForPopup = R.color.lightGrey;
                break;
            case R.id.green_pink_theme:
                editor.putString(VIEW_KEY, "GREEN_PINK").apply();
                Toast.makeText(this, themeSelected, Toast.LENGTH_SHORT).show();
                backgroundColorForPopup = R.color.green;

                break;
            default:
                break;

        }
//        ePopup.set(R.drawable.);
//        oPopup.setBackgroundColor(backgroundColorForPopup);
//        aPopup.setBackgroundColor(backgroundColorForPopup);
//        sPopup.setBackgroundColor(backgroundColorForPopup);
//        lPopup.setBackgroundColor(backgroundColorForPopup);
//        zPopup.setBackgroundColor(backgroundColorForPopup);
//        cPopup.setBackgroundColor(backgroundColorForPopup);
//        nPopup.setBackgroundColor(backgroundColorForPopup);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}