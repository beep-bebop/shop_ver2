package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.csu.jpetstore.domain.Account;
import org.csu.jpetstore.service.AccountService;
import org.csu.jpetstore.service.OrderService;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;
//    @PostMapping("/session")
//    public ReturnEntity signIn(@RequestBody Map<String, String> params) {
//        JSONObject data = new JSONObject();
//        String password = params.get("password");
//        String username = params.get("username");
//        String encodedPassword = accountService.getPassword(username);
//        System.out.println(encodedPassword);
//        if (encodedPassword == null || encodedPassword.isEmpty()) {
//            return ReturnEntity.failedResult("用户名不存在");
//        }
//        if (!passwordEncoder.matches(password, encodedPassword)) {
//            return ReturnEntity.failedResult("用户名或密码错误");
//        }
//        Account account = accountService.getAccount(username);
//        System.out.println(account);
//        if (account == null) {
//            return ReturnEntity.failedResult("用户名不存在");
//        }
//        data.put("account", account);
//        return ReturnEntity.successResult(data);
//    }

    @DeleteMapping("/session")
    public ReturnEntity signOut(@RequestBody Map<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", null);
        return ReturnEntity.successResult(jsonObject);
    }

//    @PostMapping("/user")
//    public ReturnEntity signUp(@RequestBody Account account) {
//        JSONObject data = new JSONObject();
//        String username = account.getUsername();
//        String password = account.getPassword();
//        Account databaseAccount = accountService.getAccount(username);
//        if (databaseAccount != null) {
//            return ReturnEntity.failedResult("用户已存在");
//        } else {
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            //加密password
//            account.setPassword(passwordEncoder.encode(password));
//            accountService.insertAccount(account);
//            data.put("username", username);
//            return ReturnEntity.successResult(data);
//        }
//    }

    /**
     * 注册前判断用户是否存在
     *
     * @param params
     * @return
     */
    @GetMapping("/user")
    public ReturnEntity getUser(@RequestParam Map<String, String> params) {
        String username = params.get("username");
        Account account = accountService.getAccount(username);
        if (account == null) {
            return ReturnEntity.failedResult("null");
        } else {
            JSONObject data = new JSONObject();
            data.put("account", account);
            return ReturnEntity.successResult(data);
        }
    }

    /**
     * 拿用户
     *
     * @param params
     * @return
     */
    @GetMapping("/users")
    public ReturnEntity getUsers(@RequestParam Map<String, String> params) {
        System.out.println("AAAAAAAAAAAget list");
        JSONObject data = new JSONObject();
        List<Account> accounts = accountService.getAccount();
        for(Account account:accounts) {
            account.setZip(String.valueOf(orderService.getOrderNum(account.getUsername())));
        }
        data.put("accounts", accounts);
        return ReturnEntity.successResult(data);
    }

    @GetMapping("/user/search")
    public ReturnEntity searchUser(@RequestParam String username) {
        JSONObject data = new JSONObject();
        List<Account> accounts = accountService.searchAccount(username);
        for(Account account:accounts) {
            account.setZip(String.valueOf(orderService.getOrderNum(account.getUsername())));
        }
        data.put("accounts", accounts);
        return ReturnEntity.successResult(data);
    }

    @PostMapping("/user/edit")
    public ReturnEntity updateUserInfo(@RequestBody String account) {
        Account account1 = JSON.parseObject(account,Account.class);
        System.out.println(account+"\n"+account1.getUsername()+account1.getEmail());
        if (account1 == null) {
            return ReturnEntity.failedResult("缺少参数account");
        }
        if(account1.getPassword().isEmpty())
            return ReturnEntity.failedResult("密码不能为空");
        JSONObject data1 = new JSONObject();
        accountService.updateAccount(account1);
        accountService.updateSignOn(account1);
        data1.put("account", account1);
        return ReturnEntity.successResult(data1);
    }
//
//    @PutMapping("/user/detailinfo")
//    public ReturnEntity updateUserInfo(@RequestBody Account account) {
//        if (account == null) {
//            return ReturnEntity.failedResult("缺少参数account");
//        }
//        JSONObject data = new JSONObject();
//        accountService.updateAccount(account);
//        data.put("account", account);
//        return ReturnEntity.successResult(data);
//    }

    @GetMapping("/user/detailinfo")
    public ReturnEntity getUserInfo(@RequestAttribute String username) {
        JSONObject data = new JSONObject();
        Account account = accountService.getAccount(username);
        data.put("account", account);
        return ReturnEntity.successResult(data);
    }
}
