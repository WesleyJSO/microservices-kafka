package br.com.kafka.ecommerce;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.dispatcher.GenericKafkaProducer;
import br.com.kafka.ecommerce.entity.Order;
import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class FraudDetectorService implements IService<Order> {

	private static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
	private static final String ECOMMERCE_ORDER_REJECTED = "ECOMMERCE_ORDER_REJECTED";
	private static final String ECOMMERCE_ORDER_APPROVED = "ECOMMERCE_ORDER_APPROVED";
	
	private final GenericKafkaProducer<Order> orderProducer = new GenericKafkaProducer<>();

	public static void main(String...strings) {
		
		var fraudDetectorService = new FraudDetectorService();
		
		try(var service = new GenericKafkaService<>(
				FraudDetectorService.class.getSimpleName(), 
				ECOMMERCE_NEW_ORDER, 
				fraudDetectorService::parse,
				Order.class)) {
			service.run();			
		}
	}
	

	@Override
	public final void parse(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		print("-----------------------------------------",
				"Processing new order, checking for fraud.",
				record.key(),
				String.valueOf(record.value()),
				String.valueOf(record.partition()),
				String.valueOf(record.offset()));
		fraudDetectorValidate(record);
	}


	private void fraudDetectorValidate(ConsumerRecord<String, Order> record)
			throws InterruptedException, ExecutionException {
		
		emulateSlowProcessing();
		validate(record);
	}

	private void validate(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		var order = record.value();
		if(!isValid(order)) {
			print("Rejected invalid order!");
			orderProducer.send(ECOMMERCE_ORDER_REJECTED, order.getUserId(), order);
		} else if(isFraud(order)) {
			print("Order is a fraud!");
			orderProducer.send(ECOMMERCE_ORDER_REJECTED, order.getUserId(), order);
		} else {
			orderProducer.send(ECOMMERCE_ORDER_APPROVED, order.getUserId(), order);			
			print("Approved: " + order);
		}		
		print("Order was processed.");
	}


	private void emulateSlowProcessing() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isFraud(Order order) {
		return order.getAmmount().compareTo(new BigDecimal(4500)) >= 0;
	}
	
	private boolean isValid(Order order) {
		return order.getAmmount() != null && order.getOrderId() != null && order.getUserId() != null;
	}
}
