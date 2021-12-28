package org.passen.valid8.validators;

import static com.jnape.palatable.lambda.adt.Either.trying;

import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.lambda.functions.Fn1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.passen.valid8.ValidationException;
import org.passen.valid8.Validator;

@RequiredArgsConstructor
public class ThrowingValidator<A, B> implements Validator<A, B> {
  @NonNull private final Fn1<A, B> transform;

  @Override
  public Either<ValidationException, B> validate(final A a) {
    return trying(() -> transform.checkedApply(a), ValidationException::new);
  }
}
