package atoz.spring.mvc.controller;

import atoz.spring.mvc.service.MemberService;
import atoz.spring.mvc.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService msrv;

    //로그 유형 : trace, debug, info, warn, error
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/join")
    public String getJoin(){

        return "join/join";
    }

    @PostMapping("/join")
    public String postJoin(MemberVO mvo){
        LOGGER.info("postJoin 호출!! {}", mvo);

        // 회원정보 저장
        if (msrv.newMember(mvo)) {
            LOGGER.info("회원가입 성공!!");
        }


        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){
        LOGGER.info("getLogin 호출!!");

        return "join/login";
    }

    @PostMapping("/login")
    public String postLogin(){
        LOGGER.info("postLogin 호출!!");

        return "redirect:/";
    }

    @GetMapping("/myinfo")
    public String getMyInfo(Model model){
        model.addAttribute("mvo", msrv.readOneMember());
        return "join/myinfo";
    }
}
