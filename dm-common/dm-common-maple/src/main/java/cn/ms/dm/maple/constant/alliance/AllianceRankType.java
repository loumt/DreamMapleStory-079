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
    LEADER(0, "联盟长"),
    VICE_LEADER(1, "副联盟长"),
    ELDER(2, "联盟长老"),
    ELITE(3, "成员"),
    MEMBER(4, "成员"),
    ;

    @EnumValue
    private final int code;
    private final String title;

    public static AllianceRankType codeOf(int code){
        for (AllianceRankType value : AllianceRankType.values()) {
            if(value.code == code) return value;
        }
        return null;
    }
}
