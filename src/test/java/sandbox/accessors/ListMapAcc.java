/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

/**
 *
 * @author AndreaR
 */
public interface ListMapAcc<K, V> extends Acc<K, V>, MapAcc<K, V>, ListAcc<K, V> {
    
    interface ListMapEdit<K, V> extends Edit<K, V>, MapEdit<K, V>, ListEdit<K, V> {
        
    }
    
    @Override
    ListMapEdit<K, V> edit();
    
}
