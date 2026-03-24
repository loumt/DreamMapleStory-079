package cn.ms.dm.domain.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name BaseLog
 * @date 2025-08-14 15:05
 * @email lmtemail163@163.com
 * @description 日志基础类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "日志基础类")
public abstract class BaseLog extends BaseModel {
    @Serial
    private static final long serialVersionUID = -2938321497743548671L;

    @TableLogic
    @Schema(description = "是否删除 0未删除 1已删除")
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "记录时间")
    private LocalDateTime createTime;
}
