package fr.byob.game.memeduel.server.rest.db.dao.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.bean.User;
import fr.byob.game.memeduel.server.rest.db.dao.UserDAO;

public class UserDAOImplTest extends AbstractDAOImplTest {

	private UserDAO userDAO;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		userDAO = injector.getInstance(UserDAO.class);
	}


	@Test
	public void testAdd() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(user);
		Assert.assertNotNull(user.getUpdated());

		// No duplicate user
		try {
			userDAO.addUser(new User("login", "passsword"));
		} catch (final DAOException e) {
			Assert.assertEquals("username login is not available", e.getMessage());
		}

		// Username
		try {
			userDAO.addUser(new User(null, "password"));
		} catch (final DAOException e) {
			Assert.assertEquals("login: ne peut pas être nul", e.getMessage());
		}
		try {
			userDAO.addUser(new User("lo", "password"));
		} catch (final DAOException e) {
			Assert.assertEquals("login: la taille doit être entre 3 et 25", e.getMessage());
		}
		try {
			userDAO.addUser(new User("looooooooooooooooooooooooooooo", "password"));
		} catch (final DAOException e) {
			Assert.assertEquals("login: la taille doit être entre 3 et 25", e.getMessage());
		}

		// Password
		try {
			userDAO.addUser(new User("login", null));
		} catch (final DAOException e) {
			Assert.assertEquals("password: ne peut pas être nul", e.getMessage());
		}
		try {
			userDAO.addUser(new User("login", "p"));
		} catch (final DAOException e) {
			Assert.assertEquals("password: la taille doit être entre 2 et 16", e.getMessage());
		}

		try {
			userDAO.addUser(new User("login", "paaaaaaaaaaaaaaaaa"));
		} catch (final DAOException e) {
			Assert.assertEquals("password: la taille doit être entre 2 et 16", e.getMessage());
		}

		// Email
		try {
			final User user2 = new User("login", "password");
			user2.setEmail("pasboooon");
			userDAO.addUser(user2);
		} catch (final DAOException e) {
			Assert.assertEquals("email: Adresse email mal formée", e.getMessage());
		}
	}

	@Test
	public void testUpdate() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(user);
		Assert.assertNotNull(user.getUpdated());

		final User user2 = new User("login", "password");
		userDAO.updateUser(user2);
		Assert.assertNotNull(user2.getUpdated());
		Assert.assertTrue(!user.getUpdated().equals(user2.getUpdated()));

		try {
			userDAO.updateUser(new User("login2", "password2"));
		} catch (final DAOException e) {
			Assert.assertEquals("unknown user login2", e.getMessage());
		}

		try {
			userDAO.addUser(new User("login", null));
		} catch (final DAOException e) {
			Assert.assertEquals("password: ne peut pas être nul", e.getMessage());
		}
	}

	@Test
	public void testGet() throws DAOException {
		final User user = new User("login", "password");
		user.setEmail("login@gmail.com");
		userDAO.addUser(user);

		Assert.assertEquals(user, userDAO.getUser("login"));

		try {
			userDAO.getUser("login2");
		} catch (final DAOException e) {
			Assert.assertEquals("unknown user login2", e.getMessage());
		}

	}

	@Test
	public void testDelete() throws DAOException {
		userDAO.addUser(new User("login", "password"));

		userDAO.deleteUser("login");

		userDAO.deleteUser("login2");
	}

}