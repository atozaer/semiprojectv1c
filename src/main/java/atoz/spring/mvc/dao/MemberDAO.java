package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;

public interface MemberDAO {
    int insertMember(MemberVO mvo);

    MemberVO selectOneMember(String userid);

    int selectOneMember(MemberVO mvo);

    int selectCheckUserId(String uid);
}
