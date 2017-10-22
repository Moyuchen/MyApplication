package com.moyuchen.mvpuser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Adapter.gwcRecycleAdapter;
import com.moyuchen.mvpuser.Bean.CreateOrderInfo;
import com.moyuchen.mvpuser.Bean.GetCartsInfo;
import com.moyuchen.mvpuser.Bean.UpdateCartsInfo;
import com.moyuchen.mvpuser.LoginView.CreateOrderView;
import com.moyuchen.mvpuser.LoginView.GetCartsView;
import com.moyuchen.mvpuser.LoginView.UpdataCartsView;
import com.moyuchen.mvpuser.Presenter.CreateOrderPresenter;
import com.moyuchen.mvpuser.Presenter.UpdateCartsPresenter;
import com.moyuchen.mvpuser.Presenter.getCartsPresenter;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-08 20:10
 * Description：
 */
public class FragmentFourth extends Fragment implements View.OnClickListener, GetCartsView, CreateOrderView {
    private View view;
    private RecyclerView gwcRecycleView;
    private LinearLayout qx_selected;
    private LinearLayout qx_unselected;
    private ImageView imageView_qx;
    private LinearLayout toJieSuan;
    private int Qxnum=0;
    private gwcRecycleAdapter adapter;
    private getCartsPresenter presenter;
    private String uid;
    private List<GetCartsInfo.DataBean> data;
    private List<Integer> listja=new ArrayList();
    private double PriceNum;
    private TextView unqx_pricenum;
    private TextView qx_pricenum;
    private LinearLayout tojiesuan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view=inflater.inflate(R.layout.fragmentfourth,container,false);
            initView();
            initData();
        }
        return view;
    }

    private void initData() {
        presenter = new getCartsPresenter(this);
        presenter.GetCartsView(API.GETCARTS,uid);

    }

    private void initView() {
        uid = CommonUtils.GetString(getContext(), "uid");

        gwcRecycleView = view.findViewById(R.id.gwcRecycleView);
        gwcRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        unqx_pricenum = view.findViewById(R.id.unqx_pricenum);
        qx_pricenum=view.findViewById(R.id.qx_PriceNum);
        qx_selected = view.findViewById(R.id.Qx_selected);
        qx_unselected = view.findViewById(R.id.Qx_unselected);
        imageView_qx = view.findViewById(R.id.ImagView_Qx);
        toJieSuan = view.findViewById(R.id.ToJieSuan);
        tojiesuan = view.findViewById(R.id.ToJieSuan);


        imageView_qx.setOnClickListener(this);
        tojiesuan.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ImagView_Qx:
                imageView_qx();
                break;
            case R.id.ToJieSuan:
                tojiesuan();
                break;
        }

    }

    private void tojiesuan() {
        String jisuan = Jisuan(PriceNum);
        CreateOrderPresenter createOrderPresenter=new CreateOrderPresenter(this);
        createOrderPresenter.CreateOrderPresenter(API.CREATEORDER,CommonUtils.GetString(getContext(),"uid"),jisuan);
    }

    /**
     * 点击ImagView_Qx执行的操作方法
     */
    private void imageView_qx() {
        System.out.println("qxnum:"+Qxnum);
        if ((Qxnum%2)==1) {
            imageView_qxSelected();
        }else {
            imageView_qxUnSelected();
        }
        Qxnum++;
    }
    /**
     * 全选未选中操作方法
     */
    private void imageView_qxUnSelected() {
        PriceNum=0;
        qx_selected.setVisibility(View.GONE);
        qx_unselected.setVisibility(View.VISIBLE);
        imageView_qx.setImageResource(R.drawable.yuanhuan);

        for (GetCartsInfo.DataBean dataBean : data) {
            List<GetCartsInfo.DataBean.ListBean> list = dataBean.getList();
            for (GetCartsInfo.DataBean.ListBean listBean : list) {
                if (listBean.getSelected()==1) {
                    double bargainPrice = listBean.getBargainPrice();
                    PriceNum=PriceNum+bargainPrice*listBean.getNum();
                }
                updatecarts(listBean,1,listBean.getNum());
            }
        }
        presenter.GetCartsView(API.GETCARTS,uid);
        updataOther();
        String js = Jisuan(PriceNum);
        unqx_pricenum.setText(js);
        adapter.notifyDataSetChanged();


    }

    private String Jisuan(double Zongjia) {
        DecimalFormat format=new DecimalFormat("####0.00");
        String format1 = format.format(Zongjia);
        return format1;
    }

    /**
     * 全选选中操作方法
     */
    private void imageView_qxSelected() {
        PriceNum=0;
        qx_selected.setVisibility(View.VISIBLE);
        qx_unselected.setVisibility(View.GONE);
        imageView_qx.setImageResource(R.drawable.duihaoselected);
        for (GetCartsInfo.DataBean dataBean : data) {
            List<GetCartsInfo.DataBean.ListBean> list = dataBean.getList();
            for (GetCartsInfo.DataBean.ListBean listBean : list) {
                if (listBean.getSelected()==1) {
                    PriceNum=PriceNum+listBean.getBargainPrice()*listBean.getNum();
                }
                updatecarts(listBean,0,listBean.getNum());
            }
        }
        presenter.GetCartsView(API.GETCARTS,uid);
        updataOther();
        String jisuan = Jisuan(PriceNum);
        qx_pricenum.setText(jisuan);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void UidError(String msg) {
        CommonUtils.SHOWTOAST(getActivity(),msg);
    }

    @Override
    public void OnResponse(Call call, Response response) {

                    if (response.isSuccessful()) {
                        if (response != null) {
                            try {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                final GetCartsInfo getCartsInfo = gson.fromJson(string, GetCartsInfo.class);
                                final String code = getCartsInfo.getCode();
                                final String msg = getCartsInfo.getMsg();
                                data = getCartsInfo.getData();
                                if ("0".equals(code)) {
                                    if (getActivity()!=null) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    other(getCartsInfo);
                                                    if (adapter == null) {
                                                        adapter = new gwcRecycleAdapter(getContext(), data, listja);
                                                    } else {
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                    adapter.setUpdateShopCarts(new gwcRecycleAdapter.updateShopCarts() {
                                                        @Override
                                                        public void onUpdateShopCarts() {
                                                            presenter.GetCartsView(API.GETCARTS,uid);
                                                        }
                                                    });
                                            gwcRecycleView.setAdapter(adapter);
                                        }
                                        });
                                    }
                                } else {
                                    CommonUtils.SHOWTOAST(getActivity(), msg);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        CommonUtils.SHOWTOAST(getActivity(),"获取购物车列表失败");
    }

    public void other(GetCartsInfo getCartsInfo){
        PriceNum=0;
        data = getCartsInfo.getData();
        int num=0;

        for (int i = 0; i <data.size(); i++) {
            int kb=0;
            List<GetCartsInfo.DataBean.ListBean> list = data.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                if(list.get(j).getSelected()==1){
                    double bargainPrice = list.get(j).getBargainPrice();
                    PriceNum=PriceNum+bargainPrice*list.get(j).getNum();
                    kb++;
                }else {
                    num++;
                }
            }
            if(kb== list.size()){
                listja.add(0);
            }else{
                listja.add(1);
            }

        }
        if (num>0) {
            imageView_qx.setImageResource(R.drawable.yuanhuan);
            qx_selected.setVisibility(View.GONE);
            qx_unselected.setVisibility(View.VISIBLE);
            String jisuan = Jisuan(PriceNum);
            System.out.println(PriceNum+"...................");
            unqx_pricenum.setText(jisuan);
            Qxnum++;

        }else {
            imageView_qx.setImageResource(R.drawable.duihaoselected);
            qx_selected.setVisibility(View.VISIBLE);
            qx_unselected.setVisibility(View.GONE);
            String jisuan = Jisuan(PriceNum);
            qx_pricenum.setText(jisuan);
            Qxnum++;
        }

    }

    private void updataOther() {
        if (Qxnum > 0) {
            if ((Qxnum % 2) == 0) {
                for (int i = 0; i < data.size(); i++) {
                    GetCartsInfo.DataBean dataBean = data.get(i);
                    List<GetCartsInfo.DataBean.ListBean> list = dataBean.getList();
                    for (GetCartsInfo.DataBean.ListBean listBean : list) {
                        int selected = listBean.getSelected();
                        if (selected == 0) {
                            updatecarts(listBean, 1, listBean.getNum());
                        }
                    }
                }
                presenter.GetCartsView(API.GETCARTS, uid);
            } else {
                for (int i = 0; i < data.size(); i++) {
                    GetCartsInfo.DataBean dataBean = data.get(i);
                    List<GetCartsInfo.DataBean.ListBean> list = dataBean.getList();
                    for (GetCartsInfo.DataBean.ListBean listBean : list) {
                        int selected = listBean.getSelected();
                        if (selected == 1) {
                            updatecarts(listBean, 0, listBean.getNum());
                        }
                    }
                }
                presenter.GetCartsView(API.GETCARTS, uid);
            }
        }
    }
    private void updatecarts(GetCartsInfo.DataBean.ListBean listBean, final int selected, int amount) {

        UpdateCartsPresenter updateCartsPresenter=new UpdateCartsPresenter(new UpdataCartsView() {
            @Override
            public void OnResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        Gson gson=new Gson();
                        UpdateCartsInfo updateCartsInfo = gson.fromJson(string, UpdateCartsInfo.class);
                        String code = updateCartsInfo.getCode();
                        String msg = updateCartsInfo.getMsg();
                        System.out.println("msg:"+msg);
                        if ("0".equals(code)) {
                            System.out.println("更新成功");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void OnFailure(Call call, Exception e) {

            }
        });
        updateCartsPresenter.UpdataCarts(API.UPDATECARTS, CommonUtils.GetString(getContext(),"uid"),listBean.getPid()+"",listBean.getSellerid()+"",amount+"",selected+"");
    }

    @Override
    public void onCreateReponse(Call call, Response response) {
        if (response.isSuccessful()) {
            try {
                String string = response.body().string();
                Gson gson=new Gson();
                CreateOrderInfo createOrderInfo = gson.fromJson(string, CreateOrderInfo.class);
                String code = createOrderInfo.getCode();
                String msg = createOrderInfo.getMsg();
                if ("0".equals(code)) {
                    CommonUtils.SHOWTOAST(getActivity(),"！！！！！！！！！！！！！！！！！！！！！！创建订单与否:"+msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onCreateFailure(Call call, Exception e) {

    }
}
