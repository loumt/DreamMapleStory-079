package cn.ms.dm.domain.buddy;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name BuddySlot
 * @date 2026-03-30 16:34
 * @email lmtemail163@163.com
 * @description 好友数量
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_buddy_slot")
@Schema(description = "好友数量")
public class BuddySlot extends BaseModel {
    @Serial
    private static final long serialVersionUID = 6522505096498438897L;

    @TableField
    @Schema(description = "玩家ID")
    private Long characterId;

    @TableField
    @Schema(description = "槽数量")
    private Integer slot;
}
