package fr.byob.game.memeduel.server.rest.manager.impl;

import fr.byob.game.memeduel.domain.User;

public class DBUserBuilder {
	private String login;
	private String email;
	private String password;

	public DBUserBuilder login(final String login) {
		this.login = login;
		return this;
	}

	public DBUserBuilder email(final String email) {
		this.email = email;
		return this;
	}

	public DBUserBuilder password(final String password) {
		this.password = password;
		return this;
	}

	public DBUserBuilder domainUser(final User user) {
		return login(user.getLogin()).password(user.getPassword()).email(user.getEmail());
	}

	public fr.byob.game.memeduel.server.rest.db.bean.User build() {
		final fr.byob.game.memeduel.server.rest.db.bean.User user = new fr.byob.game.memeduel.server.rest.db.bean.User(login, password);
		user.setEmail(email);
		return user;
	}
}