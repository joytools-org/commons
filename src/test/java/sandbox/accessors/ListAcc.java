/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

/**
 *
 * @author AndreaR
 */
public interface ListAcc<K, V> extends Acc<K, V> {
    
    interface ListEdit<K, V> extends Edit<K, V> {
        
    }
    
    @Override
    ListEdit<K, V> edit();
    
}
