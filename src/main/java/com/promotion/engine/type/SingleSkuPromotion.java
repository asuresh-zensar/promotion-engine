package com.promotion.engine.type;

public class SingleSkuPromotion extends PromotionType {

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
	public SingleSkuPromotion(double fixedPrice, String sku, int quantity) {
		super(fixedPrice);
		this.sku = sku;
		this.quantity = quantity;
	}
	
}
