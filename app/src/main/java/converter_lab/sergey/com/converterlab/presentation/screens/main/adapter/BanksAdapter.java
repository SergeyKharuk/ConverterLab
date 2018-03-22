package converter_lab.sergey.com.converterlab.presentation.screens.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import converter_lab.sergey.com.converterlab.R;
import converter_lab.sergey.com.converterlab.data.models.Organization;
import converter_lab.sergey.com.converterlab.presentation.screens.main.MainActivity;

/**
 * Created by Moby on 14.03.2018.
 */

@EBean
public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BanksViewHolder> {

    protected LayoutInflater mInflater;
    MainActivity mActivity;
    List<Organization> mList = new ArrayList<>();

    public BanksAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public BanksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BanksViewHolder(mInflater.inflate(R.layout.item_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(BanksViewHolder holder, int position) {
        final Organization organization = mList.get(position);

        holder.tvBankName.setText(organization.getTitle());
        holder.tvRegion.setText(organization.getRegionValue());
        holder.tvCity.setText(organization.getCityValue());
        holder.tvPhone.setText("Тел.: " + organization.getPhone());
        holder.tvAddress.setText("Адрес: " + organization.getAddress());

        holder.ivLink.setOnClickListener(v -> mActivity.openLink(organization.getLink()));

        holder.ivMap.setOnClickListener(v -> mActivity.openMap(organization.getCityValue(), organization.getAddress()));

        holder.ivPhone.setOnClickListener(v -> mActivity.doCall(organization.getPhone()));

        holder.rlNext.setOnClickListener(v -> mActivity.openDetailScreen(organization));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Organization> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }





    public class BanksViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBankName;
        private TextView tvRegion;
        private TextView tvCity;
        private TextView tvPhone;
        private TextView tvAddress;
        private ImageView ivLink;
        private ImageView ivMap;
        private ImageView ivPhone;
        private RelativeLayout rlNext;

        public BanksViewHolder(View itemView) {
            super(itemView);
            tvBankName = (TextView) itemView.findViewById(R.id.tv_bank_name_IB);
            tvRegion = (TextView) itemView.findViewById(R.id.tv_bank_region_IB);
            tvCity = (TextView) itemView.findViewById(R.id.tv_bank_city_IB);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_bank_phone_IB);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_bank_address_IB);
            ivLink = (ImageView) itemView.findViewById(R.id.iv_link_IB);
            ivMap = (ImageView) itemView.findViewById(R.id.iv_map_IB);
            ivPhone = (ImageView) itemView.findViewById(R.id.iv_phone_IB);
            rlNext = (RelativeLayout) itemView.findViewById(R.id.rl_next_IB);
        }
    }

}
