package fr.byob.game.memeduel.server.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.core.util.Base64;

import fr.byob.gae.server.commons.guice.InjectLogger;
import fr.byob.game.memeduel.domain.User;
import fr.byob.game.memeduel.server.rest.db.DAOException;
import fr.byob.game.memeduel.server.rest.manager.UserManager;

@Singleton
public class AuthenticationFilter implements Filter {

	@InjectLogger
	private Logger log;

	@Inject
	private UserManager userManager;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		// OPTIONS methods is used for CORS, no auth is required with this
		// method
		if (!"OPTIONS".equals(httpRequest.getMethod())) {
			final String authHeader = httpRequest.getHeader("authorization");

			final User headerUser = getUserFromHeader(authHeader);
			if (headerUser == null) {
				log.warn("Login failed : no  auth header!");
				httpResponse.setContentType("text/plain");
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "unauthorized access: no authorization header");
				return;
			}

			try {
				final User user = userManager.getUser(headerUser.getLogin());

				if (!user.getPassword().equals(headerUser.getPassword())) {
					log.info("Login failed for " + headerUser.getLogin());
					httpResponse.setContentType("text/plain");
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "unauthorized access: wrong password");
					return;
				}
			} catch (final DAOException e) {
				log.info("Login failed for " + headerUser.getLogin());
				httpResponse.setContentType("text/plain");
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "unauthorized access: unknown user");
				return;
			}

			log.info("Login success for " + headerUser.getLogin());
		}
		chain.doFilter(request, response);

	}

	public static User getUserFromHeader(final String authHeader) {
		if (authHeader == null) {
			return null;
		}

		final String encodedValue = authHeader.split(" ")[1];
		final String[] decodedValue = Base64.base64Decode(encodedValue).split(":");

		if (decodedValue.length != 2) {
			return null;
		}

		final String login = decodedValue[0];
		final String password = decodedValue[1];

		return new User(login, password, null);
	}

	public static String getHeaderFromUser(final User user) {
		final byte[] pwd = Base64.encode(user.getLogin() + ":" + user.getPassword());
		final String value = new String(pwd);
		return "Basic " + value;
	}

	@Override
	public void destroy() {

	}
}
