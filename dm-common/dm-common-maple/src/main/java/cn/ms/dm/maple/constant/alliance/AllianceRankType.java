package cn.ms.dm.maple.constant.alliance;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name AllianceRankType
 * @date 2026-03-25 9:36
 * @email lmtemail163@163.com
 * @description 联盟职级类型
 */
@Getter
@AllArgsConstructor
public enum AllianceRankType {
    LEADER(1, "联盟长"),
    VICE_LEADER(2, "副联盟长"),
    ELDER(3, "联盟长老"),
    MEMBER(5, "成员")
    ;

    @EnumValue
    private final int code;
    private final String type;

    public static AllianceRankType codeOf(int code){
        for (AllianceRankType value : AllianceRankType.values()) {
            if(value.code == code) return value;
        }
        return null;
    }
}
