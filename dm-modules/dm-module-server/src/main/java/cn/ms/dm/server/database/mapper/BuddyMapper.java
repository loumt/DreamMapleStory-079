package cn.ms.dm.server.database.mapper;

import cn.ms.dm.domain.buddy.Buddy;
import cn.ms.dm.domain.buddy.vo.BuddyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface BuddyMapper extends BaseMapper<Buddy> {
    List<BuddyVO> listDetail();
}
