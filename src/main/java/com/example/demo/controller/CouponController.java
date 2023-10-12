package com.example.demo.controller;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.service.CouponService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/coupon")
    public String couponList(Model model, @SessionAttribute(name = "userId", required = false) Long userId){

        //BoardService에서 만들어준 boardList가 반환되는데, list라는 이름으로 받아서 넘기겠다는 뜻
        model.addAttribute("list" , couponService.couponListDesc()); //4번
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }

        return "page/admin/coupon";
    }

    @GetMapping("/api/couponList")
    @ResponseBody // JSON 형식으로 반환하는 어노테이션 추가
    public List<Coupon> apiCouponList(Model model) {
        List<Coupon> couponList = couponService.couponList();
        return couponList;
    }

    @PostMapping("/api/checkCode")
    @ResponseBody
    public String checkCode(@RequestBody String couponcode){
        if (couponService.doesCouponExist(couponcode)) {
            return "코드 등록에 성공했습니다";
        } else {
            return "코드 등록에 실패했습니다";
        }
    }

    @GetMapping("/admin/coupon/write") //어떤 url로 접근할 것인지 정해주는 어노테이션 //localhost:8080/board/write
    public String couponWriteForm( @SessionAttribute(name = "userId", required = false) Long userId) {

        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        return "page/coupon/coupon_write";
    }

    @PostMapping("/admin/coupon/writepro")
    public String couponWritePro(Coupon coupon, Model model, @SessionAttribute(name = "userId", required = false) Long userId) throws Exception{

        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }

        couponService.write(coupon);
        model.addAttribute("message","쿠폰등록이 완료되었습니다");
        model.addAttribute("searchUrl","/admin/coupon");

        return "message";
    }




    @GetMapping("/coupon/view") //localhost:8080/board/view?id=1 //(get방식 파라미터)
    public String couponView(Model model, Integer id){
        /*상세페이지4*/
        model.addAttribute("coupon", couponService.couponView(id));
        return "page/coupon/coupon_view";
    }

    @GetMapping("/admin/coupon/delete")
        public String couponDelete(Integer id, @SessionAttribute(name = "userId", required = false) Long userId){
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }

        couponService.couponDelete(id);
        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "redirect:/admin/coupon";
    }

    //PathVariable이라는 것은 modify 뒤에있는 {id}부분이 인식이되서 Integer형태의 id로 들어온다는것
    @GetMapping("/admin/coupon/modify/{id}")
    public String couponModify(@PathVariable("id") Integer id, Model model,  @SessionAttribute(name = "userId", required = false) Long userId){
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        //수정4    //상세페이지에 있는 내용과, 수정페이지의 내용이 같기때문에 위 코드와 같은 것을 확인할수있다
        model.addAttribute("coupon", couponService.couponView(id));

        return "page/coupon/coupon_modify";
    }
    @PostMapping("/admin/coupon/update/{id}")
    public String couponUpdate(@PathVariable("id") Integer id, Coupon coupon,  @SessionAttribute(name = "userId", required = false) Long userId) throws IOException {
        //기존에있던글이 담겨져서온다.
        User loginUser = userService.getLoginUserById(userId);
        if(loginUser == null) {
            return "redirect:/product/list";
        }
        if(!loginUser.getRole().equals(User.UserRole.ADMIN)) {
            return "redirect:/product/list";
        }
        Coupon couponTemp = couponService.couponView(id);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        couponTemp.setCouponcode(coupon.getCouponcode());
        couponTemp.setDiscount(coupon.getDiscount());

        couponService.write(couponTemp); //추가 → 수정한내용을 boardService의 write부분에 넣기
        return "redirect:/admin/coupon";
    }
}