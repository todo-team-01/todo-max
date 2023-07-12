package org.codesquad.todo.domain.column;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColumnRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ColumnRepository(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public Boolean isExist(Long columnId) {
		String sql = "SELECT EXISTS(SELECT 1 FROM columns WHERE id = :columnId)";
		return jdbcTemplate.queryForObject(sql, Map.of("columnId", columnId), Boolean.class);
	}
}
