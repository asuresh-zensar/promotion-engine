package com.promotion.engine.type;

public abstract class PromotionType {

public double fixedPrice;
	
	public double getFixedPrice() {
		return fixedPrice;
	}
	public void setFixedPrice(double fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	public PromotionType(double fixedPrice) {
		super();
		this.fixedPrice = fixedPrice;
	}
}
