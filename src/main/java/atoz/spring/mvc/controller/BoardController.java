package atoz.spring.mvc.controller;

import atoz.spring.mvc.service.BoardService;
import atoz.spring.mvc.vo.BoardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    // bean 클래스로 정의한 경우 @Autowired 어노테이션 생략 가능
    @Autowired
    private BoardService bsrv;

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public String getList(){

        return "board/list";
    }

    @GetMapping("/view")
    public String getView(){

        return "board/view";
    }

    @GetMapping("/write")
    public String getWrite(){

        return "board/write";
    }

    @PostMapping("/write")
    public String postWrite(BoardVO bvo){
        bsrv.newWrite(bvo);

        return "redirect:/list";
    }
}
