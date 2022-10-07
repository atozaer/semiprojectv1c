package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;
import atoz.spring.mvc.vo.ZipcodeVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int insertMember(MemberVO mvo) {

        return sqlSession.insert("member.insertMember", mvo);
    }

    @Override
    public MemberVO selectOneMember(String userid) {

        return sqlSession.selectOne("member.selectOneMember", userid);
    }

    @Override
    public int selectOneMember(MemberVO mvo) {

        return sqlSession.selectOne("member.selectCountMember", mvo);
    }

    @Override
    public int selectCheckUserId(String uid) {

        return sqlSession.selectOne("member.selectCheckUserId", uid);
    }

    @Override
    public List<ZipcodeVO> selectZipcode(String dong) {

        return sqlSession.selectList("member.selectZipcode", dong);
    }
}