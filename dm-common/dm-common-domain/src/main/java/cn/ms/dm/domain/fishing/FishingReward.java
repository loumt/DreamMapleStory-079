package cn.ms.dm.domain.fishing;

import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name FishingReward
 * @date 2026-01-30 14:03
 * @email lmtemail163@163.com
 * @description 钓鱼奖励
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_fishing_reward")
@Schema(description = "钓鱼奖励")
public class FishingReward extends BaseModel {
    @Serial
    private static final long serialVersionUID = -4300745155925549378L;

    @TableField
    @Schema(description = "物品ID")
    private Long itemId;

    @TableField
    @Schema(description = "几率")
    private Integer chance;

    @TableField
    @Schema(description = "删除")
    private YesOrNo isDelete;

    @TableField
    @Schema(description = "物品上限")
    private Integer expiration;
}
