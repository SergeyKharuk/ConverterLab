package converter_lab.sergey.com.converterlab.presentation.screens.Detail.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.internal.LinkedTreeMap;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import converter_lab.sergey.com.converterlab.R;

/**
 * Created by Moby on 18.03.2018.
 */

@EBean
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private Context context;
    private ArrayList<Map.Entry<String, LinkedTreeMap<String, String>>> mList = new ArrayList<>();
    private LayoutInflater mInflater;

    public PriceAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceViewHolder(mInflater.inflate(R.layout.item_price, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(PriceViewHolder holder, int position) {
        final Map.Entry<String, LinkedTreeMap<String, String>> entry = mList.get(position);

        holder.tvCurrencyName.setText(entry.getKey());

        if (Boolean.valueOf(entry.getValue().get("isRisenAsk"))) {
            Glide.with(context)
                    .load(R.drawable.ic_green_arrow_up)
                    .into(holder.ivArrowAsk);
            holder.tvPriceAsk.setTextColor(context.getResources().getColor(R.color.risen_price));
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_red_arrow_down)
                    .into(holder.ivArrowAsk);
            holder.tvPriceAsk.setTextColor(context.getResources().getColor(R.color.not_risen_price));
        }

        if (Boolean.valueOf(entry.getValue().get("isRisenBid"))) {
            Glide.with(context)
                    .load(R.drawable.ic_green_arrow_up)
                    .into(holder.ivArrowBid);
            holder.tvPriceBid.setTextColor(context.getResources().getColor(R.color.risen_price));
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_red_arrow_down)
                    .into(holder.ivArrowBid);
            holder.tvPriceBid.setTextColor(context.getResources().getColor(R.color.not_risen_price));
        }

        holder.tvPriceAsk.setText(entry.getValue().get("ask"));
        holder.tvPriceBid.setText(entry.getValue().get("bid"));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(LinkedTreeMap<String, LinkedTreeMap<String, String>> map) {
        mList.clear();
        Set<Map.Entry<String, LinkedTreeMap<String, String>>> entries = map.entrySet();
        for (Map.Entry<String, LinkedTreeMap<String, String>> entry : entries) {
            mList.add(entry);
        }
        notifyDataSetChanged();
    }






    public class PriceViewHolder extends RecyclerView.ViewHolder {

        TextView tvCurrencyName;
        TextView tvPriceAsk;
        TextView tvPriceBid;
        ImageView ivArrowAsk;
        ImageView ivArrowBid;

        public PriceViewHolder(View itemView) {
            super(itemView);
            tvCurrencyName = (TextView) itemView.findViewById(R.id.tv_currency_IP);
            tvPriceAsk = (TextView) itemView.findViewById(R.id.tv_price_ask_IP);
            tvPriceBid = (TextView) itemView.findViewById(R.id.tv_price_bid_IP);
            ivArrowAsk = (ImageView) itemView.findViewById(R.id.iv_arrow_ask_IP);
            ivArrowBid = (ImageView) itemView.findViewById(R.id.iv_arrow_bid_IP);
        }
    }
}
