package fr.byob.game.memeduel.server.rest.manager;

import fr.byob.game.memeduel.domain.Level;
import fr.byob.game.memeduel.server.rest.db.DAOException;

public interface LevelManager {

	public void addLevel(Level level) throws DAOException;

	public Level getLevel(String ownerId, Long id) throws DAOException;

	public Level[] owned(String ownerId, int limit, int offset);

	public Level[] latest(int limit, int offset);

	public void deleteLevel(Level level);

	public void updateLevel(Level level) throws DAOException;
}
