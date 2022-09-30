package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.MemberVO;
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
import java.util.Collections;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<MemberVO> memberVORowMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);

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
    public MemberVO selectOneMember() {
        String sql = " select userid,name,email,regdate from member where mno = 1 ";

        return namedParameterJdbcTemplate.queryForObject(sql, Collections.emptyMap(), memberVORowMapper);
    }
}