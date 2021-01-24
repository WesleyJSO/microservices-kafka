package br.com.kafka.ecommerce;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpService {

	public static void main(String... strings) throws Exception {

		var server = new Server(8080);

		server.setHandler(createContext());

		server.start();

		server.join(); // wait for the server to terminate before finishing the application
	}

	private static ServletContextHandler createContext() {
		var context = new ServletContextHandler();
		context.setContextPath("/ecommerce");
		context.addServlet(new ServletHolder(new NewOrderServlet()), "/new-order");
		return context;
	}
}
