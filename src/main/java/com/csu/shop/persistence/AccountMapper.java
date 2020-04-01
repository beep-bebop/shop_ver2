package com.csu.shop.persistence;

import com.csu.shop.domain.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    Account getAccountByUsername(String username);

    Account getAccountByUsernameAndPassword(String username, String password);

    void insertAccount(Account account);

    void insertProfile(Account account);

    void insertSignOn(Account account);

    void updateAccount(Account account);

    void updateProfile(Account account);

    void updateSignOn(Account account);
}
