package fr.byob.game.memeduel.server.rest.db.bean;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;
import com.googlecode.objectify.condition.IfEmptyString;
import com.googlecode.objectify.condition.IfNull;

@Entity
public class User {

	@Id
	@NotNull
	@Size(min = 3, max = 25)
	@Pattern(regexp = "[\\w]*", message = "can only contain letters, numbers and '_'")
	private String login;

	@Unindexed({ IfNull.class, IfEmptyString.class })
	@Email
	private String email;

	@Unindexed
	@NotNull
	@Size(min = 2, max = 16)
	private String password;

	private Date updated;

	public User() {
	}

	public User(final String login, final String password) {
		this.login = login;
		this.password = password;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}


	public Date getUpdated() {
		return updated;
	}

	@PrePersist
	void onPersist() {
		updated = new Date();
	}


	@Override
	public String toString() {
		return "User [login=" + login + ", email=" + email + ", password=" + password + ", updated=" + updated + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		return true;
	}
}
