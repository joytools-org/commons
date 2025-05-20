/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

/**
 *
 * @author AndreaR
 */
public interface StrListMapAcc<V> extends ListMapAcc<String, V> {
    
    interface StrListMapEdit<V> extends ListMapEdit<String, V> {
        
    }
    
    @Override
    StrListMapEdit<V> edit();
    
}
