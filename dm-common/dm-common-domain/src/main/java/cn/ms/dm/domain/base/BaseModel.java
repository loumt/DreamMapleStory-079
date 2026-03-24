package cn.ms.dm.domain.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name BaseModel
 * @date 2025-08-14 15:13
 * @email lmtemail163@163.com
 * @description
 */
@Data
public class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 8289965464402209419L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;

}
