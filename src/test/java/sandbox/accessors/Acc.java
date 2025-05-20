/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

/**
 *
 * @author AndreaR
 */
public interface Acc<K, V> {
    
    interface Edit<K, V> {
        
    }
    
    Edit<K, V> edit();
    
}
