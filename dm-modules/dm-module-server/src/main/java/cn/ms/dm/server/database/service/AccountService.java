package cn.ms.dm.server.database.service;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.domain.Result;
import cn.ms.dm.core.enums.ResultEnum;
import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.domain.account.Account;
import cn.ms.dm.domain.account.vo.AccountVO;
import cn.ms.dm.maple.aop.annotation.MapleMethodWatcher;
import cn.ms.dm.maple.netty.LoginCryptoLegacy;
import cn.ms.dm.server.database.mapper.AccountMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LouMT
 * @name AccountService
 * @date 2026-01-30 15:06
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class AccountService extends ServiceImpl<AccountMapper, Account> {

    @MapleMethodWatcher("重置账号状态")
    public void resetAccountLoginStatus(){
        long count = this.count();
        long resetCount = this.count(Wrappers.lambdaQuery(Account.class).eq(Account::getLoginStatus, YesOrNo.YES));
        this.update(Wrappers.lambdaUpdate(Account.class).eq(Account::getLoginStatus, YesOrNo.YES).set(Account::getLoginStatus, YesOrNo.NO));
        log.info("[重置账号状态] 账号数量: {} 重置数量: {}", count, resetCount);
    }


    public Result<AccountVO> login(String accountName, String password) {
        Account account = getOne(Wrappers.lambdaQuery(Account.class).eq(Account::getUsername, accountName));
        if(ObjectUtil.isNull(account)) return Result.fail(ResultEnum.LOGIN_NO_ACCOUNT);

        boolean checkPassword = LoginCryptoLegacy.isLegacyPassword(account.getPassword()) && LoginCryptoLegacy.checkPassword(password, account.getPassword());
        if(!checkPassword) return Result.fail(ResultEnum.LOGIN_PWD_ERROR);

        AccountVO vo = new AccountVO();
        vo.setId(account.getId());
        vo.setVip(account.getVip());
        vo.setGender(account.getGender());
        vo.setUsername(account.getUsername());
        return Result.ok(vo);
    }

}
