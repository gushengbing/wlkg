package com.wlkg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnums {
    /**
     * 1xxxx:用户问题
     * 2xxxx:商品问题
     * 3xxxx:分类问题
     * 4xxxx:品牌问题
     * 5xxxx:规格问题
     * 6xxxx:搜索问题
     * 7xxxx:问题
     */
    INVALID_VERFIY_CODE(10001,"验证码错误"),
    INVALID_USERNAME_PASSWORD(10002,"用户名或密码不正确"),
    NO_AUTHORIZED(10003,"未授权用户!!!"),

    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    INEM_IS_NOT_FOUND(404,"商品不存在"),

    BRAND_IS_NOT(40000,"品牌不存在!"),
    CATEGORY_IS_EMPTY(300000,"分类不存在"),
    ADD_IS_FAIL(300001,"添加分类失败"),

    SPECGROUP_IS_NOT_FOUND(50000,"查询的 规格组不存在"),
    SPECPARAM_IS_NOT_FOUND(500001,"查询的规格参数不存在"),
    GOODS_NOT_FOUND(200000,"商品不存在"),
    OK(6666,"成功"),
    GOODS_UPDATE_ERROR(44444,"修改详情失败"),
    ;

    private int code;
    private String msg;
}

