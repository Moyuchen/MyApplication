package com.moyuchen.mvpuser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moyuchen.mvpuser.Adapter.VPadapter;
import com.moyuchen.mvpuser.Bean.ProductInfo;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.text.DecimalFormat;

/**
 * User: 张亚博
 * Date: 2017-10-17 16:41
 * Description：
 */
public class productFragment extends Fragment {

    private View view;
    private ProductInfo.DataBean data;
    private ViewPager viewpager;
    private TextView productjiage;
    private TextView title;
    private TextView productxiaoliang;
    private TextView productsubhead;

    public ProductInfo.DataBean getData() {
        return data;
    }

    public void setData(ProductInfo.DataBean data) {
        this.data = data;
    }

    public Fragment productFragment(ProductInfo.DataBean data) {
        productFragment product=new productFragment();
        Bundle bundle=new Bundle();
        bundle.putString("imagesurl",data.getImages());
        bundle.putString("title",data.getTitle());
        bundle.putDouble("jiage",data.getBargainPrice());
        bundle.putString("xiaoliang",data.getSalenum()+"");
        bundle.putString("subhead",data.getSubhead());

        product.setArguments(bundle);
        return product;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = inflater.inflate(R.layout.product, container, false);
            initView();
            initData();
        }
        return view;
    }

    private void initData() {
        Bundle arguments = getArguments();
        String imagesurl = arguments.getString("imagesurl");
        String[] images = CommonUtils.FenGeString(imagesurl);
        String titlestr = arguments.getString("title");
        title.setMaxLines(2);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setText(titlestr);
        double jiage = arguments.getDouble("jiage");
        DecimalFormat df = new DecimalFormat("######0.00");
        String format = df.format(jiage);
        productjiage.setText("￥"+format);
        String xiaoliang = arguments.getString("xiaoliang");
        productxiaoliang.setText("销量:"+xiaoliang);

        productsubhead.setMaxLines(2);
        productsubhead.setEllipsize(TextUtils.TruncateAt.END);
        String subhead = arguments.getString("subhead");
        productsubhead.setText(subhead);

        VPadapter ad=new VPadapter(images,getContext());
        viewpager.setAdapter(ad);


    }

    private void initView() {
        //获取viewpager视图，展示商品图片
        viewpager = view.findViewById(R.id.productintovp);

        //获取商品价格视图，展示商品价格
        productjiage = view.findViewById(R.id.productjiage);
        productjiage.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
        //获取商品标题视图，展示商品标题
        title = view.findViewById(R.id.producttitle);
        productxiaoliang=view.findViewById(R.id.xiaoliang);
        productsubhead = view.findViewById(R.id.subhead);

    }


}
