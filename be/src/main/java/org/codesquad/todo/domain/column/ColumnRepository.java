package org.codesquad.todo.domain.column;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColumnRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ColumnRepository(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}
