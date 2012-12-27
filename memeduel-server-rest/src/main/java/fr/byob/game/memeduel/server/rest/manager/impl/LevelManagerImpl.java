package fr.byob.game.memeduel.server.rest.manager.impl;

import java.util.List;

import com.google.inject.Inject;

import fr.byob.game.memeduel.domain.Level;
import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.db.dao.LevelDAO;
import fr.byob.game.memeduel.server.rest.manager.LevelManager;

public class LevelManagerImpl implements LevelManager {

	@Inject
	private LevelDAO levelDAO;

	@Override
	public void addLevel(final Level level) throws DAOException {
		final fr.byob.game.memeduel.server.rest.db.bean.Level dbLevel = new DBLevelBuilder().domainLevel(level).build();
		levelDAO.addLevel(dbLevel);
		level.setId(dbLevel.getId());
	}

	@Override
	public Level getLevel(final String ownerId, final Long id) throws DAOException {
		return new DomainLevelBuilder().dbLevel(levelDAO.getLevel(ownerId, id)).build();
	}

	@Override
	public Level[] owned(final String ownerId, final int limit, final int offset) {
		final List<fr.byob.game.memeduel.server.rest.db.bean.Level> dbLevels = levelDAO.owned(ownerId, limit, offset);
		return convertLevels(dbLevels);
	}

	@Override
	public Level[] latest(final int limit, final int offset) {
		final List<fr.byob.game.memeduel.server.rest.db.bean.Level> dbLevels = levelDAO.latest(limit, offset);
		return convertLevels(dbLevels);
	}

	@Override
	public void deleteLevel(final Level level) {
		levelDAO.deleteLevel(new DBLevelBuilder().domainLevel(level).build());
	}

	@Override
	public void updateLevel(final Level level) throws DAOException {
		levelDAO.updateLevel(new DBLevelBuilder().domainLevel(level).build());
	}

	private static Level[] convertLevels(final List<fr.byob.game.memeduel.server.rest.db.bean.Level> dbLevels) {
		final Level[] levels = new Level[dbLevels.size()];
		int i = 0;
		for (final fr.byob.game.memeduel.server.rest.db.bean.Level level : dbLevels) {
			levels[i] = new DomainLevelBuilder().dbLevel(level).build();
			i++;
		}
		return levels;
	}

}
