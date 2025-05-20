/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author AndreaR
 */
public class TaggedValues {

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @return 
     */ /*
    public static <E, T> TaggedValue<E, T> tagged(final E value) {
        return new AbstractTaggedValue<>() {
            @Override
            public E get() {
                return value;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public T tag() {
                return null;
            }
        };
    } */

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @return 
     */
    public static <E, T> TaggedValue<E, T> some(final E value) {
        return TaggedValues.some(value, None());
    }

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @param tag
     * @return 
     */
    public static <E, T> TaggedValue<E, T> some(final E value, final T tag) {
        return TaggedValues.some(value, Some(tag));
    }

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @param tag
     * @return 
     */
    public static <E, T> TaggedValue<E, T> some(final E value, final Option<T> tag) {
        Objects.requireNonNull(tag, "Tag");
        return new AbstractTaggedValue<E, T>() {
            @Override
            public E get() {
                return value;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Option<T> getTag() {
                return tag;
            }
        };
    } 
    
    /**
     * 
     * @param <E>
     * @param <T>
     * @return 
     */
    public static <E, T> TaggedValue<E, T> none() {
        return (TaggedValue<E, T>)NONE;
    }

    /**
     * 
     */
    private final static TaggedValue NONE = new AbstractTaggedValue<Object, Object>() {
        @Override
        public Object get() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Option<Object> getTag() {
            return None();
        }
    };

}
