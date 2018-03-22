package converter_lab.sergey.com.converterlab.presentation.screens.Detail;

import converter_lab.sergey.com.converterlab.data.models.Organization;

/**
 * Created by Moby on 18.03.2018.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private DetailContract.Model mModel;

    public DetailPresenter(DetailContract.View view, DetailContract.Model model) {
        this.mView = view;
        this.mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getBankById(String id) {
        Organization bankById = mModel.getBankById(id);
        mView.setInfoInToolBar(bankById.getTitle(), bankById.getCityValue());
        mView.setDetailInfo(bankById);
        mView.setList(bankById.getCurrencies());
    }
}
