package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired //new를 써야하지만, 스프링부트가 알아서 읽어와서 주입을해준다.
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws IOException {
        /*우리의 프로젝트경로를 담아주게 된다 - 저장할 경로를 지정*/
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        /*식별자 . 랜덤으로 이름 만들어줌*/
        UUID uuid = UUID.randomUUID();

        /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
        String fileName = uuid + "_" + file.getOriginalFilename();

        /*빈 껍데기 생성*/
        /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        /*디비에 파일 넣기*/
        board.setFilename(fileName);
        /*저장되는 경로*/
        board.setFilepath("/files/" + fileName); /*저장된파일의이름,저장된파일의경로*/

        /*파일 저장*/
        boardRepository.save(board);
    }

    public List<Board> boardList(){
        //findAll : 테스트보드라는 클래스가 담긴 List를 반환하는것을 확인할수있다
        return boardRepository.findAll();
    }

    public Board boardview(Integer id){
        return boardRepository.findById(id).get(); //어떤게시글을 불러올지 지정을해주어야한다 (Integer값으로)
    }

    public void boardDelete(Integer id){ /*id값 1번을 넣어주면 1번을 삭제한다*/
        boardRepository.deleteById(id);
    }
}
