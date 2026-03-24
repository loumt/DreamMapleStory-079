package cn.ms.dm.server.database.mapper;

import cn.ms.dm.domain.inventory.ItemInventoryEquipment;
import cn.ms.dm.domain.inventory.vo.ItemInventoryEquipmentVO;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemInventoryEquipmentMapper extends BaseMapper<ItemInventoryEquipment> {

}
