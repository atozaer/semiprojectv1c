package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;
import atoz.spring.mvc.vo.ZipcodeVO;

import java.util.List;

public interface MemberDAO {
    int insertMember(MemberVO mvo);

    MemberVO selectOneMember(String userid);

    int selectOneMember(MemberVO mvo);

    int selectCheckUserId(String uid);

    List<ZipcodeVO> selectZipcode(String dong);
}
