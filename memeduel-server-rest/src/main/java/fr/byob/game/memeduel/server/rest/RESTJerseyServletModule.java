package fr.byob.game.memeduel.server.rest;

import java.util.HashMap;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import fr.byob.game.memeduel.server.rest.manager.LevelManager;
import fr.byob.game.memeduel.server.rest.manager.UserManager;
import fr.byob.game.memeduel.server.rest.manager.impl.LevelManagerImpl;
import fr.byob.game.memeduel.server.rest.manager.impl.UserManagerImpl;

public class RESTJerseyServletModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {
		this.bind(UserManager.class).to(UserManagerImpl.class);
		this.bind(LevelManager.class).to(LevelManagerImpl.class);

		/* bind the REST resources */
		this.bind(fr.byob.game.memeduel.server.rest.v1x.resource.UserResource.class);
		// this.bind(fr.byob.game.memeduel.server.rest.v1x.resource.LevelResource.class);

		/* bind jackson converters for JAXB/JSON serialization */
		this.bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
		this.bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

		final HashMap<String, String> initParams = new HashMap<String, String>();
		// <init-param>
		// <param-name>com.sun.jersey.api.json.POJOMappingFeature</param-name>
		// <param-value>true</param-value>
		// </init-param>

		initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		initParams.put("com.sun.jersey.config.property.packages", "fr.byob.game.memeduel.server.rest.exception;fr.byob.game.memeduel.server.rest.v1x");
		// http://stackoverflow.com/questions/8275064/google-app-engine-500-error
		initParams.put("com.sun.jersey.config.feature.DisableWADL", "true");

		this.serve("/api/*").with(GuiceContainer.class, initParams);
		this.filter("/*").through(CORSHeadersFilter.class);
		this.filter("/api/v1.0/user/delete/*").through(AuthenticationFilter.class);
		this.filter("/api/v1.0/user/update/*").through(AuthenticationFilter.class);
		this.filter("/api/v1.0/user/get/*").through(AuthenticationFilter.class);
		this.filter("/api/v1.0/level/*").through(AuthenticationFilter.class);
	}
}
