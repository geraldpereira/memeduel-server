package fr.byob.game.memeduel.server.rest.db.dao.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.bean.Level;
import fr.byob.game.memeduel.server.rest.db.bean.User;
import fr.byob.game.memeduel.server.rest.db.bean.validation.AddLevelChecks;
import fr.byob.game.memeduel.server.rest.db.bean.validation.UpdateLevelChecks;
import fr.byob.game.memeduel.server.rest.db.dao.LevelDAO;

public class LevelDAOImpl implements LevelDAO {

	@Inject
	private Validator validator;

	@Override
	public void addLevel(final Level level) throws DAOException{
		final Set<ConstraintViolation<Level>> constraintViolations = validator.validate(level, AddLevelChecks.class);
		if (!constraintViolations.isEmpty()) {
			throw new DAOException(constraintViolations.iterator().next());
		}
		final Objectify ofy = ObjectifyService.begin();
		ofy.put(level);
	}

	@Override
	public Level getLevel(final String ownerId,final Long id) throws DAOException {
		final Objectify ofy = ObjectifyService.begin();
		try {

			return ofy.get(new Key<Level>(new Key<User>(User.class, ownerId), Level.class, id));
		} catch (final NotFoundException exception) {
			throw new DAOException("Unknown level " + id);
		}
	}

	@Override
	public List<Level> owned(final String ownerId, final int limit, final int offset) {
		final Objectify ofy = ObjectifyService.begin();
		final List<Level> levels = ofy.query(Level.class).ancestor(new Key<User>(User.class, ownerId)).order("-updated").limit(limit).offset(offset).list();
		return levels;
	}

	@Override
	public List<Level> latest(final int limit, final int offset) {
		final Objectify ofy = ObjectifyService.begin();
		final List<Level> levels = ofy.query(Level.class).order("-updated").limit(limit).offset(offset).list();
		return levels;
	}

	@Override
	public void deleteLevel(final Level level) {
		final Objectify ofy = ObjectifyService.begin();
		ofy.delete(level);
	}

	@Override
	public void updateLevel(final Level level) throws DAOException{
		final Set<ConstraintViolation<Level>> constraintViolations = validator.validate(level, UpdateLevelChecks.class);
		if (!constraintViolations.isEmpty()) {
			throw new DAOException(constraintViolations.iterator().next());
		}
		final Objectify ofy = ObjectifyService.begin();
		ofy.put(level);
	}
}
