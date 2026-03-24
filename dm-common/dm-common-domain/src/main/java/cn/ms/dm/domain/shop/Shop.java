package cn.ms.dm.domain.shop;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name Shop
 * @date 2025-08-14 15:15
 * @email lmtemail163@163.com
 * @description 商店
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "商店")
@TableName("sys_shop")
public class Shop extends BaseModel {
    @Serial
    private static final long serialVersionUID = 1671207237752861528L;

    @TableField("npc_id")
    @Schema(description = "NPC ID")
    private Long npcId;
}
