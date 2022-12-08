package org.passen.valid8;

import static com.jnape.palatable.lambda.adt.Either.left;
import static com.jnape.palatable.lambda.adt.Either.right;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jnape.palatable.lambda.adt.Either;
import org.junit.jupiter.api.MethodOrderer.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(Random.class)
class ValidatorTest {
  @Test
  void canPassValidation() {
    final Validator<Boolean, Boolean> alwaysPasses = Either::right;
    alwaysPasses
        .validate(true)
        .match(__ -> fail("this should not fail"), b -> assertThat(b).isTrue());
  }

  @Test
  void canFailValidation() {
    final Validator<Boolean, Boolean> alwaysFails =
        b -> left(new ValidationException("always fails"));
    alwaysFails
        .validate(true)
        .match(
            e -> assertThat(e).isExactlyInstanceOf(ValidationException.class),
            __ -> fail("this should not pass"));
  }

  @Test
  void canChainValidation() {
    final Validator<Integer, Integer> isGreaterThanOne =
        n -> (n > 1) ? right(n) : left(new ValidationException("not greater than one"));
    final Validator<Integer, Integer> isLessThanFour =
        n -> (n < 4) ? right(n) : left(new ValidationException("not less than four"));
    final Validator<Integer, Integer> isTwo =
        n -> (n == 2) ? right(n) : left(new ValidationException("not two"));

    final int passingSubject = 2;
    isLessThanFour
        .validate(passingSubject)
        .flatMap(isGreaterThanOne::validate)
        .flatMap(isTwo::validate)
        .match(__ -> fail("this should not fail"), n -> assertThat(n).isEqualTo(passingSubject));

    final int failingSubject = 3;
    isLessThanFour
        .validate(failingSubject)
        .flatMap(isGreaterThanOne::validate)
        .flatMap(isTwo::validate)
        .match(
            e -> assertThat(e).isExactlyInstanceOf(ValidationException.class),
            __ -> fail("this should not pass"));
  }
}
