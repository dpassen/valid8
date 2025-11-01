package org.passen.valid8.validators;

import static com.jnape.palatable.lambda.adt.Either.left;
import static com.jnape.palatable.lambda.adt.Either.right;

import com.jnape.palatable.lambda.adt.Either;
import java.util.Objects;
import java.util.function.Predicate;
import org.passen.valid8.SimpleValidator;
import org.passen.valid8.ValidationException;

public record PredicateValidator<V>(Predicate<V> predicate, String message)
    implements SimpleValidator<V> {
  public PredicateValidator {
    Objects.requireNonNull(predicate);
    Objects.requireNonNull(message);
  }

  public PredicateValidator(final Predicate<V> predicate) {
    this(predicate, "");
  }

  @Override
  public Either<ValidationException, V> validate(final V v) {
    return predicate.test(v) ? right(v) : left(new ValidationException(message));
  }
}
