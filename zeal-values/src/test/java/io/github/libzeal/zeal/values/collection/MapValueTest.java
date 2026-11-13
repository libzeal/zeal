package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapValueTest extends BaseValueTest<Map<String, Integer>, MapValue<String, Integer>> {

    private static final String NULL_KEYS_HINT = "Desired keys is (null), which passes by default";
    private static final String NULL_VALUES_HINT = "Desired values is (null), which passes by default";
    private static final String NULL_ENTRIES_HINT = "Desired entries is (null), which passes by default";

    @Override
    protected MapValue<String, Integer> expression(final Map<String, Integer> value) {
        return new MapValue<>(value);
    }

    @Override
    protected Map<String, Integer> nonNullValueA() {
        return Map.of("a", 1);
    }

    @Override
    protected Map<String, Integer> nonNullValueB() {
        return Map.of("b", 3);
    }

    // Keeps insertion order (so messages are predictable) and permits nulls
    private static Map<String, Integer> mapOf(final Object... keysAndValues) {

        final Map<String, Integer> map = new LinkedHashMap<>();

        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((String) keysAndValues[i], (Integer) keysAndValues[i + 1]);
        }

        return map;
    }

    private static Map<String, Integer> caseInsensitiveMapOf(final Object... keysAndValues) {

        final Map<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.putAll(mapOf(keysAndValues));

        return map;
    }

    // ---- size ----

    @Test
    void givenEmptyMap_whenIsEmpty_thenTrue() {
        newTestCase()
            .subject(Map.of())
            .expression(MapValue::isEmpty)
            .result(true)
            .name("isEmpty")
            .expected("size = 0")
            .actual("size = 0")
            .run();
    }

    @Test
    void givenNonEmptyMap_whenIsEmpty_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(MapValue::isEmpty)
            .result(false)
            .name("isEmpty")
            .expected("size = 0")
            .actual("size = 1")
            .run();
    }

    @Test
    void givenNullMap_whenIsEmpty_thenFalse() {
        newTestCase()
            .subject(null)
            .expression(MapValue::isEmpty)
            .result(false)
            .name("isEmpty")
            .expected("size = 0")
            .actual("(null)")
            .run();
    }

    @Test
    void givenNonEmptyMap_whenIsNotEmpty_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(MapValue::isNotEmpty)
            .result(true)
            .name("isNotEmpty")
            .expected("size > 0")
            .actual("size = 1")
            .run();
    }

    @Test
    void givenSameSize_whenHasSizeOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.hasSizeOf(2))
            .result(true)
            .name("hasSizeOf[2]")
            .expected("size = 2")
            .actual("size = 2")
            .run();
    }

    @Test
    void givenSameSize_whenDoesNotHaveSizeOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.doesNotHaveSizeOf(2))
            .result(false)
            .name("doesNotHaveSizeOf[2]")
            .expected("size != 2")
            .actual("size = 2")
            .run();
    }

    @Test
    void givenSameSize_whenIsLongerThan_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.isLongerThan(2))
            .result(false)
            .name("isLongerThan[2]")
            .expected("size > 2")
            .actual("size = 2")
            .run();
    }

    @Test
    void givenSameSize_whenIsLongerThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.isLongerThanOrEqualTo(2))
            .result(true)
            .name("isLongerThanOrEqualTo[2]")
            .expected("size >= 2")
            .actual("size = 2")
            .run();
    }

    @Test
    void givenSameSize_whenIsShorterThan_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.isShorterThan(2))
            .result(false)
            .name("isShorterThan[2]")
            .expected("size < 2")
            .actual("size = 2")
            .run();
    }

    @Test
    void givenSameSize_whenIsShorterThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.isShorterThanOrEqualTo(2))
            .result(true)
            .name("isShorterThanOrEqualTo[2]")
            .expected("size <= 2")
            .actual("size = 2")
            .run();
    }

    // ---- keys ----

    @Test
    void givenKeyPresent_whenIncludesKey_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesKey("a"))
            .result(true)
            .name("includesKey[a]")
            .expected("includesKey[a]")
            .actual("includesKey[a]")
            .run();
    }

    @Test
    void givenKeyMissing_whenIncludesKey_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesKey("z"))
            .result(false)
            .name("includesKey[z]")
            .expected("includesKey[z]")
            .actual("excludesKey[z]")
            .run();
    }

    @Test
    void givenMapNotPermittingNullKeys_whenIncludesNullKey_thenFalse() {
        newTestCase()
            .subject(Map.of("a", 1))
            .expression(value -> value.includesKey(null))
            .result(false)
            .name("includesKey[(null)]")
            .expected("includesKey[(null)]")
            .actual("excludesKey[(null)]")
            .run();
    }

    @Test
    void givenMapWithNullKey_whenIncludesNullKey_thenTrue() {
        newTestCase()
            .subject(mapOf(null, 1))
            .expression(value -> value.includesKey(null))
            .result(true)
            .name("includesKey[(null)]")
            .expected("includesKey[(null)]")
            .actual("includesKey[(null)]")
            .run();
    }

    @Test
    void givenSortedMapWithCaseInsensitiveOrder_whenIncludesKeyWithDifferentCase_thenTrue() {
        newTestCase()
            .subject(caseInsensitiveMapOf("a", 1))
            .expression(value -> value.includesKey("A"))
            .result(true)
            .name("includesKey[A]")
            .expected("includesKey[A]")
            .actual("includesKey[A]")
            .run();
    }

    @Test
    void givenNullMap_whenIncludesKey_thenFalse() {
        newTestCase()
            .subject(null)
            .expression(value -> value.includesKey("a"))
            .result(false)
            .name("includesKey[a]")
            .expected("includesKey[a]")
            .actual("(null)")
            .run();
    }

    @Test
    void givenKeyPresent_whenExcludesKey_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesKey("a"))
            .result(false)
            .name("excludesKey[a]")
            .expected("excludesKey[a]")
            .actual("includesKey[a]")
            .run();
    }

    @Test
    void givenMapNotPermittingNullKeys_whenExcludesNullKey_thenTrue() {
        newTestCase()
            .subject(Map.of("a", 1))
            .expression(value -> value.excludesKey(null))
            .result(true)
            .name("excludesKey[(null)]")
            .expected("excludesKey[(null)]")
            .actual("excludesKey[(null)]")
            .run();
    }

    @Test
    void givenAllKeysPresent_whenIncludesAllKeysOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesAllKeysOf("a", "b"))
            .result(true)
            .name("includesAllKeys")
            .expected("All desired keys in subject")
            .actual("All desired keys in subject")
            .hint("All desired keys found in subject")
            .run();
    }

    @Test
    void givenOneKeyMissing_whenIncludesAllKeys_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesAllKeys(List.of("a", "z")))
            .result(false)
            .name("includesAllKeys")
            .expected("All desired keys in subject")
            .actual("At least one desired key missing in subject")
            .hint("The following desired keys were not found: [z]")
            .run();
    }

    @Test
    void givenNullDesired_whenIncludesAllKeys_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAllKeys(null))
            .result(true)
            .name("includesAllKeys")
            .expected("All desired keys in subject")
            .actual("All desired keys in subject")
            .hint(NULL_KEYS_HINT)
            .run();
    }

    @Test
    void givenManyKeysMissing_whenIncludesAllKeys_thenMissingKeysTruncated() {

        final List<String> desired = List.of(
            "k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8", "k9", "k10", "k11",
            "k12", "k13", "k14", "k15", "k16", "k17", "k18", "k19", "k20", "k21"
        );

        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAllKeys(desired))
            .result(false)
            .name("includesAllKeys")
            .expected("All desired keys in subject")
            .actual("At least one desired key missing in subject")
            .hint("The following desired keys were not found: " + desired.subList(0, 20).toString().replace("]", ", ...]"))
            .run();
    }

    @Test
    void givenOneKeyPresent_whenIncludesAnyKeysOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesAnyKeysOf("z", "b"))
            .result(true)
            .name("includesAnyKeys")
            .expected("Any desired keys in subject")
            .actual("At least one desired key found in subject")
            .hint("The following desired keys were found: [b]")
            .run();
    }

    @Test
    void givenNoKeysPresent_whenIncludesAnyKeys_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAnyKeys(List.of("y", "z")))
            .result(false)
            .name("includesAnyKeys")
            .expected("Any desired keys in subject")
            .actual("All desired keys missing in subject")
            .hint("All of the desired keys are missing in subject")
            .run();
    }

    @Test
    void givenOneKeyPresent_whenExcludesAllKeysOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAllKeysOf("a", "z"))
            .result(false)
            .name("excludesAllKeys")
            .expected("All desired keys missing in subject")
            .actual("At least one desired key found in subject")
            .hint("The following desired keys were found: [a]")
            .run();
    }

    @Test
    void givenNoKeysPresent_whenExcludesAllKeys_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAllKeys(List.of("y", "z")))
            .result(true)
            .name("excludesAllKeys")
            .expected("All desired keys missing in subject")
            .actual("All desired keys missing in subject")
            .hint("All of the desired keys are missing in subject")
            .run();
    }

    @Test
    void givenOneKeyMissing_whenExcludesAnyKeysOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAnyKeysOf("a", "z"))
            .result(true)
            .name("excludesAnyKeys")
            .expected("At least one desired key missing in subject")
            .actual("At least one desired key missing in subject")
            .hint("The following desired keys were not found: [z]")
            .run();
    }

    @Test
    void givenAllKeysPresent_whenExcludesAnyKeys_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.excludesAnyKeys(List.of("a", "b")))
            .result(false)
            .name("excludesAnyKeys")
            .expected("At least one desired key missing in subject")
            .actual("All desired keys found in subject")
            .hint("All desired keys found in subject")
            .run();
    }

    // ---- hasKeysExactly ----

    @Test
    void givenSameKeys_whenHasKeysExactlyOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.hasKeysExactlyOf("b", "a"))
            .result(true)
            .name("hasKeysExactly[b, a]")
            .expected("keys = [b, a]")
            .actual("keys = [a, b]")
            .hint("Subject has exactly the desired keys")
            .run();
    }

    @Test
    void givenDuplicateDesiredKeys_whenHasKeysExactlyOf_thenDuplicatesIgnored() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.hasKeysExactlyOf("a", "a"))
            .result(true)
            .name("hasKeysExactly[a]")
            .expected("keys = [a]")
            .actual("keys = [a]")
            .run();
    }

    @Test
    void givenUnexpectedKey_whenHasKeysExactly_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.hasKeysExactly(List.of("a")))
            .result(false)
            .name("hasKeysExactly[a]")
            .expected("keys = [a]")
            .actual("keys = [a, b]")
            .hint("Missing keys: [], unexpected keys: [b]")
            .run();
    }

    @Test
    void givenMissingKey_whenHasKeysExactly_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.hasKeysExactly(List.of("a", "c")))
            .result(false)
            .name("hasKeysExactly[a, c]")
            .expected("keys = [a, c]")
            .actual("keys = [a]")
            .hint("Missing keys: [c], unexpected keys: []")
            .run();
    }

    @Test
    void givenSortedMapWithCaseInsensitiveOrder_whenHasKeysExactlyWithDifferentCase_thenTrue() {
        newTestCase()
            .subject(caseInsensitiveMapOf("a", 1, "b", 2))
            .expression(value -> value.hasKeysExactlyOf("A", "B"))
            .result(true)
            .name("hasKeysExactly[A, B]")
            .expected("keys = [A, B]")
            .actual("keys = [a, b]")
            .run();
    }

    @Test
    void givenNullDesired_whenHasKeysExactly_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.hasKeysExactly(null))
            .result(false)
            .name("hasKeysExactly[(null)]")
            .expected("Always fail: cannot compare to (null) keys")
            .actual("keys = [a]")
            .hint("Desired keys is (null), which fails by default")
            .run();
    }

    // ---- values ----

    @Test
    void givenValuePresent_whenIncludesValue_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesValue(1))
            .result(true)
            .name("includesValue[1]")
            .expected("includesValue[1]")
            .actual("includesValue[1]")
            .run();
    }

    @Test
    void givenMapNotPermittingNullValues_whenIncludesNullValue_thenFalse() {
        newTestCase()
            .subject(Map.of("a", 1))
            .expression(value -> value.includesValue(null))
            .result(false)
            .name("includesValue[(null)]")
            .expected("includesValue[(null)]")
            .actual("excludesValue[(null)]")
            .run();
    }

    @Test
    void givenMapWithNullValue_whenIncludesNullValue_thenTrue() {
        newTestCase()
            .subject(mapOf("a", null))
            .expression(value -> value.includesValue(null))
            .result(true)
            .name("includesValue[(null)]")
            .expected("includesValue[(null)]")
            .actual("includesValue[(null)]")
            .run();
    }

    @Test
    void givenValuePresent_whenExcludesValue_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesValue(1))
            .result(false)
            .name("excludesValue[1]")
            .expected("excludesValue[1]")
            .actual("includesValue[1]")
            .run();
    }

    @Test
    void givenOneValueMissing_whenIncludesAllValuesOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesAllValuesOf(1, 3))
            .result(false)
            .name("includesAllValues")
            .expected("All desired values in subject")
            .actual("At least one desired value missing in subject")
            .hint("The following desired values were not found: [3]")
            .run();
    }

    @Test
    void givenNullDesired_whenIncludesAllValues_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAllValues(null))
            .result(true)
            .name("includesAllValues")
            .expected("All desired values in subject")
            .actual("All desired values in subject")
            .hint(NULL_VALUES_HINT)
            .run();
    }

    @Test
    void givenOneValuePresent_whenIncludesAnyValuesOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAnyValuesOf(3, 1))
            .result(true)
            .name("includesAnyValues")
            .expected("Any desired values in subject")
            .actual("At least one desired value found in subject")
            .hint("The following desired values were found: [1]")
            .run();
    }

    @Test
    void givenNoValuesPresent_whenIncludesAnyValues_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAnyValues(List.of(2, 3)))
            .result(false)
            .name("includesAnyValues")
            .expected("Any desired values in subject")
            .actual("All desired values missing in subject")
            .run();
    }

    @Test
    void givenNoValuesPresent_whenExcludesAllValuesOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAllValuesOf(2, 3))
            .result(true)
            .name("excludesAllValues")
            .expected("All desired values missing in subject")
            .actual("All desired values missing in subject")
            .run();
    }

    @Test
    void givenOneValuePresent_whenExcludesAllValues_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAllValues(List.of(1, 3)))
            .result(false)
            .name("excludesAllValues")
            .expected("All desired values missing in subject")
            .actual("At least one desired value found in subject")
            .hint("The following desired values were found: [1]")
            .run();
    }

    @Test
    void givenAllValuesPresent_whenExcludesAnyValuesOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.excludesAnyValuesOf(1, 2))
            .result(false)
            .name("excludesAnyValues")
            .expected("At least one desired value missing in subject")
            .actual("All desired values found in subject")
            .run();
    }

    @Test
    void givenOneValueMissing_whenExcludesAnyValues_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAnyValues(List.of(1, 3)))
            .result(true)
            .name("excludesAnyValues")
            .expected("At least one desired value missing in subject")
            .actual("At least one desired value missing in subject")
            .hint("The following desired values were not found: [3]")
            .run();
    }

    // ---- value occurrences ----

    @Test
    void givenValueTwice_whenIncludesValueExactlyTwice_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 1, "c", 2))
            .expression(value -> value.includesValueExactly(1, 2))
            .result(true)
            .name("includesValueExactly[desired=1, times=2]")
            .expected("found '1' 2 times")
            .actual("found '1' 2 times")
            .run();
    }

    @Test
    void givenValueTwice_whenIncludesValueOnce_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 1))
            .expression(value -> value.includesValueOnce(1))
            .result(false)
            .name("includesValueExactly[desired=1, times=1]")
            .expected("found '1' 1 time")
            .actual("found '1' 2 times")
            .hint("Desired value was found, but 2 times")
            .run();
    }

    @Test
    void givenNullValueOnce_whenIncludesNullValueOnce_thenTrue() {
        newTestCase()
            .subject(mapOf("a", null, "b", 1))
            .expression(value -> value.includesValueOnce(null))
            .result(true)
            .name("includesValueExactly[desired=(null), times=1]")
            .expected("found '(null)' 1 time")
            .actual("found '(null)' 1 time")
            .run();
    }

    @Test
    void givenValueTwice_whenIncludesValueMoreThanOnce_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 1))
            .expression(value -> value.includesValueMoreThan(1, 1))
            .result(true)
            .name("includesValueMoreThan[desired=1, times=1]")
            .expected("found '1' more than 1 time")
            .actual("found '1' 2 times")
            .run();
    }

    @Test
    void givenValueTwice_whenIncludesValueMoreThanOrEqualToTwice_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 1))
            .expression(value -> value.includesValueMoreThanOrEqualTo(1, 2))
            .result(true)
            .name("includesValueMoreThanOrEqualTo[desired=1, times=2]")
            .expected("found '1' more than or equal to 2 times")
            .actual("found '1' 2 times")
            .run();
    }

    @Test
    void givenValueMissing_whenIncludesValueLessThanOnce_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesValueLessThan(3, 1))
            .result(true)
            .name("includesValueLessThan[desired=3, times=1]")
            .expected("found '3' less than 1 time")
            .actual("found '3' 0 times")
            .hint("Desired value not found in subject")
            .run();
    }

    @Test
    void givenValueTwice_whenIncludesValueLessThanOrEqualToOnce_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 1))
            .expression(value -> value.includesValueLessThanOrEqualTo(1, 1))
            .result(false)
            .name("includesValueLessThanOrEqualTo[desired=1, times=1]")
            .expected("found '1' less than or equal to 1 time")
            .actual("found '1' 2 times")
            .run();
    }

    // ---- entries ----

    @Test
    void givenEntryPresent_whenIncludesEntry_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesEntry("a", 1))
            .result(true)
            .name("includesEntry[key=a, value=1]")
            .expected("entry[a=1]")
            .actual("entry[a=1]")
            .hint("Subject includes entry[a=1]")
            .run();
    }

    @Test
    void givenKeyWithDifferentValue_whenIncludesEntry_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesEntry("a", 2))
            .result(false)
            .name("includesEntry[key=a, value=2]")
            .expected("entry[a=2]")
            .actual("entry[a=1]")
            .hint("Key 'a' maps to '1'")
            .run();
    }

    @Test
    void givenKeyMissing_whenIncludesEntry_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesEntry("z", 1))
            .result(false)
            .name("includesEntry[key=z, value=1]")
            .expected("entry[z=1]")
            .actual("no entry for key 'z'")
            .hint("Key 'z' not found in subject")
            .run();
    }

    @Test
    void givenKeyMappedToNull_whenIncludesEntryWithNullValue_thenTrue() {
        newTestCase()
            .subject(mapOf("a", null))
            .expression(value -> value.includesEntry("a", null))
            .result(true)
            .name("includesEntry[key=a, value=(null)]")
            .expected("entry[a=(null)]")
            .actual("entry[a=(null)]")
            .run();
    }

    @Test
    void givenKeyMissing_whenIncludesEntryWithNullValue_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesEntry("z", null))
            .result(false)
            .name("includesEntry[key=z, value=(null)]")
            .expected("entry[z=(null)]")
            .actual("no entry for key 'z'")
            .run();
    }

    @Test
    void givenMapNotPermittingNullKeys_whenIncludesEntryWithNullKey_thenFalse() {
        newTestCase()
            .subject(Map.of("a", 1))
            .expression(value -> value.includesEntry(null, 1))
            .result(false)
            .name("includesEntry[key=(null), value=1]")
            .expected("entry[(null)=1]")
            .actual("no entry for key '(null)'")
            .run();
    }

    @Test
    void givenEntryPresent_whenExcludesEntry_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesEntry("a", 1))
            .result(false)
            .name("excludesEntry[key=a, value=1]")
            .expected("not[entry[a=1]]")
            .actual("entry[a=1]")
            .hint("Subject includes entry[a=1]")
            .run();
    }

    @Test
    void givenKeyWithDifferentValue_whenExcludesEntry_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesEntry("a", 2))
            .result(true)
            .name("excludesEntry[key=a, value=2]")
            .expected("not[entry[a=2]]")
            .actual("entry[a=1]")
            .hint("Key 'a' maps to '1'")
            .run();
    }

    @Test
    void givenMatchingValue_whenHasValueMatching_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.hasValueMatching("a", v -> v > 0))
            .result(true)
            .name("hasValueMatching[a]")
            .expected("value for key 'a' satisfies condition")
            .actual("value for key 'a' satisfies condition")
            .hint("Key 'a' maps to '1'")
            .run();
    }

    @Test
    void givenNonMatchingValue_whenHasValueMatching_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.hasValueMatching("a", v -> v > 1))
            .result(false)
            .name("hasValueMatching[a]")
            .expected("value for key 'a' satisfies condition")
            .actual("value for key 'a' does not satisfy condition")
            .hint("Key 'a' maps to '1'")
            .run();
    }

    @Test
    void givenKeyMissing_whenHasValueMatching_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.hasValueMatching("z", v -> true))
            .result(false)
            .name("hasValueMatching[z]")
            .expected("value for key 'z' satisfies condition")
            .actual("no entry for key 'z'")
            .hint("Key 'z' not found in subject")
            .run();
    }

    @Test
    void givenKeyMappedToNull_whenHasValueMatchingNull_thenTrue() {
        newTestCase()
            .subject(mapOf("a", null))
            .expression(value -> value.hasValueMatching("a", Objects::isNull))
            .result(true)
            .name("hasValueMatching[a]")
            .expected("value for key 'a' satisfies condition")
            .actual("value for key 'a' satisfies condition")
            .run();
    }

    @Test
    void givenNullPredicate_whenHasValueMatching_thenExceptionThrown() {

        final MapValue<String, Integer> value = expression(mapOf("a", 1));

        assertThrows(NullPointerException.class, () -> value.hasValueMatching("a", null));
    }

    @Test
    void givenAllEntriesPresent_whenIncludesAllEntries_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesAllEntries(Map.of("a", 1)))
            .result(true)
            .name("includesAllEntries")
            .expected("All desired entries in subject")
            .actual("All desired entries in subject")
            .hint("All desired entries found in subject")
            .run();
    }

    @Test
    void givenOneEntryWithDifferentValue_whenIncludesAllEntriesOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.includesAllEntriesOf(entry("a", 1), entry("b", 3)))
            .result(false)
            .name("includesAllEntries")
            .expected("All desired entries in subject")
            .actual("At least one desired entry missing in subject")
            .hint("The following desired entries were not found: [b=3]")
            .run();
    }

    @Test
    void givenEntryWithNullValue_whenIncludesAllEntriesOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", null))
            .expression(value -> value.includesAllEntriesOf(new SimpleEntry<>("a", null)))
            .result(true)
            .name("includesAllEntries")
            .expected("All desired entries in subject")
            .actual("All desired entries in subject")
            .run();
    }

    @Test
    void givenNullDesired_whenIncludesAllEntries_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAllEntries(null))
            .result(true)
            .name("includesAllEntries")
            .expected("All desired entries in subject")
            .actual("All desired entries in subject")
            .hint(NULL_ENTRIES_HINT)
            .run();
    }

    @Test
    void givenOneEntryPresent_whenIncludesAnyEntriesOf_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAnyEntriesOf(entry("a", 2), entry("a", 1)))
            .result(true)
            .name("includesAnyEntries")
            .expected("Any desired entries in subject")
            .actual("At least one desired entry found in subject")
            .hint("The following desired entries were found: [a=1]")
            .run();
    }

    @Test
    void givenNoEntriesPresent_whenIncludesAnyEntries_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.includesAnyEntries(Map.of("a", 2)))
            .result(false)
            .name("includesAnyEntries")
            .expected("Any desired entries in subject")
            .actual("All desired entries missing in subject")
            .run();
    }

    @Test
    void givenNoEntriesPresent_whenExcludesAllEntries_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAllEntries(Map.of("a", 2, "b", 1)))
            .result(true)
            .name("excludesAllEntries")
            .expected("All desired entries missing in subject")
            .actual("All desired entries missing in subject")
            .run();
    }

    @Test
    void givenOneEntryPresent_whenExcludesAllEntriesOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAllEntriesOf(entry("a", 1), entry("b", 1)))
            .result(false)
            .name("excludesAllEntries")
            .expected("All desired entries missing in subject")
            .actual("At least one desired entry found in subject")
            .hint("The following desired entries were found: [a=1]")
            .run();
    }

    @Test
    void givenOneEntryMissing_whenExcludesAnyEntries_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.excludesAnyEntries(mapOf("a", 1, "b", 2)))
            .result(true)
            .name("excludesAnyEntries")
            .expected("At least one desired entry missing in subject")
            .actual("At least one desired entry missing in subject")
            .hint("The following desired entries were not found: [b=2]")
            .run();
    }

    @Test
    void givenAllEntriesPresent_whenExcludesAnyEntriesOf_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.excludesAnyEntriesOf(entry("a", 1), entry("b", 2)))
            .result(false)
            .name("excludesAnyEntries")
            .expected("At least one desired entry missing in subject")
            .actual("All desired entries found in subject")
            .run();
    }

    // ---- predicates over all elements ----

    @Test
    void givenAllEntriesMatch_whenAllEntriesSatisfy_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.allEntriesSatisfy((k, v) -> v > 0))
            .result(true)
            .name("allEntriesSatisfy")
            .expected("All entries satisfy condition")
            .actual("All entries satisfy condition")
            .run();
    }

    @Test
    void givenOneEntryDoesNotMatch_whenAllEntriesSatisfy_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", -1))
            .expression(value -> value.allEntriesSatisfy((k, v) -> v > 0))
            .result(false)
            .name("allEntriesSatisfy")
            .expected("All entries satisfy condition")
            .actual("At least one entry does not satisfy condition")
            .hint("The following entries do not satisfy condition: [b=-1]")
            .run();
    }

    @Test
    void givenEmptyMap_whenAllEntriesSatisfy_thenTrue() {
        newTestCase()
            .subject(Map.of())
            .expression(value -> value.allEntriesSatisfy((k, v) -> false))
            .result(true)
            .name("allEntriesSatisfy")
            .expected("All entries satisfy condition")
            .actual("All entries satisfy condition")
            .run();
    }

    @Test
    void givenOneEntryMatches_whenAnyEntrySatisfies_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", -1))
            .expression(value -> value.anyEntrySatisfies((k, v) -> k.equals("a") && v == 1))
            .result(true)
            .name("anyEntrySatisfies")
            .expected("At least one entry satisfies condition")
            .actual("At least one entry satisfies condition")
            .hint("The following entries satisfy condition: [a=1]")
            .run();
    }

    @Test
    void givenEmptyMap_whenAnyEntrySatisfies_thenFalse() {
        newTestCase()
            .subject(Map.of())
            .expression(value -> value.anyEntrySatisfies((k, v) -> true))
            .result(false)
            .name("anyEntrySatisfies")
            .expected("At least one entry satisfies condition")
            .actual("No entries satisfy condition")
            .run();
    }

    @Test
    void givenNoEntriesMatch_whenNoEntrySatisfies_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1))
            .expression(value -> value.noEntrySatisfies((k, v) -> v < 0))
            .result(true)
            .name("noEntrySatisfies")
            .expected("No entries satisfy condition")
            .actual("No entries satisfy condition")
            .run();
    }

    @Test
    void givenOneEntryMatches_whenNoEntrySatisfies_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", -1))
            .expression(value -> value.noEntrySatisfies((k, v) -> v < 0))
            .result(false)
            .name("noEntrySatisfies")
            .expected("No entries satisfy condition")
            .actual("At least one entry satisfies condition")
            .hint("The following entries satisfy condition: [b=-1]")
            .run();
    }

    @Test
    void givenEntryWithNullValue_whenAllEntriesSatisfy_thenNullValuePassedToPredicate() {
        newTestCase()
            .subject(mapOf("a", null))
            .expression(value -> value.allEntriesSatisfy((k, v) -> v == null))
            .result(true)
            .name("allEntriesSatisfy")
            .expected("All entries satisfy condition")
            .actual("All entries satisfy condition")
            .run();
    }

    @Test
    void givenOneKeyDoesNotMatch_whenAllKeysSatisfy_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "bb", 2))
            .expression(value -> value.allKeysSatisfy(k -> k.length() == 1))
            .result(false)
            .name("allKeysSatisfy")
            .expected("All keys satisfy condition")
            .actual("At least one key does not satisfy condition")
            .hint("The following keys do not satisfy condition: [bb]")
            .run();
    }

    @Test
    void givenAllValuesMatch_whenAllValuesSatisfy_thenTrue() {
        newTestCase()
            .subject(mapOf("a", 1, "b", 2))
            .expression(value -> value.allValuesSatisfy(v -> v > 0))
            .result(true)
            .name("allValuesSatisfy")
            .expected("All values satisfy condition")
            .actual("All values satisfy condition")
            .run();
    }

    @Test
    void givenOneValueDoesNotMatch_whenAllValuesSatisfy_thenFalse() {
        newTestCase()
            .subject(mapOf("a", 1, "b", -1))
            .expression(value -> value.allValuesSatisfy(v -> v > 0))
            .result(false)
            .name("allValuesSatisfy")
            .expected("All values satisfy condition")
            .actual("At least one value does not satisfy condition")
            .hint("The following values do not satisfy condition: [-1]")
            .run();
    }

    @Test
    void givenNullPredicate_whenAllEntriesSatisfy_thenExceptionThrown() {

        final MapValue<String, Integer> value = expression(mapOf("a", 1));

        assertThrows(NullPointerException.class, () -> value.allEntriesSatisfy(null));
    }

    @Test
    void givenNullPredicate_whenAllKeysSatisfy_thenExceptionThrown() {

        final MapValue<String, Integer> value = expression(mapOf("a", 1));

        assertThrows(NullPointerException.class, () -> value.allKeysSatisfy(null));
    }
}
