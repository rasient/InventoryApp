package com.alextest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.alextest.model.Category;
import com.alextest.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void testCreateCategory() {
		Category category = new Category();
		category.setName("Electronics");
		Category savedCategory = categoryRepository.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
}
