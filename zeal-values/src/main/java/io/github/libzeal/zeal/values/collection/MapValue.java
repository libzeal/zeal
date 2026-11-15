package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.MapConditions.Noun;
import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.SizeConditions;
import io.github.libzeal.zeal.values.sdk.sequence.SizedValue;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A value used to evaluate {@link Map}s.
 * <p>
 * Keys are looked up using the subject's {@link Map#containsKey(Object)} and
 * {@link Map#get(Object)}, ensuring key checks follow the subject's semantics
 * (such as the comparator of a {@link SortedMap} or the identity comparison of
 * an {@link IdentityHashMap}). Value checks use
 * {@link Map#containsValue(Object)}, while value counts and entry values are
 * compared using {@link Objects#equals(Object, Object)}.
 * <p>
 * If the subject does not permit {@code null} keys or values (such as a
 * {@link Map} created through {@link Map#of()}), a {@code null} key or value is
 * treated as not found. Any other exception thrown by the subject or by a
 * supplied predicate is propagated when the value is evaluated.
 *
 * @param <K>
 *         The type of the keys in the {@link Map}.
 * @param <V>
 *         The type of the values in the {@link Map}.
 *
 * @author Justin Albano
 */
public final class MapValue<K extends @Nullable Object, V extends @Nullable Object>
        extends BaseValue<Map<K, V>, MapValue<K, V>>
        implements SizedValue<MapValue<K, V>> {

    private final BiPredicate<Map<K, V>, K> containsKey = MapConditions::containsKey;
    private final BiPredicate<Map<K, V>, V> containsValue = MapConditions::containsValue;
    private final BiPredicate<Map<K, V>, Map.Entry<? extends K, ? extends V>> containsEntry = MapConditions::containsEntry;

    /**
     * Creates a Map value from the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public MapValue(final @Nullable Map<K, V> subject) {
        super(subject, "Map value");
    }

    // -------------------------------------------------------------------------
    // Size
    // -------------------------------------------------------------------------

    @Override
    public MapValue<K, V> isEmpty() {
        return append(SizeConditions.isEmpty(Map::size));
    }

    @Override
    public MapValue<K, V> isNotEmpty() {
        return append(SizeConditions.isNotEmpty(Map::size));
    }

    @Override
    public MapValue<K, V> hasSizeOf(final int size) {
        return append(SizeConditions.hasSizeOf(size, Map::size));
    }

    @Override
    public MapValue<K, V> doesNotHaveSizeOf(final int size) {
        return append(SizeConditions.doesNotHaveSizeOf(size, Map::size));
    }

    @Override
    public MapValue<K, V> isLongerThan(final int size) {
        return append(SizeConditions.isLongerThan(size, Map::size));
    }

    @Override
    public MapValue<K, V> isLongerThanOrEqualTo(final int size) {
        return append(SizeConditions.isLongerThanOrEqualTo(size, Map::size));
    }

    @Override
    public MapValue<K, V> isShorterThan(final int size) {
        return append(SizeConditions.isShorterThan(size, Map::size));
    }

    @Override
    public MapValue<K, V> isShorterThanOrEqualTo(final int size) {
        return append(SizeConditions.isShorterThanOrEqualTo(size, Map::size));
    }

    // -------------------------------------------------------------------------
    // Keys
    // -------------------------------------------------------------------------

    /**
     * Checks if the value includes the supplied key.
     *
     * @param desired
     *         The desired key.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesKey(final K desired) {
        return append(MapConditions.includes(Noun.KEY, desired, containsKey));
    }

    /**
     * Checks if the value excludes the supplied key.
     *
     * @param undesired
     *         The undesired key.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesKey(final K undesired) {
        return append(MapConditions.excludes(Noun.KEY, undesired, containsKey));
    }

    /**
     * Checks if the value includes all the supplied keys. If no keys are
     * supplied or the supplied keys are {@code null}, the check passes.
     * <p>
     * The supplied keys can be a subset of the keys in the subject. For
     * example, if the subject has the keys {@code [a, b]} and the supplied keys
     * are {@code [b]}, the check will pass since the subject's keys contain at
     * least {@code b}. This differs from {@link #hasKeysExactly(Collection)},
     * which must match the keys exactly.
     *
     * @param desired
     *         The desired keys.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesAllKeys(final @Nullable Collection<? extends K> desired) {
        return append(MapConditions.includesAll(Noun.KEY, desired, containsKey));
    }

    /**
     * Checks if the value includes all the supplied keys. If no keys are
     * supplied, the check passes.
     * <p>
     * The supplied keys can be a subset of the keys in the subject. For
     * example, if the subject has the keys {@code [a, b]} and the supplied keys
     * are {@code [b]}, the check will pass since the subject's keys contain at
     * least {@code b}. This differs from {@link #hasKeysExactlyOf(Object[])},
     * which must match the keys exactly.
     *
     * @param desired
     *         The desired keys.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> includesAllKeysOf(final K... desired) {
        return includesAllKeys(Arrays.asList(desired));
    }

    /**
     * Checks if the value includes any of the supplied keys. If no keys are
     * supplied, the check fails. If the supplied keys are {@code null}, the
     * check passes.
     *
     * @param desired
     *         The desired keys.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesAnyKeys(final @Nullable Collection<? extends K> desired) {
        return append(MapConditions.includesAny(Noun.KEY, desired, containsKey));
    }

    /**
     * Checks if the value includes any of the supplied keys. If no keys are
     * supplied, the check fails.
     *
     * @param desired
     *         The desired keys.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> includesAnyKeysOf(final K... desired) {
        return includesAnyKeys(Arrays.asList(desired));
    }

    /**
     * Checks if the value excludes all the supplied keys (none of the supplied
     * keys are present). If no keys are supplied or the supplied keys are
     * {@code null}, the check passes.
     *
     * @param undesired
     *         The undesired keys.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesAllKeys(final @Nullable Collection<? extends K> undesired) {
        return append(MapConditions.excludesAll(Noun.KEY, undesired, containsKey));
    }

    /**
     * Checks if the value excludes all the supplied keys (none of the supplied
     * keys are present). If no keys are supplied, the check passes.
     *
     * @param undesired
     *         The undesired keys.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> excludesAllKeysOf(final K... undesired) {
        return excludesAllKeys(Arrays.asList(undesired));
    }

    /**
     * Checks if the value excludes any of the supplied keys (at least one of
     * the supplied keys is missing). If no keys are supplied, the check fails.
     * If the supplied keys are {@code null}, the check passes.
     *
     * @param undesired
     *         The undesired keys.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesAnyKeys(final @Nullable Collection<? extends K> undesired) {
        return append(MapConditions.excludesAny(Noun.KEY, undesired, containsKey));
    }

    /**
     * Checks if the value excludes any of the supplied keys (at least one of
     * the supplied keys is missing). If no keys are supplied, the check fails.
     *
     * @param undesired
     *         The undesired keys.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> excludesAnyKeysOf(final K... undesired) {
        return excludesAnyKeys(Arrays.asList(undesired));
    }

    /**
     * Checks if the value has exactly the supplied keys: every supplied key is
     * present and the subject has no other keys. The order of the supplied keys
     * does not matter. Unlike {@link #includesAllKeys(Collection)}, this check
     * fails if the supplied keys are {@code null}.
     * <p>
     * Duplicate supplied keys are removed using {@link Object#equals(Object)}
     * before comparing against the size of the subject. For subjects with
     * custom key semantics (such as a case-insensitive {@link TreeMap}),
     * supplying keys that are distinct by {@code equals} but equivalent in the
     * subject (such as {@code a} and {@code A}) will cause the check to fail.
     *
     * @param desired
     *         The desired keys.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> hasKeysExactly(final @Nullable Collection<? extends K> desired) {
        return append(MapConditions.hasKeysExactly(desired));
    }

    /**
     * Checks if the value has exactly the supplied keys. See
     * {@link #hasKeysExactly(Collection)} for details.
     *
     * @param desired
     *         The desired keys.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> hasKeysExactlyOf(final K... desired) {
        return hasKeysExactly(Arrays.asList(desired));
    }

    // -------------------------------------------------------------------------
    // Values
    // -------------------------------------------------------------------------

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value.
     *
     * @param desired
     *         The desired value.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValue(final V desired) {
        return append(MapConditions.includes(Noun.VALUE, desired, containsValue));
    }

    /**
     * Checks if the {@link Map} value (subject) excludes the supplied value.
     *
     * @param undesired
     *         The undesired value.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesValue(final V undesired) {
        return append(MapConditions.excludes(Noun.VALUE, undesired, containsValue));
    }

    /**
     * Checks if the {@link Map} value (subject) includes all the supplied
     * values. If no values are supplied or the supplied values are
     * {@code null}, the check passes.
     * <p>
     * The supplied values can be a subset of the values in the subject. For
     * example, if the subject is {@code [a -> x, b -> y]} and the supplied
     * values are {@code [y]}, the check will pass since the subject's values
     * contain at least {@code y}. Each supplied value only needs to be present
     * once; to check the number of times a value is present, use
     * {@link #includesValueExactly(Object, long)}.
     *
     * @param desired
     *         The desired values.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesAllValues(final @Nullable Collection<? extends V> desired) {
        return append(MapConditions.includesAll(Noun.VALUE, desired, containsValue));
    }

    /**
     * Checks if the {@link Map} value (subject) includes all the supplied
     * values. If no values are supplied, the check passes.
     * <p>
     * The supplied values can be a subset of the values in the subject. For
     * example, if the subject is {@code [a -> x, b -> y]} and the supplied
     * values are {@code [y]}, the check will pass since the subject's values
     * contain at least {@code y}. Each supplied value only needs to be present
     * once; to check the number of times a value is present, use
     * {@link #includesValueExactly(Object, long)}.
     *
     * @param desired
     *         The desired values.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> includesAllValuesOf(final V... desired) {
        return includesAllValues(Arrays.asList(desired));
    }

    /**
     * Checks if the {@link Map} value (subject) includes any of the supplied
     * values. If no values are supplied, the check fails. If the supplied
     * values are {@code null}, the check passes.
     *
     * @param desired
     *         The desired values.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesAnyValues(final @Nullable Collection<? extends V> desired) {
        return append(MapConditions.includesAny(Noun.VALUE, desired, containsValue));
    }

    /**
     * Checks if the {@link Map} value (subject) includes any of the supplied
     * values. If no values are supplied, the check fails.
     *
     * @param desired
     *         The desired values.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> includesAnyValuesOf(final V... desired) {
        return includesAnyValues(Arrays.asList(desired));
    }

    /**
     * Checks if the {@link Map} value (subject) excludes all the supplied
     * values (none of the supplied values are present). If no values are
     * supplied or the supplied values are {@code null}, the check passes.
     *
     * @param undesired
     *         The undesired values.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesAllValues(final @Nullable Collection<? extends V> undesired) {
        return append(MapConditions.excludesAll(Noun.VALUE, undesired, containsValue));
    }

    /**
     * Checks if the {@link Map} value (subject) excludes all the supplied
     * values (none of the supplied values are present). If no values are
     * supplied, the check passes.
     *
     * @param undesired
     *         The undesired values.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> excludesAllValuesOf(final V... undesired) {
        return excludesAllValues(Arrays.asList(undesired));
    }

    /**
     * Checks if the {@link Map} value (subject) excludes any of the supplied
     * values (at least one of the supplied values is missing). If no values are
     * supplied, the check fails. If the supplied values are {@code null}, the
     * check passes.
     *
     * @param undesired
     *         The undesired values.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesAnyValues(final @Nullable Collection<? extends V> undesired) {
        return append(MapConditions.excludesAny(Noun.VALUE, undesired, containsValue));
    }

    /**
     * Checks if the {@link Map} value (subject) excludes any of the supplied
     * values (at least one of the supplied values is missing). If no values are
     * supplied, the check fails.
     *
     * @param undesired
     *         The undesired values.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> excludesAnyValuesOf(final V... undesired) {
        return excludesAnyValues(Arrays.asList(undesired));
    }

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value the
     * supplied number of times. A negative number of times always fails.
     * <p>
     * For example, if the subject is {@code [a -> x, b -> x, c -> y]}, a
     * supplied value of {@code x} would be found twice, a supplied value of
     * {@code y} would be found once, and any other supplied value would be
     * found zero times. Values are compared using
     * {@link Objects#equals(Object, Object)}.
     *
     * @param desired
     *         The desired value.
     * @param times
     *         The desired number of times the supplied value should be present
     *         in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValueExactly(final V desired, final long times) {
        return append(
                MapConditions.valueOccurrences(
                        "includesValueExactly",
                        "",
                        desired,
                        times,
                        count -> count == times
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value
     * once. See {@link #includesValueExactly(Object, long)} for an example of
     * how occurrences are counted.
     *
     * @param desired
     *         The desired value.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValueOnce(final V desired) {
        return includesValueExactly(desired, 1);
    }

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value
     * more than the supplied number of times. See
     * {@link #includesValueExactly(Object, long)} for an example of how
     * occurrences are counted.
     *
     * @param desired
     *         The desired value.
     * @param times
     *         The desired lower limit (exclusive) of the number of times the
     *         supplied value is found in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValueMoreThan(final V desired, final long times) {
        return append(
                MapConditions.valueOccurrences(
                        "includesValueMoreThan",
                        "more than ",
                        desired,
                        times,
                        count -> count > times
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value
     * more than or equal to the supplied number of times. See
     * {@link #includesValueExactly(Object, long)} for an example of how
     * occurrences are counted.
     *
     * @param desired
     *         The desired value.
     * @param times
     *         The desired lower limit (inclusive) of the number of times the
     *         supplied value is found in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValueMoreThanOrEqualTo(final V desired, final long times) {
        return append(
                MapConditions.valueOccurrences(
                        "includesValueMoreThanOrEqualTo",
                        "more than or equal to ",
                        desired,
                        times,
                        count -> count >= times
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value
     * less than the supplied number of times. See
     * {@link #includesValueExactly(Object, long)} for an example of how
     * occurrences are counted.
     *
     * @param desired
     *         The desired value.
     * @param times
     *         The desired upper limit (exclusive) of the number of times the
     *         supplied value is found in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValueLessThan(final V desired, final long times) {
        return append(
                MapConditions.valueOccurrences(
                        "includesValueLessThan",
                        "less than ",
                        desired,
                        times,
                        count -> count < times
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) includes the supplied value
     * less than or equal to the supplied number of times. See
     * {@link #includesValueExactly(Object, long)} for an example of how
     * occurrences are counted.
     *
     * @param desired
     *         The desired value.
     * @param times
     *         The desired upper limit (inclusive) of the number of times the
     *         supplied value is found in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesValueLessThanOrEqualTo(final V desired, final long times) {
        return append(
                MapConditions.valueOccurrences(
                        "includesValueLessThanOrEqualTo",
                        "less than or equal to ",
                        desired,
                        times,
                        count -> count <= times
                )
        );
    }

    // -------------------------------------------------------------------------
    // Entries
    // -------------------------------------------------------------------------

    /**
     * Checks if the {@link Map} value (subject) includes an entry matching the
     * supplied key and value. The check passes only if the key is present and
     * maps to a value equal to the supplied value, so a {@code null} supplied
     * value matches a key mapped to {@code null}, but not a missing key.
     *
     * @param key
     *         The desired key of the entry.
     * @param value
     *         The desired value of the entry.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesEntry(final K key, final V value) {
        return append(MapConditions.includesEntry(key, value));
    }

    /**
     * Checks if the {@link Map} value (subject) excludes (does not contain) an
     * entry matching the supplied key and value. The check passes if the key is
     * missing or if the key maps to a different value.
     *
     * @param key
     *         The key of the entry to avoid.
     * @param value
     *         The value of the entry to avoid.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesEntry(final K key, final V value) {
        return append(MapConditions.excludesEntry(key, value));
    }

    /**
     * Checks if the {@link Map} value (subject) includes an entry matching the
     * supplied key and whose value satisfies the supplied predicate. This check
     * will pass only if both an entry with the supplied key is found and the
     * value of that same entry satisfies the predicate. The predicate is not
     * called if the key is missing, and may receive {@code null} if the key
     * maps to a {@code null} value.
     *
     * @param key
     *         The desired key of the entry.
     * @param predicate
     *         The predicate used to match the value.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> hasValueMatching(final K key, final Predicate<? super V> predicate) {
        return append(MapConditions.hasValueMatching(key, predicate));
    }

    /**
     * Checks if the {@link Map} value (subject) includes all the entries in the
     * supplied {@link Map}. If the supplied {@link Map} is empty or
     * {@code null}, the check passes.
     *
     * @param desired
     *         The {@link Map} whose entries (key-value pairs) should be present
     *         in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesAllEntries(final @Nullable Map<? extends K, ? extends V> desired) {
        return append(MapConditions.includesAll(Noun.ENTRY, entriesOf(desired), containsEntry));
    }

    /**
     * Checks if the {@link Map} value (subject) includes all the supplied
     * entries. If no entries are supplied, the check passes.
     *
     * @param desired
     *         The entries (key-value pairs) that should be present in the
     *         subject. The entries themselves must not be {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null} or any of the
     *         supplied entries are {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> includesAllEntriesOf(final Map.Entry<? extends K, ? extends V>... desired) {
        return append(MapConditions.includesAll(Noun.ENTRY, List.of(desired), containsEntry));
    }

    /**
     * Checks if the {@link Map} value (subject) includes any of the entries in
     * the supplied {@link Map}. If the supplied {@link Map} is empty, the check
     * fails. If the supplied {@link Map} is {@code null}, the check passes.
     *
     * @param desired
     *         The {@link Map} whose entries (key-value pairs), at least one of
     *         which should be present in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> includesAnyEntries(final @Nullable Map<? extends K, ? extends V> desired) {
        return append(
                MapConditions.includesAny(
                        Noun.ENTRY,
                        entriesOf(desired),
                        containsEntry
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) includes any of the supplied
     * entries. If no entries are supplied, the check fails.
     *
     * @param desired
     *         The entries (key-value pairs), at least one of which should be
     *         present in the subject. The entries themselves must not be
     *         {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null} or any of the
     *         supplied entries are {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> includesAnyEntriesOf(final Map.Entry<? extends K, ? extends V>... desired) {
        return append(
                MapConditions.includesAny(
                        Noun.ENTRY,
                        List.of(desired),
                        containsEntry
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) excludes all the entries in the
     * supplied {@link Map} (none of the entries are present). If the supplied
     * {@link Map} is empty or {@code null}, the check passes.
     *
     * @param undesired
     *         The {@link Map} whose entries (key-value pairs) should not be
     *         present in the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesAllEntries(final @Nullable Map<? extends K, ? extends V> undesired) {
        return append(
                MapConditions.excludesAll(
                        Noun.ENTRY,
                        entriesOf(undesired),
                        containsEntry
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) excludes all the supplied
     * entries (none of the entries are present). If no entries are supplied,
     * the check passes.
     *
     * @param undesired
     *         The entries (key-value pairs) that should not be present in the
     *         subject. The entries themselves must not be {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null} or any of the
     *         supplied entries are {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> excludesAllEntriesOf(final Map.Entry<? extends K, ? extends V>... undesired) {
        return append(
                MapConditions.excludesAll(
                        Noun.ENTRY,
                        List.of(undesired),
                        containsEntry
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) excludes any of the entries in
     * the supplied {@link Map} (at least one of the entries is missing). If the
     * supplied {@link Map} is empty, the check fails. If the supplied
     * {@link Map} is {@code null}, the check passes.
     *
     * @param undesired
     *         The {@link Map} whose entries (key-value pairs), at least one of
     *         which should be missing from the subject.
     *
     * @return This value (fluent interface).
     */
    public MapValue<K, V> excludesAnyEntries(final @Nullable Map<? extends K, ? extends V> undesired) {
        return append(
                MapConditions.excludesAny(
                        Noun.ENTRY,
                        entriesOf(undesired),
                        containsEntry
                )
        );
    }

    /**
     * Checks if the {@link Map} value (subject) excludes any of the supplied
     * entries (at least one of the entries is missing). If no entries are
     * supplied, the check fails.
     *
     * @param undesired
     *         The entries (key-value pairs), at least one of which should be
     *         missing from the subject. The entries themselves must not be
     *         {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied varargs argument is {@code null} or any of the
     *         supplied entries are {@code null}.
     */
    @SafeVarargs
    public final MapValue<K, V> excludesAnyEntriesOf(final Map.Entry<? extends K, ? extends V>... undesired) {
        return append(
                MapConditions.excludesAny(
                        Noun.ENTRY,
                        List.of(undesired),
                        containsEntry
                )
        );
    }

    // -------------------------------------------------------------------------
    // Predicates over all elements
    // -------------------------------------------------------------------------

    /**
     * Checks if all entries of the {@link Map} value (subject) satisfy the
     * supplied predicate. If the subject is empty, the check passes.
     *
     * @param predicate
     *         The predicate that all entries of the subject should satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> allEntriesSatisfy(final BiPredicate<? super K, ? super V> predicate) {
        return append(
                MapConditions.allSatisfy(
                        Noun.ENTRY,
                        MapConditions::entriesOf,
                        asEntryPredicate(predicate)
                )
        );
    }

    /**
     * Checks if any of the entries of the {@link Map} value (subject) satisfy
     * the supplied predicate. If the subject is empty, the check fails.
     *
     * @param predicate
     *         The predicate that at least one entry of the subject should
     *         satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> anyEntrySatisfies(final BiPredicate<? super K, ? super V> predicate) {
        return append(
                MapConditions.anySatisfies(
                        Noun.ENTRY,
                        MapConditions::entriesOf,
                        asEntryPredicate(predicate)
                )
        );
    }

    /**
     * Checks if no entries of the {@link Map} value (subject) satisfy the
     * supplied predicate. If the subject is empty, the check passes.
     *
     * @param predicate
     *         The predicate that no entries of the subject should satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> noEntrySatisfies(final BiPredicate<? super K, ? super V> predicate) {
        return append(
                MapConditions.noneSatisfy(
                        Noun.ENTRY,
                        MapConditions::entriesOf,
                        asEntryPredicate(predicate)
                )
        );
    }

    /**
     * Checks if all keys of the {@link Map} value (subject) satisfy the
     * supplied predicate. If the subject is empty, the check passes.
     *
     * @param predicate
     *         The predicate that all keys of the subject should satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> allKeysSatisfy(final Predicate<? super K> predicate) {
        return append(MapConditions.allSatisfy(Noun.KEY, Map::keySet, predicate));
    }

    /**
     * Checks if any of the keys of the {@link Map} value (subject) satisfy the
     * supplied predicate. If the subject is empty, the check fails.
     *
     * @param predicate
     *         The predicate that at least one key of the subject should
     *         satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> anyKeySatisfies(final Predicate<? super K> predicate) {
        return append(MapConditions.anySatisfies(Noun.KEY, Map::keySet, predicate));
    }

    /**
     * Checks if no key of the {@link Map} value (subject) satisfies the
     * supplied predicate. If the subject is empty, the check passes.
     *
     * @param predicate
     *         The predicate that no key of the subject should satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> noKeySatisfies(final Predicate<? super K> predicate) {
        return append(MapConditions.noneSatisfy(Noun.KEY, Map::keySet, predicate));
    }

    /**
     * Checks if all values of the {@link Map} value (subject) satisfy the
     * supplied predicate. Duplicate values are each tested. If the subject is
     * empty, the check passes.
     *
     * @param predicate
     *         The predicate that all values of the subject should satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> allValuesSatisfy(final Predicate<? super V> predicate) {
        return append(MapConditions.allSatisfy(Noun.VALUE, Map::values, predicate));
    }

    /**
     * Checks if any of the values of the {@link Map} value (subject) satisfy
     * the supplied predicate. If the subject is empty, the check fails.
     *
     * @param predicate
     *         The predicate that at least one value of the subject should
     *         satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> anyValueSatisfies(final Predicate<? super V> predicate) {
        return append(MapConditions.anySatisfies(Noun.VALUE, Map::values, predicate));
    }

    /**
     * Checks if no value of the {@link Map} value (subject) satisfies the
     * supplied predicate. If the subject is empty, the check passes.
     *
     * @param predicate
     *         The predicate that no value of the subject should satisfy.
     *
     * @return This value (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public MapValue<K, V> noValueSatisfies(final Predicate<? super V> predicate) {
        return append(MapConditions.noneSatisfy(Noun.VALUE, Map::values, predicate));
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static <K extends @Nullable Object, V extends @Nullable Object> @Nullable Collection<? extends Map.Entry<? extends K, ? extends V>> entriesOf(
            final @Nullable Map<? extends K, ? extends V> map
    ) {
        return map == null ? null : map.entrySet();
    }

    private static <K extends @Nullable Object, V extends @Nullable Object> Predicate<Map.Entry<K, V>> asEntryPredicate(
            final BiPredicate<? super K, ? super V> predicate
    ) {
        Objects.requireNonNull(predicate);
        return entry -> predicate.test(entry.getKey(), entry.getValue());
    }
}
