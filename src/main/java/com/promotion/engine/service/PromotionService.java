package com.promotion.engine.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.promotion.engine.model.Cart;
import com.promotion.engine.model.Item;
import com.promotion.engine.pricelist.PriceList;
import com.promotion.engine.type.GroupedSkuPromotion;
import com.promotion.engine.type.PromotionType;
import com.promotion.engine.type.SingleSkuPromotion;

public class PromotionService {

	public double calculateOrderTotal(Cart cart) {
		
		double total=0.0;
		
		if(cart !=null) {
			List<Item> items=cart.getItems();
			if(items!=null&&!items.isEmpty()) {
				List<PromotionType> activePromotions=cart.getActivePromotions();
				if(activePromotions!=null&&!activePromotions.isEmpty()) {
					SingleSkuPromotion sPromotion=null;
					Set<String> skus=new HashSet<String>();
					Map<String,Integer> itemQuantityMap= new HashMap<>();
					Map<String,Boolean> appliedMap=new HashMap<String, Boolean>();
					
					//Populating unique SKUs
					//Populating the map with SKU with bought quantity
					//initializing the SKU promotion applied status false
					
					items.forEach(item->{
						skus.add(item.getSku());
						itemQuantityMap.put(item.getSku(), item.getQuantity());
						appliedMap.put(item.getSku(), false);
					});
					String currentSku=null;
					for(PromotionType promotion:activePromotions) {
						if(promotion instanceof SingleSkuPromotion) {
							sPromotion= (SingleSkuPromotion) promotion;
							currentSku=sPromotion.getSku();
							int itemQuantity=itemQuantityMap.get(sPromotion.getSku());
							int promotionQuqntity=sPromotion.getQuantity();
							if(!appliedMap.get(currentSku)&&skus.contains(currentSku) &&itemQuantity>=promotionQuqntity) {
								total+=(itemQuantity/promotionQuqntity)*sPromotion.getFixedPrice()+(itemQuantity%promotionQuqntity)*PriceList.getPrice(currentSku);
								appliedMap.put(currentSku,true);
								
							}
						}else if(promotion instanceof GroupedSkuPromotion) {
							GroupedSkuPromotion gPromotion=(GroupedSkuPromotion) promotion;
							Set<String> promotionSkus=new HashSet<String>(gPromotion.getSkus());
							promotionSkus.retainAll(skus);
							if(promotionSkus.size()==gPromotion.getSkus().size()) {
								
								Boolean applied=Boolean.FALSE;
								for(String gsku:promotionSkus) {
									if(appliedMap.get(gsku))
										applied=Boolean.TRUE;
									
								}
								if(!applied) {
									total+=gPromotion.getFixedPrice();
									promotionSkus.forEach(s->appliedMap.put(s, true));
								}
							}
						}
					}
					List<String> remainingSku=appliedMap.entrySet().stream().filter(s->!s.getValue()).map(s->s.getKey()).collect(Collectors.toList());
					if(remainingSku != null &&!remainingSku.isEmpty()) {
						for(String sku:remainingSku) {
							total+=itemQuantityMap.get(sku)*PriceList.getPrice(sku);
						}
					}
					
				}else {
					return orderTotal(items);
				}
			}
		}
		return total;
	}
	
	private double orderTotal(List<Item> items) {
		double total=0.0;
		for(Item item:items) {
			total+=item.getQuantity()*PriceList.getPrice(item.getSku());
		}
		return total;
	}
}
