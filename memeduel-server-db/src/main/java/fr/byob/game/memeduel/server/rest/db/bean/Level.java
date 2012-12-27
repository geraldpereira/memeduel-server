package fr.byob.game.memeduel.server.rest.db.bean;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

import fr.byob.game.memeduel.server.rest.db.bean.validation.AddLevelChecks;
import fr.byob.game.memeduel.server.rest.db.bean.validation.UpdateLevelChecks;

@Entity
public class Level {

	@Id
	@NotNull(groups = UpdateLevelChecks.class)
	@Null(groups = AddLevelChecks.class)
	private Long id;

	@Parent
	@NotNull(groups = { AddLevelChecks.class, UpdateLevelChecks.class })
	private Key<User> owner;

	@Unindexed
	@Pattern(regexp = "[\\w\\s]*", groups = { AddLevelChecks.class, UpdateLevelChecks.class }, message = "can only contain letters, numbers, spaces and '_'")
	private String title;

	@Unindexed
	@NotNull(groups = { AddLevelChecks.class, UpdateLevelChecks.class })
	private String content;

	private Date updated;

	public Level() {
	}

	public Level(final Key<User> owner, final String content, final String title) {
		this.owner = owner;
		this.content = content;
		this.title = title;
	}

	public Level(final Key<User> owner, final String content) {
		this(owner, content, null);
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setOwner(final Key<User> owner) {
		this.owner = owner;
	}

	public Key<User> getOwner() {
		return this.owner;
	}

	public String getContent() {
		return this.content;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@PrePersist
	void onPersist() {
		updated = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		final Level other = (Level) obj;
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (updated == null) {
			if (other.updated != null) {
				return false;
			}
		} else if (!updated.equals(other.updated)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Level [id=" + id + ", owner=" + owner + ", title=" + title + ", content=" + content + ", updated=" + updated + "]";
	}

}
