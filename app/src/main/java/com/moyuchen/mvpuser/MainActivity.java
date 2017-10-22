package com.moyuchen.mvpuser;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moyuchen.mvpuser.Bean.Resultinfo;
import com.moyuchen.mvpuser.LoginView.LoginView;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements LoginView{

    private EditText et_userName;
    private EditText et_password;
    private Button but_Login;
    private ProgressBar progressbar;
    private LoginPresenter presenter;
    private TextView tv_ZhuCe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {
        presenter = new LoginPresenter(this);
    }

    private void initView() {
        et_userName = (EditText) findViewById(R.id.et_UserName);
        et_password = (EditText) findViewById(R.id.et_Password);
        but_Login = (Button) findViewById(R.id.but_Login);
        tv_ZhuCe = (TextView) findViewById(R.id.tv_ZhuCe);
        TextView loginXieYi = (TextView) findViewById(R.id.LoginXieYi);
        loginXieYi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void ShowProgressBar() {
      progressbar.setVisibility(ProgressBar.VISIBLE);

    }

    @Override
    public void HideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressbar.setVisibility(ProgressBar.GONE);
            }
        });

    }

    @Override
    public void NameError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void PasswordError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void LoginSuccess(String code, final String msg, Resultinfo resultinfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });
        CommonUtils.BaoCunLoginStatic(this,true,"LoginStatic");
        CommonUtils.BaoCunString(this,resultinfo.getData().getUid()+"","uid");
        Intent intent=new Intent(MainActivity.this,JDMainActivity.class);
        intent.putExtra("TouXiangUrl",resultinfo.getData().getIcon().toString());
        intent.putExtra("LoginStaticTrueUserName",resultinfo.getData().getUsername());
        intent.putExtra("Fragmentnumber","Fifth");
        startActivity(intent);
    }

    @Override
    public void LoginFailure(String code, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onFailure(Call call, Exception e) {
    Toast.makeText(this,"请求错误",Toast.LENGTH_LONG).show();
    }

    public void OnLogin(View view){
        presenter.Login(et_userName.getText().toString(),et_password.getText().toString());
    }
    public void OnRegister(View view){
        Intent in=new Intent(this,ZhuCeActivity.class);
        startActivity(in);
    }


}
