package cn.acyou.gemini.entity;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-11 下午 05:47]
 **/
public class Boss implements Serializable {

    private static final long serialVersionUID = -8527441629888974311L;
    private Integer id;
    private String name;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Boss{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
