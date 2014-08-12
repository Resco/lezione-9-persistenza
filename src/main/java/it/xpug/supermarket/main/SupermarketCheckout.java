package it.xpug.supermarket.main;

public class SupermarketCheckout {

	private int id;
	private int total;
	private PriceList priceList;

	public SupermarketCheckout(int id, PriceList priceList) {
		this.priceList = priceList;
		this.id = id;
	}

	public int scan(String code) {
		int price = priceList.findPrice(code);
		this.total += price;
		return price;
	}

	public int total() {
		return total;
	}

	public void setTotal(int newTotal) {
		this.total = newTotal;
	}
	
	public int getId(){
		return this.id;
	}

}
