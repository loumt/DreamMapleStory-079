package cn.ms.dm.domain.account;

import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.account.VipLevel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name Account
 * @date 2026-01-28 16:30
 * @email lmtemail163@163.com
 * @description 账号表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_account")
@Schema(description = "账号")
public class Account extends BaseModel {
    @Serial
    private static final long serialVersionUID = 7684972421991298414L;

    @TableField("username")
    @Schema(description = "账号")
    private String username;

    @TableField("password")
    @Schema(description = "密码")
    private String password;

    @TableField("salt")
    @Schema(description = "账号")
    private String salt;

    @TableField("login_status")
    @Schema(description = "登录状态")
    private YesOrNo loginStatus;

    @TableField("birthday")
    @Schema(description = "生日")
    private LocalDate birthday;

    @TableField
    @Schema(description = "性别 -1未知 0男 1女")
    private Gender gender;

    @TableField("gm")
    @Schema(description = "GM标识位")
    private YesOrNo gm;

    @TableField("vip")
    @Schema(description = "vip")
    private VipLevel vip;

    @TableField("banned")
    @Schema(description = "禁用")
    private YesOrNo banned;

    @TableField("ban_reason")
    @Schema(description = "禁用原因")
    private String banReason;

    @TableField
    @Schema(description = "点券数")
    private Integer meso;

    @TableLogic
    @TableField("is_delete")
    @Schema(description = "是否删除")
    private YesOrNo isDelete;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
