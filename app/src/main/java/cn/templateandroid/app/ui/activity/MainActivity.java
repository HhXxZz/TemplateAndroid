package cn.templateandroid.app.ui.activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.templateandroid.app.R;
import cn.templateandroid.app.adapter.TestRecyclerViewAdapter;
import cn.templateandroid.app.base.BaseActivity;
import cn.templateandroid.app.bean.HotMovieBean;
import cn.templateandroid.app.bean.News;
import cn.templateandroid.app.rxbus.BusCodeConstants;
import cn.templateandroid.app.rxbus.RxBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    TestRecyclerViewAdapter mAdapter;
    List<HotMovieBean> list = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RxBus.getInstance().toObservable(BusCodeConstants.GEGISTER_CONSTANTS,News.class)
                .subscribe(news -> news.getName());

        initRecycler();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    Consumer<HotMovieBean> consumer = new Consumer<HotMovieBean>() {
        @Override
        public void accept(HotMovieBean hotMovieBean) {
            list.add(hotMovieBean);
            mAdapter.notifyDataSetChanged();
        }
    };


    Observable<HotMovieBean> observable = Observable.create(new ObservableOnSubscribe<HotMovieBean>() {
        @Override
        public void subscribe(ObservableEmitter<HotMovieBean> e) throws Exception {
           while(true){
               HotMovieBean b = new HotMovieBean();
               b.setTitle("test");
               e.onNext(b);
               Thread.sleep(1000);
           }
        }
    });

//    Observable<String> observable1 = Observable.create((e)->{
//        while(true){
//            HotMovieBean b = new HotMovieBean();
//            b.setTitle("test");
//            e.onNext(b.getTitle());
//            Thread.sleep(1000);
//        }
//    });

    private void initRecycler() {
        mAdapter = new TestRecyclerViewAdapter(R.layout.item_test,list);
        for(int i=0;i<0;i++){
            HotMovieBean b = new HotMovieBean();
            b.setTitle("test"+i);
            list.add(b);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//
//            }
//        });
        mAdapter.setOnLoadMoreListener(()->{

        });
    }

    @Override
    protected void initInjector() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    News news = new News();
                    news.setName("66666666666666");
                    RxBus.getInstance().post(BusCodeConstants.GEGISTER_CONSTANTS,news);
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
}
