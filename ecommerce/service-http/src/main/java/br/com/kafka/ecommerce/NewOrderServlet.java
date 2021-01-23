package br.com.kafka.ecommerce;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import br.com.kafka.ecommerce.dispatcher.GenericKafkaProducer;
import br.com.kafka.ecommerce.entity.Email;
import br.com.kafka.ecommerce.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewOrderServlet extends HttpServlet {

	private static final long serialVersionUID = -2287142807769945451L;
	private static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
	private static final String ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";

	private final GenericKafkaProducer<Order> orderDispatcher = new GenericKafkaProducer<>();
	private final GenericKafkaProducer<Email> emailDispatcher = new GenericKafkaProducer<>();
			
	@Override
	public void destroy() {
		orderDispatcher.close();
		emailDispatcher.close();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try(var orderDispatcher = new GenericKafkaProducer<Order>();
				var emailDispatcher = new GenericKafkaProducer<Email>()) {
			
			try {
				var order = getOrderData(req);
				
				orderDispatcher.send(ECOMMERCE_NEW_ORDER, order.getEmail(), order);
				
				var emailData = new Email("Emil subject", "Thank you for your order! We are processing your order!");
				emailDispatcher.send(ECOMMERCE_SEND_EMAIL, order.getEmail(), emailData);			
				
				returnSuccessMessage(res);
				
			} catch(InterruptedException | ExecutionException e) {
				throw new ServletException(e);
			}
		}	
	}

	private Order getOrderData(HttpServletRequest req) {
		var reqAmmount = req.getParameter("ammount");
		var reqEmail = req.getParameter("email");
		
		var ammount = isValidNumber(reqAmmount)
				? new BigDecimal(reqAmmount) 
				: BigDecimal.ZERO;
		
		var email = isNullOrEmpty(reqEmail) 
				? "blank@email.com" 
				: req.getParameter("email");
		
		var order = new Order(UUID.randomUUID().toString(), ammount, email);
		return order;
	}

	private boolean isValidNumber(String parameter) {
		return !isNullOrEmpty(parameter) && isNumber(parameter);
	}
	
	private boolean isNumber(String parameter) {
		try {
			Double.parseDouble(parameter);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private boolean isNullOrEmpty(String parameter) {
		return parameter == null || "".equals(parameter);
	}

	private void returnSuccessMessage(HttpServletResponse res) throws IOException {
		var message = "Successfully sent a new order.";
		System.out.println(message);
		res.getWriter().println(message);
		res.setStatus(SC_OK);
	}
}
