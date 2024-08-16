package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.TRUE;

class StringUnaryExpressionTest extends ObjectUnaryExpressionTest<String, StringUnaryExpression> {

    @Override
    protected StringUnaryExpression expression(String value) {
        return new StringUnaryExpression(value);
    }

    @Override
    protected String exampleValue1() {
        return "foo";
    }

    @Override
    protected String exampleValue2() {
        return "bar";
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        isEmptyTestCases(builder);
        isNotEmptyTestCases(builder);
        isBlankTestCases(builder);
        isNotBlankTestCases(builder);
        hasLengthOfTestCases(builder);
        isLongerThanTestCases(builder);
        isLongerThanOrEqualToTestCases(builder);
        isShorterThanTestCases(builder);
        isShorterThanOrEqualToTestCases(builder);
        includesTestCases(builder);
        excludesTestCases(builder);
        occursTestCases(builder);
        occursMoreThanTestCases(builder);
        occursMoreThanOrEqualToTestCases(builder);
        occursLessThanTestCases(builder);
        occursLessThanOrEqualToTestCases(builder);
        startsWithTestCases(builder);
        doesNotStartWithTestCases(builder);
        endsWithTestCases(builder);
        doesNotEndWithTestCases(builder);
        matchesTestCases(builder);
        doesNotMatchTestCases(builder);
        isCaseInsensitiveEqualToTestCases(builder);
        hasAtIndexTestCases(builder);
        doesNotHaveAtIndexTestCase(builder);
        hasAtLastIndexTestCases(builder);
        doesNotHaveAtLastIndexTestCases(builder);
    }

    private void isEmptyTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isEmpty())
                .subject("a")
                .expectedState(FALSE)
                .expectedName("isEmpty")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest()
            .newTest((expression, value) -> expression.isEmpty())
                .subject("")
                .expectedState(TRUE)
                .expectedName("isEmpty")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isEmpty())
                .subject(" ")
                .expectedState(FALSE)
                .expectedName("isEmpty")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest();
    }

    private void isNotEmptyTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotEmpty())
                .subject("a")
                .expectedState(TRUE)
                .expectedName("isNotEmpty")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isNotEmpty())
                .subject("")
                .expectedState(FALSE)
                .expectedName("isNotEmpty")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest()
            .newTest((expression, value) -> expression.isNotEmpty())
                .subject(" ")
                .expectedState(TRUE)
                .expectedName("isNotEmpty")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest();
    }

    private void isBlankTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isBlank())
                .subject("a")
                .expectedState(FALSE)
                .expectedName("isBlank")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest()
            .newTest((expression, value) -> expression.isBlank())
                .subject("")
                .expectedState(TRUE)
                .expectedName("isBlank")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isBlank())
                .subject(" ")
                .expectedState(TRUE)
                .expectedName("isBlank")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest();
    }

    private void isNotBlankTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotBlank())
                .subject("a")
                .expectedState(TRUE)
                .expectedName("isNotBlank")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isNotBlank())
                .subject("")
                .expectedState(FALSE)
                .expectedName("isNotBlank")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest()
            .newTest((expression, value) -> expression.isNotBlank())
                .subject(" ")
                .expectedState(FALSE)
                .expectedName("isNotBlank")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest();
    }

    private void hasLengthOfTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.hasLengthOf(1))
                .subject("a")
                .expectedState(TRUE)
                .expectedName("hasLengthOf[1]")
                .expectedExpected("length := 1")
                .expectedActual("length := 1")
                .addTest()
            .newTest((expression, value) -> expression.hasLengthOf(1))
                .subject("")
                .expectedState(FALSE)
                .expectedName("hasLengthOf[1]")
                .expectedExpected("length := 1")
                .expectedActual("length := 0")
                .addTest()
            .newTest((expression, value) -> expression.hasLengthOf(1))
                .subject("aa")
                .expectedState(FALSE)
                .expectedName("hasLengthOf[1]")
                .expectedExpected("length := 1")
                .expectedActual("length := 2")
                .addTest();
    }

    private void isLongerThanTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isLongerThan(1))
                .subject("a")
                .expectedState(FALSE)
                .expectedName("isLongerThan[1]")
                .expectedExpected("length > 1")
                .expectedActual("length := 1")
                .addTest()
            .newTest((expression, value) -> expression.isLongerThan(1))
                .subject("")
                .expectedState(FALSE)
                .expectedName("isLongerThan[1]")
                .expectedExpected("length > 1")
                .expectedActual("length := 0")
                .addTest()
            .newTest((expression, value) -> expression.isLongerThan(1))
                .subject("aa")
                .expectedState(TRUE)
                .expectedName("isLongerThan[1]")
                .expectedExpected("length > 1")
                .expectedActual("length := 2")
                .addTest();
    }

    private void isLongerThanOrEqualToTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isLongerThanOrEqualTo(1))
                .subject("a")
                .expectedState(TRUE)
                .expectedName("isLongerThanOrEqualTo[1]")
                .expectedExpected("length >= 1")
                .expectedActual("length := 1")
                .addTest()
            .newTest((expression, value) -> expression.isLongerThanOrEqualTo(1))
                .subject("")
                .expectedState(FALSE)
                .expectedName("isLongerThanOrEqualTo[1]")
                .expectedExpected("length >= 1")
                .expectedActual("length := 0")
                .addTest()
            .newTest((expression, value) -> expression.isLongerThanOrEqualTo(1))
                .subject("aa")
                .expectedState(TRUE)
                .expectedName("isLongerThanOrEqualTo[1]")
                .expectedExpected("length >= 1")
                .expectedActual("length := 2")
                .addTest();
    }

    private void isShorterThanTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isShorterThan(1))
                .subject("a")
                .expectedState(FALSE)
                .expectedName("isShorterThan[1]")
                .expectedExpected("length < 1")
                .expectedActual("length := 1")
                .addTest()
            .newTest((expression, value) -> expression.isShorterThan(1))
                .subject("")
                .expectedState(TRUE)
                .expectedName("isShorterThan[1]")
                .expectedExpected("length < 1")
                .expectedActual("length := 0")
                .addTest()
            .newTest((expression, value) -> expression.isShorterThan(1))
                .subject("aa")
                .expectedState(FALSE)
                .expectedName("isShorterThan[1]")
                .expectedExpected("length < 1")
                .expectedActual("length := 2")
                .addTest();
    }

    private void isShorterThanOrEqualToTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isShorterThanOrEqualTo(1))
                .subject("a")
                .expectedState(TRUE)
                .expectedName("isShorterThanOrEqualTo[1]")
                .expectedExpected("length <= 1")
                .expectedActual("length := 1")
                .addTest()
            .newTest((expression, value) -> expression.isShorterThanOrEqualTo(1))
                .subject("")
                .expectedState(TRUE)
                .expectedName("isShorterThanOrEqualTo[1]")
                .expectedExpected("length <= 1")
                .expectedActual("length := 0")
                .addTest()
            .newTest((expression, value) -> expression.isShorterThanOrEqualTo(1))
                .subject("aa")
                .expectedState(FALSE)
                .expectedName("isShorterThanOrEqualTo[1]")
                .expectedExpected("length <= 1")
                .expectedActual("length := 2")
                .addTest();
    }

    private void includesTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.includes('a'))
                .subject("ab")
                .expectedState(TRUE)
                .expectedName("includes[a]")
                .expectedExpected("includes[a]")
                .expectedActual("includes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("ab", 'a'))
                .addTest()
            .newTest((expression, value) -> expression.includes('a'))
                .subject("b")
                .expectedState(FALSE)
                .expectedName("includes[a]")
                .expectedExpected("includes[a]")
                .expectedActual("excludes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("b", 'a'))
                .addTest()
            .newTest((expression, value) -> expression.includes("a"))
                .subject("ab")
                .expectedState(TRUE)
                .expectedName("includes[a]")
                .expectedExpected("includes[a]")
                .expectedActual("includes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("ab", "a"))
                .addTest()
            .newTest((expression, value) -> expression.includes("a"))
                .subject("b")
                .expectedState(FALSE)
                .expectedName("includes[a]")
                .expectedExpected("includes[a]")
                .expectedActual("excludes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("b", "a"))
                .addTest();
    }

    private void excludesTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.excludes('a'))
                .subject("ab")
                .expectedState(FALSE)
                .expectedName("excludes[a]")
                .expectedExpected("excludes[a]")
                .expectedActual("includes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("ab", 'a'))
                .addTest()
            .newTest((expression, value) -> expression.excludes('a'))
                .subject("b")
                .expectedState(TRUE)
                .expectedName("excludes[a]")
                .expectedExpected("excludes[a]")
                .expectedActual("excludes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("b", 'a'))
                .addTest()
            .newTest((expression, value) -> expression.excludes("a"))
                .subject("ab")
                .expectedState(FALSE)
                .expectedName("excludes[a]")
                .expectedExpected("excludes[a]")
                .expectedActual("includes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("ab", "a"))
                .addTest()
            .newTest((expression, value) -> expression.excludes("a"))
                .subject("b")
                .expectedState(TRUE)
                .expectedName("excludes[a]")
                .expectedExpected("excludes[a]")
                .expectedActual("excludes[a]")
                .expectedHint(StringUnaryExpression.needleInHaystackHint("b", "a"))
                .addTest();
    }

    private void occursTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.occursName('a', 1))
                .subject("ab")
                .expectedState(TRUE)
                .expectedName("occurs[a] := 1")
                .expectedExpected("occurrences := 1")
                .expectedActual("occurrences := 1")
                .addTest()
            .newTest((expression, value) -> expression.occursName('a', 1))
                .subject("aab")
                .expectedState(FALSE)
                .expectedName("occurs[a] := 1")
                .expectedExpected("occurrences := 1")
                .expectedActual("occurrences := 2")
                .addTest()
            .newTest((expression, value) -> expression.occursName('a', 1))
                .subject("b")
                .expectedState(FALSE)
                .expectedName("occurs[a] := 1")
                .expectedExpected("occurrences := 1")
                .expectedActual("occurrences := 0")
                .addTest();
    }

    private void occursMoreThanTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.occursMoreThan('a', 1))
                .subject("ab")
                .expectedState(FALSE)
                .expectedName("occurs[a] > 1")
                .expectedExpected("occurrences > 1")
                .expectedActual("occurrences := 1")
                .addTest()
            .newTest((expression, value) -> expression.occursMoreThan('a', 1))
                .subject("aab")
                .expectedState(TRUE)
                .expectedName("occurs[a] > 1")
                .expectedExpected("occurrences > 1")
                .expectedActual("occurrences := 2")
                .addTest()
            .newTest((expression, value) -> expression.occursMoreThan('a', 1))
                .subject("b")
                .expectedState(FALSE)
                .expectedName("occurs[a] > 1")
                .expectedExpected("occurrences > 1")
                .expectedActual("occurrences := 0")
                .addTest();
    }

    private void occursMoreThanOrEqualToTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.occursMoreThanOrEqualTo('a', 1))
                .subject("ab")
                .expectedState(TRUE)
                .expectedName("occurs[a] >= 1")
                .expectedExpected("occurrences >= 1")
                .expectedActual("occurrences := 1")
                .addTest()
            .newTest((expression, value) -> expression.occursMoreThanOrEqualTo('a', 1))
                .subject("aab")
                .expectedState(TRUE)
                .expectedName("occurs[a] >= 1")
                .expectedExpected("occurrences >= 1")
                .expectedActual("occurrences := 2")
                .addTest()
            .newTest((expression, value) -> expression.occursMoreThanOrEqualTo('a', 1))
                .subject("b")
                .expectedState(FALSE)
                .expectedName("occurs[a] >= 1")
                .expectedExpected("occurrences >= 1")
                .expectedActual("occurrences := 0")
                .addTest();
    }

    private void occursLessThanTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.occursLessThan('a', 1))
                .subject("ab")
                .expectedState(FALSE)
                .expectedName("occurs[a] < 1")
                .expectedExpected("occurrences < 1")
                .expectedActual("occurrences := 1")
                .addTest()
            .newTest((expression, value) -> expression.occursLessThan('a', 1))
                .subject("aab")
                .expectedState(FALSE)
                .expectedName("occurs[a] < 1")
                .expectedExpected("occurrences < 1")
                .expectedActual("occurrences := 2")
                .addTest()
            .newTest((expression, value) -> expression.occursLessThan('a', 1))
                .subject("b")
                .expectedState(TRUE)
                .expectedName("occurs[a] < 1")
                .expectedExpected("occurrences < 1")
                .expectedActual("occurrences := 0")
                .addTest();
    }

    private void occursLessThanOrEqualToTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.occursLessThanOrEqualTo('a', 1))
                .subject("ab")
                .expectedState(TRUE)
                .expectedName("occurs[a] <= 1")
                .expectedExpected("occurrences <= 1")
                .expectedActual("occurrences := 1")
                .addTest()
            .newTest((expression, value) -> expression.occursLessThanOrEqualTo('a', 1))
                .subject("aab")
                .expectedState(FALSE)
                .expectedName("occurs[a] <= 1")
                .expectedExpected("occurrences <= 1")
                .expectedActual("occurrences := 2")
                .addTest()
            .newTest((expression, value) -> expression.occursLessThanOrEqualTo('a', 1))
                .subject("b")
                .expectedState(TRUE)
                .expectedName("occurs[a] <= 1")
                .expectedExpected("occurrences <= 1")
                .expectedActual("occurrences := 0")
                .addTest();
    }

    private void startsWithTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.startsWith("aa"))
                .subject("bbaa")
                .expectedState(FALSE)
                .expectedName("startsWith[aa]")
                .expectedExpected("startsWith[aa]")
                .expectedActual("bbaa")
                .addTest()
            .newTest((expression, value) -> expression.startsWith("aa"))
                .subject("aabb")
                .expectedState(TRUE)
                .expectedName("startsWith[aa]")
                .expectedExpected("startsWith[aa]")
                .expectedActual("aabb")
                .addTest();
    }

    private void doesNotStartWithTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.doesNotStartWith("aa"))
                .subject("bbaa")
                .expectedState(TRUE)
                .expectedName("doesNotStartWith[aa]")
                .expectedExpected("not[startsWith[aa]]")
                .expectedActual("bbaa")
                .addTest()
            .newTest((expression, value) -> expression.doesNotStartWith("aa"))
                .subject("aabb")
                .expectedState(FALSE)
                .expectedName("doesNotStartWith[aa]")
                .expectedExpected("not[startsWith[aa]]")
                .expectedActual("aabb")
                .addTest();
    }

    private void endsWithTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.endsWith("aa"))
                .subject("bbaa")
                .expectedState(TRUE)
                .expectedName("endsWith[aa]")
                .expectedExpected("endsWith[aa]")
                .expectedActual("bbaa")
                .addTest()
            .newTest((expression, value) -> expression.endsWith("aa"))
                .subject("aabb")
                .expectedState(FALSE)
                .expectedName("endsWith[aa]")
                .expectedExpected("endsWith[aa]")
                .expectedActual("aabb")
                .addTest();
    }

    private void doesNotEndWithTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.doesNotEndWith("aa"))
                .subject("bbaa")
                .expectedState(FALSE)
                .expectedName("doesNotEndWith[aa]")
                .expectedExpected("not[endsWith[aa]]")
                .expectedActual("bbaa")
                .addTest()
            .newTest((expression, value) -> expression.doesNotEndWith("aa"))
                .subject("aabb")
                .expectedState(TRUE)
                .expectedName("doesNotEndWith[aa]")
                .expectedExpected("not[endsWith[aa]]")
                .expectedActual("aabb")
                .addTest();
    }

    private void matchesTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.matches("a*b"))
                .subject("aab")
                .expectedState(TRUE)
                .expectedName("matches[a*b]")
                .expectedExpected("matches[a*b]")
                .expectedActual("aab")
                .addTest()
            .newTest((expression, value) -> expression.matches("a*b"))
                .subject("ccc")
                .expectedState(FALSE)
                .expectedName("matches[a*b]")
                .expectedExpected("matches[a*b]")
                .expectedActual("ccc")
                .addTest();
    }

    private void doesNotMatchTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.doesNotMatch("a*b"))
                .subject("aab")
                .expectedState(FALSE)
                .expectedName("doesNotMatch[a*b]")
                .expectedExpected("not[matches[a*b]]")
                .expectedActual("aab")
                .addTest()
            .newTest((expression, value) -> expression.doesNotMatch("a*b"))
                .subject("ccc")
                .expectedState(TRUE)
                .expectedName("doesNotMatch[a*b]")
                .expectedExpected("not[matches[a*b]]")
                .expectedActual("ccc")
                .addTest();
    }

    private void isCaseInsensitiveEqualToTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isCaseInsensitiveEqualTo("foo"))
                .subject("foo")
                .expectedState(TRUE)
                .expectedName("caseInsensitiveEqualTo[foo]")
                .expectedExpected("foo")
                .expectedActual("foo")
                .addTest()
            .newTest((expression, value) -> expression.isCaseInsensitiveEqualTo("Foo"))
                .subject("foo")
                .expectedState(TRUE)
                .expectedName("caseInsensitiveEqualTo[Foo]")
                .expectedExpected("Foo")
                .expectedActual("foo")
                .addTest()
            .newTest((expression, value) -> expression.isCaseInsensitiveEqualTo("bar"))
                .subject("foo")
                .expectedState(FALSE)
                .expectedName("caseInsensitiveEqualTo[bar]")
                .expectedExpected("bar")
                .expectedActual("foo")
                .addTest();
    }

    private void hasAtIndexTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.hasAtIndex('c', 0))
                .testCaseName("[hasAtIndex:char] Has letter but not at index")
                .subject("abc")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex('c', 0))
                .testCaseName("[hasAtIndex:char] Has letter at index")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex('z', 0))
                .testCaseName("[hasAtIndex:char] Does not have letter")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'z' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex('c', 0))
                .testCaseName("[hasAtIndex:char] First index matches")
                .subject("cabc")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex("c", 0))
                .testCaseName("[hasAtIndex:char] Has letter but not at index")
                .subject("abc")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex("c", 0))
                .testCaseName("[hasAtIndex:string] Has letter at index")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex("z", 0))
                .testCaseName("[hasAtIndex:string] Does not have letter")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'z' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.hasAtIndex("c", 0))
                .testCaseName("[hasAtIndex:string] First index matches")
                .subject("cabc")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("0")
                .addTest();
    }

    private void doesNotHaveAtIndexTestCase(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.doesNotHaveAtIndex('c', 0))
                .testCaseName("[doesNotHaveAtIndex:char] Has letter but not at index")
                .subject("abc")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex('c', 0))
                .testCaseName("[doesNotHaveAtIndex:char] Has letter at index")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex('z', 0))
                .testCaseName("[doesNotHaveAtIndex:char] Does not have letter")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'z' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex('c', 0))
                .testCaseName("[doesNotHaveAtIndex:char] First index matches")
                .subject("cabc")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex("c", 0))
                .testCaseName("[doesNotHaveAtIndex:string] Has letter but not at index")
                .subject("abc")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex("c", 0))
                .testCaseName("[doesNotHaveAtIndex:string] Has letter at index")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex("z", 0))
                .testCaseName("[doesNotHaveAtIndex:string] Does not have letter")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("indexOf[" + 'z' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtIndex("c", 0))
                .testCaseName("[doesNotHaveAtIndex:string] First index matches")
                .subject("cabc")
                .expectedState(FALSE)
                .expectedName("indexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("0")
                .addTest();
    }

    private void hasAtLastIndexTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.hasAtLastIndex('c', 0))
                .testCaseName("[hasAtLastIndex:char] Has letter but not at index")
                .subject("abc")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex('c', 0))
                .testCaseName("[hasAtLastIndex:char] Has letter at index")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex('z', 0))
                .testCaseName("[hasAtLastIndex:char] Does not have letter")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'z' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex('c', 0))
                .testCaseName("[hasAtLastIndex:char] First index matches")
                .subject("cabc")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("3")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex("c", 0))
                .testCaseName("[hasAtLastIndex:char] Has letter but not at index")
                .subject("abc")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex("c", 0))
                .testCaseName("[hasAtLastIndex:string] Has letter at index")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex("z", 0))
                .testCaseName("[hasAtLastIndex:string] Does not have letter")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'z' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.hasAtLastIndex("c", 0))
                .testCaseName("[hasAtLastIndex:string] First index matches")
                .subject("cabc")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'c' + "] := " + 0)
                .expectedExpected("0")
                .expectedActual("3")
                .addTest();
    }

    private void doesNotHaveAtLastIndexTestCases(ExpressionTestCaseBuilder<String, StringUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.doesNotHaveAtLastIndex('c', 0))
                .testCaseName("[doesNotHaveAtLastIndex:char] Has letter but not at index")
                .subject("abc")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex('c', 0))
                .testCaseName("[doesNotHaveAtLastIndex:char] Has letter at index")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex('z', 0))
                .testCaseName("[doesNotHaveAtLastIndex:char] Does not have letter")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'z' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex('c', 0))
                .testCaseName("[hasAtLastIndex:char] First index matches")
                .subject("cabc")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("3")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex("c", 0))
                .testCaseName("[doesNotHaveAtLastIndex:string] Has letter but not at index")
                .subject("abc")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex("c", 0))
                .testCaseName("[doesNotHaveAtLastIndex:string] Has letter at index")
                .subject("cba")
                .expectedState(FALSE)
                .expectedName("lastIndexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex("z", 0))
                .testCaseName("[doesNotHaveAtLastIndex:string] Does not have letter")
                .subject("cba")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'z' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.doesNotHaveAtLastIndex("c", 0))
                .testCaseName("[hasAtLastIndex:string] First index matches")
                .subject("cabc")
                .expectedState(TRUE)
                .expectedName("lastIndexOf[" + 'c' + "] != " + 0)
                .expectedExpected("not[0]")
                .expectedActual("3")
                .addTest();
    }
}
