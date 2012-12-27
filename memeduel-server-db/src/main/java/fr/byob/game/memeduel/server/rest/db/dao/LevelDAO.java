package fr.byob.game.memeduel.server.rest.db.dao;

import java.util.List;

import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.bean.Level;

public interface LevelDAO {

	public void addLevel(Level level) throws DAOException;

	public Level getLevel(String ownerId, Long id) throws DAOException;

	public List<Level> owned(String ownerId, int limit, int offset);

	public List<Level> latest(int limit, int offset);

	public void deleteLevel(Level level);

	public void updateLevel(Level level) throws DAOException;
}
