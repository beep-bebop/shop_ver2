package com.csu.shop.service;

import com.csu.shop.domain.Account;
import com.csu.shop.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public Account getAccount(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        return accountMapper.getAccountByUsernameAndPassword(username, password);
    }

    public void insertAccount(Account account) {
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignOn(account);
    }

    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        Optional.ofNullable(account.getPassword()).filter(password -> password.length() > 0)
                .ifPresent(password -> accountMapper.updateSignOn(account));
    }
}

