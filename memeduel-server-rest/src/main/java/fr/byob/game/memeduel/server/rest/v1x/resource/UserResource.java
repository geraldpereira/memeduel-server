package fr.byob.game.memeduel.server.rest.v1x.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import fr.byob.gae.server.commons.guice.InjectLogger;
import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.AuthenticationFilter;
import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.exception.IllegalRequestException;
import fr.byob.game.memeduel.server.rest.manager.UserManager;
import fr.byob.game.memeduel.server.rest.v1x.V1XConstants;

@Path(V1XConstants.PATH + "/user/")
public class UserResource {
	@InjectLogger
	private Logger log;

	@Inject
	private UserManager userManager;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/add")
	public User add(final User user) {
		log.info("ADD " + user);
		try {
			userManager.addUser(user);
		} catch (final DAOException e) {
			throw new IllegalRequestException(e);
		}
		return user;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/update")
	public User update(final User user, @HeaderParam("authorization") final String authHeader) {
		log.info("UPDATE " + user);
		checkSelf(user.getLogin(), authHeader);
		try {
			userManager.updateUser(user);
		} catch (final DAOException e) {
			throw new IllegalRequestException(e);
		}
		return user;
	}

	// @GET
	// @Produces({ MediaType.APPLICATION_JSON })
	// @Path("/test/{val}")
	// public User test(@PathParam("val") final String val) {
	// log.info("TEST " + val);
	// if ("fail".equals(val)) {
	// throw new IllegalRequestException("putain");
	// }
	// return new User("noob", "noob");
	// }

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/get/{login}")
	public User get(@PathParam("login") final String login, @HeaderParam("authorization") final String authHeader) {
		log.info("GET " + login);
		checkSelf(login, authHeader);
		try {
			return userManager.getUser(login);
		} catch (final DAOException e) {
			throw new IllegalRequestException(e);
		}
	}

	// @DELETE Does not work with some web browsers
	@GET
	@Path("/delete/{login}")
	public void delete(@PathParam("login") final String login, @HeaderParam("authorization") final String authHeader) {
		log.info("DELETE " + login);
		checkSelf(login, authHeader);
		userManager.deleteUser(login);
	}

	private static void checkSelf(final String login, final String authHeader) {
		final User headerUser = AuthenticationFilter.getUserFromHeader(authHeader);
		// No need to check the password, it is done in the AuthenticationFilter
		if (headerUser == null || !headerUser.getLogin().equals(login)) {
			throw new IllegalRequestException("unauthorized access");
		}
	}

}
