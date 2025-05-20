/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joytools.commons.collections.EquatorHashMap;
import org.joytools.commons.collections.EquatorLinkedHashMap;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryMapPerf implements Runnable {
    
    TryMapPerf() {
    }
    
    public void run() {
        List<Map> maps;
        System.out.println("*** CASE SENSITIVE ***");
        maps = Arrays.asList(
                new HashMap(), 
                new TreeMap(), 
                new EquatorHashMap(),
                new EquatorLinkedHashMap(),
                new ListOrderedMap(),
                new LinkedHashMap());
        for (final Map map : shuffle(maps)) {
            test(map);
        }
        System.out.println("*** CASE INSENSITIVE ***");
        maps = Arrays.asList(
                new TreeMap(CaseSensitivity.INSENSITIVE.comparator()),
                new EquatorHashMap(CaseSensitivity.INSENSITIVE.equator()),
                new EquatorLinkedHashMap(CaseSensitivity.INSENSITIVE.equator()),
                new CaseInsensitiveMap());
        for (final Map map : shuffle(maps)) {
            test(map);
        }
    }
    
    void test(final Map<String, String> map) {
        System.out.println("" + map.getClass().getName() + "");
        final Supplier<String> keySupplier = null; // StreamIterables.of(keys).cycle().toSupplier();
        final Supplier<String> getKeySupplier = null; // StreamIterables.of(getKeys).cycle().toSupplier();
        final Supplier<String> valueSupplier = null; // StreamIterables.of(values).cycle().toSupplier();
        for (int i = 0; i < 100; i++) {
            map.put(keySupplier.get(), valueSupplier.get());
        }
        long elapsed = -System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            for (int j = 0; j < 10; j++) {
                map.get(getKeySupplier.get());
            }
            map.put(getKeySupplier.get(), valueSupplier.get());
        }
        elapsed += System.nanoTime();
        System.out.println("  => " + TimeUnit.NANOSECONDS.toMillis(elapsed) + "ms");
        map.clear();
        /*
        System.gc();
        try {
            Thread.sleep(5000);
        } catch (final Exception e) {}
        */
    }
    
    final private List<String> keys = buildKeys();
    
    final private List<String> getKeys = buildGetKeys(keys);

    final private List<String> values = buildValues();

    static List<String> buildKeys() {
        final List<String> keys = new ArrayList();
        for (int i = 0; i < 130; i++) {
            keys.add("key-" + RandomStringUtils.randomAlphanumeric(10, 20));
        }
        return keys;
    }

    static List<String> buildGetKeys(final List<String> keys) {
        final List<String> getKeys = new ArrayList();
        keys.forEach(s -> {
            getKeys.add(s);
            getKeys.add(s.toUpperCase());
            getKeys.add(s.toLowerCase());
        });
        final String[] s = getKeys.toArray(new String[0]);
        ArrayUtils.shuffle(s);
        for (int i = 0; i < s.length; i++) {
            getKeys.set(i, s[i]);
        }
        return getKeys;
    }

    public static <T> List<T> shuffle(final List<T> list) {
        return shuffle(list, new Random());
    }
    
    public static <T> List<T> shuffle(final List<T> list, final Random random) {
        for (int i = list.size(); i > 1; i--) {
            swap(list, i - 1, random.nextInt(i), 1);
        }
        return list;
    }

    static void swap(final List list,  int offset1, int offset2, int len) {
        if (list == null || list.size() == 0 || offset1 >= list.size() || offset2 >= list.size()) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, list.size() - offset1), list.size() - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final Object aux = list.get(offset1);
            list.set(offset1, list.get(offset2));
            list.set(offset2, aux);
        }
    }

    static List<String> buildValues() {
        final List<String> values = new ArrayList();
        for (int i = 0; i < 100000; i++) {
            values.add("value-" + RandomStringUtils.randomAlphanumeric(10, 20));
        }
        return values;
    }
    
    public static void main(final String... args) {
        final TryMapPerf app = new TryMapPerf();
        app.run();
    }
    
}
