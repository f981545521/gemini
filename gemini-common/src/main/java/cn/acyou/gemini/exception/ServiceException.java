package cn.acyou.gemini.exception;

/**
 * 服务异常
 * @author youfang
 * @version [1.0.0, 2018-08-07 下午 03:40]
 **/
public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
