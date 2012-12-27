package fr.byob.game.memeduel.server.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import fr.byob.gae.server.commons.guice.LoggerModule;
import fr.byob.game.memeduel.server.rest.db.DAOModule;

public class GuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new LoggerModule(), new RESTJerseyServletModule(), new DAOModule());
	}
}