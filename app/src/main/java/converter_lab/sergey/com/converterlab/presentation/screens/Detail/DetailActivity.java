package converter_lab.sergey.com.converterlab.presentation.screens.Detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import converter_lab.sergey.com.converterlab.R;
import converter_lab.sergey.com.converterlab.Utils.AppConstants;
import converter_lab.sergey.com.converterlab.Utils.MyBitMapUtil;
import converter_lab.sergey.com.converterlab.Utils.StringUtil;
import converter_lab.sergey.com.converterlab.data.database.DBConnector;
import converter_lab.sergey.com.converterlab.data.models.Organization;
import converter_lab.sergey.com.converterlab.presentation.screens.Detail.adapter.CurrencyDialogAdapter;
import converter_lab.sergey.com.converterlab.presentation.screens.Detail.adapter.PriceAdapter;

/**
 * Created by Moby on 18.03.2018.
 */

@EActivity(R.layout.activity_detail)
public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private DetailContract.Presenter presenter;
    private Organization currentOrganization;

    @Bean
    protected DBConnector dbConnector;

    @Bean
    protected PriceAdapter adapter;

    @Extra
    protected String id;

    @ViewById(R.id.tv_detail_info_AD)
    protected TextView tvDetailInfo;

    @ViewById(R.id.tv_info_bank_name_AD)
    protected TextView tvInfoBankName;

    @ViewById(R.id.rv_AD)
    protected RecyclerView recyclerView;

    @ViewById(R.id.tv_bank_name_AD)
    protected TextView tvBankNameToolBar;

    @ViewById(R.id.tv_city_AD)
    protected TextView tvCityToolBar;

    @ViewById(R.id.iv_share_AD)
    protected ImageView ivShare;

    @Click(R.id.iv_back_AD)
    protected void clickBack() {
        onBackPressed();
    }

    @Click(R.id.iv_share_AD)
    protected void clickShare() {
        openDialog();
    }

    @Click(R.id.fab_map)
    protected void clickOpenMap() {
        openMap(currentOrganization.getCityValue(), currentOrganization.getAddress());
    }

    @Click(R.id.fab_site)
    protected void clickOpenLink() {
        openLink(currentOrganization.getLink());
    }

    @Click(R.id.fab_call)
    protected void clickDoCall() {
        doCall(currentOrganization.getPhone());
    }

    @AfterViews
    protected void onCreate(){
        initComponents();
        presenter.getBankById(id);
    }

    private void initComponents() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new DetailPresenter(this, dbConnector);

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void openDialog() {
        DetailShareFragment detailShareFragment = new DetailShareFragment();
        detailShareFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public Bitmap createBitMap() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);

        ((TextView)view.findViewById(R.id.tv_bank_name_DL)).setText(currentOrganization.getTitle());
        ((TextView)view.findViewById(R.id.tv_bank_region_DL)).setText(currentOrganization.getRegionValue());
        ((TextView)view.findViewById(R.id.tv_bank_city_DL)).setText(currentOrganization.getCityValue());
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_DL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CurrencyDialogAdapter adapter = new CurrencyDialogAdapter(this);
        adapter.setList(currentOrganization.getCurrencies());
        recyclerView.setAdapter(adapter);

        return MyBitMapUtil.getBitmapFromView(view);
    }

    @Override
    public void setList(LinkedTreeMap<String, LinkedTreeMap<String, String>> treeMap) {
        adapter.setList(treeMap);
    }

    @Override
    public void setInfoInToolBar(String bank, String city) {
        tvBankNameToolBar.setText(bank);
        tvCityToolBar.setText(city);
    }

    @Override
    public void setDetailInfo(Organization organization) {
        currentOrganization = organization;
        tvInfoBankName.setText(organization.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Официальный сайт банка:")
                .append("\n" + organization.getLink())
                .append("\nАдрес: " + organization.getAddress())
                .append("\nТелефон: " + organization.getPhone());
        tvDetailInfo.setText(stringBuilder);

    }

    @Override
    public void sendBitMap(Bitmap bitmap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_PERMISSION_WRITE_EXT_STR);
        } else {
            String bitMapPath = MediaStore.Images.Media.insertImage(getContentResolver(),
                    bitmap,
                    "temp_image_ConverterLab",
                    null);
            Uri bitMapUri = Uri.parse(bitMapPath);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, bitMapUri);
            startActivity(Intent.createChooser(intent, "Share"));
        }




    }

    @Override
    public void openMap(String city, String address) {
        String string = "geo:0,0?q=" + StringUtil.getWithoutSpace(city) + "," + StringUtil.getWithoutSpace(address);
        Uri uri = Uri.parse(string);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void openLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    @Override
    public void doCall(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, AppConstants.REQUEST_CODE_PERMISSION_CALL_PHONE);
        } else startActivity(intent);
    }
}
