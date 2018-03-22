package converter_lab.sergey.com.converterlab.presentation.screens.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import converter_lab.sergey.com.converterlab.R;
import converter_lab.sergey.com.converterlab.Utils.AppConstants;
import converter_lab.sergey.com.converterlab.Utils.MyProgressDialog;
import converter_lab.sergey.com.converterlab.Utils.StringUtil;
import converter_lab.sergey.com.converterlab.data.api.Connector;
import converter_lab.sergey.com.converterlab.data.database.DBConnector;
import converter_lab.sergey.com.converterlab.data.models.Organization;
import converter_lab.sergey.com.converterlab.presentation.screens.Detail.DetailActivity_;
import converter_lab.sergey.com.converterlab.presentation.screens.main.adapter.BanksAdapter;
import converter_lab.sergey.com.converterlab.presentation.service.AutoUpdateService_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;
    private MyProgressDialog progressDialog;

    @Bean
    protected Connector connector;

    @Bean
    protected DBConnector dbConnector;

    @Bean
    protected BanksAdapter adapter;

    @ViewById(R.id.iv_search_AM)
    protected ImageView imSearch;

    @ViewById(R.id.rv_banks)
    protected RecyclerView recyclerView;

    @ViewById(R.id.swipe_refresh_layout_AM)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @AfterInject
    @Override
    public void initPresenter() {
        new MainPresenter(this, dbConnector, connector);
    }

    @AfterViews
    protected void onCreate() {
        initComponents();
        presenter.getData();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void openLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
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
    public void doCall(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, AppConstants.REQUEST_CODE_PERMISSION_CALL_PHONE);
        } else startActivity(intent);
    }

    @Override
    public void openDetailScreen(Organization organization) {
        DetailActivity_.intent(this).id(organization.getId()).start();
    }

    @Override
    public void setList(ArrayList<Organization> list) {
        adapter.setList(list);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startService() {
        AutoUpdateService_.intent(this).start();
    }

    private void initComponents() {
        progressDialog = new MyProgressDialog(this);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        startService();
    }
}
