package com.moyuchen.mvpuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

public class ZhangHuSettingActivity extends AppCompatActivity {

    private RoundedImageView touXiang;
    private String touxiangurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hu_setting);
        touxiangurl = getIntent().getStringExtra("touxiangurl");
        initView();
        initData();
    }

    private void initData() {
        Glide.with(this).load(touxiangurl).into(touXiang);
    }

    private void initView() {
        touXiang = (RoundedImageView) findViewById(R.id.ZhangHuTouXiang);

    }

    public void ToPersonInfo(View view){
        Intent in=new Intent(this,HeadReplaceActivity.class);
        startActivity(in);
    }
}
