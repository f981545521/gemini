package cn.acyou.gemini.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author youfang
 * @version [1.0.0, 2019-04-22 下午 07:23]
 **/
public class SecuritiesTimesHuShenA implements Serializable {
    private static final long serialVersionUID = 1396827733034934764L;

    private Long id;
    private String code;
    private String abbreviation;
    private BigDecimal clinchToday;
    private BigDecimal columnOne;
    private BigDecimal columnTwo;
    private BigDecimal columnThree;
    private BigDecimal columnFour;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public BigDecimal getClinchToday() {
        return clinchToday;
    }

    public void setClinchToday(BigDecimal clinchToday) {
        this.clinchToday = clinchToday;
    }

    public BigDecimal getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(BigDecimal columnOne) {
        this.columnOne = columnOne;
    }

    public BigDecimal getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(BigDecimal columnTwo) {
        this.columnTwo = columnTwo;
    }

    public BigDecimal getColumnThree() {
        return columnThree;
    }

    public void setColumnThree(BigDecimal columnThree) {
        this.columnThree = columnThree;
    }

    public BigDecimal getColumnFour() {
        return columnFour;
    }

    public void setColumnFour(BigDecimal columnFour) {
        this.columnFour = columnFour;
    }
}
