package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;

public class RepeatableSequenceExpressions {

    public static <T, S> Condition<S> includesExactly(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

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

    public static <T, S> Condition<S> includesMoreThan(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

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

    public static <T, S> Condition<S> includesMoreThanOrEqualTo(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

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

    public static <T, S> Condition<S> includesLessThan(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

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

    public static <T, S> Condition<S> includesLessThanOrEqualTo(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

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

    public interface RepeatableSequenceOperations<T, S> {
        int occurrences(S haystack, T needle);
    }
}
