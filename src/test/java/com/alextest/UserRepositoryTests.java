package com.alextest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.alextest.model.Role;
import com.alextest.model.User;
import com.alextest.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateRoles() {
		Role roleAdmin = new Role("Administrator");
		Role roleEditor = new Role("Editor");
		Role roleVisitor = new Role("Visitor");
		
		entityManager.persist(roleAdmin);
		entityManager.persist(roleEditor);
		entityManager.persist(roleVisitor);
	}
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User user = new User("alexander.berg.job@gmail.com", "123456");
		user.addRole(roleAdmin);
		userRepository.save(user);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		Role roleEditor = entityManager.find(Role.class, 2);
		Role roleVisitor = entityManager.find(Role.class, 3);
		User user = new User("ravi.kumar@gmail.com", "123456");
		user.addRole(roleEditor);
		user.addRole(roleVisitor);
		userRepository.save(user);
	}
	
	@Test
	public void testAssignRoleToExistingUser() {
		User user = userRepository.findById(1).get();
		Role roleEditor = entityManager.find(Role.class, 2);
		user.addRole(roleEditor);
	}
	
	@Test
	public void testRemoveRoleFromExistingUser() {
		User user = userRepository.findById(1).get();
		Role role = new Role(2);
		user.removeRole(role);
	}
	
	@Test
	public void testCreateNewUserWithNewRole() {
		Role roleSalespersion = new Role("Salesperson");
		User user = new User("john@gmail.com", "123456");
		user.addRole(roleSalespersion);
		userRepository.save(user);
	}
	
	@Test
	public void testGetUser() {
		User user = userRepository.findById(1).get();
		entityManager.detach(user);
		System.out.println(user.getEmail());
		System.out.println(user.getRoles());
	}
	
	@Test
	public void testRemoveUser() {
		userRepository.deleteById(4);
	}
}
