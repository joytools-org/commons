/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package sandbox.accessors.beans;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author AndreaR
 */
public class MyBean implements Serializable {
    
    private String firstName;
    
    private String lastName;

    private Integer age;
    
    private Integer notAccessible;

    public MyBean() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, notAccessible);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != MyBean.class) {
            return false;
        }
        final MyBean oth = MyBean.class.cast(obj);
        return Objects.equals(firstName, oth.firstName)
                && Objects.equals(lastName, oth.lastName)
                && Objects.equals(age, oth.age)
                && Objects.equals(notAccessible, oth.notAccessible);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(final String value) {
        this.firstName = value;
    }
   
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(final String value) {
        this.lastName = value;
    }

    public Integer getAge() {
        return age;
    }
    
    public void setAge(final Integer value) {
        this.age = value;
    }

    protected Integer getNotAccessible() {
        return notAccessible;
    }
    
    protected void setNotAccessible(final Integer value) {
        this.notAccessible = value;
    }

}
