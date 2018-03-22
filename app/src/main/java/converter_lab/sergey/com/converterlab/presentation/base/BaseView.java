package converter_lab.sergey.com.converterlab.presentation.base;

/**
 * Created by Moby on 14.03.2018.
 */

public interface BaseView<T extends BasePresenter> {
    void initPresenter();
    void setPresenter(T presenter);
}
