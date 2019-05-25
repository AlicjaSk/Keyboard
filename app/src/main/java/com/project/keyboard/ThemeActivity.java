package com.project.keyboard;

import android.content.SharedPreferences;
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

        ImageButton darkBlueThemeBtn = findViewById(R.id.dark_blue_theme);
        ImageButton lightBlueThemeBtn = findViewById(R.id.light_blue_theme);
        ImageButton lightThemeBtn = findViewById(R.id.light_theme);

        largeSizeBtn.setOnClickListener(this);
        smallSizeBtn.setOnClickListener(this);
        mediumSizeBtn.setOnClickListener(this);

        darkBlueThemeBtn.setOnClickListener(this);
        lightBlueThemeBtn.setOnClickListener(this);
        lightThemeBtn.setOnClickListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(THEME_KEY, 3).apply();
//        editor.commit();

        switch (view.getId()) {
            case R.id.large_size_imageButton:
                editor.putString(THEME_KEY, "LARGE_SIZE").apply();
                break;
            case R.id.small_size_imageButton:
                editor.putString(THEME_KEY, "SMALL_SIZE").apply();
                break;
            case R.id.medium_size_imageButton:
                editor.putString(THEME_KEY, "MEDIUM_SIZE").apply();
                break;
            case R.id.dark_blue_theme:
                editor.putString(VIEW_KEY, "DARK_BLUE").apply();
                break;
            case R.id.light_blue_theme:
                editor.putString(VIEW_KEY, "LIGHT_BLUE").apply();
                break;
            case R.id.light_theme:
                editor.putString(VIEW_KEY, "LIGHT").apply();
                break;

//            case R.id.theme3_imageButton:
//                editor.putInt(THEME_KEY, 2).apply();
//                break;
//            case R.id.theme4_imageButton:
//                editor.putInt(THEME_KEY, 3).apply();
//                break;
//            case R.id.theme5_imageButton:
//                editor.putInt(THEME_KEY, 4).apply();
//                break;
//            case R.id.theme6_imageButton:
//                editor.putInt(THEME_KEY, 5).apply();
//                break;
//            case R.id.theme7_imageButton:
//                editor.putInt(THEME_KEY, 6).apply();
//                break;
//            case R.id.theme8_imageButton:
//                editor.putInt(THEME_KEY, 7).apply();
//                break;
//            case R.id.theme9_imageButton:
//                editor.putInt(THEME_KEY, 8).apply();
//                break;
//            case R.id.theme10_imageButton:
//                editor.putInt(THEME_KEY, 9).apply();
//                break;
            default:
                break;
        }
//        editor.commit();


        Toast.makeText(this, "Theme is selected.", Toast.LENGTH_SHORT).show();
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