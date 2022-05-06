package com.promotion.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.promotion.engine.model.Cart;
import com.promotion.engine.model.Item;
import com.promotion.engine.service.PromotionService;
import com.promotion.engine.type.GroupedSkuPromotion;
import com.promotion.engine.type.SingleSkuPromotion;


@SpringBootTest
class PromotionEngineApplicationTests {
	public static Cart cart=new Cart();
	PromotionService restResource= new PromotionService ();
	@BeforeAll
	public static void initializeCart() {
		cart.setActivePromotions(Arrays.asList(new SingleSkuPromotion( 130, "A", 3),new SingleSkuPromotion( 45, "B", 2),new GroupedSkuPromotion( 30, new HashSet<String>(Arrays.asList("C","D")))));
	}
	@Test
	void scenarioA() {
		cart.setItems(Arrays.asList(new Item("A",1),new Item("B",1),new Item("C",1)));
		assertEquals(restResource.calculateOrderTotal(cart), 100.0);
		
		
	}
	
	@Test
	void senarioB() {
		cart.setItems(Arrays.asList(new Item("A",5),new Item("B",5),new Item("C",1)));
		assertEquals(restResource.calculateOrderTotal(cart), 370.0);
		
	}
	@Test
	void senarioC() {
		cart.setItems(Arrays.asList(new Item("A",3),new Item("B",5),new Item("C",1),new Item("D",1)));
		assertEquals(restResource.calculateOrderTotal(cart), 280.0);
	}


}
