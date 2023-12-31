package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/") //어떤 url로 접근할 것인지 정해주는 어노테이션 //localhost:8080/board/write
    public String main() {
        return "redirect:/product/list";
    }






}