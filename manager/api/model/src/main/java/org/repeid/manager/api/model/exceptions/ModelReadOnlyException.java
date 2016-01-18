package org.repeid.manager.api.model.exceptions;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */
public class ModelReadOnlyException extends ModelException {

	private static final long serialVersionUID = 1L;

	public ModelReadOnlyException() {
	}

	public ModelReadOnlyException(String message) {
		super(message);
	}

	public ModelReadOnlyException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelReadOnlyException(Throwable cause) {
		super(cause);
	}
}
