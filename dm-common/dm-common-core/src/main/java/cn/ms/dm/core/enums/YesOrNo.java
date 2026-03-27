package cn.ms.dm.core.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNo {
    YES(1, "是"),
    NO(0, "否");

    @EnumValue
    private final int code;

    private final String msg;
}
