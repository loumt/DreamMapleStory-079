package cn.ms.dm.core.domain;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author JRI
 */
@Getter
@Setter
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 925476279380866855L;

    private ResultEnum tag;

    private int code;

    private String msg;

    private T data;

    private Result(ResultEnum r) {
        this.setTag(r);
        this.setCode(r.getCode());
        this.setMsg(r.getMsg());
    }

    private Result(ResultEnum r, String msg) {
        this.setTag(r);
        this.setCode(r.getCode());
        this.setMsg(msg);
    }

    private Result(T data, ResultEnum r, String msg) {
        this.setTag(r);
        this.setCode(r.getCode());
        this.setData(data);
        this.setMsg(msg);
    }

    private Result(T data, ResultEnum r) {
        this.setTag(r);
        this.setCode(r.getCode());
        this.setMsg(r.getMsg());
        this.setData(data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(data, ResultEnum.SUCCESS);
    }

    public static <T> Result<T> fail() {
        return fail(ResultEnum.FAILURE);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(ResultEnum.FAILURE, msg);
    }

    public static <T> Result<T> fail(ResultEnum r) {
        return new Result<>(r);
    }

    public static <T> Result<T> fail(ResultEnum r, String msg) {
        return new Result<>(r, msg);
    }

    public boolean ne(ResultEnum c){
        return ObjectUtil.isNotNull(c) && !ObjectUtil.equal(c, getTag());
    }

    public boolean eq(ResultEnum c){
        return ObjectUtil.isNotNull(c) && ObjectUtil.equal(c, getTag());
    }

}
