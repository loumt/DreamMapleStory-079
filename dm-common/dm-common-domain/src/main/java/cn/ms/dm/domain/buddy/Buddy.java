package cn.ms.dm.domain.buddy;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name Buddy
 * @date 2026-02-28 16:50
 * @email lmtemail163@163.com
 * @description 好友
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_buddy")
@Schema(description = "好友关系")
public class Buddy extends BaseModel {
    @Serial
    private static final long serialVersionUID = 4619437674413040374L;

    @TableField
    @Schema(description = "角色ID")
    private Long characterId;

    @TableField
    @Schema(description = "好友角色ID")
    private Long buddyId;
}