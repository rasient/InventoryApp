package com.alextest;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.alextest.model.CartItem;
import com.alextest.model.Product;
import com.alextest.model.User;
import com.alextest.repository.CartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddItemFromDatabase() {
		Product product = entityManager.find(Product.class, 7);
		User user = entityManager.find(User.class, 1);
		CartItem cartItem = new CartItem(1, product, user);
		cartItemRepository.save(cartItem);
	}
	
	@Test
	public void testAddItemByIds() {
		Product product = new Product(8);
		User user = new User(2);
		CartItem cartItem = new CartItem(2, product, user);
		cartItemRepository.save(cartItem);
	}
	
	@Test
	public void testAddMultipleItems() {
		User user = new User(6);
		Product product1 = new Product(7);
		Product product2 = new Product(8);
		Product product3 = new Product(9);
		
		CartItem cartItem1 = new CartItem(1, product1, user);
		CartItem cartItem2 = new CartItem(4, product2, user);
		CartItem cartItem3 = new CartItem(5, product3, user);
		
		cartItemRepository.saveAll(Arrays.asList(cartItem1, cartItem2, cartItem3));
		
	}
	
	@Test
	public void testListItems() {
		List<CartItem> listItems = cartItemRepository.findAll();
		listItems.forEach(item -> System.out.println(item));
	}
	
	@Test
	public void testUpdateItem() {
		CartItem cartItem = cartItemRepository.findById(1).get();
		cartItem.setQuantity(10);
		cartItem.setProduct(new Product(9));
	}
	
	@Test
	public void testRemoveItem() {
		cartItemRepository.deleteById(1);
	}
}
