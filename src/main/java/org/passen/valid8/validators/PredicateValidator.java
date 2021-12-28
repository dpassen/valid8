package org.passen.valid8.validators;

import static com.jnape.palatable.lambda.adt.Either.left;
import static com.jnape.palatable.lambda.adt.Either.right;

import com.jnape.palatable.lambda.adt.Either;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.passen.valid8.SimpleValidator;
import org.passen.valid8.ValidationException;

@AllArgsConstructor
@RequiredArgsConstructor
public class PredicateValidator<V> implements SimpleValidator<V> {
  @NonNull private final Predicate<V> predicate;
  @NonNull private String message = "";

  @Override
  public Either<ValidationException, V> validate(final V v) {
    return predicate.test(v) ? right(v) : left(new ValidationException(message));
  }
}
