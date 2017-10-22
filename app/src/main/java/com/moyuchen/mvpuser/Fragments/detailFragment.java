package com.moyuchen.mvpuser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moyuchen.mvpuser.R;

/**
 * User: 张亚博
 * Date: 2017-10-17 16:41
 * Description：
 */
public class detailFragment extends Fragment {

    private View view;
    private String url;
    private WebView webview;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public Fragment detailFragment(String url){
        detailFragment detailFragment=new detailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = inflater.inflate(R.layout.detail, container, false);
            initView();
            initData();

        }
        return view;
    }

    private void initData() {
        String url = getArguments().getString("url");
        webview.loadUrl(url);
    }

    private void initView() {
        webview = view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
      webview.setWebViewClient(new WebViewClient(){
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url) {
              view.loadUrl(url);
              return false;
          }
      });
    }


}
