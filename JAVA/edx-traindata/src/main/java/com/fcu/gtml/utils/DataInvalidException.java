package com.fcu.gtml.utils;

/**
 * 数据解析失败异常
 * 
 *
 */

public class DataInvalidException extends Exception {
    private static final long serialVersionUID = -7026795428311489169L;

    public DataInvalidException(String message) {
        super(message);
    }

    public DataInvalidException(Throwable e) {
        super(e);
    }

    public DataInvalidException(String message1, String message2) {
        super(message1 + "__" + message2);
    }

}
