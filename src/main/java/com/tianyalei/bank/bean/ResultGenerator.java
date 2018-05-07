package com.tianyalei.bank.bean;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static BaseData genSuccessResult() {
        return new BaseData()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static BaseData genSuccessResult(Object data) {
        return new BaseData()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static PageData genSuccessResult(Object data, long count) {
        PageData pageData = new PageData();
        pageData.setCount(count);
        pageData.setCode(ResultCode.SUCCESS);
        pageData.setMessage(DEFAULT_SUCCESS_MESSAGE);
        pageData.setData(data);
        return pageData;
    }

    public static BaseData genFailResult(String message) {
        return new BaseData()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static BaseData genFailResult(ResultCode resultCode, String message) {
        return new BaseData()
                .setCode(resultCode)
                .setMessage(message);
    }
}
