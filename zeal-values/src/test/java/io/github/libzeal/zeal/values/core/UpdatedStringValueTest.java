package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;

public class UpdatedStringValueTest {

    @Test
    void given1_whenIsEmpty_thenEvaluationIsCorrect() {
        ValueTestCaseBuilder.forValue(StringValue::new)
            .subject("a")
            .expression((value, subject) -> value.isEmpty())
            .result(FALSE)
            .name("isEmpty")
            .expected("length = 0")
            .actual("length = 1")
            .run();
    }
}
