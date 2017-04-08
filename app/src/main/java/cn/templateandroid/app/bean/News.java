package cn.templateandroid.app.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */

public class News implements Serializable {
    private String name;
    private String age;

    public News(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
