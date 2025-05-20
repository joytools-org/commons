/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

/**
 *
 * @author AndreaR
 */
public interface MapAcc<K, V> extends Acc<K, V> {
    
    interface MapEdit<K, V> extends Edit<K, V> {
        
    }
    
    @Override
    MapEdit<K, V> edit();
    
}
