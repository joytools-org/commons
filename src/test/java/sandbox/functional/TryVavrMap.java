/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.functional;

/**
 *
 * @author AndreaR
 */
public class TryVavrMap {
    
    public static void main(final String... args) {
        final io.vavr.collection.Map<String, Integer> m = io.vavr.collection.HashMap.of("Uno", 1, "Due", 2, "Tre", 3);
        // System.out.println(m.to(io.vavr.collection.HashMap::ofEntries).apply("Uno"));
        // System.out.println(m.to(io.vavr.collection.HashMap::ofEntries).apply("Zero"));
    }           
    
}
