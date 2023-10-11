package com.example.demo.controller;

import com.example.demo.entity.Buylog;
import com.example.demo.service.BuylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class BuylogController {

    @Autowired
    private BuylogService buylogService;

    @GetMapping("/admin/buylog")
    public String buylogList(Model model){
        //BoardService에서 만들어준 boardList가 반환되는데, list라는 이름으로 받아서 넘기겠다는 뜻
        model.addAttribute("list" , buylogService.buylogListDesc()); //4번
        return "page/admin/buylog";
    }


    @GetMapping("/buylog/write") //어떤 url로 접근할 것인지 정해주는 어노테이션 //localhost:8080/board/write
    public String buylogWriteForm() {
        return "page/buylog/buylog_write";
    }

    @PostMapping("/buylog/writepro")
    public String buylogWritePro(Buylog buylog, Model model) throws Exception{

        buylogService.write(buylog);
        model.addAttribute("message","주문이 완료되었습니다");
        model.addAttribute("searchUrl","/product/list");

        return "message";
    }




    @GetMapping("/buylog/view") //localhost:8080/board/view?id=1 //(get방식 파라미터)
    public String buylogView(Model model, Integer id){
        /*상세페이지4*/
        model.addAttribute("buylog", buylogService.buylogView(id));
        return "page/buylog/buylog_view";
    }

    @GetMapping("/admin/buylog/delete")
        public String buylogDelete(Integer id){
        buylogService.buylogDelete(id);
        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "redirect:/admin/buylog";
    }

    //PathVariable이라는 것은 modify 뒤에있는 {id}부분이 인식이되서 Integer형태의 id로 들어온다는것
    @GetMapping("/admin/buylog/modify/{id}")
    public String buylogModify(@PathVariable("id") Integer id, Model model){

        //수정4    //상세페이지에 있는 내용과, 수정페이지의 내용이 같기때문에 위 코드와 같은 것을 확인할수있다
        model.addAttribute("buylog", buylogService.buylogView(id));

        return "page/buylog/buylog_modify";
    }
    @PostMapping("/admin/buylog/update/{id}")
    public String buylogUpdate(@PathVariable("id") Integer id, Buylog buylog) throws IOException {
        //기존에있던글이 담겨져서온다.
        Buylog buylogTemp = buylogService.buylogView(id);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        buylogTemp.setId(buylog.getId());
        buylogTemp.setState(buylog.getState());


        buylogService.write(buylogTemp); //추가 → 수정한내용을 boardService의 write부분에 넣기
        return "redirect:/admin/buylog";
    }
}