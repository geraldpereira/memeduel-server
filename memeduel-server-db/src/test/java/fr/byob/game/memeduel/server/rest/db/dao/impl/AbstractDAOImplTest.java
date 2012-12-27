package fr.byob.game.memeduel.server.rest.db.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.byob.game.memeduel.server.rest.db.DAOModule;

public abstract class AbstractDAOImplTest {
	protected static Injector injector;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void beforeClass() {
		injector = Guice.createInjector(new DAOModule());
	}

	@Before
	public void setUp() {
		this.helper.setUp();
	}

	@After
	public void tearDown() {
		this.helper.tearDown();
	}
}
