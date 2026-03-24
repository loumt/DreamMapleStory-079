/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.ms.dm.maple.wz.base;

import java.util.List;

/**
 * @author Matze
 */
public interface MapleDataDirectoryEntry extends MapleDataEntry {
    /**
     * 获取子文件夹
     */
    List<MapleDataDirectoryEntry> getDirectories();

    /**
     * 获取文件(文件不包含在内)
     */
    List<MapleDataFileEntry> getFiles();

    /**
     * 获取文件入口
     */
    MapleDataEntry getEntry(String name);

}
