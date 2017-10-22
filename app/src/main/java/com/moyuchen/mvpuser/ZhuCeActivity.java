package com.moyuchen.mvpuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.moyuchen.mvpuser.LoginView.ZhuceView;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;

import okhttp3.Call;

public class ZhuCeActivity extends AppCompatActivity implements ZhuceView{

    private LoginPresenter presenter;
    private EditText zc_et_userName;
    private EditText ZC_et_IconUrl;
    private EditText ZC_et_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        initView();
        initData();
    }

    private void initView() {
        zc_et_userName = (EditText) findViewById(R.id.ZC_et_UserName);
        ZC_et_Password = (EditText) findViewById(R.id.ZC_et_Password);
    }

    private void initData() {
        presenter = new LoginPresenter(this);
    }

    public void ZhuCe(View view){
        presenter.Register(zc_et_userName.getText().toString(),ZC_et_Password.getText().toString());
    }

    @Override
    public void ZhuceSuccess(String code, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZhuCeActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void ZhuceFailure(String code, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZhuCeActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void NameError(String msg) {
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void PasswordError(String msg) {
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onZhuceFailure(Call call, Exception e) {
            Toast.makeText(this,"注册失败请求",Toast.LENGTH_LONG).show();

    }









}
