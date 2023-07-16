package org.codesquad.todo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

	private JdbcTemplate jdbcTemplate;
	private List<String> tableNames;

	@Autowired
	public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		init();
	}

	private void init() {
		try {
			tableNames = new ArrayList<>();
			ResultSet resultSet = jdbcTemplate.getDataSource().getConnection()
				.getMetaData()
				.getTables(null, "PUBLIC", null, new String[] {"TABLE"});

			while (resultSet.next()) {
				tableNames.add(resultSet.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void execute() {
		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
		tableNames.forEach(name -> {
			jdbcTemplate.execute(String.format("TRUNCATE TABLE %s", name));
			jdbcTemplate.execute(String.format("ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1", name));
		});
		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
	}

	@Transactional
	public void execute(String sql) {
		jdbcTemplate.execute(sql);
	}
}
