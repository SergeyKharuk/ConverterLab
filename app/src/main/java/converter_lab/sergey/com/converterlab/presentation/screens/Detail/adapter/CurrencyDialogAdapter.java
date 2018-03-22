package converter_lab.sergey.com.converterlab.presentation.screens.Detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import converter_lab.sergey.com.converterlab.R;

/**
 * Created by Moby on 19.03.2018.
 */

@EBean
public class CurrencyDialogAdapter extends RecyclerView.Adapter<CurrencyDialogAdapter.CurrencyDialogViewHolder> {

    private ArrayList<Map.Entry<String, LinkedTreeMap<String, String>>> mList = new ArrayList<>();
    private LayoutInflater mInflater;

    public CurrencyDialogAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public CurrencyDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CurrencyDialogViewHolder(mInflater.inflate(R.layout.item_dialog_currency, parent, false));
    }

    @Override
    public void onBindViewHolder(CurrencyDialogViewHolder holder, int position) {
        final Map.Entry<String, LinkedTreeMap<String, String>> entry = mList.get(position);

        holder.tvCurrency.setText(entry.getKey());
        holder.tvPrice.setText(entry.getValue().get("ask") + "/" + entry.getValue().get("bid"));

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







    public class CurrencyDialogViewHolder extends RecyclerView.ViewHolder {

        TextView tvCurrency;
        TextView tvPrice;

        public CurrencyDialogViewHolder(View itemView) {
            super(itemView);
            tvCurrency = itemView.findViewById(R.id.tv_currency_name_DL);
            tvPrice = itemView.findViewById(R.id.tv_currency_price_DL);
        }
    }
}
