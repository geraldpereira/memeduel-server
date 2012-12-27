package fr.byob.game.memeduel.server.rest.manager.impl;

import com.googlecode.objectify.Key;

import fr.byob.game.memeduel.domain.Level;
import fr.byob.game.memeduel.server.rest.db.bean.User;

public class DBLevelBuilder {

	private Long id;
	private String owner;
	private String content;
	private String title;

	public DBLevelBuilder id(final Long id) {
		this.id = id;
		return this;
	}

	public DBLevelBuilder owner(final String owner) {
		this.owner = owner;
		return this;
	}

	public DBLevelBuilder content(final String content) {
		this.content = content;
		return this;
	}

	public DBLevelBuilder title(final String title) {
		this.title = title;
		return this;
	}

	public DBLevelBuilder domainLevel(final Level level) {
		return id(level.getId()).owner(level.getOwner()).content(level.getContent()).title(level.getTitle());
	}

	public fr.byob.game.memeduel.server.rest.db.bean.Level build() {
		final fr.byob.game.memeduel.server.rest.db.bean.Level level = new fr.byob.game.memeduel.server.rest.db.bean.Level(new Key<User>(User.class, owner), content, title);
		level.setId(id);
		return level;
	}
}