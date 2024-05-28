package com.xj.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : xiongjun
 * @date : 2024/5/27 14:10
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Code {

    SUCCESS("200", "成功"),
    FAIL("5001", "失败"),
    PARAM_ERROR("400", "参数错误"),
    NOT_FOUND("404", "未找到"),
    UN_ERROR("5002", "调用失败"),
    ;

    private String code;

    private String info;

}
