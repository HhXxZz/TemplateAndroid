package cn.templateandroid.app.ui.fragment;
import cn.templateandroid.app.R;

/**
 * Created by Administrator on 2017/3/7.
 */

public enum SimplePage {
    REGISTER(1, R.string.app_name,RegisterFragment.class),
    TEST(2,R.string.app_name,TestFragment.class);
    private int title;
    private Class<?> clz;
    private int value;

    SimplePage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimplePage getPageByValue(int val) {
        for (SimplePage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}
