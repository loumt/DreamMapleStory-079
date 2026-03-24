package cn.ms.dm.domain.account.vo;

import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.maple.constant.account.VipLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name AccountLoginVO
 * @date 2026-02-24 15:58
 * @email lmtemail163@163.com
 * @description 账号VO
 */
@Data
@Schema(description = "账号VO")
public class AccountVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1188751405552183490L;

    @Schema(description = "账号ID")
    private Long id;

    @Schema(description = "VIP等级")
    private VipLevel vip;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "性别 -1未知 1男 2女")
    private Gender gender;

}
