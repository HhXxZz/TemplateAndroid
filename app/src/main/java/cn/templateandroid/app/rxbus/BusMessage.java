package cn.templateandroid.app.rxbus;

/**
 * Created by Administrator on 2017/3/9.
 */

public class BusMessage {
    private int code;
    private Object object;

    public BusMessage(int code,Object object){
        this.code = code;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
