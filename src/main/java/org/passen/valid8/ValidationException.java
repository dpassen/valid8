package org.passen.valid8;

public class ValidationException extends RuntimeException {
  public ValidationException(final String message) {
    super(message);
  }

  public ValidationException(final Throwable cause) {
    super(cause);
  }
}
