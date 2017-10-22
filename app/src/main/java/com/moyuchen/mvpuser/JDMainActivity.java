package com.moyuchen.mvpuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.moyuchen.mvpuser.Fragments.FragmentFifth;
import com.moyuchen.mvpuser.Fragments.FragmentFirst;
import com.moyuchen.mvpuser.Fragments.FragmentFourth;
import com.moyuchen.mvpuser.Fragments.FragmentSecond;
import com.moyuchen.mvpuser.Fragments.FragmentThird;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.stx.xhb.xbanner.XBanner;

public class JDMainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recycleView;
    private XBanner xbanner;
    private LoginPresenter presenter;
    private RecyclerViewHeader recyclerViewHeader;

    private ImageView first_iv;
    private LinearLayout first;
    private TextView first_tv;

    private LinearLayout Second;
    private ImageView Second_iv;
    private TextView Second_tv;

    private LinearLayout Third;
    private ImageView Third_iv;
    private TextView Third_tv;

    private LinearLayout Fourth;
    private ImageView Fourth_iv;
    private TextView Fourth_tv;

    private LinearLayout Fifth;
    private ImageView Fifth_iv;
    private TextView Fifth_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdmain);
        //标题栏隐藏
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        String fragmentNumber = getIntent().getStringExtra("Fragmentnumber");

        if ("Fifth".equals(fragmentNumber)) {
            ReplaceFifth();
        }else {
            ReplaceFirst();
        }


    }

    private void initView() {
        first = (LinearLayout) findViewById(R.id.first);
        first_iv = (ImageView) findViewById(R.id.first_iv);
        first_tv = (TextView) findViewById(R.id.first_tv);
        first.setOnClickListener(this);

        Second = (LinearLayout) findViewById(R.id.Second);
        Second_iv = (ImageView) findViewById(R.id.Second_iv);
        Second_tv = (TextView) findViewById(R.id.Second_tv);
        Second.setOnClickListener(this);

        Third = (LinearLayout) findViewById(R.id.Third);
        Third_iv = (ImageView) findViewById(R.id.Third_iv);
        Third_tv = (TextView) findViewById(R.id.Third_tv);
        Third.setOnClickListener(this);

        Fourth = (LinearLayout) findViewById(R.id.Fourth);
        Fourth_iv = (ImageView) findViewById(R.id.Fourth_iv);
        Fourth_tv = (TextView) findViewById(R.id.Fourth_tv);
        Fourth.setOnClickListener(this);

        Fifth = (LinearLayout) findViewById(R.id.Fifth);
        Fifth_iv = (ImageView) findViewById(R.id.Fifth_iv);
        Fifth_tv = (TextView) findViewById(R.id.Fifth_tv);
        Fifth.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.first:
                ReplaceFirst();
                break;
            case R.id.Second:
                ReplaceSecond();
                break;
            case R.id.Third:
                ReplaceThird();
                break;
            case R.id.Fourth:
                ReplaceFourth();
                break;
            case R.id.Fifth:
                ReplaceFifth();
                break;
        }
    }

    private void ReplaceFifth() {
        Fifth_iv.setImageResource(R.drawable.wode_yes);
        Fifth_tv.setTextColor(getResources().getColor(R.color.red));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout,new FragmentFifth().FragmentFifth(getIntent().getStringExtra("TouXiangUrl"),getIntent().getStringExtra("LoginStaticTrueUserName"))).commit();

        Fourth_iv.setImageResource(R.drawable.gouwuche_no);
        Fourth_tv.setTextColor(getResources().getColor(R.color.black));

        Third_iv.setImageResource(R.drawable.faxian_no);
        Third_tv.setTextColor(getResources().getColor(R.color.black));
        Third_tv.setText("发现");

        Second_iv.setImageResource(R.drawable.fenlei_no);
        Second_tv.setTextColor(getResources().getColor(R.color.black));

        first_iv.setImageResource(R.drawable.shouye_no);
        first_tv.setTextColor(getResources().getColor(R.color.black));
    }

    private void ReplaceFourth() {
        Fourth_iv.setImageResource(R.drawable.gouwuche_yes);
        Fourth_tv.setTextColor(getResources().getColor(R.color.red));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout,new FragmentFourth()).commit();

        Fifth_iv.setImageResource(R.drawable.wode_no);
        Fifth_tv.setTextColor(getResources().getColor(R.color.black));

        Third_iv.setImageResource(R.drawable.faxian_no);
        Third_tv.setTextColor(getResources().getColor(R.color.black));
        Third_tv.setText("发现");

        Second_iv.setImageResource(R.drawable.fenlei_no);
        Second_tv.setTextColor(getResources().getColor(R.color.black));

        first_iv.setImageResource(R.drawable.shouye_no);
        first_tv.setTextColor(getResources().getColor(R.color.black));



    }

    private void ReplaceThird() {
        Third_iv.setImageResource(R.drawable.shuaxin);
        Third_tv.setTextColor(getResources().getColor(R.color.red));
        Third_tv.setText("刷新");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout,new FragmentThird()).commit();

        Fifth_iv.setImageResource(R.drawable.wode_no);
        Fifth_tv.setTextColor(getResources().getColor(R.color.black));

        Fourth_iv.setImageResource(R.drawable.gouwuche_no);
        Fourth_tv.setTextColor(getResources().getColor(R.color.black));

        Second_iv.setImageResource(R.drawable.fenlei_no);
        Second_tv.setTextColor(getResources().getColor(R.color.black));

        first_iv.setImageResource(R.drawable.shouye_no);
        first_tv.setTextColor(getResources().getColor(R.color.black));

    }

    private void ReplaceSecond() {
        Second_iv.setImageResource(R.drawable.feilei_yes);
        Second_tv.setTextColor(getResources().getColor(R.color.red));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout,new FragmentSecond()).commit();

        Fifth_iv.setImageResource(R.drawable.wode_no);
        Fifth_tv.setTextColor(getResources().getColor(R.color.black));

        Fourth_iv.setImageResource(R.drawable.gouwuche_no);
        Fourth_tv.setTextColor(getResources().getColor(R.color.black));

        Third_iv.setImageResource(R.drawable.faxian_no);
        Third_tv.setTextColor(getResources().getColor(R.color.black));
        Third_tv.setText("发现");

        first_iv.setImageResource(R.drawable.shouye_no);
        first_tv.setTextColor(getResources().getColor(R.color.black));



    }

    private void ReplaceFirst() {

        //状态栏透明
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        first_iv.setImageResource(R.drawable.shouye_yes);
        first_tv.setTextColor(getResources().getColor(R.color.red));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout,new FragmentFirst()).commit();

        Fifth_iv.setImageResource(R.drawable.wode_no);
        Fifth_tv.setTextColor(getResources().getColor(R.color.black));

        Fourth_iv.setImageResource(R.drawable.gouwuche_no);
        Fourth_tv.setTextColor(getResources().getColor(R.color.black));

        Third_iv.setImageResource(R.drawable.faxian_no);
        Third_tv.setTextColor(getResources().getColor(R.color.black));
        Third_tv.setText("发现");

        Second_iv.setImageResource(R.drawable.fenlei_no);
        Second_tv.setTextColor(getResources().getColor(R.color.black));

    }
}
