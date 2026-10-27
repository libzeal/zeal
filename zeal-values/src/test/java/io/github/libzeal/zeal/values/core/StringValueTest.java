package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import io.github.libzeal.zeal.values.sdk.StandardRationales;
import org.junit.jupiter.api.Test;


class StringValueTest extends BaseValueTest<String, StringValue> {

    @Override
    protected StringValue expression(String value) {
        return new StringValue(value);
    }

    @Override
    protected String nonNullValueA() {
        return "foo";
    }

    @Override
    protected String nonNullValueB() {
        return "bar";
    }

    // ---- isEmpty ----

    @Test
    void givenNonEmpty_whenIsEmpty_thenFalse() {
        newTestCase()
            .subject("a")
            .expression(StringValue::isEmpty)
            .result(false)
            .name("isEmpty")
            .expected("length = 0")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenEmpty_whenIsEmpty_thenTrue() {
        newTestCase()
            .subject("")
            .expression(StringValue::isEmpty)
            .result(true)
            .name("isEmpty")
            .expected("length = 0")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenWhitespace_whenIsEmpty_thenFalse() {
        newTestCase()
            .subject(" ")
            .expression(StringValue::isEmpty)
            .result(false)
            .name("isEmpty")
            .expected("length = 0")
            .actual("length = 1")
            .run();
    }

    // ---- isNotEmpty ----

    @Test
    void givenNonEmpty_whenIsNotEmpty_thenTrue() {
        newTestCase()
            .subject("a")
            .expression(StringValue::isNotEmpty)
            .result(true)
            .name("isNotEmpty")
            .expected("length > 0")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenEmpty_whenIsNotEmpty_thenFalse() {
        newTestCase()
            .subject("")
            .expression(StringValue::isNotEmpty)
            .result(false)
            .name("isNotEmpty")
            .expected("length > 0")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenWhitespace_whenIsNotEmpty_thenTrue() {
        newTestCase()
            .subject(" ")
            .expression(StringValue::isNotEmpty)
            .result(true)
            .name("isNotEmpty")
            .expected("length > 0")
            .actual("length = 1")
            .run();
    }

    // ---- isBlank ----

    @Test
    void givenNonBlank_whenIsBlank_thenFalse() {
        newTestCase()
            .subject("a")
            .expression(StringValue::isBlank)
            .result(false)
            .name("isBlank")
            .expected("true")
            .actual("false")
            .run();
    }

    @Test
    void givenEmpty_whenIsBlank_thenTrue() {
        newTestCase()
            .subject("")
            .expression(StringValue::isBlank)
            .result(true)
            .name("isBlank")
            .expected("true")
            .actual("true")
            .run();
    }

    @Test
    void givenWhitespace_whenIsBlank_thenTrue() {
        newTestCase()
            .subject(" ")
            .expression(StringValue::isBlank)
            .result(true)
            .name("isBlank")
            .expected("true")
            .actual("true")
            .run();
    }

    // ---- isNotBlank ----

    @Test
    void givenNonBlank_whenIsNotBlank_thenTrue() {
        newTestCase()
            .subject("a")
            .expression(StringValue::isNotBlank)
            .result(true)
            .name("isNotBlank")
            .expected("true")
            .actual("true")
            .run();
    }

    @Test
    void givenEmpty_whenIsNotBlank_thenFalse() {
        newTestCase()
            .subject("")
            .expression(StringValue::isNotBlank)
            .result(false)
            .name("isNotBlank")
            .expected("true")
            .actual("false")
            .run();
    }

    @Test
    void givenWhitespace_whenIsNotBlank_thenFalse() {
        newTestCase()
            .subject(" ")
            .expression(StringValue::isNotBlank)
            .result(false)
            .name("isNotBlank")
            .expected("true")
            .actual("false")
            .run();
    }

    // ---- hasLengthOf ----

    @Test
    void givenSameLength_whenHasLengthOf_thenTrue() {
        newTestCase()
            .subject("a")
            .expression(value -> value.hasLengthOf(1))
            .result(true)
            .name("hasLengthOf[1]")
            .expected("length = 1")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenShorter_whenHasLengthOf_thenFalse() {
        newTestCase()
            .subject("")
            .expression(value -> value.hasLengthOf(1))
            .result(false)
            .name("hasLengthOf[1]")
            .expected("length = 1")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenLonger_whenHasLengthOf_thenFalse() {
        newTestCase()
            .subject("aa")
            .expression(value -> value.hasLengthOf(1))
            .result(false)
            .name("hasLengthOf[1]")
            .expected("length = 1")
            .actual("length = 2")
            .run();
    }

    // ---- isLongerThan ----

    @Test
    void givenSameLength_whenIsLongerThan_thenFalse() {
        newTestCase()
            .subject("a")
            .expression(value -> value.isLongerThan(1))
            .result(false)
            .name("isLongerThan[1]")
            .expected("length > 1")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenShorter_whenIsLongerThan_thenFalse() {
        newTestCase()
            .subject("")
            .expression(value -> value.isLongerThan(1))
            .result(false)
            .name("isLongerThan[1]")
            .expected("length > 1")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenLonger_whenIsLongerThan_thenTrue() {
        newTestCase()
            .subject("aa")
            .expression(value -> value.isLongerThan(1))
            .result(true)
            .name("isLongerThan[1]")
            .expected("length > 1")
            .actual("length = 2")
            .run();
    }

    // ---- isLongerThanOrEqualTo ----

    @Test
    void givenSameLength_whenIsLongerThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("a")
            .expression(value -> value.isLongerThanOrEqualTo(1))
            .result(true)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenShorter_whenIsLongerThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject("")
            .expression(value -> value.isLongerThanOrEqualTo(1))
            .result(false)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenLonger_whenIsLongerThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("aa")
            .expression(value -> value.isLongerThanOrEqualTo(1))
            .result(true)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 2")
            .run();
    }

    // ---- isShorterThan ----

    @Test
    void givenSameLength_whenIsShorterThan_thenFalse() {
        newTestCase()
            .subject("a")
            .expression(value -> value.isShorterThan(1))
            .result(false)
            .name("isShorterThan[1]")
            .expected("length < 1")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenShorter_whenIsShorterThan_thenTrue() {
        newTestCase()
            .subject("")
            .expression(value -> value.isShorterThan(1))
            .result(true)
            .name("isShorterThan[1]")
            .expected("length < 1")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenLonger_whenIsShorterThan_thenFalse() {
        newTestCase()
            .subject("aa")
            .expression(value -> value.isShorterThan(1))
            .result(false)
            .name("isShorterThan[1]")
            .expected("length < 1")
            .actual("length = 2")
            .run();
    }

    // ---- isShorterThanOrEqualTo ----

    @Test
    void givenSameLength_whenIsShorterThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("a")
            .expression(value -> value.isShorterThanOrEqualTo(1))
            .result(true)
            .name("isShorterThanOrEqualTo[1]")
            .expected("length <= 1")
            .actual("length = 1")
            .run();
    }

    @Test
    void givenShorter_whenIsShorterThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("")
            .expression(value -> value.isShorterThanOrEqualTo(1))
            .result(true)
            .name("isShorterThanOrEqualTo[1]")
            .expected("length <= 1")
            .actual("length = 0")
            .run();
    }

    @Test
    void givenLonger_whenIsShorterThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject("aa")
            .expression(value -> value.isShorterThanOrEqualTo(1))
            .result(false)
            .name("isShorterThanOrEqualTo[1]")
            .expected("length <= 1")
            .actual("length = 2")
            .run();
    }

    // ---- includes ----

    @Test
    void givenIncludedChar_whenIncludes_thenTrue() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includes('a'))
            .result(true)
            .name("includes[a]")
            .expected("includes[a]")
            .actual("includes[a]")
            .hint(StandardRationales.needleInHaystackHint(0, 'a'))
            .run();
    }

    @Test
    void givenExcludedChar_whenIncludes_thenFalse() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includes('a'))
            .result(false)
            .name("includes[a]")
            .expected("includes[a]")
            .actual("excludes[a]")
            .hint(StandardRationales.needleInHaystackHint(-1, 'a'))
            .run();
    }

    @Test
    void givenIncludedString_whenIncludes_thenTrue() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includes("a"))
            .result(true)
            .name("includes[a]")
            .expected("includes[a]")
            .actual("includes[a]")
            .hint(StandardRationales.needleInHaystackHint(0, "a"))
            .run();
    }

    @Test
    void givenExcludedString_whenIncludes_thenFalse() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includes("a"))
            .result(false)
            .name("includes[a]")
            .expected("includes[a]")
            .actual("excludes[a]")
            .hint(StandardRationales.needleInHaystackHint(-1, "a"))
            .run();
    }

    // ---- excludes ----

    @Test
    void givenIncludedChar_whenExcludes_thenFalse() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.excludes('a'))
            .result(false)
            .name("excludes[a]")
            .expected("excludes[a]")
            .actual("includes[a]")
            .hint(StandardRationales.needleInHaystackHint(0, 'a'))
            .run();
    }

    @Test
    void givenExcludedChar_whenExcludes_thenTrue() {
        newTestCase()
            .subject("b")
            .expression(value -> value.excludes('a'))
            .result(true)
            .name("excludes[a]")
            .expected("excludes[a]")
            .actual("excludes[a]")
            .hint(StandardRationales.needleInHaystackHint(-1, 'a'))
            .run();
    }

    @Test
    void givenIncludedString_whenExcludes_thenFalse() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.excludes("a"))
            .result(false)
            .name("excludes[a]")
            .expected("excludes[a]")
            .actual("includes[a]")
            .hint(StandardRationales.needleInHaystackHint(0, "a"))
            .run();
    }

    @Test
    void givenExcludedString_whenExcludes_thenTrue() {
        newTestCase()
            .subject("b")
            .expression(value -> value.excludes("a"))
            .result(true)
            .name("excludes[a]")
            .expected("excludes[a]")
            .actual("excludes[a]")
            .hint(StandardRationales.needleInHaystackHint(-1, "a"))
            .run();
    }

    // ---- includesExactly ----

    @Test
    void givenOneOccurrence_whenIncludesExactly_thenTrue() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includesExactly('a', 1))
            .result(true)
            .name("includesExactly[desired=a, times=1]")
            .expected("found 'a' 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesExactly_thenFalse() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.includesExactly('a', 1))
            .result(false)
            .name("includesExactly[desired=a, times=1]")
            .expected("found 'a' 1 time")
            .actual("found 'a' 2 times")
            .run();
    }

    @Test
    void givenNoOccurrences_whenIncludesExactly_thenFalse() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includesExactly('a', 1))
            .result(false)
            .name("includesExactly[desired=a, times=1]")
            .expected("found 'a' 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    // ---- includesMoreThan ----

    @Test
    void givenOneOccurrence_whenIncludesMoreThan_thenFalse() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includesMoreThan('a', 1))
            .result(false)
            .name("includesMoreThan[desired=a, times=1]")
            .expected("found 'a' more than 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesMoreThan_thenTrue() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.includesMoreThan('a', 1))
            .result(true)
            .name("includesMoreThan[desired=a, times=1]")
            .expected("found 'a' more than 1 time")
            .actual("found 'a' 2 times")
            .run();
    }

    @Test
    void givenNoOccurrences_whenIncludesMoreThan_thenFalse() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includesMoreThan('a', 1))
            .result(false)
            .name("includesMoreThan[desired=a, times=1]")
            .expected("found 'a' more than 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    // ---- includesMoreThanOrEqualTo ----

    @Test
    void givenOneOccurrence_whenIncludesMoreThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includesMoreThanOrEqualTo('a', 1))
            .result(true)
            .name("includesMoreThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' more than or equal to 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesMoreThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.includesMoreThanOrEqualTo('a', 1))
            .result(true)
            .name("includesMoreThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' more than or equal to 1 time")
            .actual("found 'a' 2 times")
            .run();
    }

    @Test
    void givenNoOccurrences_whenIncludesMoreThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includesMoreThanOrEqualTo('a', 1))
            .result(false)
            .name("includesMoreThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' more than or equal to 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    // ---- includesLessThan ----

    @Test
    void givenOneOccurrence_whenIncludesLessThan_thenFalse() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includesLessThan('a', 1))
            .result(false)
            .name("includesLessThan[desired=a, times=1]")
            .expected("found 'a' less than 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesLessThan_thenFalse() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.includesLessThan('a', 1))
            .result(false)
            .name("includesLessThan[desired=a, times=1]")
            .expected("found 'a' less than 1 time")
            .actual("found 'a' 2 times")
            .run();
    }

    @Test
    void givenNoOccurrences_whenIncludesLessThan_thenTrue() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includesLessThan('a', 1))
            .result(true)
            .name("includesLessThan[desired=a, times=1]")
            .expected("found 'a' less than 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    // ---- includesLessThanOrEqualTo ----

    @Test
    void givenOneOccurrence_whenIncludesLessThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("ab")
            .expression(value -> value.includesLessThanOrEqualTo('a', 1))
            .result(true)
            .name("includesLessThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' less than or equal to 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesLessThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.includesLessThanOrEqualTo('a', 1))
            .result(false)
            .name("includesLessThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' less than or equal to 1 time")
            .actual("found 'a' 2 times")
            .run();
    }

    @Test
    void givenNoOccurrences_whenIncludesLessThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject("b")
            .expression(value -> value.includesLessThanOrEqualTo('a', 1))
            .result(true)
            .name("includesLessThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' less than or equal to 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    // ---- startsWith ----

    @Test
    void givenDifferentPrefix_whenStartsWith_thenFalse() {
        newTestCase()
            .subject("bbaa")
            .expression(value -> value.startsWith("aa"))
            .result(false)
            .name("startsWith[aa]")
            .expected("aa")
            .actual("bb")
            .run();
    }

    @Test
    void givenMatchingPrefix_whenStartsWith_thenTrue() {
        newTestCase()
            .subject("aabb")
            .expression(value -> value.startsWith("aa"))
            .result(true)
            .name("startsWith[aa]")
            .expected("aa")
            .actual("aa")
            .run();
    }

    // ---- doesNotStartWith ----

    @Test
    void givenDifferentPrefix_whenDoesNotStartWith_thenTrue() {
        newTestCase()
            .subject("bbaa")
            .expression(value -> value.doesNotStartWith("aa"))
            .result(true)
            .name("doesNotStartWith[aa]")
            .expected("not[aa]")
            .actual("bb")
            .run();
    }

    @Test
    void givenMatchingPrefix_whenDoesNotStartWith_thenFalse() {
        newTestCase()
            .subject("aabb")
            .expression(value -> value.doesNotStartWith("aa"))
            .result(false)
            .name("doesNotStartWith[aa]")
            .expected("not[aa]")
            .actual("aa")
            .run();
    }

    // ---- endsWith ----

    @Test
    void givenMatchingSuffix_whenEndsWith_thenTrue() {
        newTestCase()
            .subject("bbaa")
            .expression(value -> value.endsWith("aa"))
            .result(true)
            .name("endsWith[aa]")
            .expected("aa")
            .actual("aa")
            .run();
    }

    @Test
    void givenDifferentSuffix_whenEndsWith_thenFalse() {
        newTestCase()
            .subject("aabb")
            .expression(value -> value.endsWith("aa"))
            .result(false)
            .name("endsWith[aa]")
            .expected("aa")
            .actual("bb")
            .run();
    }

    // ---- doesNotEndWith ----

    @Test
    void givenMatchingSuffix_whenDoesNotEndWith_thenFalse() {
        newTestCase()
            .subject("bbaa")
            .expression(value -> value.doesNotEndWith("aa"))
            .result(false)
            .name("doesNotEndWith[aa]")
            .expected("not[aa]")
            .actual("aa")
            .run();
    }

    @Test
    void givenDifferentSuffix_whenDoesNotEndWith_thenTrue() {
        newTestCase()
            .subject("aabb")
            .expression(value -> value.doesNotEndWith("aa"))
            .result(true)
            .name("doesNotEndWith[aa]")
            .expected("not[aa]")
            .actual("bb")
            .run();
    }

    // ---- matches ----

    @Test
    void givenMatchingString_whenMatches_thenTrue() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.matches("a*b"))
            .result(true)
            .name("matches[a*b]")
            .expected("matches[a*b]")
            .actual("aab")
            .run();
    }

    @Test
    void givenNonMatchingString_whenMatches_thenFalse() {
        newTestCase()
            .subject("ccc")
            .expression(value -> value.matches("a*b"))
            .result(false)
            .name("matches[a*b]")
            .expected("matches[a*b]")
            .actual("ccc")
            .run();
    }

    // ---- doesNotMatch ----

    @Test
    void givenMatchingString_whenDoesNotMatch_thenFalse() {
        newTestCase()
            .subject("aab")
            .expression(value -> value.doesNotMatch("a*b"))
            .result(false)
            .name("doesNotMatch[a*b]")
            .expected("not[matches[a*b]]")
            .actual("aab")
            .run();
    }

    @Test
    void givenNonMatchingString_whenDoesNotMatch_thenTrue() {
        newTestCase()
            .subject("ccc")
            .expression(value -> value.doesNotMatch("a*b"))
            .result(true)
            .name("doesNotMatch[a*b]")
            .expected("not[matches[a*b]]")
            .actual("ccc")
            .run();
    }

    // ---- isCaseInsensitiveEqualTo ----

    @Test
    void givenSameCase_whenIsCaseInsensitiveEqualTo_thenTrue() {
        newTestCase()
            .subject("foo")
            .expression(value -> value.isCaseInsensitiveEqualTo("foo"))
            .result(true)
            .name("caseInsensitiveEqualTo[foo]")
            .expected("foo")
            .actual("foo")
            .run();
    }

    @Test
    void givenDifferentCase_whenIsCaseInsensitiveEqualTo_thenTrue() {
        newTestCase()
            .subject("foo")
            .expression(value -> value.isCaseInsensitiveEqualTo("Foo"))
            .result(true)
            .name("caseInsensitiveEqualTo[Foo]")
            .expected("Foo")
            .actual("foo")
            .run();
    }

    @Test
    void givenDifferentValue_whenIsCaseInsensitiveEqualTo_thenFalse() {
        newTestCase()
            .subject("foo")
            .expression(value -> value.isCaseInsensitiveEqualTo("bar"))
            .result(false)
            .name("caseInsensitiveEqualTo[bar]")
            .expected("bar")
            .actual("foo")
            .run();
    }

    // ---- hasAtIndex ----

    @Test
    void givenLetterNotAtIndex_whenHasAtIndexChar_thenFalse() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.hasAtIndex('c', 0))
            .result(false)
            .name("hasAtIndex[desired=c, index=0]")
            .expected("c")
            .actual("a")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenHasAtIndexChar_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex('c', 0))
            .result(true)
            .name("hasAtIndex[desired=c, index=0]")
            .expected("c")
            .actual("c")
            .run();
    }

    @Test
    void givenMissingLetter_whenHasAtIndexChar_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex('z', 0))
            .result(false)
            .name("hasAtIndex[desired=z, index=0]")
            .expected("z")
            .actual("c")
            .run();
    }

    @Test
    void givenLetterAtIndexAndLater_whenHasAtIndexChar_thenTrue() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.hasAtIndex('c', 0))
            .result(true)
            .name("hasAtIndex[desired=c, index=0]")
            .expected("c")
            .actual("c")
            .run();
    }

    @Test
    void givenIndexOutOfBounds_whenHasAtIndexChar_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex('c', 3))
            .result(false)
            .name("hasAtIndex[desired=c, index=3]")
            .expected("c")
            .actual("Index 3 is out of bounds")
            .run();
    }

    @Test
    void givenLetterNotAtIndex_whenHasAtIndexString_thenFalse() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.hasAtIndex("c", 0))
            .result(false)
            .name("hasAtIndex[desired=c, index=0]")
            .expected("c")
            .actual("a")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenHasAtIndexString_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex("c", 0))
            .result(true)
            .name("hasAtIndex[desired=c, index=0]")
            .expected("c")
            .actual("c")
            .run();
    }

    @Test
    void givenMissingLetter_whenHasAtIndexString_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex("z", 0))
            .result(false)
            .name("hasAtIndex[desired=z, index=0]")
            .expected("z")
            .actual("c")
            .run();
    }

    @Test
    void givenLetterAtIndexAndLater_whenHasAtIndexString_thenTrue() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.hasAtIndex("c", 0))
            .result(true)
            .name("hasAtIndex[desired=c, index=0]")
            .expected("c")
            .actual("c")
            .run();
    }

    @Test
    void givenIndexOutOfBounds_whenHasAtIndexString_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex("c", 3))
            .result(false)
            .name("hasAtIndex[desired=c, index=3]")
            .expected("c")
            .actual("Index 3 is out of bounds")
            .run();
    }

    @Test
    void givenMultiCharacterNeedleAfterFirstIndex_whenHasAtIndexString_thenTrue() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.hasAtIndex("bc", 1))
            .result(true)
            .name("hasAtIndex[desired=bc, index=1]")
            .expected("bc")
            .actual("bc")
            .run();
    }

    @Test
    void givenMultiCharacterNeedleAtIndexPastNeedleLength_whenHasAtIndexString_thenTrue() {
        newTestCase()
            .subject("abcabc")
            .expression(value -> value.hasAtIndex("bc", 4))
            .result(true)
            .name("hasAtIndex[desired=bc, index=4]")
            .expected("bc")
            .actual("bc")
            .run();
    }

    @Test
    void givenNegativeIndex_whenHasAtIndexChar_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex('c', -1))
            .result(false)
            .name("hasAtIndex[desired=c, index=-1]")
            .expected("c")
            .actual("Index -1 is out of bounds")
            .run();
    }

    @Test
    void givenNegativeIndex_whenHasAtIndexString_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtIndex("c", -1))
            .result(false)
            .name("hasAtIndex[desired=c, index=-1]")
            .expected("c")
            .actual("Index -1 is out of bounds")
            .run();
    }

    // ---- doesNotHaveAtIndex ----

    @Test
    void givenLetterNotAtIndex_whenDoesNotHaveAtIndexChar_thenTrue() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.doesNotHaveAtIndex('c', 0))
            .result(true)
            .name("doesNotHaveAtIndex[desired=c, index=0]")
            .expected("not[c]")
            .actual("a")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenDoesNotHaveAtIndexChar_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtIndex('c', 0))
            .result(false)
            .name("doesNotHaveAtIndex[desired=c, index=0]")
            .expected("not[c]")
            .actual("c")
            .run();
    }

    @Test
    void givenMissingLetter_whenDoesNotHaveAtIndexChar_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtIndex('z', 0))
            .result(true)
            .name("doesNotHaveAtIndex[desired=z, index=0]")
            .expected("not[z]")
            .actual("c")
            .run();
    }

    @Test
    void givenLetterAtIndexAndEarlier_whenDoesNotHaveAtIndexChar_thenFalse() {
        newTestCase()
            .subject("cbc")
            .expression(value -> value.doesNotHaveAtIndex('c', 2))
            .result(false)
            .name("doesNotHaveAtIndex[desired=c, index=2]")
            .expected("not[c]")
            .actual("c")
            .run();
    }

    @Test
    void givenIndexOutOfBounds_whenDoesNotHaveAtIndexChar_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtIndex('c', 3))
            .result(true)
            .name("doesNotHaveAtIndex[desired=c, index=3]")
            .expected("not[c]")
            .actual("Index 3 is out of bounds")
            .run();
    }

    @Test
    void givenLetterNotAtIndex_whenDoesNotHaveAtIndexString_thenTrue() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.doesNotHaveAtIndex("c", 0))
            .result(true)
            .name("doesNotHaveAtIndex[desired=c, index=0]")
            .expected("not[c]")
            .actual("a")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenDoesNotHaveAtIndexString_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtIndex("c", 0))
            .result(false)
            .name("doesNotHaveAtIndex[desired=c, index=0]")
            .expected("not[c]")
            .actual("c")
            .run();
    }

    @Test
    void givenMissingLetter_whenDoesNotHaveAtIndexString_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtIndex("z", 0))
            .result(true)
            .name("doesNotHaveAtIndex[desired=z, index=0]")
            .expected("not[z]")
            .actual("c")
            .run();
    }

    @Test
    void givenLetterAtIndexAndEarlier_whenDoesNotHaveAtIndexString_thenFalse() {
        newTestCase()
            .subject("cbc")
            .expression(value -> value.doesNotHaveAtIndex("c", 2))
            .result(false)
            .name("doesNotHaveAtIndex[desired=c, index=2]")
            .expected("not[c]")
            .actual("c")
            .run();
    }

    @Test
    void givenMultiCharacterNeedleAfterFirstIndex_whenDoesNotHaveAtIndexString_thenFalse() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.doesNotHaveAtIndex("bc", 1))
            .result(false)
            .name("doesNotHaveAtIndex[desired=bc, index=1]")
            .expected("not[bc]")
            .actual("bc")
            .run();
    }

    // ---- hasAtLastIndex ----

    @Test
    void givenLetterNotAtIndex_whenHasAtLastIndexChar_thenFalse() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.hasAtLastIndex('c', 0))
            .result(false)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("2")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenHasAtLastIndexChar_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtLastIndex('c', 0))
            .result(true)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("0")
            .run();
    }

    @Test
    void givenMissingLetter_whenHasAtLastIndexChar_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtLastIndex('z', 0))
            .result(false)
            .name("lastIndexOf[z] = 0")
            .expected("0")
            .actual("-1")
            .run();
    }

    @Test
    void givenLetterAtIndexAndLater_whenHasAtLastIndexChar_thenFalse() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.hasAtLastIndex('c', 0))
            .result(false)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("3")
            .run();
    }

    @Test
    void givenLetterNotAtIndex_whenHasAtLastIndexString_thenFalse() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.hasAtLastIndex("c", 0))
            .result(false)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("2")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenHasAtLastIndexString_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtLastIndex("c", 0))
            .result(true)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("0")
            .run();
    }

    @Test
    void givenMissingLetter_whenHasAtLastIndexString_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.hasAtLastIndex("z", 0))
            .result(false)
            .name("lastIndexOf[z] = 0")
            .expected("0")
            .actual("-1")
            .run();
    }

    @Test
    void givenLetterAtIndexAndLater_whenHasAtLastIndexString_thenFalse() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.hasAtLastIndex("c", 0))
            .result(false)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("3")
            .run();
    }

    // ---- doesNotHaveAtLastIndex ----

    @Test
    void givenLetterNotAtIndex_whenDoesNotHaveAtLastIndexChar_thenTrue() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.doesNotHaveAtLastIndex('c', 0))
            .result(true)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("2")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenDoesNotHaveAtLastIndexChar_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtLastIndex('c', 0))
            .result(false)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("0")
            .run();
    }

    @Test
    void givenMissingLetter_whenDoesNotHaveAtLastIndexChar_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtLastIndex('z', 0))
            .result(true)
            .name("lastIndexOf[z] != 0")
            .expected("not[0]")
            .actual("-1")
            .run();
    }

    @Test
    void givenLetterAtIndexAndLater_whenDoesNotHaveAtLastIndexChar_thenTrue() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.doesNotHaveAtLastIndex('c', 0))
            .result(true)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("3")
            .run();
    }

    @Test
    void givenLetterNotAtIndex_whenDoesNotHaveAtLastIndexString_thenTrue() {
        newTestCase()
            .subject("abc")
            .expression(value -> value.doesNotHaveAtLastIndex("c", 0))
            .result(true)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("2")
            .run();
    }

    @Test
    void givenLetterAtIndex_whenDoesNotHaveAtLastIndexString_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtLastIndex("c", 0))
            .result(false)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("0")
            .run();
    }

    @Test
    void givenMissingLetter_whenDoesNotHaveAtLastIndexString_thenTrue() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.doesNotHaveAtLastIndex("z", 0))
            .result(true)
            .name("lastIndexOf[z] != 0")
            .expected("not[0]")
            .actual("-1")
            .run();
    }

    @Test
    void givenLetterAtIndexAndLater_whenDoesNotHaveAtLastIndexString_thenTrue() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.doesNotHaveAtLastIndex("c", 0))
            .result(true)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("3")
            .run();
    }

    // ---- lastIndexOfIs ----

    @Test
    void givenLastOccurrenceAtIndex_whenLastIndexOfIsCharacter_thenTrue() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.lastIndexOfIs((Character) 'c', 3))
            .result(true)
            .name("lastIndexOf[c] = 3")
            .expected("3")
            .actual("3")
            .run();
    }

    @Test
    void givenMissingLetter_whenLastIndexOfIsCharacter_thenFalse() {
        newTestCase()
            .subject("cba")
            .expression(value -> value.lastIndexOfIs((Character) 'z', 0))
            .result(false)
            .name("lastIndexOf[z] = 0")
            .expected("0")
            .actual("-1")
            .run();
    }

    @Test
    void givenLastOccurrenceAtIndex_whenLastIndexOfIsCharSequence_thenTrue() {
        newTestCase()
            .subject("abcabc")
            .expression(value -> value.lastIndexOfIs((CharSequence) "bc", 4))
            .result(true)
            .name("lastIndexOf[bc] = 4")
            .expected("4")
            .actual("4")
            .run();
    }

    @Test
    void givenOnlyEarlierOccurrenceAtIndex_whenLastIndexOfIsCharSequence_thenFalse() {
        newTestCase()
            .subject("abcabc")
            .expression(value -> value.lastIndexOfIs((CharSequence) "bc", 1))
            .result(false)
            .name("lastIndexOf[bc] = 1")
            .expected("1")
            .actual("4")
            .run();
    }

    // ---- lastIndexOfIsNot ----

    @Test
    void givenLastOccurrenceElsewhere_whenLastIndexOfIsNotCharacter_thenTrue() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.lastIndexOfIsNot((Character) 'c', 0))
            .result(true)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("3")
            .run();
    }

    @Test
    void givenLastOccurrenceAtIndex_whenLastIndexOfIsNotCharacter_thenFalse() {
        newTestCase()
            .subject("cabc")
            .expression(value -> value.lastIndexOfIsNot((Character) 'c', 3))
            .result(false)
            .name("lastIndexOf[c] != 3")
            .expected("not[3]")
            .actual("3")
            .run();
    }

    @Test
    void givenLastOccurrenceElsewhere_whenLastIndexOfIsNotCharSequence_thenTrue() {
        newTestCase()
            .subject("abcabc")
            .expression(value -> value.lastIndexOfIsNot((CharSequence) "bc", 1))
            .result(true)
            .name("lastIndexOf[bc] != 1")
            .expected("not[1]")
            .actual("4")
            .run();
    }

    @Test
    void givenLastOccurrenceAtIndex_whenLastIndexOfIsNotCharSequence_thenFalse() {
        newTestCase()
            .subject("abcabc")
            .expression(value -> value.lastIndexOfIsNot((CharSequence) "bc", 4))
            .result(false)
            .name("lastIndexOf[bc] != 4")
            .expected("not[4]")
            .actual("4")
            .run();
    }
}
