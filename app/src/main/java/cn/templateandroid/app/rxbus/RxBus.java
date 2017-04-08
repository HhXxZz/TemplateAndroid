package cn.templateandroid.app.rxbus;


import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * Created by Administrator on 2017/3/9.
 *
 */

public class RxBus {
    private static volatile RxBus mInstance;
    private Relay<Object>bus;

    private RxBus(){
        bus = PublishRelay.create().toSerialized();
    }

    /**
     * 获取RxBus单例
     * @return
     */
    public static RxBus getInstance(){
        RxBus rxBus2 = mInstance;
        if(mInstance == null){
            synchronized (RxBus.class){
                rxBus2 = mInstance;
                if(mInstance == null){
                    rxBus2 = new RxBus();
                    mInstance = rxBus2;
                }
            }
        }
        return rxBus2;
    }

    public void post(Object object){
        bus.accept(object);
    }

    /**
     * 发送一个带参数的事件
     * @param code
     * @param object
     */
    public void post(int code,Object object){
        bus.accept(new BusMessage(code,object));
    }

    public <T> Observable<T> toObservable(Class<T> eventType){
        return bus.ofType(eventType);
    }

    /**
     * 订阅一个带参数的事件
     * @param code
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(final int code, final Class<T> eventType) {
        return bus.ofType(BusMessage.class)
                .filter(new Predicate<BusMessage>() {
                    @Override
                    public boolean test(BusMessage busMessage) throws Exception {
                        //过滤掉不同注册类型
                        return code == busMessage.getCode() && eventType.isInstance(busMessage.getObject());
                    }
                })
                //map 转换事件
                .map(new Function<BusMessage, Object>() {
                    @Override
                    public Object apply(BusMessage busMessage) throws Exception {
                        return busMessage.getObject();
                    }
                }).cast(eventType);
    }

}
