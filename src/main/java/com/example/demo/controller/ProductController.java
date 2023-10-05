package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String productList(Model model){
        //BoardService에서 만들어준 boardList가 반환되는데, list라는 이름으로 받아서 넘기겠다는 뜻
        model.addAttribute("list" , productService.productList()); //4번
        return "page/product/product_list";
    }

    @GetMapping("/product/write") //어떤 url로 접근할 것인지 정해주는 어노테이션 //localhost:8080/board/write
    public String productWriteForm() {
        return "page/product/product_write";
    }

    @PostMapping("/product/writepro")
    public String productWritePro(Product product, Model model, MultipartFile file) throws Exception{

        productService.write(product, file);
        model.addAttribute("message","글작성이 완료되었습니다");
        model.addAttribute("searchUrl","/");

        return "message";
    }




    @GetMapping("/product/view") //localhost:8080/board/view?id=1 //(get방식 파라미터)
    public String productView(Model model, Integer id){
        /*상세페이지4*/
        model.addAttribute("product", productService.productView(id));
        return "page/product/product_view";
    }

    @GetMapping("/product/delete")
    public String productDelete(Integer id){

        productService.productDelete(id);
        //게시물삭제하고 게시물리스트로 넘어가야하므로
        return "redirect:/";
    }

    //PathVariable이라는 것은 modify 뒤에있는 {id}부분이 인식이되서 Integer형태의 id로 들어온다는것
    @GetMapping("/product/modify/{id}")
    public String productModify(@PathVariable("id") Integer id, Model model){

        //수정4    //상세페이지에 있는 내용과, 수정페이지의 내용이 같기때문에 위 코드와 같은 것을 확인할수있다
        model.addAttribute("product", productService.productView(id));

        return "page/product/product_modify";
    }
    @PostMapping("/product/update/{id}")
    public String productUpdate(@PathVariable("id") Integer id, Product product, MultipartFile file) throws IOException {
        //기존에있던글이 담겨져서온다.
        Product productTemp = productService.productView(id);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        productTemp.setTitle(product.getTitle());
        productTemp.setContent(product.getContent());

        productService.write(productTemp, file); //추가 → 수정한내용을 boardService의 write부분에 넣기
        return "redirect:/";
    }
}