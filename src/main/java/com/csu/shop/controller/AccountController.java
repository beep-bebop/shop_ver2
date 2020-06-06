package com.csu.shop.controller;

import com.csu.shop.domain.Account;
import com.csu.shop.domain.Product;
import com.csu.shop.service.AccountService;
import com.csu.shop.service.CatalogService;
import com.csu.shop.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/account")
@SessionAttributes({"account", "myList", "authenticated"})
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AccountService accountService;

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private LogService logService;
    @Autowired
    Account account;

    private static final List<String> LANGUAGE_LIST;
    private static final List<String> CATEGORY_LIST;

    static {
        List<String> langList = new ArrayList<String>();
        langList.add("ENGLISH");
        langList.add("CHINESE");
        LANGUAGE_LIST = Collections.unmodifiableList(langList);

        List<String> catList = new ArrayList<String>();
        catList.add("FISH");
        catList.add("DOGS");
        catList.add("REPTILES");
        catList.add("CATS");
        catList.add("BIRDS");

        CATEGORY_LIST = Collections.unmodifiableList(catList);
    }

    @GetMapping("/signOnForm")
    public String signOnForm(){
        return "account/SignOnForm";
    }

    @PostMapping("/signOn")
    public String signOn(String username, String password, Model model){
        account = accountService.getAccount(username,password);

        if(account == null){
            String msg = "Invalid username or password.  Signon failed.";
            model.addAttribute("msg", msg);
            return "account/SignOnForm";
        }
        else{
            account.setPassword(null);
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            boolean authenticated = true;
            model.addAttribute("account", account);
            //logService.log(username,"用户登入");
            logger.info("test");
            model.addAttribute("myList", myList);
            model.addAttribute("authenticated", authenticated);

            return "redirect:/catalog/main";
        }
    }

    @GetMapping("signOff")
    public String signOff(Model model, @SessionAttribute("account") Account account) {
        logService.log(account.getUsername(),"用户登出");
        List<Product> myList = null;
        boolean authenticated = false;
        account = null;
        model.addAttribute("account", null);
        model.addAttribute("myList", myList);
        model.addAttribute("authenticated", authenticated);
        return "redirect:/catalog/main";
    }

    @GetMapping("editAccountForm")
    public String editAccountForm(@SessionAttribute("account") Account account , Model model) {
        model.addAttribute("account", account);
        model.addAttribute("LANGUAGE_LIST", LANGUAGE_LIST);
        model.addAttribute("CATEGORY_LIST", CATEGORY_LIST);
        return "account/EditAccountForm";
    }

    @PostMapping("editAccount")
    public String editAccount(Account account, String repeatedPassword, Model model) {
        if (account.getPassword() == null || account.getPassword().length() == 0 || repeatedPassword == null || repeatedPassword.length() == 0) {
            String msg = "密码不能为空";
            model.addAttribute("msg", msg);
            return "account/EditAccountForm";
        } else if (!account.getPassword().equals(repeatedPassword)) {
            String msg = "两次密码不一致";
            model.addAttribute("msg", msg);
            return "account/EditAccountForm";
        } else {
            accountService.updateAccount(account);
            logService.log(account.getUsername(),"修改用户数据");
            account = accountService.getAccount(account.getUsername());
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            boolean authenticated = true;
            model.addAttribute("account", account);
                        model.addAttribute("myList", myList);
            model.addAttribute("authenticated", authenticated);
            return "redirect:/catalog/main";
        }
    }

    @GetMapping("newAccountForm")
    public String newAccountForm(Model model){
        model.addAttribute("newAccount",new Account());
        model.addAttribute("LANGUAGE_LIST", LANGUAGE_LIST);
        model.addAttribute("CATEGORY_LIST", CATEGORY_LIST);
        return "account/NewAccountForm";
    }

    @PostMapping("newAccount")
    public String newAccount(Account newAccount, String repeatedPassword, Model model) {
        if (newAccount.getPassword() == null || newAccount.getPassword().length() == 0 || repeatedPassword == null || repeatedPassword.length() == 0) {
            String msg = "密码不能为空";
            model.addAttribute("msg", msg);
            return "account/NewAccountForm";
        } else if (!newAccount.getPassword().equals(repeatedPassword)) {
            String msg = "两次密码不一致";
            model.addAttribute("msg", msg);
            return "account/NewAccountForm";
        } else {
            accountService.insertAccount(newAccount);
            logService.log(newAccount.getUsername(),"注册成功");
            newAccount = accountService.getAccount(newAccount.getUsername());
            List<Product> myList = catalogService.getProductListByCategory(newAccount.getFavouriteCategoryId());
            boolean authenticated = true;
            model.addAttribute("account", newAccount);
            model.addAttribute("myList", myList);
            model.addAttribute("authenticated", authenticated);
            return "redirect:/catalog/main";
        }
    }

    public Account getAccount() {
        return account;
    }

    public String getUsername() {
        return account.getUsername();
    }
}
