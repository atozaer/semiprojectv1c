package atoz.spring.mvc.controller;

import atoz.spring.mvc.service.BoardService;
import atoz.spring.mvc.vo.BoardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BoardController {

    // bean 클래스로 정의한 경우 @Autowired 어노테이션 생략 가능
    @Autowired
    private BoardService bsrv;

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public String getList(Model model, HttpSession session){
        LOGGER.info("getList 호출!!");

        session.getAttribute("mvo");

        model.addAttribute("boardList", bsrv.readBoard());

        return "board/list";
    }

    @GetMapping("/view")
    public String getView(){
        LOGGER.info("getView 호출!!");

        return "board/view";
    }

    // 로그인 O -> board/write
    // 로그인 X -> join/login
    @GetMapping("/write")
    public String getWrite(HttpSession session){
        LOGGER.info("getWrite 호출!!");

        String returnPage = "join/login";

        if (session.getAttribute("mvo") != null) {
            returnPage = "board/write";
        }
        return returnPage;
    }

    @PostMapping("/write")
    public String postWrite(BoardVO bvo, HttpSession session){
        LOGGER.info("postWrite 호출!!");
        
        bsrv.newWrite(bvo);

        return "redirect:/list";
    }
}
