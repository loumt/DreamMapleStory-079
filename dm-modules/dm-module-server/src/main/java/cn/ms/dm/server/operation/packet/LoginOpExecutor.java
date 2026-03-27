package cn.ms.dm.server.operation.packet;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.domain.Result;
import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.core.enums.ResultEnum;
import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.domain.account.Account;
import cn.ms.dm.domain.account.Characters;
import cn.ms.dm.domain.account.vo.AccountVO;
import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.MapleWorld;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.maple.constant.netty.NettyConstant;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.client.MapleInventory;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.database.service.AccountService;
import cn.ms.dm.server.database.service.CharacterService;
import cn.ms.dm.server.handling.channel.ChannelServer;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.handling.login.LoginServer;
import cn.ms.dm.server.operation.CacheOperation;
import cn.ms.dm.server.operation.WzOperation;
import cn.ms.dm.server.operation.packet.creator.LoginPacketCreator;
import cn.ms.dm.server.operation.packet.creator.MaplePacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LouMT
 * @name MapleLoginExecutor
 * @date 2026-02-13 13:57
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][LOGIN]")
public class LoginOpExecutor {
    @Resource
    private AccountService accountService;
    @Resource
    private CharacterService characterService;
    @Resource
    private AppConfigProperties properties;
    @Resource
    @Lazy
    private LoginServer loginServer;

    /**
     * 登录请求
     */
    @PacketHandler(ReceivePacketOpcode.LOGIN_PASSWORD)
    public void handleLogin(final LittleEndianAccessor slea,final MapleClient client) {
        String accountName = slea.readMapleAsciiString();
        String password = slea.readMapleAsciiString();
        log.info("账号登录=> 账号:{} 密码:{}", accountName, password);

        Result<AccountVO> loginResult = accountService.login(accountName, password);
        if(!loginResult.eq(ResultEnum.SUCCESS)){
            if(!client.isAttemptLogin()) client.getSession().close();

            client.sendPacket(LoginPacketCreator.getLoginFailed(loginResult.getTag()));
            client.getSession().writeAndFlush(MessagePacketCreator.serverNotice(1, loginResult.getTag().getMsg()));
            return;
        }

        //成功登录
        client.setAccountId(loginResult.getData().getId().intValue());
        client.setAccountVip(loginResult.getData().getVip());
        client.setAccountName(loginResult.getData().getUsername());
        client.setGender(loginResult.getData().getGender());

        //获取世界连接数据
        if(ChannelServerGroup.noChannel()){
            client.sendPacket(LoginPacketCreator.getLoginFailed(ResultEnum.LOGIN_NO_CHANNEL_SERVER));
            return;
        }


        //缓存，这个缓存数据不知道用来做什么
        loginServer.getStorage().register(client);
        //发送登录成功数据包
        client.sendPacket(LoginPacketCreator.getAuthSuccessRequest(client.getAccountId(), client.getAccountName(), client.getGender()));

        //这个数据包拆解一下
        client.getSession().writeAndFlush(LoginPacketCreator.getServerList(properties.getName(), properties.getMessage(), properties.getChannelCount(), MapleWorld.WORLD_EXAMPLE));
        client.sendPacket(LoginPacketCreator.getEndOfServerList());
    }

    /**
     * 请求服务器列表
     */
    @PacketHandler(ReceivePacketOpcode.SERVER_LIST_REQUEST)
    public void handleServerListRequest(final LittleEndianAccessor slea,final MapleClient client) {
        client.getSession().writeAndFlush(LoginPacketCreator.getServerList(properties.getName(), properties.getMessage(), properties.getChannelCount(), MapleWorld.WORLD_EXAMPLE));
        client.sendPacket(LoginPacketCreator.getEndOfServerList());
    }

    /**
     * 获取角色列表
     */
    @PacketHandler(ReceivePacketOpcode.CHAR_LIST_REQUEST)
    public void handleCharacterListRequest(final LittleEndianAccessor slea,final MapleClient client) {
        final int world = slea.readByte();
        final int channelNo = slea.readByte() + 1;
        client.setWorld(world);
        client.setChannelNo(channelNo);
        List<MapleCharacter> characters = characterService.listMapleCharacter(client.getAccountId().longValue(), world);
        client.sendPacket(LoginPacketCreator.getCharacterList(characters));
    }

    /**
     * 设置账号本人性别
     */
    @PacketHandler(ReceivePacketOpcode.SET_GENDER)
    public void handleSetGender(LittleEndianAccessor slea, MapleClient client) {
        byte gender = slea.readByte();
        String username = slea.readMapleAsciiString();

        Gender changeGender = Gender.codeOf(gender);
        if(changeGender == null || !client.getAccountName().equals(username)) {
            client.getSession().close();
            return;
        }

        client.setGender(changeGender);
        characterService.changeGender(client.getAccountId().longValue(), changeGender);
        client.sendPacket(LoginPacketCreator.genderChanged(client));
    }

    /**
     * 删除角色
     */
    @PacketHandler(ReceivePacketOpcode.DELETE_CHAR)
    public void handleDeleteCharacter(LittleEndianAccessor slea, MapleClient client) {
        if(slea.available() < 7) return;
        //TODO 校验1: 二次密码?????
        slea.readMapleAsciiString();

        final int characterId = slea.readInt();
        //校验2: 是否为本账号角色
        Characters characters = characterService.getById(characterId);
        if(characters == null || ObjectUtil.equal(characters.getAccountId(), client.getAccountId())) {
            log.error("非法删除其他账号角色...");
            client.getSession().close();
            return;
        }

        //校验3: 是否为活动中的角色
        MapleCharacter mapleCharacter = ChannelServerGroup.getCharacter(characterId);
        if(ObjectUtil.isNotNull(mapleCharacter)){
            log.error("角色正在频道服务器中，非法删除");
            client.getSession().close();
            return;
        }
        //业务处理
        characterService.deleteCharacters((long) characterId);
        //返回封包
        client.sendPacket(LoginPacketCreator.deleteCharacterResponse(characterId, 0));
    }

    /**
     * 创建角色
     */
    @PacketHandler(ReceivePacketOpcode.CREATE_CHAR)
    public void handleCreateCharacter(LittleEndianAccessor slea, MapleClient client) {
        boolean fullCharacterSlot = characterService.isFullCharactersSlot(client.getAccountId().longValue(), client.getWorld());
        if(fullCharacterSlot){
            log.error("角色栏位已满， accountId:{}", client.getAccountId());
            client.getSession().close();
            return;
        }

        final String name = slea.readMapleAsciiString();
        final int job = slea.readInt();
        final int face = slea.readInt();
        final int hair = slea.readInt();

        //这四个啥玩意儿
        final int topItemId = slea.readInt();
        final int bottomItemId = slea.readInt();
        final int shoesItemId = slea.readInt();
        final int weaponItemId = slea.readInt();

        MapleCharacter characters = characterService.getDefaultMapleCharacters(client);
        characters.setName(name);
        characters.setFace(face);
        characters.setHair(hair);
        characters.setSkinColor(0);
        characters.setJob(job);

        MapleInventory equipped = characters.getInventory(MapleInventoryType.EQUIPPED);
        IMapleItem topItem = CacheOperation.Equip.getByItemId(topItemId);
        equipped.addItem(topItem);

        IMapleItem bottomItem = CacheOperation.Equip.getByItemId(bottomItemId);
        equipped.addItem(bottomItem);

        IMapleItem shoesItem = CacheOperation.Equip.getByItemId(shoesItemId);
        equipped.addItem(shoesItem);

        IMapleItem weaponItem = CacheOperation.Equip.getByItemId(weaponItemId);
        equipped.addItem(weaponItem);
    }

    /**
     * 获取服务器状态
     */
    @PacketHandler(ReceivePacketOpcode.SERVER_STATUS_REQUEST)
    public void handleServerStatus(LittleEndianAccessor slea, MapleClient client) {
        final int onlineClient = ChannelServerGroup.getOnlineClient();
        final int serverLimit = NettyConstant.USER_LIMIT;
        if (onlineClient >= serverLimit) {
            client.sendPacket(LoginPacketCreator.getServerStatus(2));
        } else if (onlineClient * 2 >= serverLimit) {
            client.sendPacket(LoginPacketCreator.getServerStatus(1));
        } else {
            client.sendPacket(LoginPacketCreator.getServerStatus(0));
        }
    }

    /**
     * 检测角色名
     */
    @PacketHandler(ReceivePacketOpcode.CHECK_CHAR_NAME)
    public void handleCheckCharacterName(LittleEndianAccessor slea, MapleClient client) {
        String name = slea.readMapleAsciiString();

        boolean illegal = StringUtil.length(name) < 2
                || StringUtil.length(name) > 15
                || characterService.exists(Wrappers.lambdaQuery(Characters.class).eq(Characters::getName, name))
                || WzOperation.Etc.isForbiddenName(name);
        client.sendPacket(LoginPacketCreator.charNameResponse(name, illegal));
    }

    /**
     * 角色选择
     */
    @PacketHandler(ReceivePacketOpcode.CHAR_SELECT)
    public void handleCharacterSelect(LittleEndianAccessor slea, MapleClient client) {
        final int characterId = slea.readInt();

        Characters characters = characterService.getById(characterId);
        if(ObjectUtil.isNull(characters) || !ObjectUtil.equal(characters.getAccountId(), client.getAccountId())){
            log.error("错误角色选择");
            client.getSession().close();
            return;
        }

        String sessionIp = client.getSession().remoteAddress().toString().split(":")[0];
        LambdaUpdateWrapper<Account> uq = Wrappers.lambdaUpdate(Account.class)
                .eq(Account::getId, client.getAccountId())
                .set(Account::getSessionIp, sessionIp)
                .set(Account::getLoginStatus, YesOrNo.YES);
        accountService.update(uq);

        ChannelServer server = ChannelServerGroup.getChannelServer(client.getChannelNo());
        client.sendPacket(MaplePacketCreator.getServerIP(server.getHost(), server.getPort(), characterId));
    }
}
