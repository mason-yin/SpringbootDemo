package edu.cmu.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: [wrapper class for Response]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/7 15:19]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/7 15:19]
 * @updateRemark: [initial commit]
 */
@Data
@AllArgsConstructor
public class Response {

    private String message;
    private Object data;

}
