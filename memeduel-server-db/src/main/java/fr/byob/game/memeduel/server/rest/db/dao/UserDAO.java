package fr.byob.game.memeduel.server.rest.db.dao;

import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.bean.User;

public interface UserDAO {

	public void addUser(User user) throws DAOException;

	public User getUser(String id) throws DAOException;

	public void deleteUser(String id);

	public void updateUser(User user) throws DAOException;
}
