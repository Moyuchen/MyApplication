package com.moyuchen.mvpuser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Bean.Resultinfo;
import com.moyuchen.mvpuser.LoginView.GetUserInfoView;
import com.moyuchen.mvpuser.LoginView.HeadUpLoadView;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.Utils.ImageUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;

public class HeadReplaceActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final int CHOOSE_PICTURE = 0;
    private ImageView touxiang;
    private TextView getuserinfo;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    private File tempFile;
    private final int PICS=1;//打开相册
    private final int CAMERA=2;//打开系统相机
    private   File takeimagefile;
    private static final String TAG = "MainActivity";
    // 拍照路径
    public static String SAVED_IMAGE_DIR_PATH =
            Environment.getExternalStorageDirectory().getPath()
                    + "/AppName/camera/";
    private String cameraPath;
    private Uri uri;
    private Uri tempUri;
    private Bitmap mBitmap;
    public static final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*; charset=utf-8");
    private String mobile;
    private LoginPresenter presenter;
    private LoginPresenter presenterheadupload;
    private Uri photoURI;
    private TextView perUserName;
    private TextView niCheng;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_replace);
        initView();
        presenter=new LoginPresenter(new GetUserInfoView() {
            @Override
            public void GetUserInfoSuccess(String code, final String message, final Resultinfo.DataBean dataBean) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        uid = dataBean.getUid();
                        Object nickname = dataBean.getNickname();
                        String username = dataBean.getUsername();
                        String iconurl = dataBean.getIcon().toString();

                        if (nickname!=null) {
                            niCheng.setText(nickname.toString());
                        }
                        if (!TextUtils.isEmpty(username)) {
                            perUserName.setText(username);
                        }
                        if (!TextUtils.isEmpty(username)) {
                            perUserName.setText(username);
                        }if (!TextUtils.isEmpty(iconurl)){
                            Glide.with(HeadReplaceActivity.this).load(iconurl).into(touxiang);
                        }

                        Toast.makeText(HeadReplaceActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void GetUserInfoFailure(String code, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeadReplaceActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                });
            }
            @Override
            public void OnGetUserInfoFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeadReplaceActivity.this,"获取用户信息请求失败",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
//        getuserinfo();
        presenterheadupload = new LoginPresenter(new HeadUpLoadView() {
            @Override
            public void HeadUpLoadSuccess(String code, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeadReplaceActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void HeadUpLoadFailure(String code, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeadReplaceActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void OnHeadUpLoadFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeadReplaceActivity.this,"上传头像请求失败",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void initView() {
        touxiang = (ImageView) findViewById(R.id.touxiang);
        getuserinfo = (TextView) findViewById(R.id.getUserinfo);
        perUserName = (TextView) findViewById(R.id.PersonUserName);
        niCheng = (TextView) findViewById(R.id.PersonNiCheng);

        touxiang.setOnClickListener(this);
        getuserinfo.setOnClickListener(this);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.touxiang:
                ReplaceHead();
                break;
            case R.id.getUserinfo:
               getuserinfo();
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getuserinfo() {
        presenter.GetUserInfo("91");
    }

    /**
     * 更换头像
     */
    private void ReplaceHead() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final String[] items=new String[]{"照相","图库","取消"};
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String item = items[i];
                if ("照相".equals(item)) {
                    gotoCamera();
                    dialogInterface.dismiss();
                }else if ("图库".equals(item)){
                    gotoPics();
                    dialogInterface.dismiss();

                } else if ("取消".equals(item)) {
                    dialogInterface.dismiss();
                }
            }
        }).show();
    }

    /**
     * 跳转到图库
     */
    private void gotoPics() {
        Intent in=new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, PICS);
    }

    /**
     * 跳转到系统相机
     */
    private void gotoCamera() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cameraPath = SAVED_IMAGE_DIR_PATH +
                    System.currentTimeMillis() + ".png";
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            String out_file_path = SAVED_IMAGE_DIR_PATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 把文件地址转换成Uri格式
            photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", new File(cameraPath));
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, CAMERA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch(requestCode){
                case PICS:
                   // 开始对图片进行裁剪处理
                    cutImage(data.getData());
                    break;
                case CAMERA:
                    // 开始对图片进行裁剪处理
                    cutImage(uri);
                    break;
                case CHOOSE_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }
    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            mBitmap = ImageUtils.toRoundBitmap(mBitmap);
            touxiang.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码，后期将单独写一篇这方面的博客，敬请期待..
            uploadPic(mBitmap);
        }
    }

    /**
     * 上传图片
     * @param photo
     */
    private void uploadPic(Bitmap photo) {

        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        File imagePath = ImageUtils.savePhoto(photo, getCacheDir().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            //上传图片值服务器
           presenterheadupload.HeadupLoad(91,imagePath);
        }
    }
    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

}
