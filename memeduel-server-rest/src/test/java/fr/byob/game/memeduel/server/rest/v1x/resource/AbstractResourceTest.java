package fr.byob.game.memeduel.server.rest.v1x.resource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.byob.gae.server.commons.guice.LoggerModule;
import fr.byob.game.memeduel.server.rest.RESTJerseyServletModule;
import fr.byob.game.memeduel.server.rest.db.DAOModule;

public abstract class AbstractResourceTest {

	protected final static String LOGIN = "login";
	protected final static String PASSWORD = "password";
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	protected static Injector injector;


	@BeforeClass
	public static void beforeClass() {
		injector = Guice.createInjector(new LoggerModule(),
				new RESTJerseyServletModule(), new DAOModule());
	}

	@Before
	public void before() {
		helper.setUp();
	}

	@After
	public void after() {
		helper.tearDown();
	}
}
