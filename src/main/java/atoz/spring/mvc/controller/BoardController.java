package atoz.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/list")
    public String getList(){

        return "board/list";
    }

    @GetMapping("/view")
    public String getLogin(){

        return "board/view";
    }

    @GetMapping("/write")
    public String getMyInfo(){

        return "board/write";
    }
}
