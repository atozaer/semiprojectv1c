package atoz.spring.mvc.service;

import atoz.spring.mvc.dao.MemberDAO;
import atoz.spring.mvc.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("msrv")
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDAO mdao;

    @Override
    public boolean newMember(MemberVO mvo) {
        boolean isInsert = false;

        //회원가입 성공시 true 리턴
        if (mdao.insertMember(mvo)>0) {
            isInsert = true;
        }

        return isInsert;
    }

    @Override
    public MemberVO readOneMember(String userid) {


        return mdao.selectOneMember(userid);
    }

    @Override
    public boolean checkLogin(MemberVO mvo) {
        boolean isLogin = false;

        //회원이 존재한다면
        if ((mdao.selectOneMember(mvo))>0) {
            isLogin = true;
        }

        return isLogin;
    }
}
