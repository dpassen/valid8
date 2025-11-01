package org.passen.valid8.validators;

import static com.jnape.palatable.lambda.adt.Either.trying;

import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.lambda.functions.Fn1;
import java.util.Objects;
import org.passen.valid8.ValidationException;
import org.passen.valid8.Validator;

public record ThrowingValidator<A, B>(Fn1<A, B> transform) implements Validator<A, B> {
  public ThrowingValidator {
    Objects.requireNonNull(transform);
  }

  @Override
  public Either<ValidationException, B> validate(final A a) {
    return trying(() -> transform.checkedApply(a), ValidationException::new);
  }
}
