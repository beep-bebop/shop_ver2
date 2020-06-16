package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.Account;
import org.csu.jpetstore.persistence.AccountMapper;
import org.csu.jpetstore.persistence.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private OrderMapper orderMapper;

  public Account getAccount(String username) {

    return accountMapper.getAccountByUsername(username);
  }

//  public Account getAccount(String username, String password) {
//    Account account = new Account();
//    account.setUsername(username);
//    account.setPassword(password);
//    return accountMapper.getAccountByUsernameAndPassword(account);
//  }

  public List<Account> getAccount() {
    return accountMapper.getAccount();
  }

  public List<Account> searchAccount(String key) {
    return accountMapper.getAccountByKey(key);
  }

  @Transactional
  public void insertAccount(Account account) {
    accountMapper.insertAccount(account);
    accountMapper.insertProfile(account);
    accountMapper.insertSignon(account);
  }

  @Transactional
  public void updateAccount(Account account) {
    accountMapper.updateAccount(account);
    accountMapper.updateProfile(account);

    if (account.getPassword() != null && account.getPassword().length() > 0) {
      accountMapper.updateSignon(account);
    }
  }

  public String getPassword(String username) {
    return accountMapper.getPasswordByUsername(username);
  }

  public void updateSignOn(Account account) {
    accountMapper.updateSignon(account);
  }
}
