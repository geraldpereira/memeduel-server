package fr.byob.game.memeduel.server.rest.db;

import javax.validation.ConstraintViolation;

public class DAOException extends Exception {
	public DAOException(final String message) {
		super(message);
	}

	public DAOException(final ConstraintViolation<?> constraint) {
		super(constraint.getPropertyPath() + ": " + constraint.getMessage());
	}
}
