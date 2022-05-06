package edu.cmu.demo.exception;

import lombok.Data;

/**
 * @description: [wrapper class for exception message]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/5 15:53]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/5 15:53]
 * @updateRemark: [initial commit]
 */

@Data
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
