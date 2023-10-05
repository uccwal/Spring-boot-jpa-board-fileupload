package com.example.demo.service;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.Product;
import com.example.demo.repository.CouponRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {
    private List<Coupon> couponList;

    @Autowired //new를 써야하지만, 스프링부트가 알아서 읽어와서 주입을해준다.
    private CouponRepository couponRepository;

    public void write(Coupon coupon) {

        /*파일 저장*/
        couponRepository.save(coupon);
    }

    public List<Coupon> couponList(){
        //findAll : 테스트보드라는 클래스가 담긴 List를 반환하는것을 확인할수있다
        return couponRepository.findAll();
    }

    public Coupon couponView(Integer id){
        return couponRepository.findById(id).get(); //어떤게시글을 불러올지 지정을해주어야한다 (Integer값으로)
    }

    public void couponDelete(Integer id){ /*id값 1번을 넣어주면 1번을 삭제한다*/
        couponRepository.deleteById(id);
    }


    // 쿠폰 코드 검증
    public boolean doesCouponExist(String couponcode) {
        Coupon coupon = couponRepository.findByCouponcode(couponcode);
        return coupon != null; // 쿠폰 코드가 존재하면 true, 그렇지 않으면 false 반환
    }
}
