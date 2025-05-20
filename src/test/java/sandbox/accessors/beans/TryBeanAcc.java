/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors.beans;

import org.joytools.commons.accessors.reflect.BeanAccessors;
import org.joytools.commons.accessors.reflect.FixedSizeBeanMutators;

/**
 *
 * @author AndreaR
 */
public class TryBeanAcc {
    
    public static void main(final String... args) throws Exception {
        final MyBean bean1 = new MyBean();
        bean1.setFirstName("Andrea");
        bean1.setLastName("Rombaldi");
        bean1.setAge(39);
        final MyBean bean2 = new MyBean();
        bean2.setFirstName("Andrea");
        bean2.setLastName("Rombaldi");
        bean2.setAge(39);
        System.out.println(bean1.hashCode());
        System.out.println(bean2.hashCode());
        System.out.println(bean1.equals(bean2));
        
        final var acc1 = BeanAccessors.of(bean1);
        System.out.println(acc1);
        
        final var acc2 = FixedSizeBeanMutators.of(bean2);
        acc2.set("firstName", "Marco");
        acc2.set("lastName", "Pastore");
        System.out.println(acc2);
    }
    
}
