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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService msrv;

    //로그 유형 : trace, debug, info, warn, error
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());


    // 로그인 O -> redirect:/myinfo
    // 로그인 X -> join/join
    @GetMapping("/join")
    public String getJoin(HttpSession session) {
        String returnPage = "join/join";

        if (session.getAttribute("mvo") != null) {

            returnPage = "redirect:/myinfo";
        }

        return returnPage;
    }

    @PostMapping("/join")
    public String postJoin(MemberVO mvo) {
        LOGGER.info("postJoin 호출!! {}", mvo);

        // 회원정보 저장
        if (msrv.newMember(mvo)) {
            LOGGER.info("회원가입 성공!!");
        }


        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        LOGGER.info("getLogin 호출!!");

        return "join/login";
    }

    @PostMapping("/login") // 로그인 처리
    public String postLogin(MemberVO mvo, HttpSession session) {
        LOGGER.info("postLogin 호출!!");

        String returnPage = "join/lgnfail";

        if (msrv.checkLogin(mvo)) {
            session.setAttribute("mvo", mvo); // 회원정보를 세션에 저장

            returnPage = "redirect:/myinfo";
        }


        return returnPage;
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

    // 로그인 O -> redirect:/login
    // 로그인 X -> join/myinfo
    @GetMapping("/myinfo")
    public String getMyInfo(Model model, HttpSession session) {
        String returnPage = "join/myinfo";

        if (session.getAttribute("mvo") != null) {
            MemberVO mvo = (MemberVO) session.getAttribute("mvo");
            model.addAttribute("mvo", msrv.readOneMember(mvo.getUserid()));
        } else {
            returnPage = "redirect:/login";
        }

        return returnPage;
    }

    //아이디 중복 검사 - REST api 이용
    @ResponseBody
    @GetMapping("/checkuid")
    public String checkUid(String uid) {
        String result = "잘못된 방식으로 호출하였습니다!!";

        if (uid != null || !uid.equals("")) {
            result = msrv.checkUid(uid);
        }

        return result;
    }
}
