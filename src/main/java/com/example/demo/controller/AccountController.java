package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Coupon;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/write") //어떤 url로 접근할 것인지 정해주는 어노테이션 //localhost:8080/account/write
    public String accountWriteForm() {
        return "account/accountwrite";
    }

    @PostMapping("/account/writepro")
    public String accountWritePro(Account account, Model model) throws Exception{

        accountService.write(account);
        model.addAttribute("message","글작성이 완료되었습니다");
        model.addAttribute("searchUrl","/account/list");

        return "message";
    }

    @GetMapping("/account/list")
    public String accountList(Model model){
        //AccountService에서 만들어준 accountList가 반환되는데, list라는 이름으로 받아서 넘기겠다는 뜻
        model.addAttribute("list" , accountService.accountList()); //4번
        return "account/accountList";
    }


    @GetMapping("/account/delete")
    public String accountDelete(Integer id){

        accountService.accountDelete(id);
        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "redirect:/account/list";
    }

    @GetMapping("/api/accountList")
    @ResponseBody // JSON 형식으로 반환하는 어노테이션 추가
    public List<Account> apiAccountList(Model model) {
        List<Account> accountList = accountService.accountList();
        return accountList;
    }


}