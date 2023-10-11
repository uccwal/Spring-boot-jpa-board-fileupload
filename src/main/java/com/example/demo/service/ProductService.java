package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.entity.Product;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired //new를 써야하지만, 스프링부트가 알아서 읽어와서 주입을해준다.
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    public void write(Product product, MultipartFile file) throws IOException {
        /*우리의 프로젝트경로를 담아주게 된다 - 저장할 경로를 지정*/
        String projectPath = System.getProperty("user.dir") + "/src/main/webapp/img";

        /*식별자 . 랜덤으로 이름 만들어줌*/
        UUID uuid = UUID.randomUUID();

        /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
        String fileName = uuid + "_" + file.getOriginalFilename();

        /*빈 껍데기 생성*/
        /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        /*디비에 파일 넣기*/
        product.setFilename(fileName);
        /*저장되는 경로*/
        product.setFilepath("/img/" + fileName); /*저장된파일의이름,저장된파일의경로*/

        /*파일 저장*/
        productRepository.save(product);
    }

    public List<Product> productList(){
        //findAll : 테스트보드라는 클래스가 담긴 List를 반환하는것을 확인할수있다
        return productRepository.findAll();
    }
    public List<Product> productListDesc(){
        String jpql = "SELECT e FROM Product e ORDER BY e.id DESC";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);

        List<Product> resultList = query.getResultList();

        return resultList;
    }

    public Product productView(Integer id){
        return productRepository.findById(id).get(); //어떤게시글을 불러올지 지정을해주어야한다 (Integer값으로)
    }

    public void productDelete(Integer id){ /*id값 1번을 넣어주면 1번을 삭제한다*/
        productRepository.deleteById(id);
    }
}
