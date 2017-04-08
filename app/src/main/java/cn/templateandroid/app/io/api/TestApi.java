package cn.templateandroid.app.io.api;

import cn.templateandroid.app.bean.HotMovieBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface TestApi {

    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();
}
