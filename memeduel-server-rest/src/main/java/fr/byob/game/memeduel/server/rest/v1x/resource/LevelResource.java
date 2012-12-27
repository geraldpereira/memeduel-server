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
import fr.byob.game.memeduel.domain.Level;
import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.AuthenticationFilter;
import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.exception.IllegalRequestException;
import fr.byob.game.memeduel.server.rest.manager.LevelManager;
import fr.byob.game.memeduel.server.rest.v1x.V1XConstants;

@Path(V1XConstants.PATH + "/level/")
public class LevelResource {
	@InjectLogger
	private Logger log;

	@Inject
	private LevelManager levelManager;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/add")
	public Level add(final Level level, @HeaderParam("authorization") final String authHeader) {
		log.info("ADD " + level);
		checkOwner(level.getOwner(), authHeader);
		try {
			levelManager.addLevel(level);
		} catch (final DAOException e) {
			throw new IllegalRequestException(e);
		}
		return level;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/update")
	public Level update(final Level level, @HeaderParam("authorization") final String authHeader) {
		log.info("UPDATE " + level);
		checkOwner(level.getOwner(), authHeader);
		try {
			levelManager.updateLevel(level);
		} catch (final DAOException e) {
			throw new IllegalRequestException(e);
		}
		return level;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/owned/{login}/{limit}/{offset}")
	public Level[] owned(@PathParam("login") final String login, @PathParam("limit") final int limit, @PathParam("offset") final int offset) {
		log.info("OWNED " + login);
		return levelManager.owned(login, limit, offset);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/latest/{limit}/{offset}")
	public Level[] latest(@PathParam("limit") final int limit, @PathParam("offset") final int offset) {
		log.info("LATEST");
		return levelManager.latest(limit, offset);
	}

	// @DELETE Does not work with some web browsers
	@GET
	@Path("/delete/{id}")
	public void delete(@PathParam("id") final Long id, @HeaderParam("authorization") final String authHeader) {
		log.info("DELETE " + id);
		final User headerUser = AuthenticationFilter.getUserFromHeader(authHeader);
		Level level = null;
		try{
			level = levelManager.getLevel(headerUser.getLogin(), id);
		}catch(final DAOException e){
			throw new IllegalRequestException(e);
		}
		levelManager.deleteLevel(level);
	}

	private static User checkOwner(final String owner, final String authHeader) {
		final User headerUser = AuthenticationFilter.getUserFromHeader(authHeader);
		// No need to check the password, it is done in the AuthenticationFilter
		if (headerUser == null || !owner.equals(headerUser.getLogin())) {
			throw new IllegalRequestException("unauthorized access");
		}
		return headerUser;
	}

}
