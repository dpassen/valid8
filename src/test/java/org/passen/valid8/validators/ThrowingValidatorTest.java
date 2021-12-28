package org.passen.valid8.validators;

import static com.jnape.palatable.lambda.functions.Effect.fromConsumer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.MethodOrderer.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.passen.valid8.ValidationException;

@TestMethodOrder(Random.class)
class ThrowingValidatorTest {
  @Test
  void canPassValidation() {
    final long testSubject = 123L;
    new ThrowingValidator<String, Long>(Long::parseLong)
        .validate(Long.toString(testSubject))
        .peek(
            __ -> fail("this should not fail"),
            fromConsumer(n -> assertThat(n).isEqualTo(testSubject)));
  }

  @Test
  void canFailValidation() {
    final String testSubject = "foo";
    new ThrowingValidator<String, Long>(Long::parseLong)
        .validate(testSubject)
        .peek(
            fromConsumer(
                e ->
                    assertThat(e)
                        .isExactlyInstanceOf(ValidationException.class)
                        .hasCauseExactlyInstanceOf(NumberFormatException.class)),
            __ -> fail("this should not pass"));
  }
}
