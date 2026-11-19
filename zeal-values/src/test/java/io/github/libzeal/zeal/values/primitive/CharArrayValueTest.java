package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

class CharArrayValueTest extends BaseValueTest<char[], CharArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final char[] VALUE_A = {'a'};
    private static final char[] VALUE_B = {'b'};

    @Override
    protected CharArrayValue expression(final char[] value) {
        return new CharArrayValue(value);
    }

    @Override
    protected char[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected char[] nonNullValueB() {
        return VALUE_B;
    }

    @Test
    void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
                .subject(new char[]{'a', 'b'})
                .expression(value -> value.includes('b'))
                .result(true)
                .name("includes[b]")
                .expected("includes[b]")
                .actual("includes[b]")
                .hint("'b' found at index 1")
                .run();
    }

    @Test
    void givenAllDigits_whenAllSatisfy_thenTrue() {
        newTestCase()
                .subject(new char[]{'1', '2'})
                .expression(value -> value.allSatisfy(Character::isDigit))
                .result(true)
                .name("allSatisfy")
                .expected("All elements satisfy condition")
                .actual("All elements satisfy condition")
                .run();
    }

    @Test
    void givenSomeLetters_whenNoneSatisfies_thenMatchingElementsInHint() {
        newTestCase()
                .subject(new char[]{'1', 'a', 'b'})
                .expression(value -> value.noneSatisfies(Character::isLetter))
                .result(false)
                .name("noneSatisfies")
                .expected("No elements satisfy condition")
                .actual("At least one element satisfies condition")
                .hint("The following elements satisfy condition: [a, b]")
                .run();
    }

    @Test
    void givenDifferentInstanceWithSameElements_whenIsEqualTo_thenTrue() {
        newTestCase()
                .subject(new char[]{'a', 'b'})
                .expression(value -> value.isEqualTo(new char[]{'a', 'b'}))
                .result(true)
                .name("isEqualTo[[a, b]]")
                .expected("[a, b]")
                .actual("[a, b]")
                .run();
    }
}
