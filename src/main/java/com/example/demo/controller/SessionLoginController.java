package com.example.demo.controller;

import com.example.demo.dto.JoinRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SessionLoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String home(Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
//        model.addAttribute("loginType", "login");
        model.addAttribute("pageName", "로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }

        return "/page/login/home";
    }



    @GetMapping("/admin/login")
    public String loginPage(Model model) {
//        model.addAttribute("loginType", "login");
        model.addAttribute("pageName", "로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "page/login/login";
    }

    @PostMapping("/admin/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest, Model model) {
//        model.addAttribute("loginType", "login");
        model.addAttribute("pageName", "로그인");

        User user = userService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "page/login/login";
        }

        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
        // 세션에 userId를 넣어줌
        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

        return "redirect:/product/list";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, Model model) {
//        model.addAttribute("loginType", "login");
        model.addAttribute("pageName", "로그인");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/admin/login";
    }




}
