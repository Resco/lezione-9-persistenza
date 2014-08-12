package it.xpug.supermarket.main;

import it.xpug.generic.db.*;

public class SupermarketCheckoutRepository {

	private Database database;

	public SupermarketCheckoutRepository(Database database) {
		this.database = database;
	}

	public SupermarketCheckout findById(int supermarketCheckoutId) {
		PriceList priceList = new DatabasePriceList(database);
		SupermarketCheckout checkout = new SupermarketCheckout(supermarketCheckoutId, 
				priceList);
		String sql = "select total from checkouts where id = ?";
		ListOfRows rows = this.database.select(sql, supermarketCheckoutId + "");
		if (0 == rows.size())
			throw new TotalNotFound();
		checkout.setTotal(((Integer) rows.get(0).get("total")).intValue());
		return checkout;
	}

	public void save(SupermarketCheckout checkout) {
		int total = checkout.total();
		int id = checkout.getId();
		String totale = total + "";
		String checkout_id = id + "";
		String sql = String.format
				("update checkouts set total='%s' where id='%s';", totale, checkout_id);
		this.database.executeNoPar(sql);
	}
}
