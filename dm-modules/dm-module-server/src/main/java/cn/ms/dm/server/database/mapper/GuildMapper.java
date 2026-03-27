package cn.ms.dm.server.database.mapper;


import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.domain.guild.vo.GuildCharacterVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuildMapper extends BaseMapper<Guild> {

    List<GuildCharacterVO> selectMembers(@Param("guildId") Long guildId);

}
