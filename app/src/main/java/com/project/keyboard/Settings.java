package com.project.keyboard;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Settings  extends Activity implements OnClickListener {


    private ListView list ;
    private ArrayAdapter<String> adapter ;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        list = (ListView) findViewById(R.id.mobile_list);

        String cars[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};

        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(cars) );

        adapter = new ArrayAdapter<String>(this, R.layout.row, carL);

        list.setAdapter(adapter);


//        mIMEMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        findViewById(R.id.show_button).setOnClickListener(this);
//        findViewById(R.id.hide_button).setOnClickListener(this);
    }
}
