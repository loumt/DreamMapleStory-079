package cn.ms.dm.domain.alliance;

import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name AllianceGuild
 * @date 2026-03-25 9:23
 * @email lmtemail163@163.com
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_alliance_guild")
@Schema(description = "联盟公会")
public class AllianceGuild extends BaseModel {
    @Serial
    private static final long serialVersionUID = 3565065347606233650L;

    @TableField
    @Schema(description = "联盟ID")
    private Long allianceId;

    @TableField
    @Schema(description = "公会ID")
    private Long guildId;

    @TableField(value = "join_time", fill = FieldFill.INSERT)
    @Schema(description = "加入时间")
    private LocalDateTime joinTime;
}
