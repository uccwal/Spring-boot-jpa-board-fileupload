package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private List<Account> accountList;

    @Autowired //new를 써야하지만, 스프링부트가 알아서 읽어와서 주입을해준다.
    private AccountRepository accountRepository;

    public void write(Account account) {

        /*파일 저장*/
        accountRepository.save(account);
    }

    public List<Account> accountList(){
        //findAll : 테스트보드라는 클래스가 담긴 List를 반환하는것을 확인할수있다
        return accountRepository.findAll();
    }

    public Account accountView(Integer id){
        return accountRepository.findById(id).get(); //어떤게시글을 불러올지 지정을해주어야한다 (Integer값으로)
    }

    public void accountDelete(Integer id){ /*id값 1번을 넣어주면 1번을 삭제한다*/
        accountRepository.deleteById(id);
    }


}
