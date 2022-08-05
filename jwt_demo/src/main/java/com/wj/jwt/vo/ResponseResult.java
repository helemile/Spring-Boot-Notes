package com.wj.jwt.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseResult {

    private Integer status = 1; // 1 成功 -1，失败

    private String message = "成功！";

    private Object data;


    public ResponseResult(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    public ResponseResult(Object data){
        this.data = data;
    }

    public ResponseResult(Integer status, String message,Object data){
        this(status,message);
        this.data = data;
    }
}
