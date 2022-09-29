package atoz.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @GetMapping("/join")
    public String getJoin(){

        return "join/join";
    }

    @PostMapping("/join")
    public String postJoin(){

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){

        return "join/login";
    }

    @GetMapping("/myinfo")
    public String getMyInfo(){

        return "join/myinfo";
    }
}
