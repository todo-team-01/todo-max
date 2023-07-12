package org.codesquad.todo.domain.member;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MemberRepository(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public Boolean isExist(Long memberId) {
		String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE id = :memberId)";
		return jdbcTemplate.queryForObject(sql, Map.of("memberId", memberId), Boolean.class);
	}

}
