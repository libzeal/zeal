package io.github.libzeal.zeal.expression.evaluation;

import io.github.libzeal.zeal.expression.predicate.ValueSupplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ValueSupplierTest {

    @Test
    void givenNullValue_whenOf_thenSupplierResultsInSuppliedValue() {

        ValueSupplier<Object> supplier = ValueSupplier.of(null);

        assertNull(supplier.compute(new Object()));
    }

    @Test
    void givenValidValue_whenOf_thenSupplierResultsInSuppliedValue() {

        String value = "foo";

        ValueSupplier<Object> supplier = ValueSupplier.of(value);

        assertEquals(value, supplier.compute(new Object()));
    }
}
