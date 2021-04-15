package org.passen.valid8.validators;

import org.junit.jupiter.api.MethodOrderer.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.passen.valid8.ValidationException;

import static com.jnape.palatable.lambda.functions.Effect.fromConsumer;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@TestMethodOrder(Random.class)
class PredicateValidatorTest {
    @Test
    void canPassValidation() {
        final long testSubject = 2L;
        new PredicateValidator<Long>(n -> n % 2 == 0)
                .validate(testSubject)
                .peek(__ -> fail("This should not fail"),
                        fromConsumer(n -> assertThat(n).isEqualTo(testSubject)));
    }

    @Test
    void canFailValidation() {
        final long testSubject = 2L;
        new PredicateValidator<Long>(n -> n % 2 == 1)
                .validate(testSubject)
                .peek(fromConsumer(e -> assertThat(e)
                                .isExactlyInstanceOf(ValidationException.class)
                                .hasMessage("")),
                        __ -> fail("This should not pass"));
    }

    @Test
    void canFailValidationWithMessage() {
        final long testSubject = 2L;
        final String validationMessage = randomUUID().toString();

        new PredicateValidator<Long>(n -> n % 2 == 1, validationMessage)
                .validate(testSubject)
                .peek(fromConsumer(e -> assertThat(e)
                                .isExactlyInstanceOf(ValidationException.class)
                                .hasMessage(validationMessage)),
                        __ -> fail("This should not pass"));
    }
}
