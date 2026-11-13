package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.logic.unary.Observed;
import org.jspecify.annotations.Nullable;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

final class MapConditions {

    private static final int MAX_LIST_DISPLAY_LENGTH = 20;
    private static final String CONDITION = "condition";

    private MapConditions() {
    }

    record Noun(String singular, String plural) {

        static final Noun KEY = new Noun("key", "keys");
        static final Noun VALUE = new Noun("value", "values");
        static final Noun ENTRY = new Noun("entry", "entries");
    }

    // -------------------------------------------------------------------------
    // Membership
    // -------------------------------------------------------------------------

    static boolean containsKey(final Map<?, ?> map, final @Nullable Object key) {

        try {
            return map.containsKey(key);
        }
        catch (final NullPointerException e) {
            // Some maps do not permit null keys and throw when queried with
            // one, in which case the null key is considered "not found"
            if (key == null) {
                return false;
            }

            throw e;
        }
    }

    static boolean containsValue(final Map<?, ?> map, final @Nullable Object value) {

        try {
            return map.containsValue(value);
        }
        catch (final NullPointerException e) {
            // Some maps do not permit null values and throw when queried with
            // one, in which case the null value is considered "not found"
            if (value == null) {
                return false;
            }

            throw e;
        }
    }

    static <K extends @Nullable Object, V extends @Nullable Object> Observed<V> lookup(final Map<K, V> map, final K key) {

        if (!containsKey(map, key)) {
            return Observed.missing();
        }

        return found(map.get(key));
    }

    // The value is only null when the key maps to null, which requires V to be nullable
    @SuppressWarnings("NullAway")
    private static <V extends @Nullable Object> Observed<V> found(final @Nullable V value) {
        return Observed.found(value);
    }

    static <K extends @Nullable Object, V extends @Nullable Object> boolean containsEntry(
            final Map<K, V> map,
            final Map.Entry<? extends K, ? extends V> entry
    ) {
        return lookup(map, entry.getKey()).matches(entry.getValue());
    }

    // -------------------------------------------------------------------------
    // Single elements
    // -------------------------------------------------------------------------

    static <M, X extends @Nullable Object> Condition<M> includes(
            final Noun noun,
            final X desired,
            final BiPredicate<M, X> contains
    ) {

        final String includes = withValue("includes" + capitalize(noun.singular()), desired);
        final String excludes = withValue("excludes" + capitalize(noun.singular()), desired);

        return ConditionBuilder.of((M m) -> contains.test(m, desired))
                .name(includes)
                .expected(includes)
                .actual(context -> context.ifPassedOrElse(includes, excludes))
                .build();
    }

    static <M, X extends @Nullable Object> Condition<M> excludes(
            final Noun noun,
            final X undesired,
            final BiPredicate<M, X> contains
    ) {

        final String includes = withValue("includes" + capitalize(noun.singular()), undesired);
        final String excludes = withValue("excludes" + capitalize(noun.singular()), undesired);

        return ConditionBuilder.of((M m) -> !contains.test(m, undesired))
                .name(excludes)
                .expected(excludes)
                .actual(context -> context.ifPassedOrElse(excludes, includes))
                .build();
    }

    private static String capitalize(final String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    // -------------------------------------------------------------------------
    // Multiple elements
    // -------------------------------------------------------------------------

    static <M, X extends @Nullable Object> Condition<M> includesAll(
            final Noun noun,
            final @Nullable Collection<? extends X> desired,
            final BiPredicate<M, X> contains
    ) {

        final String name = "includesAll" + capitalize(noun.plural());
        final String expected = "All desired " + noun.plural() + " in subject";

        if (desired == null) {
            return passesForNullDesired(noun, name, expected);
        }

        return ConditionBuilder.observing((M m) -> filter(desired, d -> !contains.test(m, d)))
                .passesIf(List::isEmpty)
                .name(name)
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        expected,
                        "At least one desired " + noun.singular() + " missing in subject"
                ))
                .hint(context -> context.passed() ?
                        "All desired " + noun.plural() + " found in subject" :
                        "The following desired " + noun.plural() + " were not found: " + display(context.observed())
                )
                .build();
    }

    static <M, X extends @Nullable Object> Condition<M> includesAny(
            final Noun noun,
            final @Nullable Collection<? extends X> desired,
            final BiPredicate<M, X> contains
    ) {

        final String name = "includesAny" + capitalize(noun.plural());
        final String expected = "Any desired " + noun.plural() + " in subject";

        if (desired == null) {
            return passesForNullDesired(noun, name, expected);
        }

        return ConditionBuilder.observing((M m) -> filter(desired, d -> contains.test(m, d)))
                .passesIf(found -> !found.isEmpty())
                .name(name)
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        "At least one desired " + noun.singular() + " found in subject",
                        "All desired " + noun.plural() + " missing in subject"
                ))
                .hint(context -> context.passed() ?
                        "The following desired " + noun.plural() + " were found: " + display(context.observed()) :
                        "All of the desired " + noun.plural() + " are missing in subject"
                )
                .build();
    }

    static <M, X extends @Nullable Object> Condition<M> excludesAll(
            final Noun noun,
            final @Nullable Collection<? extends X> undesired,
            final BiPredicate<M, X> contains
    ) {

        final String name = "excludesAll" + capitalize(noun.plural());
        final String expected = "All desired " + noun.plural() + " missing in subject";

        if (undesired == null) {
            return passesForNullDesired(noun, name, expected);
        }

        return ConditionBuilder.observing((M m) -> filter(undesired, d -> contains.test(m, d)))
                .passesIf(List::isEmpty)
                .name(name)
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        expected,
                        "At least one desired " + noun.singular() + " found in subject"
                ))
                .hint(context -> context.passed() ?
                        "All of the desired " + noun.plural() + " are missing in subject" :
                        "The following desired " + noun.plural() + " were found: " + display(context.observed())
                )
                .build();
    }

    static <M, X extends @Nullable Object> Condition<M> excludesAny(
            final Noun noun,
            final @Nullable Collection<? extends X> undesired,
            final BiPredicate<M, X> contains
    ) {

        final String name = "excludesAny" + capitalize(noun.plural());
        final String expected = "At least one desired " + noun.singular() + " missing in subject";

        if (undesired == null) {
            return passesForNullDesired(noun, name, expected);
        }

        return ConditionBuilder.observing((M m) -> filter(undesired, d -> !contains.test(m, d)))
                .passesIf(missing -> !missing.isEmpty())
                .name(name)
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        expected,
                        "All desired " + noun.plural() + " found in subject"
                ))
                .hint(context -> context.passed() ?
                        "The following desired " + noun.plural() + " were not found: " + display(context.observed()) :
                        "All desired " + noun.plural() + " found in subject"
                )
                .build();
    }

    private static <M> Condition<M> passesForNullDesired(final Noun noun, final String name, final String expected) {
        return ConditionBuilder.of((M m) -> true)
                .nullable()
                .name(name)
                .expected(expected)
                .actual(expected)
                .hint("Desired " + noun.plural() + " is (null), which passes by default")
                .build();
    }

    private static <X extends @Nullable Object> List<X> filter(
            final Collection<? extends X> elements,
            final Predicate<X> test
    ) {

        final List<X> matching = new ArrayList<>();

        for (final X element : elements) {
            if (test.test(element)) {
                matching.add(element);
            }
        }

        return matching;
    }

    private static List<String> display(final Collection<?> elements) {

        final List<String> displayed = elements.stream()
                .limit(MAX_LIST_DISPLAY_LENGTH)
                .map(ObjectFormatter::stringify)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        if (elements.size() > MAX_LIST_DISPLAY_LENGTH) {
            displayed.add("...");
        }

        return displayed;
    }

    // -------------------------------------------------------------------------
    // Entries
    // -------------------------------------------------------------------------

    static <K extends @Nullable Object, V extends @Nullable Object> Condition<Map<K, V>> includesEntry(
            final K key,
            final V value
    ) {

        final String entry = entryDisplay(key, value);

        return ConditionBuilder.observing((Map<K, V> m) -> lookup(m, key))
                .passesIf(observed -> observed.matches(value))
                .name(withPairs("includesEntry", "key", key, "value", value))
                .expected(entry)
                .actual(context -> lookupDisplay(key, context.observed()))
                .hint(context -> context.passed() ?
                        "Subject includes " + entry :
                        lookupHint(key, context.observed())
                )
                .build();
    }

    static <K extends @Nullable Object, V extends @Nullable Object> Condition<Map<K, V>> excludesEntry(
            final K key,
            final V value
    ) {

        final String entry = entryDisplay(key, value);

        return ConditionBuilder.observing((Map<K, V> m) -> lookup(m, key))
                .passesIf(observed -> !observed.matches(value))
                .name(withPairs("excludesEntry", "key", key, "value", value))
                .expected("not[" + entry + "]")
                .actual(context -> lookupDisplay(key, context.observed()))
                .hint(context -> context.passed() ?
                        lookupHint(key, context.observed()) :
                        "Subject includes " + entry
                )
                .build();
    }

    static <K extends @Nullable Object, V extends @Nullable Object> Condition<Map<K, V>> hasValueMatching(
            final K key,
            final Predicate<? super V> predicate
    ) {

        Objects.requireNonNull(predicate);

        final String satisfied = "value for key " + quote(key) + " satisfies " + CONDITION;

        return ConditionBuilder.observing((Map<K, V> m) -> lookup(m, key))
                .passesIf(observed -> observed.ifPresentOrElseGet(predicate::test, () -> false))
                .name(withValue("hasValueMatching", key))
                .expected(satisfied)
                .actual(context -> context.observed().ifPresentOrElseGet(
                        v -> context.ifPassedOrElse(
                                satisfied,
                                "value for key " + quote(key) + " does not satisfy " + CONDITION
                        ),
                        () -> missingKey(key)
                ))
                .hint(context -> lookupHint(key, context.observed()))
                .build();
    }

    private static String entryDisplay(final @Nullable Object key, final @Nullable Object value) {
        return "entry[" + stringify(key) + "=" + stringify(value) + "]";
    }

    private static <V extends @Nullable Object> String lookupDisplay(final @Nullable Object key, final Observed<V> observed) {
        return observed.ifPresentOrElseGet(v -> entryDisplay(key, v), () -> missingKey(key));
    }

    private static <V extends @Nullable Object> String lookupHint(final @Nullable Object key, final Observed<V> observed) {
        return observed.ifPresentOrElseGet(
                v -> "Key " + quote(key) + " maps to " + quote(v),
                () -> "Key " + quote(key) + " not found in subject"
        );
    }

    private static String missingKey(final @Nullable Object key) {
        return "no entry for key " + quote(key);
    }

    private static String quote(final @Nullable Object o) {
        return "'" + stringify(o) + "'";
    }

    static <K extends @Nullable Object, V extends @Nullable Object> Condition<Map<K, V>> hasKeysExactly(
            final @Nullable Collection<? extends K> desired
    ) {

        if (desired == null) {
            return ConditionBuilder.of((Map<K, V> m) -> false)
                    .name("hasKeysExactly[(null)]")
                    .expected("Always fail: cannot compare to (null) keys")
                    .actual(context -> "keys = " + display(context.observed().keySet()))
                    .hint("Desired keys is (null), which fails by default")
                    .build();
        }

        final LinkedHashSet<? extends K> distinct = new LinkedHashSet<>(desired);
        final String expected = "keys = " + display(distinct);

        return ConditionBuilder.observing((Map<K, V> m) -> KeyDifference.of(m, distinct))
                .passesIf(KeyDifference::isEmpty)
                .name("hasKeysExactly" + display(distinct))
                .expected(expected)
                .actual(context -> "keys = " + display(context.observed().actual()))
                .hint(context -> context.passed() ?
                        "Subject has exactly the desired keys" :
                        "Missing keys: " + display(context.observed().missing())
                                + ", unexpected keys: " + display(context.observed().unexpected())
                )
                .build();
    }

    private record KeyDifference(List<?> actual, List<?> missing, List<?> unexpected, boolean sizeMatches) {

        static <K extends @Nullable Object> KeyDifference of(final Map<K, ?> map, final Collection<? extends K> desired) {

            final List<K> actual = new ArrayList<>(map.keySet());
            final List<K> missing = filter(desired, d -> !containsKey(map, d));
            final List<K> unexpected = filter(actual, k -> !desired.contains(k));

            return new KeyDifference(actual, missing, unexpected, map.size() == desired.size());
        }

        // Membership uses the map's own lookup (such as the comparator of a
        // sorted map), so all desired keys being present with matching sizes is
        // sufficient for an exact match
        boolean isEmpty() {
            return missing.isEmpty() && sizeMatches;
        }
    }

    // -------------------------------------------------------------------------
    // Value occurrences
    // -------------------------------------------------------------------------

    static <K extends @Nullable Object, V extends @Nullable Object> Condition<Map<K, V>> valueOccurrences(
            final String name,
            final String qualifier,
            final V desired,
            final long times,
            final LongPredicate test
    ) {

        final String desiredAsString = stringify(desired);

        return ConditionBuilder.observing((Map<K, V> m) -> occurrences(m, desired))
                .passesIf(test::test)
                .name(withPairs(name, "desired", desiredAsString, "times", times))
                .expected("found '" + desiredAsString + "' " + qualifier + times + " " + timesSuffix(times))
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

    private static long occurrences(final Map<?, ?> map, final @Nullable Object desired) {
        return map.values()
                .stream()
                .filter(v -> Objects.equals(v, desired))
                .count();
    }

    private static String timesSuffix(final long times) {
        return times == 1 ? "time" : "times";
    }

    // -------------------------------------------------------------------------
    // Predicates over all elements
    // -------------------------------------------------------------------------

    static <M, X extends @Nullable Object> Condition<M> allSatisfy(
            final Noun noun,
            final Function<M, Collection<X>> elements,
            final Predicate<? super X> predicate
    ) {

        Objects.requireNonNull(predicate);

        final String expected = "All " + noun.plural() + " satisfy " + CONDITION;

        return ConditionBuilder.observing((M m) -> filter(elements.apply(m), x -> !predicate.test(x)))
                .passesIf(List::isEmpty)
                .name("all" + capitalize(noun.plural()) + "Satisfy")
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        expected,
                        "At least one " + noun.singular() + " does not satisfy " + CONDITION
                ))
                .hint(context -> context.passed() ?
                        expected :
                        "The following " + noun.plural() + " do not satisfy " + CONDITION + ": " + display(context.observed())
                )
                .build();
    }

    static <M, X extends @Nullable Object> Condition<M> anySatisfies(
            final Noun noun,
            final Function<M, Collection<X>> elements,
            final Predicate<? super X> predicate
    ) {

        Objects.requireNonNull(predicate);

        final String expected = "At least one " + noun.singular() + " satisfies " + CONDITION;

        return ConditionBuilder.observing((M m) -> filter(elements.apply(m), predicate::test))
                .passesIf(matching -> !matching.isEmpty())
                .name("any" + capitalize(noun.singular()) + "Satisfies")
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        expected,
                        "No " + noun.plural() + " satisfy " + CONDITION
                ))
                .hint(context -> context.passed() ?
                        "The following " + noun.plural() + " satisfy " + CONDITION + ": " + display(context.observed()) :
                        "No " + noun.plural() + " satisfy " + CONDITION
                )
                .build();
    }

    static <M, X extends @Nullable Object> Condition<M> noneSatisfy(
            final Noun noun,
            final Function<M, Collection<X>> elements,
            final Predicate<? super X> predicate
    ) {

        Objects.requireNonNull(predicate);

        final String expected = "No " + noun.plural() + " satisfy " + CONDITION;

        return ConditionBuilder.observing((M m) -> filter(elements.apply(m), predicate::test))
                .passesIf(List::isEmpty)
                .name("no" + capitalize(noun.singular()) + "Satisfies")
                .expected(expected)
                .actual(context -> context.ifPassedOrElse(
                        expected,
                        "At least one " + noun.singular() + " satisfies " + CONDITION
                ))
                .hint(context -> context.passed() ?
                        expected :
                        "The following " + noun.plural() + " satisfy " + CONDITION + ": " + display(context.observed())
                )
                .build();
    }

    static <K extends @Nullable Object, V extends @Nullable Object> Collection<Map.Entry<K, V>> entriesOf(final Map<K, V> map) {

        // Entries are copied, since some maps reuse or mutate entry objects
        // during iteration
        final List<Map.Entry<K, V>> entries = new ArrayList<>(map.size());

        for (final Map.Entry<K, V> entry : map.entrySet()) {
            entries.add(new SimpleImmutableEntry<>(entry));
        }

        return entries;
    }
}
