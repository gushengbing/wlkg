package com.wlkg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SucceedEnums {
    ADD_OK(6666,"添加成功"),
    ADD_FAIL(0000,"添加失败"),
    UPDATE_OK(6666,"修改成功"),
    DEL_OK(6666,"删除成功"),
    ;

    private int code;
    private String msg;
}
