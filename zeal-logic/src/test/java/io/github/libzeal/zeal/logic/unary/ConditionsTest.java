package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class ConditionsTest {

    // ------------------------------------------------------------------------
    // Conditions#of(String, Predicate)
    // ------------------------------------------------------------------------

    @Test
    void givenNullName_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.of(null, t -> true)
        );
    }

    @Test
    void givenNullPredicate_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.of("foo", null)
        );
    }

    @Test
    void givenValidName_whenOf_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final String name = "foo";

        Condition<Object> condition = Conditions.of(name, s -> true);

        assertEquals(name, condition.bind(subject).name());
    }

    @Test
    void givenAlwaysTruePredicate_whenOf_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of("foo", s -> true);

        assertTrue(condition.bind(subject).evaluate().isTrue());
    }

    @Test
    void givenAlwaysFalsePredicate_whenOf_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of("foo", s -> false);

        assertFalse(condition.bind(subject).evaluate().isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#of(Predicate)
    // ------------------------------------------------------------------------

    @Test
    void givenNullPredicate_whenOfPredicateOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.of(null)
        );
    }

    @Test
    void givenValidName_whenOfPredicateOnly_thenCorrectConditionCreated() {

        final Object subject = new Object();

        Condition<Object> condition = Conditions.of(s -> true);

        assertEquals(Expression.DEFAULT_NAME, condition.bind(subject).name());
    }

    @Test
    void givenAlwaysTruePredicate_whenOfPredicateOnly_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of(s -> true);

        assertTrue(condition.bind(subject).evaluate().isTrue());
    }

    @Test
    void givenAlwaysFalsePredicate_whenOfPredicateOnly_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of(s -> false);

        assertFalse(condition.bind(subject).evaluate().isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#exactly(Object)
    // ------------------------------------------------------------------------

    @Test
    void givenMatchingValue_whenExactly_thenCorrectConditionCreated() {

        final Object subject = new Object();

        final Condition<Object> condition = Conditions.exactly(subject);

        final Expression expression = condition.bind(subject);
        final Evaluation evaluation = expression.evaluate();

        assertTrue(evaluation.isTrue());
        assertEquals(Conditions.exactlyName(subject), expression.name());
        assertEquals(Conditions.exactlyName(subject), evaluation.name());
        assertEquals(subject.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(Conditions.exactlyPassedHint(subject), evaluation.rationale().hint().get());
    }

    @Test
    void givenNonMatchingValue_whenExactly_thenCorrectConditionCreated() {

        final Object subject = new AlwaysUnequalValue();
        final Object desired = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.exactly(desired);

        final Expression expression = condition.bind(subject);
        final Evaluation evaluation = expression.evaluate();

        assertFalse(evaluation.isTrue());
        assertEquals(Conditions.exactlyName(desired), expression.name());
        assertEquals(Conditions.exactlyName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(Conditions.exactlyHint(desired), evaluation.rationale().hint().get());
    }

    private static final class AlwaysUnequalValue {

        @Override
        public boolean equals(final Object other) {
            return false;
        }
    }

    @Test
    void givenEqualButNotIdenticalValue_whenExactly_thenCorrectConditionCreated() {

        final Object subject = new AlwaysEqualValue();
        final Object desired = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.exactly(desired);

        final Expression expression = condition.bind(subject);
        final Evaluation evaluation = expression.evaluate();

        assertFalse(evaluation.isTrue());
        assertEquals(Conditions.exactlyName(desired), expression.name());
        assertEquals(Conditions.exactlyName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(Conditions.exactlyHint(desired), evaluation.rationale().hint().get());
    }

    private static final class AlwaysEqualValue {

        @Override
        public boolean equals(final Object other) {
            return true;
        }
    }

    // ------------------------------------------------------------------------
    // Conditions#equalTo(Object)
    // ------------------------------------------------------------------------

    @Test
    void givenMatchingValue_whenEqualTo_thenCorrectConditionCreated() {

        final Object subject = new AlwaysEqualValue();
        final Object desired = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.equalTo(desired);

        final Expression expression = condition.bind(subject);
        final Evaluation evaluation = expression.evaluate();

        assertTrue(evaluation.isTrue());
        assertEquals(Conditions.equalToName(desired), expression.name());
        assertEquals(Conditions.equalToName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(Conditions.equalToPassedHint(desired), evaluation.rationale().hint().get());
    }

    @Test
    void givenNonMatchingValue_whenEqualTo_thenCorrectConditionCreated() {

        final Object subject = new AlwaysUnequalValue();
        final Object desired = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.equalTo(desired);

        final Expression expression = condition.bind(subject);
        final Evaluation evaluation = expression.evaluate();

        assertFalse(evaluation.isTrue());
        assertEquals(Conditions.equalToName(desired), expression.name());
        assertEquals(Conditions.equalToName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(Conditions.equalToHint(desired), evaluation.rationale().hint().get());
    }

    @Test
    void givenArraysWithSameElements_whenEqualTo_thenTrue() {

        final Condition<Object> condition = Conditions.equalTo(new int[][]{{1}, {2, 3}});

        final Evaluation evaluation = condition.bind(new int[][]{{1}, {2, 3}}).evaluate();

        assertTrue(evaluation.isTrue());
        assertEquals("isEqualTo[[[1], [2, 3]]]", evaluation.name());
        assertEquals("[[1], [2, 3]]", evaluation.rationale().expected());
        assertEquals("[[1], [2, 3]]", evaluation.rationale().actual());
    }

    @Test
    void givenArraysWithDifferentElements_whenEqualTo_thenFalse() {

        final Condition<Object> condition = Conditions.equalTo(new String[]{"a"});

        final Evaluation evaluation = condition.bind(new String[]{"b"}).evaluate();

        assertFalse(evaluation.isTrue());
        assertEquals("[a]", evaluation.rationale().expected());
        assertEquals("[b]", evaluation.rationale().actual());
    }

    // ------------------------------------------------------------------------
    // Conditions#anyOf(Condition[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullConditions_whenAnyOfCondition_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.anyOf((Condition<Object>[]) null)
        );
    }

    @Test
    void givenOneConditionAndMatches_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue = Condition.tautology();

        final Condition<Object> condition = Conditions.anyOf(alwaysTrue);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    /**
     * This class is needed so that the equality of the object under evaluation is used to check the equality, rather
     * than this object itself. This allows for special objects whose {@link Object#equals(Object)} method always result
     * in true or false to be used. If a {@code new Object()} is used instead, the special equality method is never used
     * (the {@link Object#equals(Object)} or {@link Object} is used instead since it's the first argument in the
     * {@link Objects#equals(Object, Object)}} evaluation).
     */
    private static final class EqualityEvaluatingValue {

        @Override
        public boolean equals(final Object other) {
            return other != null && other.equals(this);
        }
    }

    @Test
    void givenOneConditionAndDoesNotMatch_whenAnyOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysFalse);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndFirstMatches_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysTrue, alwaysFalse);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndSecondMatches_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysFalse, alwaysTrue);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndBothMatch_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue1 = Condition.tautology();
        final Condition<Object> alwaysTrue2 = Condition.tautology();

        final Condition<Object> condition = Conditions.anyOf(alwaysTrue1, alwaysTrue2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndBothDoNotMatch_whenAnyOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse1 = Condition.contradiction();
        final Condition<Object> alwaysFalse2 = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysFalse1, alwaysFalse2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#oneOf(Object[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenOneOfValues_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.oneOf((Object[]) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenOneOfValues_thenMatches() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.oneOf(match);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenOneOfValues_thenDoesNotMatch() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.oneOf(nonMatch);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenOneOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.oneOf(match, nonMatch);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenOneOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.oneOf(nonMatch, match);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValueAndBothMatch_whenOneOfValue_thenMatches() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.oneOf(match1, match2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenOneOfValues_thenDoesNotMatch() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.oneOf(nonMatch1, nonMatch2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#in(List)
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenIn_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.in((List<Object>) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenIn_thenMatches() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.in(singletonList(match));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenIn_thenDoesNotMatch() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.in(singletonList(nonMatch));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenIn_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.in(asList(match, nonMatch));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenIn_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.in(asList(nonMatch, match));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValueAndBothMatch_whenIn_thenMatches() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.in(asList(match1, match2));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenIn_thenDoesNotMatch() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.in(asList(nonMatch1, nonMatch2));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#allOf(Condition[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullConditions_whenAllOfCondition_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.allOf((Condition<Object>[]) null)
        );
    }

    @Test
    void givenOneConditionAndMatches_whenAllOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue = Condition.tautology();

        final Condition<Object> condition = Conditions.allOf(alwaysTrue);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenOneConditionAndDoesNotMatch_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysFalse);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndFirstMatches_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysTrue, alwaysFalse);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndSecondMatches_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysFalse, alwaysTrue);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndBothMatch_whenAllOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue1 = Condition.tautology();
        final Condition<Object> alwaysTrue2 = Condition.tautology();

        final Condition<Object> condition = Conditions.allOf(alwaysTrue1, alwaysTrue2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndBothDoNotMatch_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse1 = Condition.contradiction();
        final Condition<Object> alwaysFalse2 = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysFalse1, alwaysFalse2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#noneOf(Condition[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullConditions_whenNoneOfCondition_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.noneOf((Condition<Object>[]) null)
        );
    }

    @Test
    void givenOneConditionAndMatches_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();

        final Condition<Object> condition = Conditions.noneOf(alwaysTrue);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenOneConditionAndDoesNotMatch_whenNoneOfCondition_thenMatches() {

        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysFalse);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndFirstMatches_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysTrue, alwaysFalse);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndSecondMatches_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysFalse, alwaysTrue);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndBothMatch_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue1 = Condition.tautology();
        final Condition<Object> alwaysTrue2 = Condition.tautology();

        final Condition<Object> condition = Conditions.noneOf(alwaysTrue1, alwaysTrue2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoConditionsAndBothDoNotMatch_whenNoneOfCondition_thenMatches() {

        final Condition<Object> alwaysFalse1 = Condition.contradiction();
        final Condition<Object> alwaysFalse2 = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysFalse1, alwaysFalse2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#notOneOf(Object[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenNotOneOfValues_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.notOneOf((Object[]) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenNotOneOfValues_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.notOneOf(match);

        final Evaluation evaluation = condition.bind(match).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenNotOneOfValues_thenMatches() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notOneOf(nonMatch);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenNotOneOfValue_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notOneOf(match, nonMatch);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenNotOneOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notOneOf(nonMatch, match);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValueAndBothMatch_whenNotOneOfValue_thenDoesNotMatch() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.notOneOf(match1, match2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenNotOneOfValues_thenMatches() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notOneOf(nonMatch1, nonMatch2);

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Conditions#notIn(List)
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenNotIn_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.notIn((List<Object>) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenNotIn_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.notIn(singletonList(match));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenNotIn_thenMatches() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notIn(singletonList(nonMatch));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenNotIn_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notIn(asList(match, nonMatch));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenNotIn_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notIn(asList(nonMatch, match));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValueAndBothMatch_whenNotIn_thenDoesNotMatch() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.notIn(asList(match1, match2));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertFalse(evaluation.isTrue());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenNotIn_thenMatches() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.notIn(asList(nonMatch1, nonMatch2));

        final Evaluation evaluation = condition.bind(new EqualityEvaluatingValue()).evaluate();

        assertTrue(evaluation.isTrue());
    }

    // ------------------------------------------------------------------------
    // Wildcard parameters
    // ------------------------------------------------------------------------

    @Test
    void givenPredicateOfSupertype_whenOf_thenConditionOfSubtypeCreated() {

        final Predicate<Object> nonNull = Objects::nonNull;

        final Condition<String> condition = Conditions.of(nonNull);

        assertTrue(condition.bind("foo").evaluate().isTrue());
    }

    @Test
    void givenNamedPredicateOfSupertype_whenOf_thenConditionOfSubtypeCreated() {

        final Predicate<Object> nonNull = Objects::nonNull;

        final Condition<String> condition = Conditions.of("nonNull", nonNull);

        assertTrue(condition.bind("foo").evaluate().isTrue());
    }

    @Test
    void givenListOfSubtype_whenIn_thenConditionOfSupertypeCreated() {

        final List<Integer> values = List.of(1, 2);

        final Condition<Number> condition = Conditions.in(values);

        assertTrue(condition.bind(2).evaluate().isTrue());
    }

    @Test
    void givenListOfSubtype_whenNotIn_thenConditionOfSupertypeCreated() {

        final List<Integer> values = List.of(1, 2);

        final Condition<Number> condition = Conditions.notIn(values);

        assertTrue(condition.bind(3).evaluate().isTrue());
    }

    @Test
    void givenConditionOfSupertype_whenAnyOf_thenConditionOfSubtypeCreated() {

        final Condition<Object> isNull = Conditions.of(Objects::isNull);
        final Condition<String> isEmpty = Conditions.of(String::isEmpty);

        final Condition<String> condition = Conditions.anyOf(isNull, isEmpty);

        assertTrue(condition.bind("").evaluate().isTrue());
    }

    @Test
    void givenConditionOfSupertype_whenAllOf_thenConditionOfSubtypeCreated() {

        final Condition<Object> nonNull = Conditions.of(Objects::nonNull);
        final Condition<String> notEmpty = Conditions.of(s -> !s.isEmpty());

        final Condition<String> condition = Conditions.allOf(nonNull, notEmpty);

        assertTrue(condition.bind("foo").evaluate().isTrue());
    }

    @Test
    void givenConditionOfSupertype_whenNoneOf_thenConditionOfSubtypeCreated() {

        final Condition<Object> isNull = Conditions.of(Objects::isNull);
        final Condition<String> isEmpty = Conditions.of(String::isEmpty);

        final Condition<String> condition = Conditions.noneOf(isNull, isEmpty);

        assertTrue(condition.bind("foo").evaluate().isTrue());
    }

    @Test
    void givenConditions_whenAnyOfCondition_thenNamedAnyOf() {

        final Condition<String> isNull = Conditions.of(Objects::isNull);

        assertEquals("anyOf", Conditions.anyOf(isNull).bind("foo").name());
    }

    @Test
    void givenValues_whenOneOfValues_thenNamedOneOf() {
        assertEquals("oneOf", Conditions.oneOf("a", "b").bind("foo").name());
    }

    @Test
    void givenList_whenIn_thenNamedIn() {
        assertEquals("in", Conditions.in(asList("a", "b")).bind("foo").name());
    }

    @Test
    void givenConditions_whenAllOfCondition_thenNamedAllOf() {

        final Condition<String> isNull = Conditions.of(Objects::isNull);

        assertEquals("allOf", Conditions.allOf(isNull).bind("foo").name());
    }

    @Test
    void givenConditions_whenNoneOfCondition_thenNamedNoneOf() {

        final Condition<String> isNull = Conditions.of(Objects::isNull);

        assertEquals("noneOf", Conditions.noneOf(isNull).bind("foo").name());
    }

    @Test
    void givenValues_whenNotOneOfValues_thenNamedNotOneOf() {
        assertEquals("notOneOf", Conditions.notOneOf("a", "b").bind("foo").name());
    }

    @Test
    void givenList_whenNotIn_thenNamedNotIn() {
        assertEquals("notIn", Conditions.notIn(asList("a", "b")).bind("foo").name());
    }

    @Test
    void givenNullList_whenAnyOfConditionList_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.anyOf((List<Condition<Object>>) null)
        );
    }

    @Test
    void givenMixedConditions_whenAnyOfConditionList_thenResultIsTrue() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology(), Condition.contradiction());

        assertTrue(Conditions.anyOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenTrueConditions_whenAnyOfConditionList_thenResultIsTrue() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology(), Condition.tautology());

        assertTrue(Conditions.anyOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenFalseConditions_whenAnyOfConditionList_thenResultIsFalse() {

        final List<Condition<Object>> conditions = List.of(Condition.contradiction(), Condition.contradiction());

        assertFalse(Conditions.anyOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenConditionListOfSupertype_whenAnyOf_thenConditionOfSubtypeCreated() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology());
        final Condition<String> condition = Conditions.anyOf(conditions);

        assertEquals("anyOf", condition.bind("foo").name());
    }

    @Test
    void givenNullList_whenAllOfConditionList_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.allOf((List<Condition<Object>>) null)
        );
    }

    @Test
    void givenMixedConditions_whenAllOfConditionList_thenResultIsFalse() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology(), Condition.contradiction());

        assertFalse(Conditions.allOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenTrueConditions_whenAllOfConditionList_thenResultIsTrue() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology(), Condition.tautology());

        assertTrue(Conditions.allOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenFalseConditions_whenAllOfConditionList_thenResultIsFalse() {

        final List<Condition<Object>> conditions = List.of(Condition.contradiction(), Condition.contradiction());

        assertFalse(Conditions.allOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenConditionListOfSupertype_whenAllOf_thenConditionOfSubtypeCreated() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology());
        final Condition<String> condition = Conditions.allOf(conditions);

        assertEquals("allOf", condition.bind("foo").name());
    }

    @Test
    void givenNullList_whenNoneOfConditionList_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.noneOf((List<Condition<Object>>) null)
        );
    }

    @Test
    void givenMixedConditions_whenNoneOfConditionList_thenResultIsFalse() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology(), Condition.contradiction());

        assertFalse(Conditions.noneOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenTrueConditions_whenNoneOfConditionList_thenResultIsFalse() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology(), Condition.tautology());

        assertFalse(Conditions.noneOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenFalseConditions_whenNoneOfConditionList_thenResultIsTrue() {

        final List<Condition<Object>> conditions = List.of(Condition.contradiction(), Condition.contradiction());

        assertTrue(Conditions.noneOf(conditions).bind("foo").evaluate().isTrue());
    }

    @Test
    void givenConditionListOfSupertype_whenNoneOf_thenConditionOfSubtypeCreated() {

        final List<Condition<Object>> conditions = List.of(Condition.tautology());
        final Condition<String> condition = Conditions.noneOf(conditions);

        assertEquals("noneOf", condition.bind("foo").name());
    }

    @Test
    void givenSingleListCandidate_whenOneOf_thenListIsSingleCandidate() {

        final Condition<List<String>> condition = Conditions.oneOf(List.of("a", "b"));

        assertTrue(condition.bind(List.of("a", "b")).evaluate().isTrue());
    }

    @Test
    void givenSingleListCandidate_whenNotOneOf_thenListIsSingleCandidate() {

        final Condition<List<String>> condition = Conditions.notOneOf(List.of("a", "b"));

        assertFalse(condition.bind(List.of("a", "b")).evaluate().isTrue());
    }
}
