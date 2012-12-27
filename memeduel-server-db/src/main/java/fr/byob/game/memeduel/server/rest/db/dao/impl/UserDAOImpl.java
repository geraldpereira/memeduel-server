package fr.byob.game.memeduel.server.rest.db.dao.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.bean.User;
import fr.byob.game.memeduel.server.rest.db.dao.UserDAO;

public class UserDAOImpl implements UserDAO {

	@Inject
	private Validator validator;

	@Override
	public void addUser(final User user) throws DAOException {
		final Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		if (!constraintViolations.isEmpty()) {
			throw new DAOException(constraintViolations.iterator().next());
		}

		final Objectify ofy = ObjectifyService.begin();

		final String login = user.getLogin();
		if (ofy.find(new Key<User>(User.class, login)) != null) {
			throw new DAOException("username " + login + " is not available");
		}

		ofy.put(user);
	}

	@Override
	public User getUser(final String id) throws DAOException {
		final Objectify ofy = ObjectifyService.begin();
		try {
			return ofy.get(new Key<User>(User.class, id));
		} catch (final NotFoundException exception) {
			throw new DAOException("unknown user " + id);
		}
	}

	@Override
	public void deleteUser(final String id) {
		final Objectify ofy = ObjectifyService.begin();
		ofy.delete(new Key<User>(User.class, id));
	}

	@Override
	public void updateUser(final User user) throws DAOException {
		final String login = user.getLogin();
		final Objectify ofy = ObjectifyService.begin();
		if (ofy.find(new Key<User>(User.class, user.getLogin())) == null) {
			throw new DAOException("unknown user " + login);
		}

		final Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		if (!constraintViolations.isEmpty()) {
			throw new DAOException(constraintViolations.iterator().next());
		}


		ofy.put(user);
	}
}
