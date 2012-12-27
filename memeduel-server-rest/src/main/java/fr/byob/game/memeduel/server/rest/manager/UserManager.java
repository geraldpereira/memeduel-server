package fr.byob.game.memeduel.server.rest.manager;

import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.db.DAOException;

public interface UserManager {
	public void addUser(User user) throws DAOException;

	public User getUser(String id) throws DAOException;

	public void deleteUser(String id);

	public void updateUser(User user) throws DAOException;
}
