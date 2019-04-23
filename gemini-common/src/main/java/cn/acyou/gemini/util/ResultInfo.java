package cn.acyou.gemini.util;

import cn.acyou.gemini.constant.GaminiConstant;

import java.io.Serializable;

/**
 * @author youfang
 * @date 2017-12-05 17:22
 **/
public class ResultInfo implements Serializable {
    private static final long serialVersionUID = -100721119889850602L;

    private int code;
    private String message;
    private Object data;

    public ResultInfo() {
        this.code = GaminiConstant.SUCCESS;
        this.message = GaminiConstant.MESSAGE;
    }

    public ResultInfo(Object data) {
        this.code = GaminiConstant.SUCCESS;
        this.message = GaminiConstant.MESSAGE;
        this.data = data;
    }


    public ResultInfo(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultInfo success(String message){
        return new ResultInfo(GaminiConstant.SUCCESS, message, null);
    }
    public static ResultInfo success(String message, Object data){
        return new ResultInfo(GaminiConstant.SUCCESS, message, data);
    }
    public static ResultInfo error(int code, String message){
        return new ResultInfo(code, message, null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultInfo{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
