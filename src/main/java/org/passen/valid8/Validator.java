package org.passen.valid8;

import com.jnape.palatable.lambda.adt.Either;

@FunctionalInterface
public interface Validator<A, B> {
    Either<ValidationException, B> validate(A a);
}
