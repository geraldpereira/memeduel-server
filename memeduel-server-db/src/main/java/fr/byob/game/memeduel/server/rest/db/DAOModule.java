package fr.byob.game.memeduel.server.rest.db;

import javax.validation.Validation;
import javax.validation.Validator;

import com.google.inject.AbstractModule;
import com.googlecode.objectify.ObjectifyService;

import fr.byob.game.memeduel.server.rest.db.bean.Level;
import fr.byob.game.memeduel.server.rest.db.bean.User;
import fr.byob.game.memeduel.server.rest.db.dao.LevelDAO;
import fr.byob.game.memeduel.server.rest.db.dao.UserDAO;
import fr.byob.game.memeduel.server.rest.db.dao.impl.LevelDAOImpl;
import fr.byob.game.memeduel.server.rest.db.dao.impl.UserDAOImpl;

public class DAOModule extends AbstractModule {

	@Override
	protected void configure() {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Level.class);
		bind(UserDAO.class).to(UserDAOImpl.class);
		bind(LevelDAO.class).to(LevelDAOImpl.class);
		bind(Validator.class).toInstance(Validation.buildDefaultValidatorFactory().getValidator());
	}

}
