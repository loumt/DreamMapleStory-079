package cn.ms.dm.domain.party;

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
 * @name Party
 * @date 2026-03-24 15:29
 * @email lmtemail163@163.com
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_party")
@Schema(description = "组队")
public class Party extends BaseModel {
    @Serial
    private static final long serialVersionUID = 8635409135120853011L;

    @TableField
    @Schema(description = "创建人ID")
    private Long characterId;

    @TableLogic
    @TableField("is_delete")
    @Schema(description = "是否删除")
    private YesOrNo isDelete;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
