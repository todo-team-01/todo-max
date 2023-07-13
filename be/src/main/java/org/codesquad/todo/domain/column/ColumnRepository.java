package org.codesquad.todo.domain.column;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ColumnRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ColumnRepository(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public Column save(Column column) {
		String sql = "INSERT INTO columns(name) VALUES(:name)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(column);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return column.createInstanceWithId(keyHolder.getKey().longValue());
	}

	public List<Column> findAll() {
		String sql = "SELECT id, name FROM columns";
		return jdbcTemplate.query(sql, COLUMN_ROW_MAPPER);
	}

	public Boolean exist(Long columnId) {
		String sql = "SELECT EXISTS(SELECT 1 FROM columns WHERE id = :columnId)";
		return jdbcTemplate.queryForObject(sql, Map.of("columnId", columnId), Boolean.class);
	}

	private static final RowMapper<Column> COLUMN_ROW_MAPPER = (rs, rowNum) ->
		new Column(rs.getLong("id"), rs.getString("name"));
}
