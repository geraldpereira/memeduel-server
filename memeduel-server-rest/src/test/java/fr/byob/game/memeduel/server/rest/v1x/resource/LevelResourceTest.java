package fr.byob.game.memeduel.server.rest.v1x.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.byob.game.memeduel.domain.Level;
import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.AuthenticationFilter;

public class LevelResourceTest extends AbstractResourceTest {


	private UserResource userResource;
	private LevelResource levelResource;

	@Override
	@Before
	public void before() {
		super.before();
		userResource = injector.getInstance(UserResource.class);
		levelResource = injector.getInstance(LevelResource.class);
		Assert.assertNotNull(userResource);
		Assert.assertNotNull(levelResource);
	}

	/**
	 * We test directly the LevelResource, without going throught the
	 * AuthenticationFilter.
	 * 
	 * The password are NEVER checked!
	 * 
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testLevel() {

		final User user = new User(LOGIN, PASSWORD, "test@gmail.com");
		userResource.add(user);

		final Level level1 = new Level(user.getLogin(), "level1", "level1");
		levelResource.add(level1, AuthenticationFilter.getHeaderFromUser(user));
		Assert.assertNotNull(level1.getId());

		final Level level2 = new Level(user.getLogin(), "level2", "level2");
		levelResource.add(level2, AuthenticationFilter.getHeaderFromUser(user));
		Assert.assertNotNull(level2.getId());

		final Level level3 = new Level(user.getLogin(), "level3", "level3");
		levelResource.add(level3, AuthenticationFilter.getHeaderFromUser(user));
		Assert.assertNotNull(level3.getId());

		final Level[] expected = new Level[] { level2, level1 };

		final Level[] levels = levelResource.owned(LOGIN, 2, 1);
		Assert.assertEquals(expected, levels);

		final Level[] expected2 = new Level[] { level3, level2, level1 };

		final Level[] levels2 = levelResource.latest(10, 0);
		Assert.assertEquals(expected2, levels2);

		level1.setContent("level1edit");
		level1.setTitle("level1edit");
		levelResource.update(level1, AuthenticationFilter.getHeaderFromUser(user));
		Assert.assertEquals("level1edit", level1.getContent());
		Assert.assertEquals("level1edit", level1.getTitle());

		levelResource.delete(level1.getId(), AuthenticationFilter.getHeaderFromUser(user));
		levelResource.delete(level2.getId(), AuthenticationFilter.getHeaderFromUser(user));
		levelResource.delete(level3.getId(), AuthenticationFilter.getHeaderFromUser(user));
	}

}
