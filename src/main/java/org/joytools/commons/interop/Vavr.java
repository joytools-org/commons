/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.interop;

import static io.vavr.API.None;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;
import io.vavr.collection.TreeMap;
import io.vavr.collection.TreeSet;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class Vavr {
    
    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> Option<V> toOption(final Iterable<V> elements) {
        Objects.requireNonNull(elements, "Elements");
        final Iterator<V> itr = elements.iterator();
        if (!itr.hasNext()) {
            return None();
        }
        final Option<V> result = Option.some(itr.next());
        if (itr.hasNext()) {
            return Commons.throwMoreThanOneElementFound();
        }
        return result;
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> V toSome(final Iterable<V> elements) {
        return toOption(elements).get();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> HashMap<K, V> toHashMap(final Iterable<Tuple2<K, V>> entries) {
        if (entries instanceof HashMap map) {
            return map;
        }
        return MapUtils.insertAll(HashMap.<K, V>empty(), entries);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> LinkedHashMap<K, V> toLinkedHashMap(final Iterable<Tuple2<K, V>> entries) {
        if (entries instanceof LinkedHashMap map) {
            return map;
        }
        return MapUtils.insertAll(LinkedHashMap.<K, V>empty(), entries);
    }

    /**
     * 
     */
    private final static Comparator VAVR_TREESET_COMPARATOR = io.vavr.collection.TreeSet.empty().comparator();
    
    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V extends Comparable<? super V>> TreeSet<V> toTreeSet(final Iterable<V> elements) {
        if (elements instanceof io.vavr.collection.TreeSet ts) {
            if (ts.comparator() == VAVR_TREESET_COMPARATOR) {
                return ts;
            }
        }
        return io.vavr.collection.TreeSet.ofAll(elements);
    }

    /**
     * 
     * @param <V>
     * @param comp
     * @param elements
     * @return 
     */
    public static <V extends Comparable<? super V>> TreeSet<V> toTreeSet(final Comparator<? super V> comp,
            final Iterable<V> elements) {
        if (elements instanceof io.vavr.collection.TreeSet ts) {
            if (ts.comparator() == comp) {
                return ts;
            }
        }
        return io.vavr.collection.TreeSet.ofAll(comp, elements);
    }

    /**
     * 
     * @param cs
     * @param elements
     * @return 
     */
    public static TreeSet<String> toTreeSet(final CaseSensitivity cs, final Iterable<String> elements) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return toTreeSet(elements);
        } else {
            return toTreeSetIgnoreCase(elements);
        }
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static TreeSet<String> toTreeSetIgnoreCase(final Iterable<String> elements) {
        if (elements instanceof io.vavr.collection.TreeSet ts) {
            if (ts.comparator() == String.CASE_INSENSITIVE_ORDER) {
                return ts;
            }
        }
        return io.vavr.collection.TreeSet.ofAll(String.CASE_INSENSITIVE_ORDER, elements);
    }

    /**
     * 
     */
    private final static Comparator VAVR_TREEMAP_COMPARATOR = io.vavr.collection.TreeMap.empty().comparator();

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K extends Comparable<? super K>, V> TreeMap<K, V> toTreeMap(final Iterable<Tuple2<K, V>> entries) {
        if (entries instanceof io.vavr.collection.TreeMap ts) {
            if (ts.comparator() == VAVR_TREEMAP_COMPARATOR) {
                return ts;
            }
        }
        return MapUtils.insertAll(TreeMap.<K, V>empty(), entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @param cs
     * @return 
     */
    public static <V> TreeMap<String, V> toTreeMap(final CaseSensitivity cs, final Iterable<Tuple2<String, V>> entries) {
        Objects.requireNonNull(cs, "Case Sensivitivy");
        if (cs.isCaseSensitive()) {
            return toTreeMap(entries);
        } else {
            return toTreeMapIgnoreCase(entries);
        }
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> TreeMap<String, V> toTreeMapIgnoreCase(final Iterable<Tuple2<String, V>> entries) {
        if (entries instanceof io.vavr.collection.TreeMap ts) {
            if (ts.comparator() == String.CASE_INSENSITIVE_ORDER) {
                return ts;
            }
        }
        return MapUtils.insertAll(TreeMap.<String, V>empty(String.CASE_INSENSITIVE_ORDER), entries);
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, Stream<V>> stream() {
        return (Function<Iterable<V>, Stream<V>>)(Function)TO_STREAM;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, Stream<Object>> TO_STREAM = Stream::ofAll;

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <V> Traversable<V> toTraversable(final Iterable<V> entries) {
        if (entries instanceof Traversable trav) {
            return trav;
        }
        return Stream.ofAll(entries);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, Traversable<V>> traversable() {
        return (Function<Iterable<V>, Traversable<V>>)(Function)TO_TRAVERSABLE;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, Traversable<Object>> TO_TRAVERSABLE = Vavr::toTraversable;

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, HashSet<V>> hashSet() {
        return (Function<Iterable<V>, HashSet<V>>)(Function)TO_HASHSET;
    }

    /**
     * 
     */
    private final static Function<Iterable<Object>, HashSet<Object>> TO_HASHSET = HashSet::ofAll;
    
    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K extends Comparable<? super K>, V> Function<Iterable<Tuple2<K, V>>, TreeMap<K, V>> treeMap() {
        return (Function<Iterable<Tuple2<K, V>>, TreeMap<K, V>>)(Function)TO_TREEMAP;
    }

    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Comparable, Object>>, TreeMap<Comparable, Object>> TO_TREEMAP = Vavr::toTreeMap;

    /**
     * 
     * @param cs
     * @return 
     */
    public static final Function<Iterable<String>, TreeSet<String>> treeSet(final CaseSensitivity cs) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return treeSet();
        } else {
            return treeSetIgnoreCase();
        }
    }

    /**
     *
     * @return
     */
    public static final Function<Iterable<String>, TreeSet<String>> treeSetIgnoreCase() {
        return TO_TREESET_IGNORECASE;
    }

    /**
     * 
     */
    private final static Function<Iterable<String>, TreeSet<String>> TO_TREESET_IGNORECASE = Vavr::toTreeSetIgnoreCase;
            
    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<Tuple2<String, V>>, TreeMap<String, V>> treeMapIgnoreCase() {
        return (Function<Iterable<Tuple2<String, V>>, TreeMap<String, V>>)(Function)TO_TREEMAP_IGNORECASE;
    }

    /**
     * 
     * @param <V>
     * @param cs
     * @return 
     */
    public static final <V> Function<Iterable<Tuple2<String, V>>, TreeMap<String, V>> treeMap(final CaseSensitivity cs) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return treeMap();
        } else {
            return treeMapIgnoreCase();
        }
    }

    /**
     * 
     */
    private final static Function<Iterable<Tuple2<String, Object>>, TreeMap<String, Object>> TO_TREEMAP_IGNORECASE =  Vavr::toTreeMapIgnoreCase;
    
    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, LinkedHashMap<K, V>> linkedHashMap() {
        return (Function<Iterable<Tuple2<K, V>>, LinkedHashMap<K, V>>)(Function)TO_LINKEDHASHMAP;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Object, Object>>, LinkedHashMap<Object, Object>> TO_LINKEDHASHMAP = Vavr::toLinkedHashMap;

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, LinkedHashSet<V>> linkedHashSet() {
        return (Function<Iterable<V>, LinkedHashSet<V>>)(Function)TO_LINKEDHASHSET;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, LinkedHashSet<Object>> TO_LINKEDHASHSET = LinkedHashSet::ofAll;

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, V> some() {
        return (Function<Iterable<V>, V>)(Function)TO_SOME;
    }

    /**
     * 
     */
    private final static Function<Iterable<Object>, Object> TO_SOME = Vavr::toSome;

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, Vector<V>> vector() {
        return (Function<Iterable<V>, Vector<V>>)(Function)TO_VECTOR;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, Vector<Object>> TO_VECTOR = Vector::ofAll;

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, HashMap<K, V>> hashMap() {
        return (Function<Iterable<Tuple2<K, V>>, HashMap<K, V>>)(Function)TO_HASHMAP;
    }

    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Object, Object>>, HashMap<Object, Object>> TO_HASHMAP = Vavr::toHashMap;

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, Array<V>> array() {
        return (Function<Iterable<V>, Array<V>>)(Function)TO_ARRAY;
    }

    /**
     * 
     */
    private final static Function<Iterable<Object>, Array<Object>> TO_ARRAY = Array::ofAll;

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, List<V>> list() {
        return (Function<Iterable<V>, List<V>>)(Function)TO_LIST;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, List<Object>> TO_LIST = List::ofAll;

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V extends Comparable<? super V>> Function<Iterable<V>, TreeSet<V>> treeSet() {
        return Vavr::toTreeSet;
    }

    /**
     * 
     * @param <V>
     * @param comp
     * @return 
     */
    public static final <V extends Comparable<? super V>> Function<Iterable<V>, TreeSet<V>> treeSet(final Comparator<? super V> comp) {
        return x -> toTreeSet(comp, x);
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, Option<V>> option() {
        return (Function<Iterable<V>, Option<V>>)(Function)TO_OPTION;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, Option<Object>> TO_OPTION = Vavr::toOption;
    
}
