package com.miaxis.common.exception;

/**
 * 业务异常对象
 * @author liu.qiao
 *
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }

    private static final long serialVersionUID = 1L;
}
