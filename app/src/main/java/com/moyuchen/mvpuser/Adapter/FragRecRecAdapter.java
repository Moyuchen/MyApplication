package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Bean.GetChildFenLeiInfo;
import com.moyuchen.mvpuser.R;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-11 14:53
 * Description：
 */
public class FragRecRecAdapter extends RecyclerView.Adapter<FragRecRecAdapter.FragRecRecViewHolder>{
    private List<GetChildFenLeiInfo.DataBean.ListBean> list;
    private Context context;

    public FragRecRecAdapter(List<GetChildFenLeiInfo.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public FragRecRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(context).inflate(R.layout.fragrecrec_item, parent, false);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.clickitemlistener(inflate);
            }
        });
        return new FragRecRecViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(FragRecRecViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getIcon()).into(holder.imageView);
        holder.tv.setMaxLines(2);
        holder.tv.setEllipsize(TextUtils.TruncateAt.END);
        holder.tv.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FragRecRecViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tv;
        public FragRecRecViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.FragRecRec_Item_ImageView);
            tv=itemView.findViewById(R.id.FragRecRec_Item_tv);

        }
    }
    private onClickItemListener onClickListener;

    public void setOnClickListener(onClickItemListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickItemListener{
        void clickitemlistener(View view);
    }
}
