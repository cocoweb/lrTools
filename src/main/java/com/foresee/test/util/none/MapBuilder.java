package com.foresee.test.util.none;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.sun.istack.Nullable;

public class MapBuilder<K, V> {

    private final Map<K, V> map = new LinkedHashMap();

    public static <K, V> Map<K, V> emptyMap() {
        return new MapBuilder().toMap();
    }

    public static <K, V> MapBuilder<K, V> newBuilder() {
        return new MapBuilder();
    }

    public static <K, V> MapBuilder<K, V> newBuilder(K key, V value) {
        return new MapBuilder().add(key, value);
    }

    public static <K, V> MapBuilder<K, V> newBuilder(@Nullable Map<? extends K, ? extends V> map) {
        MapBuilder builder = newBuilder();
        builder.addAll(map);
        return builder;
    }

    public static <K, V> Map<K, V> singletonMap(K key, V value) {
        return Collections.singletonMap(key, value);
    }

    public static <K, V> Map<K, V> build(K key, V value) {
        MapBuilder builder = newBuilder();
        return builder.add(key, value).toMap();
    }

    public MapBuilder<K, V> add(@Nullable K key, @Nullable V value) {
        this.map.put(key, value);
        return this;
    }

    public MapBuilder<K, V> addIfValueNotNull(K key, @Nullable V value) {
        if (value != null) {
            add(key, value);
        }
        return this;
    }

    public MapBuilder<K, V> addAll(@Nullable Map<? extends K, ? extends V> map) {
        if (map != null) {
            this.map.putAll(map);
        }
        return this;
    }

    public Map<K, V> toMap() {
        return Collections.unmodifiableMap(new HashMap(this.map));
    }

    public HashMap<K, V> toHashMap() {
        return new HashMap(this.map);
    }

    public LinkedHashMap<K, V> toLinkedHashMap() {
        return new LinkedHashMap(this.map);
    }

    public Map<K, V> toListOrderedMap() {
        return Collections.unmodifiableMap(toLinkedHashMap());
    }

    public SortedMap<K, V> toSortedMap() {
        return Collections.unmodifiableSortedMap(toTreeMap());
    }

    public SortedMap<K, V> toSortedMap(Comparator<K> comparator) {
        return Collections.unmodifiableSortedMap(toTreeMap(comparator));
    }

    public TreeMap<K, V> toTreeMap() {
        return new TreeMap(this.map);
    }

    public TreeMap<K, V> toTreeMap(Comparator<K> comparator) {
        TreeMap result = new TreeMap(comparator);
        result.putAll(this.map);
        return result;
    }

    public Map<K, V> toMutableMap() {
        return toHashMap();
    }

    public Map<K, V> toFastMap() {
        return ImmutableMap.copyOf(this.map);
    }

    public SortedMap<K, V> toFastSortedMap(Comparator<K> comparator) {
        return ImmutableSortedMap.copyOf(this.map, comparator);
    }

    @Deprecated
    public Map<K, V> toImmutableMap() {
        return toMap();
    }
}