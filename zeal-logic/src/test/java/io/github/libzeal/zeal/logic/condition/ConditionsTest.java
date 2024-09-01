package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import org.junit.jupiter.api.Test;

import java.util.Objects;

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

        assertEquals(name, condition.create(subject).name());
    }

    @Test
    void givenAlwaysTruePredicate_whenOf_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of("foo", s -> true);

        assertEquals(Result.TRUE, condition.create(subject).evaluate().result());
    }

    @Test
    void givenAlwaysFalsePredicate_whenOf_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of("foo", s -> false);

        assertEquals(Result.FALSE, condition.create(subject).evaluate().result());
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

        assertEquals(Condition.DEFAULT_NAME, condition.create(subject).name());
    }

    @Test
    void givenAlwaysTruePredicate_whenOfPredicateOnly_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of(s -> true);

        assertEquals(Result.TRUE, condition.create(subject).evaluate().result());
    }

    @Test
    void givenAlwaysFalsePredicate_whenOfPredicateOnly_thenCorrectConditionCreated() {

        final Object subject = new Object();
        final Condition<Object> condition = Conditions.of(s -> false);

        assertEquals(Result.FALSE, condition.create(subject).evaluate().result());
    }

    // ------------------------------------------------------------------------
    // Conditions#exactly(Object)
    // ------------------------------------------------------------------------

    @Test
    void givenMatchingValue_whenExactly_thenCorrectConditionCreated() {

        final Object subject = new Object();

        final Condition<Object> condition = Conditions.exactly(subject);

        final Expression expression = condition.create(subject);
        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(ObjectConditions.exactlyName(subject), expression.name());
        assertEquals(ObjectConditions.exactlyName(subject), evaluation.name());
        assertEquals(subject.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(ObjectConditions.exactlyHint(subject), evaluation.rationale().hint().get());
    }

    @Test
    void givenNonMatchingValue_whenExactly_thenCorrectConditionCreated() {

        final Object subject = new AlwaysUnequalValue();
        final Object desired = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.exactly(desired);

        final Expression expression = condition.create(subject);
        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(ObjectConditions.exactlyName(desired), expression.name());
        assertEquals(ObjectConditions.exactlyName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(ObjectConditions.exactlyHint(desired), evaluation.rationale().hint().get());
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

        final Expression expression = condition.create(subject);
        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(ObjectConditions.exactlyName(desired), expression.name());
        assertEquals(ObjectConditions.exactlyName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(ObjectConditions.exactlyHint(desired), evaluation.rationale().hint().get());
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

        final Expression expression = condition.create(subject);
        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(ObjectConditions.equalToName(desired), expression.name());
        assertEquals(ObjectConditions.equalToName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(ObjectConditions.equalToHint(desired), evaluation.rationale().hint().get());
    }

    @Test
    void givenNonMatchingValue_whenEqualTo_thenCorrectConditionCreated() {

        final Object subject = new AlwaysUnequalValue();
        final Object desired = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.equalTo(desired);

        final Expression expression = condition.create(subject);
        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(ObjectConditions.equalToName(desired), expression.name());
        assertEquals(ObjectConditions.equalToName(desired), evaluation.name());
        assertEquals(desired.toString(), evaluation.rationale().expected());
        assertEquals(subject.toString(), evaluation.rationale().actual());
        assertTrue(evaluation.rationale().hint().isPresent());
        assertEquals(ObjectConditions.equalToHint(desired), evaluation.rationale().hint().get());
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

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
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

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndFirstMatches_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysTrue, alwaysFalse);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndSecondMatches_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysFalse, alwaysTrue);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndBothMatch_whenAnyOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue1 = Condition.tautology();
        final Condition<Object> alwaysTrue2 = Condition.tautology();

        final Condition<Object> condition = Conditions.anyOf(alwaysTrue1, alwaysTrue2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndBothDoNotMatch_whenAnyOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse1 = Condition.contradiction();
        final Condition<Object> alwaysFalse2 = Condition.contradiction();

        final Condition<Object> condition = Conditions.anyOf(alwaysFalse1, alwaysFalse2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    // ------------------------------------------------------------------------
    // Conditions#anyOf(Object[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenAnyOfValues_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.anyOf((Object[]) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenAnyOfValues_thenMatches() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.anyOf(match);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenAnyOfValues_thenDoesNotMatch() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(nonMatch);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenAnyOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(match, nonMatch);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenAnyOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(nonMatch, match);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValueAndBothMatch_whenAnyOfValue_thenMatches() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.anyOf(match1, match2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenAnyOfValues_thenDoesNotMatch() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(nonMatch1, nonMatch2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    // ------------------------------------------------------------------------
    // Conditions#anyOf(Iterable)
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenAnyOfIterable_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.anyOf((Iterable<Object>) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenAnyOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.anyOf(singletonList(match));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenAnyOfIterable_thenDoesNotMatch() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(singletonList(nonMatch));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenAnyOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(asList(match, nonMatch));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenAnyOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(asList(nonMatch, match));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValueAndBothMatch_whenAnyOfIterable_thenMatches() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.anyOf(asList(match1, match2));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenAnyOfIterable_thenDoesNotMatch() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.anyOf(asList(nonMatch1, nonMatch2));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
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

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenOneConditionAndDoesNotMatch_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysFalse);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndFirstMatches_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysTrue, alwaysFalse);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndSecondMatches_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysFalse, alwaysTrue);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndBothMatch_whenAllOfCondition_thenMatches() {

        final Condition<Object> alwaysTrue1 = Condition.tautology();
        final Condition<Object> alwaysTrue2 = Condition.tautology();

        final Condition<Object> condition = Conditions.allOf(alwaysTrue1, alwaysTrue2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndBothDoNotMatch_whenAllOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysFalse1 = Condition.contradiction();
        final Condition<Object> alwaysFalse2 = Condition.contradiction();

        final Condition<Object> condition = Conditions.allOf(alwaysFalse1, alwaysFalse2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    // ------------------------------------------------------------------------
    // Conditions#allOf(Object[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenAllOfValues_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.allOf((Object[]) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenAllOfValues_thenMatches() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.allOf(match);

        final Evaluation evaluation = condition.create(match).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenAllOfValues_thenDoesNotMatch() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(nonMatch);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenAllOfValue_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(match, nonMatch);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenAllOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(nonMatch, match);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValueAndBothMatch_whenAllOfValue_thenMatches() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.allOf(match1, match2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenAllOfValues_thenDoesNotMatch() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(nonMatch1, nonMatch2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    // ------------------------------------------------------------------------
    // Conditions#allOf(Iterable)
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenAllOfIterable_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.allOf((Iterable<Object>) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenAllOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.allOf(singletonList(match));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenAllOfIterable_thenDoesNotMatch() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(singletonList(nonMatch));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenAllOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(asList(match, nonMatch));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenAllOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(asList(nonMatch, match));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValueAndBothMatch_whenAllOfIterable_thenMatches() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.allOf(asList(match1, match2));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenAllOfIterable_thenDoesNotMatch() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.allOf(asList(nonMatch1, nonMatch2));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
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

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenOneConditionAndDoesNotMatch_whenNoneOfCondition_thenMatches() {

        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysFalse);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndFirstMatches_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysTrue, alwaysFalse);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndSecondMatches_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue = Condition.tautology();
        final Condition<Object> alwaysFalse = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysFalse, alwaysTrue);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndBothMatch_whenNoneOfCondition_thenDoesNotMatch() {

        final Condition<Object> alwaysTrue1 = Condition.tautology();
        final Condition<Object> alwaysTrue2 = Condition.tautology();

        final Condition<Object> condition = Conditions.noneOf(alwaysTrue1, alwaysTrue2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoConditionsAndBothDoNotMatch_whenNoneOfCondition_thenMatches() {

        final Condition<Object> alwaysFalse1 = Condition.contradiction();
        final Condition<Object> alwaysFalse2 = Condition.contradiction();

        final Condition<Object> condition = Conditions.noneOf(alwaysFalse1, alwaysFalse2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    // ------------------------------------------------------------------------
    // Conditions#noneOf(Object[])
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenNoneOfValues_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.noneOf((Object[]) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenNoneOfValues_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.noneOf(match);

        final Evaluation evaluation = condition.create(match).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenNoneOfValues_thenMatches() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(nonMatch);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenNoneOfValue_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(match, nonMatch);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenNoneOfValue_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(nonMatch, match);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValueAndBothMatch_whenNoneOfValue_thenDoesNotMatch() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.noneOf(match1, match2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenNoneOfValues_thenMatches() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(nonMatch1, nonMatch2);

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    // ------------------------------------------------------------------------
    // Conditions#noneOf(Iterable)
    // ------------------------------------------------------------------------

    @Test
    void givenNullValues_whenNoneOfIterable_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Conditions.noneOf((Iterable<Object>) null)
        );
    }

    @Test
    void givenOneValueAndMatches_whenNoneOfIterable_thenDoesNotMatch() {

        final Object match = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.noneOf(singletonList(match));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenOneValueAndDoesNotMatch_whenNoneOfIterable_thenMatches() {

        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(singletonList(nonMatch));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndFirstMatches_whenNoneOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(asList(match, nonMatch));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndSecondMatches_whenNoneOfIterable_thenMatches() {

        final Object match = new AlwaysEqualValue();
        final Object nonMatch = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(asList(nonMatch, match));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValueAndBothMatch_whenNoneOfIterable_thenDoesNotMatch() {

        final Object match1 = new AlwaysEqualValue();
        final Object match2 = new AlwaysEqualValue();

        final Condition<Object> condition = Conditions.noneOf(asList(match1, match2));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.FALSE, evaluation.result());
    }

    @Test
    void givenTwoValuesAndBothDoNotMatch_whenNoneOfIterable_thenMatches() {

        final Object nonMatch1 = new AlwaysUnequalValue();
        final Object nonMatch2 = new AlwaysUnequalValue();

        final Condition<Object> condition = Conditions.noneOf(asList(nonMatch1, nonMatch2));

        final Evaluation evaluation = condition.create(new EqualityEvaluatingValue()).evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }
}
