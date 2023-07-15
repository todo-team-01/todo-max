package org.codesquad.todo.controller.util;

import javax.sql.DataSource;

import org.codesquad.todo.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	@Transactional
	void setUp() {
		RestAssured.port = port;
		databaseCleaner.execute();
		databaseCleaner.execute("INSERT INTO member(name, profile_image_url) VALUES ('왕만두', 'test')");
	}
}
