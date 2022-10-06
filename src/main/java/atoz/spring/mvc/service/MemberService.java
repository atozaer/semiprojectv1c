package atoz.spring.mvc.service;

import atoz.spring.mvc.vo.MemberVO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MemberService {
    boolean newMember(MemberVO mvo);

    MemberVO readOneMember(String userid);

    boolean checkLogin(MemberVO mvo);

    String checkUid(String uid);

    String findZipcode(String dong) throws JsonProcessingException;
}
