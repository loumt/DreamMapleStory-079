package cn.ms.dm.domain.guild;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name Guild
 * @date 2026-02-28 16:44
 * @email lmtemail163@163.com
 * @description 公会
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_guild")
@Schema(description = "公会")
public class Guild extends BaseModel {
    @Serial
    private static final long serialVersionUID = 2618447257341706034L;

    @TableField
    @Schema(description = "公会名")
    private String name;

    @TableField
    @Schema(description = "公会长ID")
    private String leaderId;

    @TableField
    @Schema(description = "公会公告")
    private String notice;

    @TableField
    @Schema(description = "联盟ID")
    private Long allianceId;

}