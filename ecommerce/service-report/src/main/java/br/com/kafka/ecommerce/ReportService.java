package br.com.kafka.ecommerce;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.entity.User;
import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class ReportService implements IService<User> {

	private static final String ECOMMERCE_USER_READING_REPORT = "ECOMMERCE_USER_READING_REPORT";
	
	public static void main(String...strings) {
		
		var reportService = new ReportService();
		
		try(var service = new GenericKafkaService<User>(
				ReportService.class.getSimpleName(),
				ECOMMERCE_USER_READING_REPORT,
				reportService::parse,
				User.class)) {
			service.run();
		}		
	}

	@Override
	public void parse(ConsumerRecord<String, User> record)
			throws InterruptedException, ExecutionException, SQLException {
		
		print("----------------------------------",
				"Processing reporto for value: ".concat(String.valueOf(record.value())));
		
		final var user = record.value();
		final var target = Paths.get(user.getReportPath());
		
		var created = createUserReport(user, target);
		
		print(created ? "Report created for user: " + user.getId() : "Error creating report for user: " + user.getId());
	}

	private boolean createUserReport(final User user, final Path target) {
		return IO.copy(Paths.get("./resources.reports.txt"), target, StandardCopyOption.REPLACE_EXISTING)
				&& IO.append(target, "Created for " + user.getId());
	}
}
