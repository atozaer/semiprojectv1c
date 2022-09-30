package atoz.spring.mvc.service;

import atoz.spring.mvc.vo.MemberVO;

public interface MemberService {
    boolean newMember(MemberVO mvo);

    MemberVO readOneMember();
}
