package fr.byob.game.memeduel.server.rest.manager.impl;

import fr.byob.game.memeduel.domain.Level;

public class DomainLevelBuilder {
	private Long id;
	private String owner;
	private String content;
	private String title;

	public DomainLevelBuilder id(final Long id) {
		this.id = id;
		return this;
	}

	public DomainLevelBuilder owner(final String owner) {
		this.owner = owner;
		return this;
	}

	public DomainLevelBuilder content(final String content) {
		this.content = content;
		return this;
	}

	public DomainLevelBuilder title(final String title) {
		this.title = title;
		return this;
	}

	public DomainLevelBuilder dbLevel(final fr.byob.game.memeduel.server.rest.db.bean.Level level) {
		return id(level.getId()).owner(level.getOwner().getName()).content(level.getContent()).title(level.getTitle());
	}

	public Level build() {
		final Level level = new Level(id, owner, content, title);
		return level;
	}
}