package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertMember(MemberVO mvo) {
        String sql = "insert into member(userid, passwd, name, email) values (?, ?, ?, ?)";

        Object[] params = new Object[]{
                mvo.getUserid(), mvo.getPasswd(), mvo.getName(), mvo.getEmail()
        };

        return jdbcTemplate.update(sql, params);
    }
}
