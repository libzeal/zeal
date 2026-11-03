package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.logic.unary.ObservableUnaryExpression.Field;
import io.github.libzeal.zeal.values.config.Configuration;
import io.github.libzeal.zeal.values.sdk.StandardRationales;
import io.github.libzeal.zeal.values.sdk.StandardRationales.Operators;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;
import static java.util.stream.Collectors.toList;

/**
 * A set of common conditions for sequences. These conditions are used by the
 * supported first-party sequence values and is also intended for use by
 * third-party sequence values.
 *
 * @author Justin Albano
 */
public class SequenceConditions {

    private static final String LENGTH = "length";
    private static final String NULL_DESIRED_HINT = "Desired elements is (null), which passes by default";

    private SequenceConditions() {
    }

    /**
     * Creates a condition that checks if a sequence is empty.
     *
     * @param ops
     *         The sequence operations to use.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence is empty.
     */
    public static <S> Condition<S> isEmpty(final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size == 0)
                .name("isEmpty")
                .expected(Operators.EQ.display(LENGTH, 0))
                .actual(actualLength())
                .build();
    }

    private static <S> Field<S, Integer> actualLength() {
        return context -> Operators.EQ.display(LENGTH, context.observed());
    }

    /**
     * Creates a condition that checks if a sequence is not empty.
     *
     * @param ops
     *         The sequence operations to use.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence is not empty.
     */
    public static <S> Condition<S> isNotEmpty(final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size > 0)
                .name("isNotEmpty")
                .expected(Operators.GT.display(LENGTH, 0))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence has the supplied length.
     *
     * @param ops
     *         The sequence operations to use.
     * @param length
     *         The desired length.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence has the supplied length.
     */
    public static <S> Condition<S> hasLengthOf(final int length, final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size == length)
                .name("hasLengthOf[" + length + "]")
                .expected(Operators.EQ.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence does not have the supplied
     * length.
     *
     * @param ops
     *         The sequence operations to use.
     * @param length
     *         The desired length.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence does not have the supplied
     * length.
     */
    public static <S> Condition<S> doesNotHaveLengthOf(final int length, final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size != length)
                .name(withValue("doesNotHaveLengthOf", length))
                .expected(Operators.NE.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence is shorter than the
     * supplied length.
     *
     * @param ops
     *         The sequence operations to use.
     * @param length
     *         The desired length.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence is shorter than the
     * supplied length.
     */
    public static <S> Condition<S> isShorterThan(final int length, final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size < length)
                .name(withValue("isShorterThan", length))
                .expected(Operators.LT.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence is shorter than or equal to
     * the supplied length.
     *
     * @param ops
     *         The sequence operations to use.
     * @param length
     *         The desired length.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence is shorter than or equal to
     * the supplied length.
     */
    public static <S> Condition<S> isShorterThanOrEqualTo(final int length, final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size <= length)
                .name(withValue("isShorterThanOrEqualTo", length))
                .expected(Operators.LTE.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence is longer than the supplied
     * length.
     *
     * @param ops
     *         The sequence operations to use.
     * @param length
     *         The desired length.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence is longer than the supplied
     * length.
     */
    public static <S> Condition<S> isLongerThan(final int length, final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size > length)
                .name(withValue("isLongerThan", length))
                .expected(Operators.GT.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence is longer than or equal to
     * than the supplied length.
     *
     * @param ops
     *         The sequence operations to use.
     * @param length
     *         The desired length.
     * @param <S>
     *         The type of the sequence.
     *
     * @return A condition that checks if a sequence is longer than or equal to
     * than the supplied length.
     */
    public static <S> Condition<S> isLongerThanOrEqualTo(final int length, final SequenceOperations<?, S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size >= length)
                .name(withValue("isLongerThanOrEqualTo", length))
                .expected(Operators.GTE.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * element.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired element.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * element.
     */
    public static <T extends @Nullable Object, S> Condition<S> includes(final @Nullable T desired, final SequenceOperations<T, S> ops) {
        return ConditionBuilder.of((S s) -> ops.includes(s, desired))
                .name(StandardRationales.includes(desired))
                .expected(StandardRationales.includes(desired))
                .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * elements.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * elements.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesAll(
            final @Nullable Collection<? extends @Nullable T> desired,
            final SequenceOperations<T, S> ops
    ) {

        if (desired == null) {
            return passesForNullDesired(
                    "includesAll", "All desired elements in subject",
                    "All desired elements in subject"
            );
        }

        return ConditionBuilder.observing((S s) -> ops.findAllIn(s, desired))
                .passesIf(found -> found.size() == desired.size())
                .name("includesAll")
                .expected("All desired elements in subject")
                .actual(context ->
                        context.passed() ?
                                "All desired elements in subject" :
                                "At least one desired element missing in subject"
                )
                .hint(context ->
                        context.passed() ?
                                "All desired elements found in subject" :
                                "The following desired elements were not found: " + computeMissing(desired, context.observed())
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes any of the
     * supplied elements.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes any of the
     * supplied elements.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesAny(final @Nullable Collection<? extends @Nullable T> desired, final SequenceOperations<T, S> ops) {

        if (desired == null) {
            return passesForNullDesired(
                    "includesAny", "Any desired elements in subject",
                    "At least one desired element found in subject"
            );
        }

        return ConditionBuilder.observing((S s) -> ops.findAllIn(s, desired))
                .passesIf(found -> !found.isEmpty())
                .name("includesAny")
                .expected("Any desired elements in subject")
                .actual(context ->
                        context.passed() ?
                                "At least one desired element found in subject" :
                                "All desired elements missing in subject"
                )
                .hint(context ->
                        context.passed() ?
                                "At least one desired element found in subject: " + stringifyAll(context.observed()) :
                                "All of the desired elements are missing in subject"
                )
                .build();
    }

    private static <T extends @Nullable Object, S> Condition<S> passesForNullDesired(
            final String name, final String expected,
            final String actual
    ) {
        return ConditionBuilder.of((S s) -> true)
                .nullable()
                .name(name)
                .expected(expected)
                .actual(actual)
                .hint(NULL_DESIRED_HINT)
                .build();
    }

    private static <T extends @Nullable Object> List<String> computeMissing(final Collection<? extends @Nullable T> desired, final List<T> found) {

        final boolean truncated = desired.size() > Configuration.MAX_LIST_DISPLAY_LENGTH;

        final List<String> missing = desired.stream()
                .filter(d -> !found.contains(d))
                .limit(Configuration.MAX_LIST_DISPLAY_LENGTH)
                .map(ObjectFormatter::stringify)
                .collect(toList());

        if (truncated) {
            missing.add("...");
        }

        return missing;
    }

    private static <T extends @Nullable Object> List<String> stringifyAll(final List<T> elements) {
        return elements.stream()
                .map(ObjectFormatter::stringify)
                .toList();
    }

    /**
     * Creates a condition that checks if a sequence excludes the supplied
     * element.
     *
     * @param ops
     *         The sequence operations to use.
     * @param undesired
     *         The undesired element.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence excludes the supplied
     * element.
     */
    public static <T extends @Nullable Object, S> Condition<S> excludes(final @Nullable T undesired, final SequenceOperations<T, S> ops) {
        return ConditionBuilder.of((S s) -> !ops.includes(s, undesired))
                .name(StandardRationales.excludes(undesired))
                .expected(StandardRationales.excludes(undesired))
                .actual(context -> context.passed() ? StandardRationales.excludes(undesired) : StandardRationales.includes(undesired))
                .build();
    }

    /**
     * Creates a condition that checks if a sequence excludes the supplied
     * elements.
     *
     * @param ops
     *         The sequence operations to use.
     * @param undesired
     *         The undesired elements.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence excludes the supplied
     * elements.
     */
    public static <T extends @Nullable Object, S> Condition<S> excludesAll(final @Nullable Collection<? extends @Nullable T> undesired, final SequenceOperations<T, S> ops) {

        if (undesired == null) {
            return passesForNullDesired(
                    "excludesAll", "All desired elements missing in subject",
                    "All desired elements missing in subject"
            );
        }

        return ConditionBuilder.observing((S s) -> ops.findAllIn(s, undesired))
                .passesIf(List::isEmpty)
                .name("excludesAll")
                .expected("All desired elements missing in subject")
                .actual(context ->
                        context.passed() ?
                                "All desired elements missing in subject" :
                                "At least one desired element found in subject"
                )
                .hint(context ->
                        context.passed() ?
                                "All of the desired elements are missing in subject" :
                                "At least one desired element found in subject: " + stringifyAll(context.observed())
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence excludes any of the
     * supplied elements.
     *
     * @param ops
     *         The sequence operations to use.
     * @param undesired
     *         The undesired elements.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence excludes any of the
     * supplied elements.
     */
    public static <T extends @Nullable Object, S> Condition<S> excludesAny(final @Nullable Collection<? extends @Nullable T> undesired, final SequenceOperations<T, S> ops) {

        if (undesired == null) {
            return passesForNullDesired(
                    "excludesAny", "At least one desired element missing in subject",
                    "At least one desired element missing in subject"
            );
        }

        return ConditionBuilder.observing((S s) -> ops.findAllIn(s, undesired))
                .passesIf(found -> found.size() < undesired.size())
                .name("excludesAny")
                .expected("At least one desired element missing in subject")
                .actual(context ->
                        context.passed() ?
                                "At least one desired element missing in subject" :
                                "All desired elements found in subject"
                )
                .hint(context ->
                        context.passed() ?
                                "The following desired elements were found: " + stringifyAll(context.observed()) :
                                "All desired elements found in subject"
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * elements the supplied number of times.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param times
     *         The desired number of occurrences.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * elements the supplied number of times.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesExactly(final @Nullable T desired, final long times, final SequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.occurrences(s, desired))
                .passesIf(count -> count == times)
                .name(withPairs("includesExactly", "desired", desiredAsString, "times", times))
                .expected("found '" + desiredAsString + "' " + times + " " + timesSuffix(times))
                .actual(context ->
                        "found '" + desiredAsString + "' " + context.observed() + " " + timesSuffix(context.observed())
                )
                .hint(context ->
                        context.observed() == 0 ?
                                "Desired value not found in subject" :
                                "Desired value was found, but " + context.observed() + " " + timesSuffix(context.observed())
                )
                .build();
    }

    private static String timesSuffix(final long times) {
        return times == 1 ? "time" : "times";
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * elements more than the supplied number of times.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param times
     *         The lower bound of occurrences.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * elements more than the supplied number of times.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesMoreThan(final @Nullable T desired, final long times, final SequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.occurrences(s, desired))
                .passesIf(count -> count > times)
                .name(withPairs("includesMoreThan", "desired", desiredAsString, "times", times))
                .expected("found '" + desiredAsString + "' more than " + times + " " + timesSuffix(times))
                .actual(context ->
                        "found '" + desiredAsString + "' " + context.observed() + " " + timesSuffix(context.observed())
                )
                .hint(context ->
                        context.observed() == 0 ?
                                "Desired value not found in subject" :
                                "Desired value was found, but " + context.observed() + " " + timesSuffix(context.observed())
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * elements more than or equal to the supplied number of times.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param times
     *         The lower bound of occurrences.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * elements more than or equal to the supplied number of times.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesMoreThanOrEqualTo(final @Nullable T desired, final long times, final SequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.occurrences(s, desired))
                .passesIf(count -> count >= times)
                .name(withPairs("includesMoreThanOrEqualTo", "desired", desiredAsString, "times", times))
                .expected("found '" + desiredAsString + "' more than or equal to " + times + " " + timesSuffix(times))
                .actual(context ->
                        "found '" + desiredAsString + "' " + context.observed() + " " + timesSuffix(context.observed())
                )
                .hint(context ->
                        context.observed() == 0 ?
                                "Desired value not found in subject" :
                                "Desired value was found, but " + context.observed() + " " + timesSuffix(context.observed())
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * elements less than the supplied number of times.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param times
     *         The lower upper of occurrences.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * elements less than the supplied number of times.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesLessThan(final @Nullable T desired, final long times, final SequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.occurrences(s, desired))
                .passesIf(count -> count < times)
                .name(withPairs("includesLessThan", "desired", desiredAsString, "times", times))
                .expected("found '" + desiredAsString + "' less than " + times + " " + timesSuffix(times))
                .actual(context ->
                        "found '" + desiredAsString + "' " + context.observed() + " " + timesSuffix(context.observed())
                )
                .hint(context ->
                        context.observed() == 0 ?
                                "Desired value not found in subject" :
                                "Desired value was found, but " + context.observed() + " " + timesSuffix(context.observed())
                )
                .build();
    }

    /**
     * Creates a condition that checks if a sequence includes the supplied
     * elements less than or equal to the supplied number of times.
     *
     * @param ops
     *         The sequence operations to use.
     * @param desired
     *         The desired elements.
     * @param times
     *         The lower upper of occurrences.
     * @param <S>
     *         The type of the sequence.
     * @param <T>
     *         The type of the elements in the sequence.
     *
     * @return A condition that checks if a sequence includes the supplied
     * elements less than or equal to the supplied number of times.
     */
    public static <T extends @Nullable Object, S> Condition<S> includesLessThanOrEqualTo(final @Nullable T desired, final long times, final SequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.occurrences(s, desired))
                .passesIf(count -> count <= times)
                .name(withPairs("includesLessThanOrEqualTo", "desired", desiredAsString, "times", times))
                .expected("found '" + desiredAsString + "' less than or equal to " + times + " " + timesSuffix(times))
                .actual(context ->
                        "found '" + desiredAsString + "' " + context.observed() + " " + timesSuffix(context.observed())
                )
                .hint(context ->
                        context.observed() == 0 ?
                                "Desired value not found in subject" :
                                "Desired value was found, but " + context.observed() + " " + timesSuffix(context.observed())
                )
                .build();
    }
}
