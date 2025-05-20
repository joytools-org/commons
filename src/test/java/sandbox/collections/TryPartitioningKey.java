/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MoreCollectors;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryPartitioningKey {
    
    public static void main(final String... args) {
        final IterableAccessor<String> names = IterableAccessors.from(
                "Andrea",
                "Alice",
                "Diana",
                "Roberto",
                "",
                "1234",
                "Diego",
                "Torino",
                "Dario",
                "Zoe");
        System.out.println(StringUtils.repeat("*", 50));
        names.collect(Collectors.groupingBy(s -> StringUtils.right(s, 1)))
                // .collect(MoreCollectors.partitioningBy(() -> new TreeMap<>(), s -> StringUtils.length(s)))
                .entrySet()
                .forEach(System.out::println);
        System.out.println(StringUtils.repeat("*", 50));
        names.collect(Collectors.groupingBy(s -> StringUtils.right(s, 1)))
                // .collect(MoreCollectors.partitioningBy(() -> new TreeMap<>(), s -> StringUtils.length(s)))
                .entrySet()
                .forEach(System.out::println);
        System.out.println(StringUtils.repeat("*", 50));
        names.collect(Collectors.groupingBy(s -> StringUtils.length(s)))
                .entrySet()
                .forEach(System.out::println);
        System.out.println(StringUtils.repeat("*", 50));
        names.collect(Collectors.groupingBy(s -> StringUtils.length(s)))
                .entrySet()
                .forEach(System.out::println);
        System.out.println(StringUtils.repeat("*", 50));
        names.collect(MoreCollectors.partitioningBy(s -> StringUtils.length(s)))
                .forEach(System.out::println);
        System.out.println(StringUtils.repeat("*", 50));
        names.collect(MoreCollectors.partitioningBy(s -> StringUtils.length(s)))
                .forEach(System.out::println);
    }
    
}
