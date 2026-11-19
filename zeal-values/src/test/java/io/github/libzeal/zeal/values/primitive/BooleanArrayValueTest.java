package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

class BooleanArrayValueTest extends BaseValueTest<boolean[], BooleanArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final boolean[] VALUE_A = {true};
    private static final boolean[] VALUE_B = {false};

    @Override
    protected BooleanArrayValue expression(final boolean[] value) {
        return new BooleanArrayValue(value);
    }

    @Override
    protected boolean[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected boolean[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new boolean[]{true, false})
                .expression(value -> value.includes(false))
                .result(true)
                .name("includes[false]")
                .expected("includes[false]")
                .actual("includes[false]")
                .hint("'false' found at index 1")
                .run();
    }

    @Test
    void givenAllTrue_whenExcludesFalse_thenTrue() {
        newTestCase()
                .subject(new boolean[]{true, true})
                .expression(value -> value.excludes(false))
                .result(true)
                .name("excludes[false]")
                .expected("excludes[false]")
                .actual("excludes[false]")
                .run();
    }

    @Test
    void givenDesiredAtIndex_whenHasAtIndex_thenTrue() {
        newTestCase()
                .subject(new boolean[]{true, false})
                .expression(value -> value.hasAtIndex(false, 1))
                .result(true)
                .name("hasAtIndex[desired=false, index=1]")
                .expected("false")
                .actual("false")
                .run();
    }

    @Test
    void givenDifferentInstanceWithSameElements_whenIsEqualTo_thenTrue() {
        newTestCase()
                .subject(new boolean[]{true, false})
                .expression(value -> value.isEqualTo(new boolean[]{true, false}))
                .result(true)
                .name("isEqualTo[[true, false]]")
                .expected("[true, false]")
                .actual("[true, false]")
                .run();
    }
}
