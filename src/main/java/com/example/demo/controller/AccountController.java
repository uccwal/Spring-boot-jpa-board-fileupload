package com.example.demo.controller;

import com.example.demo.entity.Account;

import com.example.demo.entity.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/account")
    public String accountList(Model model,@SessionAttribute(name = "userId", required = false) Long userId){
        //AccountService에서 만들어준 accountList가 반환되는데, list라는 이름으로 받아서 넘기겠다는 뜻

        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        model.addAttribute("list" , accountService.accountList()); //4번
        return "page/admin/account";
    }

    @GetMapping("/admin/account/write") //어떤 url로 접근할 것인지 정해주는 어노테이션 //localhost:8080/account/write
    public String accountWriteForm(@SessionAttribute(name = "userId", required = false) Long userId) {
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        return "page/account/account_write";
    }

    @PostMapping("/admin/account/writepro")
    public String accountWritePro(Account account, Model model, @SessionAttribute(name = "userId", required = false) Long userId) throws Exception{
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        accountService.write(account);
        model.addAttribute("message","계좌추가를 완료되었습니다");
        model.addAttribute("searchUrl","/admin/account");

        return "message";
    }




    @GetMapping("/admin/account/delete")
    public String accountDelete(Integer id, @SessionAttribute(name = "userId", required = false) Long userId){
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        accountService.accountDelete(id);
        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "redirect:/admin/account";
    }

    @GetMapping("/api/accountList")
    @ResponseBody // JSON 형식으로 반환하는 어노테이션 추가
    public List<Account> apiAccountList(Model model) {
        List<Account> accountList = accountService.accountList();
        return accountList;
    }


    //PathVariable이라는 것은 modify 뒤에있는 {id}부분이 인식이되서 Integer형태의 id로 들어온다는것
    @GetMapping("/admin/account/modify/{id}")
    public String accountModify(@PathVariable("id") Integer id, Model model, @SessionAttribute(name = "userId", required = false) Long userId){
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        //수정4    //상세페이지에 있는 내용과, 수정페이지의 내용이 같기때문에 위 코드와 같은 것을 확인할수있다
        model.addAttribute("account", accountService.accountView(id));

        return "page/account/account_modify";
    }
    @PostMapping("/admin/account/update/{id}")
    public String accountUpdate(@PathVariable("id") Integer id, Account account, @SessionAttribute(name = "userId", required = false) Long userId) throws IOException {

        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        //기존에있던글이 담겨져서온다.
        Account accountTemp = accountService.accountView(id);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        accountTemp.setAccountHolder(account.getAccountHolder());
        accountTemp.setBank(account.getBank());
        accountTemp.setNumber(account.getNumber());

        accountService.write(accountTemp); //추가 → 수정한내용을 boardService의 write부분에 넣기
        return "redirect:/admin/account";
    }


}