package cn.ms.dm.domain.account;

import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name Character
 * @date 2026-01-30 16:56                                                                               
 * @email lmtemail163@163.com
 * @description 角色
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_characters")
@Schema(description = "角色表")
public class Characters extends BaseModel {
    @Serial
    private static final long serialVersionUID = -4807843040073999016L;
    @TableField
    @Schema(description = "账号ID")
    private Long accountId;
    @TableField
    @Schema(description = "世界序号")
    private Integer world;
    @TableField
    @Schema(description = "角色名")
    private String name;
    @TableField
    @Schema(description = "等级")
    private Integer level;
    @TableField
    @Schema(description = "经验")
    private Long exp;
    @TableField
    @Schema(description = "力量")
    private Integer strength;
    @TableField
    @Schema(description = "敏捷")
    private Integer dexterity;
    @TableField
    @Schema(description = "运气")
    private Integer luck;
    @TableField
    @Schema(description = "智力")
    private Integer intelligence;
    @TableField
    @Schema(description = "HP")
    private Integer hp;
    @TableField
    @Schema(description = "最大HP")
    private Integer maxHp;
    @TableField
    @Schema(description = "MP")
    private Integer mp;
    @TableField
    @Schema(description = "最大MP")
    private Integer maxMp;
    @TableField
    @Schema(description = "职业")
    private Integer job;
    @TableField
    @Schema(description = "性别 -1未知 0男 1女")
    private Gender gender;
    @TableField
    @Schema(description = "发型")
    private Integer hair;
    @TableField
    @Schema(description = "脸型")
    private Integer face;
    @TableField
    @Schema(description = "肤色")
    private Integer skinColor;
    @TableField
    @Schema(description = "人气")
    private Short fame;
    @TableField
    @Schema(description = "剩余能力点")
    private Short ap;
    @TableField
    @Schema(description = "剩余技能点")
    private String sp;
    @TableField("spawn_point")
    @Schema(description = "出生点")
    private Byte spawnPoint;
    @TableField
    @Schema(description = "GM标志位")
    private YesOrNo gm;
    @TableField
    @Schema(description = "公会ID")
    private Long guildId;
    @TableField
    @Schema(description = "地图ID")
    private Integer mapId;
    @TableField
    @Schema(description = "家族ID")
    private Long familyId;
    @TableField
    @Schema(description = "组队ID")
    private Integer partyId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @TableLogic
    @TableField("is_delete")
    @Schema(description = "是否删除")
    private YesOrNo isDelete;
}
