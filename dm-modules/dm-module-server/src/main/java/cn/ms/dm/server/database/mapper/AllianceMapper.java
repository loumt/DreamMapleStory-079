package cn.ms.dm.server.database.mapper;

import cn.ms.dm.domain.alliance.Alliance;
import cn.ms.dm.domain.alliance.vo.AllianceDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface AllianceMapper extends BaseMapper<Alliance> {
    AllianceDetailVO detail(@Param("id") Long id);
}
