package com.mvp.project_mvp.mvp.presenter;

import com.mvp.project_mvp.mvp.bean.JokeTextBean;
import com.mvp.project_mvp.mvp.model.BaseDataBridge;
import com.mvp.project_mvp.mvp.model.BaseModel;
import com.mvp.project_mvp.mvp.model.JokeTextModeImpl;
import com.mvp.project_mvp.mvp.view.BaseView;

import java.util.List;

/**
 * by y on 2016/5/30.
 */
public class JokeTextPresenterImpl extends BasePresenterImpl<BaseView.JokeTextView>
        implements BasePresenter1.JokeTextPresenter, BaseDataBridge.JokeTextList {

    private final BaseModel.JokeTextListModel jokeListModel;

    public JokeTextPresenterImpl(BaseView.JokeTextView view) {
        super(view);
        this.jokeListModel = new JokeTextModeImpl();
    }

    @Override
    public void requestNetWork(int page, boolean isNull) {
        if (page == 1) {
            view.showProgress();
        } else {
            if (!isNull) {
                view.showFoot();
            }
        }
        jokeListModel.netWorkJoke(page, this);
    }

    @Override
    public void addData(List<JokeTextBean.JokeTextInfo> datas) {
        view.setData(datas);
        view.hideFoot();
        view.hideProgress();
    }

    @Override
    public void error() {
        view.hideFoot();
        view.hideProgress();
        view.netWorkError();
    }
}
