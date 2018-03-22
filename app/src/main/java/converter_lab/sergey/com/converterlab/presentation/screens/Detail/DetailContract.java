package converter_lab.sergey.com.converterlab.presentation.screens.Detail;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;

import converter_lab.sergey.com.converterlab.data.models.Organization;
import converter_lab.sergey.com.converterlab.data.models.Price;
import converter_lab.sergey.com.converterlab.presentation.base.BaseModel;
import converter_lab.sergey.com.converterlab.presentation.base.BasePresenter;
import converter_lab.sergey.com.converterlab.presentation.base.BaseView;

/**
 * Created by Moby on 18.03.2018.
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void openDialog();
        Bitmap createBitMap();
        void setList(LinkedTreeMap<String, LinkedTreeMap<String, String>> treeMap);
        void setInfoInToolBar(String bank, String city);
        void setDetailInfo(Organization organization);
        void sendBitMap(Bitmap bitmap);
        void openMap(String city, String address);
        void openLink(String link);
        void doCall(String number);
    }

    interface Presenter extends BasePresenter {
        void getBankById(String id);
    }

    interface Model extends BaseModel {
        Organization getBankById(String id);
    }
}
