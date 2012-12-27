package fr.byob.game.memeduel.server.rest.v1x.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.AuthenticationFilter;
import fr.byob.game.memeduel.server.rest.exception.IllegalRequestException;

public class UserResourceTest extends AbstractResourceTest {

	private UserResource userResource;

	@Override
	@Before
	public void before() {
		super.before();
		userResource = injector.getInstance(UserResource.class);
		Assert.assertNotNull(userResource);
	}

	/**
	 * We test directly the UserResource, without going throught the
	 * AuthenticationFilter.
	 * 
	 * The password are NEVER checked!
	 * 
	 */
	@Test
	public void testUser() {

		final User user = new User(LOGIN, PASSWORD, "test@gmail.com");

		userResource.add(user);

		final User user2 = new User(LOGIN, PASSWORD + "2", null);
		final User updatedUser2 = userResource.update(user2, AuthenticationFilter.getHeaderFromUser(user));
		Assert.assertEquals(user2, updatedUser2);

		final User user3 = userResource.get(LOGIN, AuthenticationFilter.getHeaderFromUser(user2));
		Assert.assertEquals(user2, user3);

		try {
			userResource.get(LOGIN + "FAIL", AuthenticationFilter.getHeaderFromUser(user2));
		} catch (final IllegalRequestException e) {
			Assert.assertEquals("unauthorized access", e.getMessage());
		}

		userResource.delete(LOGIN, AuthenticationFilter.getHeaderFromUser(user2));
	}

}
