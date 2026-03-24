package cn.ms.dm.server.database.mapper;

import cn.ms.dm.domain.inventory.ItemInventory;
import cn.ms.dm.domain.inventory.vo.ItemInventoryEquipmentVO;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemInventoryMapper extends BaseMapper<ItemInventory> {
    List<ItemInventoryEquipmentVO> loadEquip(@Param("characterId") Long characterId, @Param("type") MapleInventoryType type);
}
