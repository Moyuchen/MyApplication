package com.moyuchen.mvpuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class ShangPinListActivity extends AppCompatActivity {

    private RecyclerView sPinListRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_pin_list);
        initView();
        
    }

    private void initView() {
        sPinListRecView = (RecyclerView) findViewById(R.id.SPinListRecView);
    }
}
