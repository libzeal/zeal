package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.values.config.Configuration;
import io.github.libzeal.zeal.values.sdk.StandardRationales;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InspectableSequenceExpressions {

    private static final String NULL_DESIRED_HINT = "Desired elements is (null), which passes by default";

    public interface InspectableSequenceOperations<T, S> {

        default List<T> findAllIn(final S haystack, final Collection<T> needles) {
            return needles.stream()
                    .filter(needle -> includes(haystack, needle))
                    .collect(toList());
        }

        boolean includes(S haystack, T needle);
    }

    public static <T, S> Condition<S> includes(final T desired, final InspectableSequenceOperations<T, S> ops) {
        return ConditionBuilder.of((S s) -> ops.includes(s, desired))
                .name(StandardRationales.includes(desired))
                .expected(StandardRationales.includes(desired))
                .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
                .build();
    }

    public static <T, S> Condition<S> includesAll(
            final Collection<T> desired,
            final InspectableSequenceOperations<T, S> ops
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

    public static <T, S> Condition<S> includesAny(final Collection<T> desired, final InspectableSequenceOperations<T, S> ops) {

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

    private static <T, S> Condition<S> passesForNullDesired(
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

    private static <T> List<String> computeMissing(final Collection<T> desired, final List<T> found) {

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

    private static <T> List<String> stringifyAll(final List<T> elements) {
        return elements.stream()
                .map(ObjectFormatter::stringify)
                .toList();
    }

    public static <T, S> Condition<S> excludes(final T desired, final InspectableSequenceOperations<T, S> ops) {
        return ConditionBuilder.of((S s) -> !ops.includes(s, desired))
                .name(StandardRationales.excludes(desired))
                .expected(StandardRationales.excludes(desired))
                .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired))
                .build();
    }

    public static <T, S> Condition<S> excludesAll(final Collection<T> desired, final InspectableSequenceOperations<T, S> ops) {

        if (desired == null) {
            return passesForNullDesired(
                    "excludesAll", "All desired elements missing in subject",
                    "All desired elements missing in subject"
            );
        }

        return ConditionBuilder.observing((S s) -> ops.findAllIn(s, desired))
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

    public static <T, S> Condition<S> excludesAny(final Collection<T> desired, final InspectableSequenceOperations<T, S> ops) {

        if (desired == null) {
            return passesForNullDesired(
                    "excludesAny", "At least one desired element missing in subject",
                    "At least one desired element missing in subject"
            );
        }

        return ConditionBuilder.observing((S s) -> ops.findAllIn(s, desired))
                .passesIf(found -> found.size() < desired.size())
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
}
