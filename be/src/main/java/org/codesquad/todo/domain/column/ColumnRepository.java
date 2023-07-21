package org.codesquad.todo.domain.column;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
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

	public ColumnRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(Column column) {
		String sql = "INSERT INTO columns(name) VALUES(:name)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(column);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public Optional<Column> findById(Long columnId) {
		String sql = "SELECT id, name FROM columns WHERE id = :id";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("id", columnId), COLUMN_ROW_MAPPER)));
	}

	public List<Column> findAll() {
		String sql = "SELECT id, name FROM columns";
		return jdbcTemplate.query(sql, COLUMN_ROW_MAPPER);
	}

	public Boolean exist(Long columnId) {
		String sql = "SELECT EXISTS(SELECT 1 FROM columns WHERE id = :columnId)";
		return jdbcTemplate.queryForObject(sql, Map.of("columnId", columnId), Boolean.class);
	}

	public int update(Column column) {
		String sql = "UPDATE columns SET name = :name WHERE id = :id";
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(column);
		return jdbcTemplate.update(sql, parameters);
	}

	public int delete(Long columnId) {
		String sql = "DELETE FROM columns WHERE id = :id";
		return jdbcTemplate.update(sql, Map.of("id", columnId));
	}

	private static final RowMapper<Column> COLUMN_ROW_MAPPER = (rs, rowNum) ->
		new Column(rs.getLong("id"), rs.getString("name"));

}
