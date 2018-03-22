package converter_lab.sergey.com.converterlab.presentation.screens.main;

import java.util.ArrayList;

import converter_lab.sergey.com.converterlab.data.models.Info;
import converter_lab.sergey.com.converterlab.data.models.Organization;
import converter_lab.sergey.com.converterlab.presentation.base.BaseModel;
import converter_lab.sergey.com.converterlab.presentation.base.BasePresenter;
import converter_lab.sergey.com.converterlab.presentation.base.BaseView;

/**
 * Created by Moby on 14.03.2018.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showProgress();
        void hideProgress();
        void openLink(String link);
        void openMap(String city, String address);
        void doCall(String number);
        void openDetailScreen(Organization organization);
        void setList(ArrayList<Organization> list);
        void stopRefreshing();
        void showMsg(String msg);
        void startService();
    }

    interface Presenter extends BasePresenter {
        void getData();
        void formatRegionAndCity(Info response);
        void refresh();
    }

    interface Model extends BaseModel {
        void putData(Info response);
        ArrayList<Organization> getOrganizations();
    }
}
