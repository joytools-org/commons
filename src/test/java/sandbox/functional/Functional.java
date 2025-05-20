/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.functional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author AndreaR
 */
public class Functional {
    
    /**
     * 
     * @param <E>
     * @param c
     * @param filtro
     * @param stampa 
     */
    static public <E> void stampaCollection(final Collection<E> c, 
            final Predicate<E> filtro, 
            final Consumer<E> stampa) {
        System.out.println("*********");
        for (final E elemento : c) {
            if (filtro.test(elemento)) {
                stampa.accept(elemento);
            }
        }
    }
    
    static class Filtro3Lettere implements Filtro<String>, Predicate<String> {

        @Override
        public boolean filtro(String elemento) {
            return elemento.length() == 3;
        }
        
        public final boolean test(String elemento) {
            return filtro(elemento);
        }
        
    } 
    
    static class StampaMaiuscolo implements Stampa<String>, Consumer<String> {

        @Override
        public void stampa(String elemento) {
            System.out.println(elemento.toUpperCase());
        }

        @Override
        public final void accept(String t) {
            stampa(t);
        }
        
    }
    
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final Predicate<String> solo3Lettere = (e) -> e.length() == 3;
        final Predicate<String> iniziaPerDMaiuscolo = (e) -> e.charAt(0) == 'D';
        final Predicate<String> solo3LettereEIniziaPerDMaiuscolo = solo3Lettere.and(iniziaPerDMaiuscolo);
        
        final List<String> lista = Arrays.asList("Uno", "Due", "tre", "quattro");
        stampaCollection(lista, 
                new Filtro3Lettere(),
                new StampaMaiuscolo());
        /*
        stampaCollection(lista, 
                new AbstractFiltro() {
                        @Override
                        public boolean filtro(final Object elemento) {
                            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                        }
                },
                new AbstractStampa() {
                        @Override
                        public void stampa(Object elemento) {
                            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                        }
                });
        */
        stampaCollection(lista,
                (e) -> e.length() == 3,
                (e) -> System.out.println(e.toUpperCase()));
        
        stampaCollection(lista,
                (e) -> e.length() == 3 && e.charAt(0) == 'D',
                (e) -> System.out.println(e.toUpperCase()));

        // stampare solo 3 lettere che iniziano per 'D'
        stampaCollection(lista,
                solo3Lettere.and(iniziaPerDMaiuscolo),
                (e) -> System.out.println(e.toUpperCase()));
        
        System.out.println("*** STAMPA CON JDK ***");
        lista.forEach(e -> System.out.println(e));
        
        System.out.println("*** STAMPA CON STREAM JAVA 8 ***");
        final String listaCombinata = lista.stream()
                .filter(solo3Lettere)
                .map(s -> "[" + s + "]")
                // .peek(e -> System.out.println(e))
                // .peek(System.out::println)
                .collect(Collectors.joining(","));
        System.out.println(listaCombinata);
    }
    
}
