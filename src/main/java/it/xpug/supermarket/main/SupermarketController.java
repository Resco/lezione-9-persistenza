package it.xpug.supermarket.main;

import static java.lang.String.*;

import java.io.*;

import javax.servlet.http.*;

public class SupermarketController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SupermarketCheckoutRepository repository;

	public SupermarketController(SupermarketCheckoutRepository repository,
			HttpServletRequest request, HttpServletResponse response) {
		this.repository = repository;
		this.request = request;
		this.response = response;
	}

	public void service() throws IOException {
		response.setContentType("application/json");
		if (request.getRequestURI().equals("/scan")) {
			doScan();
		} else if (request.getRequestURI().equals("/total")) {
			doTotal();
		} else {
			do404();
		}
	}

	private void doScan() throws IOException {
		try {
			int id = Integer.parseInt(request.getParameter("checkout_id"));
			//prende il checkout in posizione check_id con il suo totale
			SupermarketCheckout checkout = repository.findById(id);
			//prende il prezzo del codice scelto
			int price = checkout.scan(request.getParameter("code"));
			repository.save(checkout);
			writeBody(format("{ \"price\": %s }", price));
		} catch (PriceNotFound e) {
			response.setStatus(400);
			writeBody("{ \"description\": \"Price not found\" }");
		}
	}

	private void doTotal() throws IOException {
		int check_id_int=Integer.parseInt(request.getParameter("checkout_id"));
		SupermarketCheckout checkout = repository.findById(check_id_int);
		int total = checkout.total();
		writeBody(toJson("total", total));
	}

	private void do404() throws IOException {
		response.setStatus(404);
		writeBody(toJson("description", "Not Found"));
	}

	private String toJson(String name, String value) {
		return format("{ \"%s\": \"%s\" }", name, value);
	}

	private String toJson(String name, int value) {
		return format("{ \"%s\": %s }", name, value);
	}

	private void writeBody(String body) throws IOException {
		response.getWriter().write(body);
	}


}
