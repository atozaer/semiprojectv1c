package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MemberVO mapRow(ResultSet rs, int num) throws SQLException {
        MemberVO mvo = new MemberVO();

        mvo.setUserid(rs.getString("userid"));
        mvo.setName(rs.getString("name"));
        mvo.setEmail(rs.getString("email"));
        mvo.setRegdate(rs.getString("regdate"));

        return mvo;
    }


    public MemberDAOImpl(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingColumns("userid", "passwd", "name", "email");

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int insertMember(MemberVO mvo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);

        return simpleJdbcInsert.execute(params);
    }

    @Override
    public MemberVO selectOneMember(String userid) {
        String sql = " select userid,name,email,regdate from member where userid = ? ";

        Object[] param = {userid};

        // 람다식
        RowMapper<MemberVO> memberVORowMapper = (rs, num) -> {
            MemberVO mvo = new MemberVO();

            mvo.setUserid(rs.getString("userid"));
            mvo.setName(rs.getString("name"));
            mvo.setEmail(rs.getString("email"));
            mvo.setRegdate(rs.getString("regdate"));

            return mvo;
        };
//        return namedParameterJdbcTemplate.queryForObject(sql, Collections.emptyMap(), memberVORowMapper);
        return jdbcTemplate.queryForObject(sql, param, memberVORowMapper);
    }

    @Override
    public int selectOneMember(MemberVO mvo) {
        String sql = " select count(mno) cnt from member where userid = ? and passwd = ? ";

        Object[] params = {
                mvo.getUserid(),
                mvo.getPasswd(),
        };

        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}

//    private RowMapper<MemberVO> memberVORowMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);

//      콜백 메서드 정의 : mapRow
//    private RowMapper<MemberVO> memberVORowMapper = new MemberVORowMapper();
//
//    private class MemberVORowMapper implements RowMapper<MemberVO> {
//        @Override
//        public MemberVO mapRow(ResultSet rs, int num) throws SQLException {
//            MemberVO mvo = new MemberVO();
//
//            mvo.setUserid(rs.getString("userid"));
//            mvo.setName(rs.getString("name"));
//            mvo.setEmail(rs.getString("email"));
//            mvo.setRegdate(rs.getString("regdate"));
//
//            return mvo;
//        }
//    }