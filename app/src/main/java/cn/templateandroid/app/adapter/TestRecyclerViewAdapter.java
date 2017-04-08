package cn.templateandroid.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.templateandroid.app.R;
import cn.templateandroid.app.bean.HotMovieBean;

/**
 * Created by Administrator on 2017/3/9.
 */

public class TestRecyclerViewAdapter extends BaseQuickAdapter<HotMovieBean,BaseViewHolder> {

    public TestRecyclerViewAdapter(int layoutResId, List<HotMovieBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotMovieBean item) {
        helper.setText(R.id.tv_test,item.getTitle());
    }

}
