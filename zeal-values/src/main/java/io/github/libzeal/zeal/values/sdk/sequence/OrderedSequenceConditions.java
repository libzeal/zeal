package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.values.sdk.StandardRationales;
import io.github.libzeal.zeal.values.sdk.StandardRationales.Operators;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

/**
 * A set of common conditions for ordered sequences. These condition are used by
 * the supported first-party ordered sequence values and is also intended for
 * use by third-party ordered sequence values.
 *
 * @author Justin Albano
 */
public class OrderedSequenceConditions {

    private OrderedSequenceConditions() {
    }

    /**
     * Creates a condition that checks if the last index of the supplied desired
     * element is the supplied index.
     *
     * @param desired
     *         The desired element.
     * @param index
     *         The desired last index of the desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if the last index of the supplied desired
     * element is the supplied index.
     */
    public static <T, S> Condition<S> lastIndexOfIs(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.lastIndexOf(s, desired))
                .passesIf(lastIndex -> lastIndex == index)
                .name(Operators.EQ.display(lastIndexOfName(desired), index))
                .expected(index)
                .build();
    }

    /**
     * Creates a condition that checks if the last index of the supplied desired
     * element is not the supplied index.
     *
     * @param desired
     *         The desired element.
     * @param index
     *         The last index that the desired element should not be.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if the last index of the supplied desired
     * element is not the supplied index.
     */
    public static <T, S> Condition<S> lastIndexOfIsNot(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.lastIndexOf(s, desired))
                .passesIf(lastIndex -> lastIndex != index)
                .name(Operators.NE.display(lastIndexOfName(desired), index))
                .expected("not[" + index + "]")
                .build();
    }

    private static <T> String lastIndexOfName(final T desired) {
        return "lastIndexOf[" + stringify(desired) + "]";
    }

    /**
     * Creates a condition that checks if the index of the supplied desired
     * element is the supplied index.
     *
     * @param desired
     *         The desired element.
     * @param index
     *         The desired index of the desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if the index of the supplied desired
     * element is the supplied index.
     */
    public static <T, S> Condition<S> hasAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.atIndex(s, index, desired))
                .passesIf(found -> found.matches(desired))
                .name(withPairs("hasAtIndex", "desired", desiredName, "index", index))
                .expected(desiredName)
                .actual(context ->
                        context.observed().ifPresentOrElseGet(
                                ObjectFormatter::stringify,
                                () -> "Index " + index + " is out of bounds"
                        ))
                .hint(context -> {
                    if (context.passed()) {
                        return "Desired element found at index " + index;
                    }
                    else {
                        return context.observed().isPresentOrElse(
                                "Index " + index + " is within bounds, but the desired element was not found there",
                                "Index " + index + " is out of bounds"
                        );
                    }
                })
                .build();
    }

    /**
     * Creates a condition that checks if the index of the supplied desired
     * element is not the supplied index.
     *
     * @param desired
     *         The desired element.
     * @param index
     *         The index that the desired element should not be.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if the index of the supplied desired
     * element is not the supplied index.
     */
    public static <T, S> Condition<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.atIndex(s, index, desired))
                .passesIf(found -> !found.matches(desired))
                .name(withPairs("doesNotHaveAtIndex", "desired", desiredName, "index", index))
                .expected("not[" + desiredName + "]")
                .actual(context ->
                        context.observed().ifPresentOrElseGet(
                                ObjectFormatter::stringify,
                                () -> "Index " + index + " is out of bounds"
                        ))
                .build();
    }

    /**
     * Creates a condition that checks if a sequence starts with the supplied
     * desired element.
     *
     * @param desired
     *         The desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence starts with the supplied
     * desired element.
     */
    public static <T, S> Condition<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.firstElement(s, desired))
                .passesIf(found -> found.matches(desired))
                .name(withValue("startsWith", desiredName))
                .expected(desiredName)
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
                )
                .hint(context -> {
                    if (context.passed()) {
                        return "The first element is " + desiredName;
                    }
                    else {
                        return context.observed().isPresentOrElse(
                                "Value has at least one element, but the first element is not " + desiredName,
                                "Value is empty"
                        );
                    }
                })
                .build();
    }

    /**
     * Creates a condition that checks if a sequence does not start with the
     * supplied desired element.
     *
     * @param desired
     *         The desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence does not start with the
     * supplied desired element.
     */
    public static <T, S> Condition<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.firstElement(s, desired))
                .passesIf(found -> !found.matches(desired))
                .name(withValue("doesNotStartWith", desiredName))
                .expected("not[" + desiredName + "]")
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
                )
                .hint(context ->
                        context.observed().ifPresentOrElseGet(
                                element -> "The first element is " + stringify(element),
                                () -> "Value is empty"
                        )
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence ends with the supplied
     * desired element.
     *
     * @param desired
     *         The desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence ends with the supplied
     * desired element.
     */
    public static <T, S> Condition<S> endsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.lastElement(s, desired))
                .passesIf(found -> found.matches(desired))
                .name(withValue("endsWith", desiredName))
                .expected(desiredName)
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
                )
                .hint(context -> {
                    if (context.passed()) {
                        return "The last element is " + desiredName;
                    }
                    else {
                        return context.observed().isPresentOrElse(
                                "Value has at least one element, but the last element is not " + desiredName,
                                "Value is empty"
                        );
                    }
                })
                .build();
    }

    /**
     * Creates a condition that checks if a sequence does not end with the
     * supplied desired element.
     *
     * @param desired
     *         The desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence does not end with the
     * supplied desired element.
     */
    public static <T, S> Condition<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.lastElement(s, desired))
                .passesIf(found -> !found.matches(desired))
                .name(withValue("doesNotEndWith", desiredName))
                .expected("not[" + desiredName + "]")
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
                )
                .hint(context ->
                        context.observed().ifPresentOrElseGet(
                                element -> "The last element is " + stringify(element),
                                () -> "Value is empty"
                        )
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * desired element.
     *
     * @param desired
     *         The desired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * desired element.
     */
    public static <T, S> Condition<S> includes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.indexOf(s, desired))
                .passesIf(index -> index > -1)
                .name(StandardRationales.includes(desired))
                .expected(StandardRationales.includes(desired))
                .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
                .hint(context -> StandardRationales.needleInHaystackHint(context.observed(), desired))
                .build();
    }

    /**
     * Creates a condition that checks if a sequence excludes the supplied
     * undesired element.
     *
     * @param undesired
     *         The undesired element.
     * @param ops
     *         The ordered sequence operations to use.
     * @param <T>
     *         The type of the elements in the sequence.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence excludes the supplied
     * undesired element.
     */
    public static <T, S> Condition<S> excludes(final T undesired, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.indexOf(s, undesired))
                .passesIf(index -> index == -1)
                .name(StandardRationales.excludes(undesired))
                .expected(StandardRationales.excludes(undesired))
                .actual(context -> context.passed() ? StandardRationales.excludes(undesired) : StandardRationales.includes(undesired))
                .hint(context -> StandardRationales.needleInHaystackHint(context.observed(), undesired))
                .build();
    }
}
