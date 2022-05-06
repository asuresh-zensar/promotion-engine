package com.promotion.engine.model;

public class Item {

	private String sku;
	private int quantity;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Item(String sku, int quantity) {
		super();
		this.sku = sku;
		this.quantity = quantity;
	}
	public Item() {
		super();
	}
	
	
}
