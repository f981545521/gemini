package cn.acyou.gemini.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 07:23]
 **/
public class MutualMarket implements Serializable {

    private static final long serialVersionUID = -3374158493423899634L;
    private Long id;
    private String code;
    private String name;
    private String columnOne;
    private String columnTwo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }
}
