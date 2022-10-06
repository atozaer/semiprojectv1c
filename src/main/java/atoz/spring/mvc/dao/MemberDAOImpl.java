package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;
import atoz.spring.mvc.vo.ZipcodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<ZipcodeVO> zipcodeVORowMapper = BeanPropertyRowMapper.newInstance(ZipcodeVO.class);

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

    @Override
    public int selectCheckUserId(String uid) {
        String sql = " select count(mno) cnt from member where userid = ? ";

        Object[] param = new Object[]{
                uid
        };

        return jdbcTemplate.queryForObject(sql, param, Integer.class);
    }

    @Override
    public List<ZipcodeVO> selectZipcode(String dong) {
        String sql = " select * from zipcode_2013 where dong like :dong ";

        Map<String,Object> param = new HashMap<>();
        param.put("dong", dong+"%");

        return namedParameterJdbcTemplate.query(sql,param,zipcodeVORowMapper);
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