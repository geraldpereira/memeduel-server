package fr.byob.game.memeduel.server.rest.manager.impl;

import fr.byob.game.memeduel.domain.User;

public class DomainUserBuilder {
	private String login;
	private String email;
	private String password;

	public DomainUserBuilder login(final String login) {
		this.login = login;
		return this;
	}

	public DomainUserBuilder email(final String email) {
		this.email = email;
		return this;
	}

	public DomainUserBuilder password(final String password) {
		this.password = password;
		return this;
	}

	public DomainUserBuilder dbUser(final fr.byob.game.memeduel.server.rest.db.bean.User user) {
		return login(user.getLogin()).password(user.getPassword()).email(user.getEmail());
	}

	public User build() {
		final User user = new User(login, password, email);
		return user;
	}
}