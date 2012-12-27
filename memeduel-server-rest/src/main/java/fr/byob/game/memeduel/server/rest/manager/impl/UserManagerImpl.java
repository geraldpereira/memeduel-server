package fr.byob.game.memeduel.server.rest.manager.impl;

import com.google.inject.Inject;

import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.dao.UserDAO;
import fr.byob.game.memeduel.server.rest.manager.UserManager;

public class UserManagerImpl implements UserManager {
	@Inject
	private UserDAO userDAO;

	@Override
	public void addUser(final User user) throws DAOException {
		userDAO.addUser(new DBUserBuilder().domainUser(user).build());
	}

	@Override
	public User getUser(final String id) throws DAOException {
		return new DomainUserBuilder().dbUser(userDAO.getUser(id)).build();
	}

	@Override
	public void deleteUser(final String id) {
		userDAO.deleteUser(id);
	}

	@Override
	public void updateUser(final User user) throws DAOException {
		userDAO.updateUser(new DBUserBuilder().domainUser(user).build());
	}

}
