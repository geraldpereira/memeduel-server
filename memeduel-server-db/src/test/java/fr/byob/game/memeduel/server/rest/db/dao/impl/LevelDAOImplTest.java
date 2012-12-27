package fr.byob.game.memeduel.server.rest.db.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.objectify.Key;

import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.bean.Level;
import fr.byob.game.memeduel.server.rest.db.bean.User;
import fr.byob.game.memeduel.server.rest.db.dao.LevelDAO;
import fr.byob.game.memeduel.server.rest.db.dao.UserDAO;

public class LevelDAOImplTest extends AbstractDAOImplTest {

	private UserDAO userDAO;
	private LevelDAO levelDAO;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		userDAO = injector.getInstance(UserDAO.class);
		levelDAO = injector.getInstance(LevelDAO.class);
	}

	@Test
	public void testAdd() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(user);


		final Level level = new Level(new Key<User>(User.class, user.getLogin()), "crriiiiiii", "title");
		levelDAO.addLevel(level);
		Assert.assertNotNull(level.getId());
		Assert.assertNotNull(level.getUpdated());

		try {
			final Level level2 = new Level(null, "crriiiiiii");
			levelDAO.addLevel(level2);
		} catch (final DAOException e) {
			Assert.assertEquals("owner: ne peut pas être nul", e.getMessage());
		}

		try {
			final Level level2 = new Level(new Key<User>(User.class, user.getLogin()), "crriiiiiii");
			level2.setId(Long.valueOf(1));
			levelDAO.addLevel(level2);
		} catch (final DAOException e) {
			Assert.assertEquals("id: doit être nul", e.getMessage());
		}
	}


	@Test
	public void testUpdate() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(user);

		final Level level = new Level(new Key<User>(User.class, user.getLogin()), "crriiiiiii", "title");
		levelDAO.addLevel(level);
		final Date date = level.getUpdated();
		Assert.assertNotNull(level.getId());
		Assert.assertNotNull(date);
		Assert.assertEquals(new Key<User>(User.class, user.getLogin()), level.getOwner());

		level.setContent("croooo");
		level.setTitle("croooo");
		levelDAO.updateLevel(level);
		Assert.assertEquals("croooo", level.getContent());
		Assert.assertEquals("croooo", level.getTitle());
		Assert.assertTrue(!date.equals(level.getUpdated()));

		try {
			level.setContent(null);
			levelDAO.updateLevel(level);
		} catch (final DAOException e) {
			Assert.assertEquals("content: ne peut pas être nul", e.getMessage());
		}

		try {
			level.setContent("croooo");
			level.setOwner(null);
			levelDAO.updateLevel(level);
		} catch (final DAOException e) {
			Assert.assertEquals("owner: ne peut pas être nul", e.getMessage());
		}

		try {
			level.setOwner(new Key<User>(User.class, user.getLogin()));
			level.setId(null);
			levelDAO.updateLevel(level);
		} catch (final DAOException e) {
			Assert.assertEquals("id: ne peut pas être nul", e.getMessage());
		}
	}

	@Test
	public void testDelete() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(user);

		final Level level = new Level(new Key<User>(User.class, user.getLogin()), "crriiiiiii", "title");
		levelDAO.addLevel(level);
		Assert.assertNotNull(level.getId());

		levelDAO.deleteLevel(level);
	}

	@Test
	public void testGet() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(new User("login", "password"));

		final Level level1 = new Level(new Key<User>(User.class, user.getLogin()), "crriiiiiii", "title");
		levelDAO.addLevel(level1);
		Assert.assertNotNull(level1.getId());

		final Level level2 = levelDAO.getLevel(user.getLogin(), level1.getId());
		Assert.assertEquals(level1, level2);
	}

	@Test
	public void testList() throws DAOException {
		final User user = new User("login", "password");
		userDAO.addUser(user);

		final Level level1 = new Level(new Key<User>(User.class, user.getLogin()), "level1", "level1");
		levelDAO.addLevel(level1);
		Assert.assertNotNull(level1.getId());

		final Level level2 = new Level(new Key<User>(User.class, user.getLogin()), "level2", "level2");
		levelDAO.addLevel(level2);
		Assert.assertNotNull(level2.getId());

		final Level level3 = new Level(new Key<User>(User.class, user.getLogin()), "level3", "level3");
		levelDAO.addLevel(level3);
		Assert.assertNotNull(level3.getId());

		final List<Level> expected = Arrays.asList(level1, level2, level3);

		final List<Level> levels = levelDAO.owned(user.getLogin(), 10, 0);
		for (final Level level : expected) {
			Assert.assertTrue(levels.contains(level));
		}

		final User user2 = new User("login2", "password");
		userDAO.addUser(user2);

		final Level level4 = new Level(new Key<User>(User.class, user2.getLogin()), "level4", "level4");
		levelDAO.addLevel(level4);
		Assert.assertNotNull(level4.getId());

		final List<Level> expected2 = Arrays.asList(level4, level3, level2, level1);
		final List<Level> levels2 = levelDAO.latest(10, 0);
		for (int i = 0; i < levels2.size(); i++) {
			Assert.assertEquals(expected2.get(i), levels2.get(i));
		}
	}


}