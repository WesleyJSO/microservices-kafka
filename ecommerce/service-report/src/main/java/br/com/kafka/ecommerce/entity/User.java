package br.com.kafka.ecommerce.entity;

public class User implements IEntity {

	private final String id;

	public User(String id) {
		super();
		this.id = id;
	}

	public String getReportPath() {
		return "target/" + id + "-report.txt";
	}

	public String getId() {
		return id;
	}
}
