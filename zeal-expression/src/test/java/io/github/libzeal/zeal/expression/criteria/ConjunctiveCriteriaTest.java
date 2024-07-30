package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.ConjunctiveEvaluation;
import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.github.libzeal.zeal.expression.criteria.CriteriaAssertions.assertIsNotSkipped;
import static io.github.libzeal.zeal.expression.criteria.CriteriaAssertions.assertIsSkipped;
import static io.github.libzeal.zeal.expression.criteria.CriteriaMocks.criteria;
import static org.junit.jupiter.api.Assertions.*;

class ConjunctiveCriteriaTest {

    private String name;
    private ConjunctiveCriteria<Object> criteria;

    @BeforeEach
    void setUp() {

        name = "foo";

        criteria = new ConjunctiveCriteria<>(name, new ArrayList<>());
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final ArrayList<Criteria<Object>> list = new ArrayList<>();

        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveCriteria<>(null, list)
        );
    }

    @Test
    void givenNullCriteriaList_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveCriteria<>(name, null)
        );
    }

    @Test
    void givenNullName_whenConstructWithNameOnly_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveCriteria<>(null)
        );
    }

    @Test
    void givenNoSubCriteria_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        EvaluationContext context = new EvaluationContext();

        Evaluation evaluation = criteria.evaluate(subject, context);

        assertEquals(Result.PASSED, evaluation.result());
        assertInstanceOf(ConjunctiveEvaluation.class, evaluation);
    }

    @Test
    void givenOnePassingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        EvaluationContext context = new EvaluationContext();
        Criteria<Object> subCriteria = criteria(Result.PASSED);

        criteria.append(subCriteria);

        Evaluation evaluation = criteria.evaluate(subject, context);

        assertEquals(Result.PASSED, evaluation.result());
        assertInstanceOf(ConjunctiveEvaluation.class, evaluation);
    }

    @Test
    void givenOneFailingCriterionAppended_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        EvaluationContext context = new EvaluationContext();
        Criteria<Object> subCriteria = criteria(Result.FAILED);

        criteria.append(subCriteria);

        Evaluation evaluation = criteria.evaluate(subject, context);

        assertEquals(Result.FAILED, evaluation.result());
        assertInstanceOf(ConjunctiveEvaluation.class, evaluation);
    }

    @Test
    void givenOneFailingCriteriaPrependedBeforePassingCriteria_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        EvaluationContext context = new EvaluationContext();
        Criteria<Object> passingSubCriteria = criteria(Result.PASSED);
        Criteria<Object> failingSubCriteria = criteria(Result.FAILED);

        criteria.append(passingSubCriteria);
        criteria.prepend(failingSubCriteria);

        Evaluation evaluation = criteria.evaluate(subject, context);

        assertEquals(Result.FAILED, evaluation.result());
        assertInstanceOf(ConjunctiveEvaluation.class, evaluation);
        assertIsSkipped(passingSubCriteria, subject);
    }

    @Test
    void givenOnePassingCriteriaPrependedBeforeFailingCriteria_whenEvaluate_thenEvaluationIsCorrect() {

        Object subject = new Object();
        EvaluationContext context = new EvaluationContext();
        Criteria<Object> passingSubCriteria = criteria(Result.PASSED);
        Criteria<Object> failingSubCriteria = criteria(Result.FAILED);

        criteria.append(failingSubCriteria);
        criteria.prepend(passingSubCriteria);

        Evaluation evaluation = criteria.evaluate(subject, context);

        assertEquals(Result.FAILED, evaluation.result());
        assertInstanceOf(ConjunctiveEvaluation.class, evaluation);
        assertIsNotSkipped(failingSubCriteria, subject);
    }
}
