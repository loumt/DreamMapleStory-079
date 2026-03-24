package cn.ms.dm.domain.account;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name CharacterSlot
 * @date 2026-03-16 10:12
 * @email lmtemail163@163.com
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_characters_slot")
@Schema(description = "角色插槽表")
public class CharactersSlot extends BaseModel {
    @Serial
    private static final long serialVersionUID = -4807843040073999016L;
    @TableField
    @Schema(description = "账号ID")
    private Long accountId;
    @TableField
    @Schema(description = "世界序号")
    private Integer world;
    @TableField
    @Schema(description = "槽数量")
    private Integer slot;
}