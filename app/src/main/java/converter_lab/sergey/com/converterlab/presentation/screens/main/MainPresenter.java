package converter_lab.sergey.com.converterlab.presentation.screens.main;

import android.content.Context;

import java.util.ArrayList;

import converter_lab.sergey.com.converterlab.R;
import converter_lab.sergey.com.converterlab.data.api.Connector;
import converter_lab.sergey.com.converterlab.data.models.Info;
import converter_lab.sergey.com.converterlab.data.models.Organization;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Moby on 14.03.2018.
 */

public class MainPresenter implements MainContract.Presenter {

    Context context;
    private MainContract.View mView;
    private MainContract.Model mModel;
    private Connector connector;

    public MainPresenter(Context context, MainContract.Model model_, Connector connector) {
        this.mView = (MainContract.View)context;
        this.context = context;
        this.mModel = model_;
        this.connector = connector;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getData() {
        mView.showProgress();
        connector
                .getCurrencyAPI()
                .getCurrencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mModel.putData(response);
                    formatRegionAndCity(response);
                    mView.setList(response.getOrganizations());
                    mView.hideProgress();
                }, e -> {
                    mView.showMsg(context.getString(R.string.error_no_internet));
                    mView.setList(mModel.getOrganizations());
                    mView.hideProgress();
                });
    }

    @Override
    public void formatRegionAndCity(Info response) {
        ArrayList<Organization> organizations = response.getOrganizations();
        for (Organization organization : organizations){
            organization.setRegionValue(response.getRegionValue(organization.getRegionId()));
            organization.setCityValue(response.getCityValue(organization.getCityId()));
        }
    }

    @Override
    public void refresh() {
        connector
                .getCurrencyAPI()
                .getCurrencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mModel.putData(response);
                    formatRegionAndCity(response);
                    mView.setList(response.getOrganizations());
                    mView.stopRefreshing();
                }, e -> {
                    mView.showMsg(context.getString(R.string.error_no_internet));
                    mView.setList(mModel.getOrganizations());
                    mView.stopRefreshing();
                });
    }
}
