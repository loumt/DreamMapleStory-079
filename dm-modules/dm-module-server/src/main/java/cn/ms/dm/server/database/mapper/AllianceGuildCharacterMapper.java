package cn.ms.dm.server.database.mapper;

import cn.ms.dm.domain.alliance.AllianceGuildCharacter;
import cn.ms.dm.domain.alliance.vo.AllianceGuildCharacterVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllianceGuildCharacterMapper extends BaseMapper<AllianceGuildCharacter> {
    List<AllianceGuildCharacterVO> details(@Param("id") Long id);
}
