package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.MapConditions.Noun;
import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.SizeConditions;
import io.github.libzeal.zeal.values.sdk.sequence.SizedValue;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class MapValue<K extends @Nullable Object, V extends @Nullable Object>
        extends BaseValue<Map<K, V>, MapValue<K, V>>
        implements SizedValue<MapValue<K, V>> {

    private final BiPredicate<Map<K, V>, K> containsKey = MapConditions::containsKey;
    private final BiPredicate<Map<K, V>, V> containsValue = MapConditions::containsValue;
    private final BiPredicate<Map<K, V>, Map.Entry<? extends K, ? extends V>> containsEntry = MapConditions::containsEntry;

    public MapValue(final @Nullable Map<K, V> subject) {
        super(subject, "Map value");
    }

    // -------------------------------------------------------------------------
    // Size
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

    public MapValue<K, V> includesKey(final K desired) {
        return append(MapConditions.includes(Noun.KEY, desired, containsKey));
    }

    public MapValue<K, V> excludesKey(final K undesired) {
        return append(MapConditions.excludes(Noun.KEY, undesired, containsKey));
    }

    public MapValue<K, V> includesAllKeys(final @Nullable Collection<? extends K> desired) {
        return append(MapConditions.includesAll(Noun.KEY, desired, containsKey));
    }

    @SafeVarargs
    public final MapValue<K, V> includesAllKeysOf(final K... desired) {
        return includesAllKeys(Arrays.asList(desired));
    }

    public MapValue<K, V> includesAnyKeys(final @Nullable Collection<? extends K> desired) {
        return append(MapConditions.includesAny(Noun.KEY, desired, containsKey));
    }

    @SafeVarargs
    public final MapValue<K, V> includesAnyKeysOf(final K... desired) {
        return includesAnyKeys(Arrays.asList(desired));
    }

    public MapValue<K, V> excludesAllKeys(final @Nullable Collection<? extends K> undesired) {
        return append(MapConditions.excludesAll(Noun.KEY, undesired, containsKey));
    }

    @SafeVarargs
    public final MapValue<K, V> excludesAllKeysOf(final K... undesired) {
        return excludesAllKeys(Arrays.asList(undesired));
    }

    public MapValue<K, V> excludesAnyKeys(final @Nullable Collection<? extends K> undesired) {
        return append(MapConditions.excludesAny(Noun.KEY, undesired, containsKey));
    }

    @SafeVarargs
    public final MapValue<K, V> excludesAnyKeysOf(final K... undesired) {
        return excludesAnyKeys(Arrays.asList(undesired));
    }

    // -------------------------------------------------------------------------
    // Values
    // -------------------------------------------------------------------------

    public MapValue<K, V> hasKeysExactly(final @Nullable Collection<? extends K> desired) {
        return append(MapConditions.hasKeysExactly(desired));
    }

    @SafeVarargs
    public final MapValue<K, V> hasKeysExactlyOf(final K... desired) {
        return hasKeysExactly(Arrays.asList(desired));
    }

    public MapValue<K, V> includesValue(final V desired) {
        return append(MapConditions.includes(Noun.VALUE, desired, containsValue));
    }

    public MapValue<K, V> excludesValue(final V undesired) {
        return append(MapConditions.excludes(Noun.VALUE, undesired, containsValue));
    }

    public MapValue<K, V> includesAllValues(final @Nullable Collection<? extends V> desired) {
        return append(MapConditions.includesAll(Noun.VALUE, desired, containsValue));
    }

    @SafeVarargs
    public final MapValue<K, V> includesAllValuesOf(final V... desired) {
        return includesAllValues(Arrays.asList(desired));
    }

    public MapValue<K, V> includesAnyValues(final @Nullable Collection<? extends V> desired) {
        return append(MapConditions.includesAny(Noun.VALUE, desired, containsValue));
    }

    @SafeVarargs
    public final MapValue<K, V> includesAnyValuesOf(final V... desired) {
        return includesAnyValues(Arrays.asList(desired));
    }

    public MapValue<K, V> excludesAllValues(final @Nullable Collection<? extends V> undesired) {
        return append(MapConditions.excludesAll(Noun.VALUE, undesired, containsValue));
    }

    @SafeVarargs
    public final MapValue<K, V> excludesAllValuesOf(final V... undesired) {
        return excludesAllValues(Arrays.asList(undesired));
    }

    public MapValue<K, V> excludesAnyValues(final @Nullable Collection<? extends V> undesired) {
        return append(MapConditions.excludesAny(Noun.VALUE, undesired, containsValue));
    }

    @SafeVarargs
    public final MapValue<K, V> excludesAnyValuesOf(final V... undesired) {
        return excludesAnyValues(Arrays.asList(undesired));
    }

    public MapValue<K, V> includesValueExactly(final V desired, final long times) {
        return append(MapConditions.valueOccurrences("includesValueExactly", "", desired, times, count -> count == times));
    }

    public MapValue<K, V> includesValueOnce(final V desired) {
        return includesValueExactly(desired, 1);
    }

    public MapValue<K, V> includesValueMoreThan(final V desired, final long times) {
        return append(MapConditions.valueOccurrences("includesValueMoreThan", "more than ", desired, times, count -> count > times));
    }

    public MapValue<K, V> includesValueMoreThanOrEqualTo(final V desired, final long times) {
        return append(MapConditions.valueOccurrences("includesValueMoreThanOrEqualTo", "more than or equal to ", desired, times, count -> count >= times));
    }

    // -------------------------------------------------------------------------
    // Entries
    // -------------------------------------------------------------------------

    public MapValue<K, V> includesValueLessThan(final V desired, final long times) {
        return append(MapConditions.valueOccurrences("includesValueLessThan", "less than ", desired, times, count -> count < times));
    }

    public MapValue<K, V> includesValueLessThanOrEqualTo(final V desired, final long times) {
        return append(MapConditions.valueOccurrences("includesValueLessThanOrEqualTo", "less than or equal to ", desired, times, count -> count <= times));
    }

    public MapValue<K, V> includesEntry(final K key, final V value) {
        return append(MapConditions.includesEntry(key, value));
    }

    public MapValue<K, V> excludesEntry(final K key, final V value) {
        return append(MapConditions.excludesEntry(key, value));
    }

    public MapValue<K, V> hasValueMatching(final K key, final Predicate<? super V> predicate) {
        return append(MapConditions.hasValueMatching(key, predicate));
    }

    public MapValue<K, V> includesAllEntries(final @Nullable Map<? extends K, ? extends V> desired) {
        return append(MapConditions.includesAll(Noun.ENTRY, entriesOf(desired), containsEntry));
    }

    @SafeVarargs
    public final MapValue<K, V> includesAllEntriesOf(final Map.Entry<? extends K, ? extends V>... desired) {
        return append(MapConditions.includesAll(Noun.ENTRY, Arrays.asList(desired), containsEntry));
    }

    public MapValue<K, V> includesAnyEntries(final @Nullable Map<? extends K, ? extends V> desired) {
        return append(MapConditions.includesAny(Noun.ENTRY, entriesOf(desired), containsEntry));
    }

    @SafeVarargs
    public final MapValue<K, V> includesAnyEntriesOf(final Map.Entry<? extends K, ? extends V>... desired) {
        return append(MapConditions.includesAny(Noun.ENTRY, Arrays.asList(desired), containsEntry));
    }

    public MapValue<K, V> excludesAllEntries(final @Nullable Map<? extends K, ? extends V> undesired) {
        return append(MapConditions.excludesAll(Noun.ENTRY, entriesOf(undesired), containsEntry));
    }

    @SafeVarargs
    public final MapValue<K, V> excludesAllEntriesOf(final Map.Entry<? extends K, ? extends V>... undesired) {
        return append(MapConditions.excludesAll(Noun.ENTRY, Arrays.asList(undesired), containsEntry));
    }

    public MapValue<K, V> excludesAnyEntries(final @Nullable Map<? extends K, ? extends V> undesired) {
        return append(MapConditions.excludesAny(Noun.ENTRY, entriesOf(undesired), containsEntry));
    }

    // -------------------------------------------------------------------------
    // Predicates over all elements
    // -------------------------------------------------------------------------

    @SafeVarargs
    public final MapValue<K, V> excludesAnyEntriesOf(final Map.Entry<? extends K, ? extends V>... undesired) {
        return append(MapConditions.excludesAny(Noun.ENTRY, Arrays.asList(undesired), containsEntry));
    }

    public MapValue<K, V> allEntriesSatisfy(final BiPredicate<? super K, ? super V> predicate) {
        return append(MapConditions.allSatisfy(Noun.ENTRY, MapConditions::entriesOf, asEntryPredicate(predicate)));
    }

    public MapValue<K, V> anyEntrySatisfies(final BiPredicate<? super K, ? super V> predicate) {
        return append(MapConditions.anySatisfies(Noun.ENTRY, MapConditions::entriesOf, asEntryPredicate(predicate)));
    }

    public MapValue<K, V> noEntrySatisfies(final BiPredicate<? super K, ? super V> predicate) {
        return append(MapConditions.noneSatisfy(Noun.ENTRY, MapConditions::entriesOf, asEntryPredicate(predicate)));
    }

    public MapValue<K, V> allKeysSatisfy(final Predicate<? super K> predicate) {
        return append(MapConditions.allSatisfy(Noun.KEY, Map::keySet, predicate));
    }

    public MapValue<K, V> allValuesSatisfy(final Predicate<? super V> predicate) {
        return append(MapConditions.allSatisfy(Noun.VALUE, Map::values, predicate));
    }
}
