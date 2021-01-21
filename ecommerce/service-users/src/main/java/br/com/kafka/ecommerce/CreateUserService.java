package br.com.kafka.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.entity.Order;
import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class CreateUserService implements IService<Order> {

	private final static String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
	private Connection connection;
	
	public CreateUserService() throws SQLException {
		String url = "jdbc:sqlite:../target/users_database.db";
		this.connection = DriverManager.getConnection(url);
		try {
			connection.createStatement().execute("create table Users ("
					+ "uuid varchar(200) primary key,"
					+ "email varchar(200))");			
		} catch(SQLException e) {
			if(!e.getMessage().matches("^(.+)table(.+)already exists(.+)"))
				throw e;
		}
	}
	
	public static void main(String...strings) throws SQLException {
		
		var createUserService = new CreateUserService();
		
		try(var service = new GenericKafkaService<>(
				CreateUserService.class.getSimpleName(), 
				ECOMMERCE_NEW_ORDER,
				createUserService::parse,
				Order.class)) {
			service.run();
		}
	}

	@Override
	public void parse(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException, SQLException {
		print("---------------------------------",
				"Processing new order checking for new user",
				String.valueOf(record.value()));
		
		var order = record.value();
		if(isNewUser(order.getEmail())) {
			insertNewUser(order);
		}
	}

	private void insertNewUser(Order order) throws SQLException {
		var insert = connection.prepareStatement("insert into Users (uuid, email) values (?, ?)");
		insert.setString(1, order.getUserId());
		insert.setString(2, order.getEmail());
		insert.execute();
		System.out.println("User uuid: " + order.getEmail() + " added");
	}

	private boolean isNewUser(String email) throws SQLException {
		var exists = connection.prepareStatement("select uuid from Users where email = ? limit 1");
		exists.setString(1, email);
		var results = exists.executeQuery();
		return !results.next();
	}
}
