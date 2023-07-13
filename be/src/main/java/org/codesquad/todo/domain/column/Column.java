package org.codesquad.todo.domain.column;

public class Column {
	private final Long id;
	private final String name;

	public Column(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Column createInstanceWithId(Long id) {
		return new Column(id, this.name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
