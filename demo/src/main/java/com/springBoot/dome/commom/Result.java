package com.springBoot.dome.commom;

public class Result {

	private String status;
	 
    private String message;
 
    private Object data;
    
    public static String SUCCESS_STATUS = "1";
    public static String SUCCESS_MSG = "操作成功";
    
    public static String ERROR_DATA_WRONG_FORMAT = "11";
    public static String ERROR_DATA_WRONG_FORMAT_MSG = "数据格式错误";
    
    public static String ERROR_DATA_MISSING = "12";
    public static String ERROR_DATA_MISSING_MSG = "数据缺失";
    
    public static String ERROR_LOGIC = "13";
    public static String ERROR_LOGIC_MSG = "逻辑错误";
    
    public static String ERROR_UNKNOWN = "14";
    public static String ERROR_UNKNOWN_MSG = "未知错误";
    
    public static String ERROR_USER_NOT_FIND = "101";
    public static String ERROR_USER_NOT_FIND_MSG = "用户不存在";
    
    public static String ERROR_SYSTEM = "1000";
    public static String ERROR_SYSTEM_MSG = "系统异常";
    
    public static String ERROR_CHECK_TOKEN = "999";
    public static String ERROR_CHECK_TOKEN_MSG = "TOKEN验证失败";
    
    public static String ERROR_NEED_LOGIN = "100";
    public static String ERROR_NEED_LOGIN_MSG = "请先登录";
    
    public static String ERROR_NO_RESULT = "30";
    public static String ERROR_NO_RESULT_MSG = "暂无结果";
    
    public Result() {}
    
    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }
 
    public String getStatus() {
        return status;
    }
 
    public Result setStatus(String status) {
        this.status = status;
        return this;
    }
 
    public String getMessage() {
        return message;
    }
 
    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
 
    public Object getData() {
        return data;
    }
 
    public Result setData(Object data) {
        this.data = data;
        return this;
    }
 
    public static Result success(Object data, String msg, String status){
    	Result response = new Result(status, msg);
        response.setData(data);
        return response;
    }
    
    public static Result error(Object data, String msg, String status) {
    	Result response = new Result(status, msg);
    	if (data != null) {
    		response.setData(data);
    	}
        return response;
    }

}
