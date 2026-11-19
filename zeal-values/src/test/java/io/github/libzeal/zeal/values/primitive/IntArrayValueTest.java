package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

// Covers the sequence checks inherited by every primitive array (through the shared operations) and the checks shared
// by the whole-number arrays. The other whole-number array tests only cover what differs for their element type.
class IntArrayValueTest extends BaseValueTest<int[], IntArrayValue> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final int[] VALUE_A = {1};
    private static final int[] VALUE_B = {2};

    @Override
    protected IntArrayValue expression(final int[] value) {
        return new IntArrayValue(value);
    }

    @Override
    protected int[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected int[] nonNullValueB() {
        return VALUE_B;
    }

    // ---- inherited sequence checks ----

    @Test
    void givenNonEmpty_whenHasSizeOf_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 2})
                .expression(value -> value.hasSizeOf(2))
                .result(true)
                .name("hasSizeOf[2]")
                .expected("size = 2")
                .actual("size = 2")
                .run();
    }

    @Test
    void givenIncluded_whenIncludes_thenHintHasIndex() {
        newTestCase()
                .subject(new int[]{1, 2})
                .expression(value -> value.includes(2))
                .result(true)
                .name("includes[2]")
                .expected("includes[2]")
                .actual("includes[2]")
                .hint("'2' found at index 1")
                .run();
    }

    @Test
    void givenExcluded_whenIncludes_thenFalse() {
        newTestCase()
                .subject(new int[]{1, 2})
                .expression(value -> value.includes(3))
                .result(false)
                .name("includes[3]")
                .expected("includes[3]")
                .actual("excludes[3]")
                .run();
    }

    @Test
    void givenDesiredAtIndex_whenHasAtIndex_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 2})
                .expression(value -> value.hasAtIndex(2, 1))
                .result(true)
                .name("hasAtIndex[desired=2, index=1]")
                .expected("2")
                .actual("2")
                .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesExactlyTwice_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 1, 2})
                .expression(value -> value.includesExactly(1, 2))
                .result(true)
                .name("includesExactly[desired=1, times=2]")
                .expected("found '1' 2 times")
                .actual("found '1' 2 times")
                .run();
    }

    @Test
    void givenSomeElementsFail_whenAllSatisfy_thenFailingElementsInHint() {
        newTestCase()
                .subject(new int[]{1, 2, 3})
                .expression(value -> value.allSatisfy(i -> i > 1))
                .result(false)
                .name("allSatisfy")
                .expected("All elements satisfy condition")
                .actual("At least one element does not satisfy condition")
                .hint("The following elements do not satisfy condition: [1]")
                .run();
    }

    @Test
    void givenDifferentInstanceWithSameElements_whenIsEqualTo_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 2})
                .expression(value -> value.isEqualTo(new int[]{1, 2}))
                .result(true)
                .name("isEqualTo[[1, 2]]")
                .expected("[1, 2]")
                .actual("[1, 2]")
                .run();
    }

    // ---- sumIs ----

    @Test
    void givenMatchingSum_whenSumIs_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 2, 3})
                .expression(value -> value.sumIs(6))
                .result(true)
                .name("sumIs[6]")
                .expected("sum = 6")
                .actual("sum = 6")
                .hint("The sum of the elements is 6")
                .run();
    }

    @Test
    void givenDifferentSum_whenSumIs_thenFalse() {
        newTestCase()
                .subject(new int[]{1, 2, 3})
                .expression(value -> value.sumIs(7))
                .result(false)
                .name("sumIs[7]")
                .expected("sum = 7")
                .actual("sum = 6")
                .hint("The sum of the elements should be 7")
                .run();
    }

    @Test
    void givenNegativeElements_whenSumIs_thenTrue() {
        newTestCase()
                .subject(new int[]{-5, 2})
                .expression(value -> value.sumIs(-3))
                .result(true)
                .name("sumIs[-3]")
                .expected("sum = -3")
                .actual("sum = -3")
                .run();
    }

    @Test
    void givenSumBeyondIntRange_whenSumIs_thenSumComputedAsLong() {
        newTestCase()
                .subject(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE})
                .expression(value -> value.sumIs(4294967294L))
                .result(true)
                .name("sumIs[4294967294]")
                .expected("sum = 4294967294")
                .actual("sum = 4294967294")
                .run();
    }

    @Test
    void givenEmpty_whenSumIsZero_thenTrue() {
        newTestCase()
                .subject(new int[]{})
                .expression(value -> value.sumIs(0))
                .result(true)
                .name("sumIs[0]")
                .expected("sum = 0")
                .actual("sum = 0")
                .run();
    }

    @Test
    void givenNullSubject_whenSumIs_thenFalse() {
        newTestCase()
                .subject(null)
                .expression(value -> value.sumIs(0))
                .result(false)
                .name("sumIs[0]")
                .expected("sum = 0")
                .actual("(null)")
                .run();
    }

    // ---- minIs ----

    @Test
    void givenMatchingMin_whenMinIs_thenTrue() {
        newTestCase()
                .subject(new int[]{3, 1, 2})
                .expression(value -> value.minimumIs(1))
                .result(true)
                .name("minIs[1]")
                .expected("min = 1")
                .actual("min = 1")
                .hint("The minimum element is 1")
                .run();
    }

    @Test
    void givenDifferentMin_whenMinIs_thenFalse() {
        newTestCase()
                .subject(new int[]{3, 1, 2})
                .expression(value -> value.minimumIs(2))
                .result(false)
                .name("minIs[2]")
                .expected("min = 2")
                .actual("min = 1")
                .hint("The minimum element should be 2")
                .run();
    }

    @Test
    void givenEmpty_whenMinIs_thenFalse() {
        newTestCase()
                .subject(new int[]{})
                .expression(value -> value.minimumIs(0))
                .result(false)
                .name("minIs[0]")
                .expected("min = 0")
                .actual("empty")
                .hint("An empty array has no minimum element")
                .run();
    }

    @Test
    void givenNullSubject_whenMinIs_thenFalse() {
        newTestCase()
                .subject(null)
                .expression(value -> value.minimumIs(0))
                .result(false)
                .name("minIs[0]")
                .expected("min = 0")
                .actual("(null)")
                .run();
    }

    // ---- maxIs ----

    @Test
    void givenMatchingMax_whenMaxIs_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 3, 2})
                .expression(value -> value.maximumIs(3))
                .result(true)
                .name("maxIs[3]")
                .expected("max = 3")
                .actual("max = 3")
                .hint("The maximum element is 3")
                .run();
    }

    @Test
    void givenDifferentMax_whenMaxIs_thenFalse() {
        newTestCase()
                .subject(new int[]{1, 3, 2})
                .expression(value -> value.maximumIs(2))
                .result(false)
                .name("maxIs[2]")
                .expected("max = 2")
                .actual("max = 3")
                .hint("The maximum element should be 2")
                .run();
    }

    @Test
    void givenEmpty_whenMaxIs_thenFalse() {
        newTestCase()
                .subject(new int[]{})
                .expression(value -> value.maximumIs(0))
                .result(false)
                .name("maxIs[0]")
                .expected("max = 0")
                .actual("empty")
                .hint("An empty array has no maximum element")
                .run();
    }

    // ---- isAscending ----

    @Test
    void givenAscendingWithDuplicates_whenIsAscending_thenTrue() {
        newTestCase()
                .subject(new int[]{1, 2, 2, 3})
                .expression(BaseNumericArrayValue::isAscending)
                .result(true)
                .name("isAscending")
                .expected("ascending")
                .actual("ascending")
                .hint("All elements are in ascending order")
                .run();
    }

    @Test
    void givenOutOfOrder_whenIsAscending_thenFalse() {
        newTestCase()
                .subject(new int[]{1, 3, 2, 0})
                .expression(BaseNumericArrayValue::isAscending)
                .result(false)
                .name("isAscending")
                .expected("ascending")
                .actual("not ascending")
                .hint("Element 3 at index 1 is greater than element 2 at index 2")
                .run();
    }

    @Test
    void givenEmpty_whenIsAscending_thenTrue() {
        newTestCase()
                .subject(new int[]{})
                .expression(BaseNumericArrayValue::isAscending)
                .result(true)
                .name("isAscending")
                .expected("ascending")
                .actual("ascending")
                .run();
    }

    @Test
    void givenSingleElement_whenIsAscending_thenTrue() {
        newTestCase()
                .subject(new int[]{5})
                .expression(BaseNumericArrayValue::isAscending)
                .result(true)
                .name("isAscending")
                .expected("ascending")
                .actual("ascending")
                .run();
    }

    @Test
    void givenNullSubject_whenIsAscending_thenFalse() {
        newTestCase()
                .subject(null)
                .expression(BaseNumericArrayValue::isAscending)
                .result(false)
                .name("isAscending")
                .expected("ascending")
                .actual("(null)")
                .run();
    }

    // ---- isDescending ----

    @Test
    void givenDescendingWithDuplicates_whenIsDescending_thenTrue() {
        newTestCase()
                .subject(new int[]{3, 2, 2, 1})
                .expression(BaseNumericArrayValue::isDescending)
                .result(true)
                .name("isDescending")
                .expected("descending")
                .actual("descending")
                .hint("All elements are in descending order")
                .run();
    }

    @Test
    void givenOutOfOrder_whenIsDescending_thenFalse() {
        newTestCase()
                .subject(new int[]{3, 1, 2})
                .expression(BaseNumericArrayValue::isDescending)
                .result(false)
                .name("isDescending")
                .expected("descending")
                .actual("not descending")
                .hint("Element 1 at index 1 is less than element 2 at index 2")
                .run();
    }
}
