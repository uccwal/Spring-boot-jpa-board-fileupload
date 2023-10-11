package com.example.demo.service;

import com.example.demo.entity.Buylog;
import com.example.demo.entity.Buylog;
import com.example.demo.entity.Product;
import com.example.demo.repository.BuylogRepository;
import com.example.demo.repository.BuylogRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuylogService {
    private List<Buylog> buylogList;

    @Autowired //new를 써야하지만, 스프링부트가 알아서 읽어와서 주입을해준다.
    private BuylogRepository buylogRepository;
    @Autowired
    private EntityManager entityManager;

    public void write(Buylog buylog) {

        /*파일 저장*/
        buylogRepository.save(buylog);
    }

    public List<Buylog> buylogList(){
        //findAll : 테스트보드라는 클래스가 담긴 List를 반환하는것을 확인할수있다
        return buylogRepository.findAll();
    }

    public List<Buylog> buylogListDesc(){
        String jpql = "SELECT e FROM Buylog e ORDER BY e.id DESC";
        TypedQuery<Buylog> query = entityManager.createQuery(jpql, Buylog.class);

        List<Buylog> resultList = query.getResultList();

        return resultList;
    }

    public Buylog buylogView(Integer id){
        return buylogRepository.findById(id).get(); //어떤게시글을 불러올지 지정을해주어야한다 (Integer값으로)
    }

    public void buylogDelete(Integer id){ /*id값 1번을 넣어주면 1번을 삭제한다*/
        buylogRepository.deleteById(id);
    }



}
