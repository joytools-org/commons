/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.joytools.commons.function.Processor;
import static org.joytools.commons.function.ProcessorOperation.isContinue;

/**
 *
 * @author AndreaR
 */
class IteratorsInternal {
    
    /**
     *
     * @author AndreaR
     * @param <T>
     */
    static class ConsumerIterator<T> implements Iterator<T> {

        /**
         * 
         * @param iterator
         * @param function 
         */
        public ConsumerIterator(final Iterator<T> iterator,
                final Function<T, ?> function) {
            this.iterator = iterator;
            this.consumer = function == null ? null : function::apply;
            this.lenient = false;
        }

        /**
         * 
         * @param iterator
         * @param function
         * @param lenient 
         */
        public ConsumerIterator(final Iterator<T> iterator,
                final Function<T, ?> function,
                final boolean lenient) {
            this.iterator = iterator;
            this.consumer = function == null ? null : function::apply;
            this.lenient = lenient;
        }

        /**
         * 
         * @param iterator 
         * @param consumer 
         */
        public ConsumerIterator(final Iterator<T> iterator,
                final Consumer<? super T> consumer) {
            this.iterator = iterator;
            this.consumer = consumer;
            this.lenient = false;
        }

        /**
         * 
         * @param iterator
         * @param consumer
         * @param lenient 
         */
        public ConsumerIterator(final Iterator<T> iterator,
                final Consumer<T> consumer,
                final boolean lenient) {
            this.iterator = iterator;
            this.consumer = consumer;
            this.lenient = lenient;
        }

        /**
         * 
         */
        private final Iterator<T> iterator;

        /**
         * 
         */
        private final Consumer<? super T> consumer;

        /**
         * 
         */
        private final boolean lenient;

        /**
         * 
         * @return 
         */
        @Override
        public boolean hasNext() {
            if (iterator == null) {
                return false;
            }
            return iterator.hasNext();
        }

        /**
         * 
         * @return 
         */
        @Override
        public T next() {
            if (iterator == null) {
                throw new NoSuchElementException();
            }
            final T input = iterator.next();
            if (consumer != null) {
                if (lenient) {
                    try {
                        consumer.accept(input);
                    } catch (final Exception e) {
                    } // ignore exceptions
                } else {
                    consumer.accept(input);
                }
            }
            return input;
        }

        /**
         * 
         */
        @Override
        public void remove() {
            if (iterator == null) {
                throw new UnsupportedOperationException();
            }
            iterator.remove();
        }

    }

    /**
     *
     * @author AndreaR
     * @param <T>
     */
    static class ProcessIterator<T> extends AbstractIterator<T> {

        /**
         * 
         * @param iterator
         * processor consumer
         * @param lenient 
         */
        public ProcessIterator(final Iterator<T> iterator,
                final Processor<T, Boolean> processor) {
            this.iterator = iterator;
            this.processor = processor;
        }

        /**
         * 
         */
        private final Iterator<T> iterator;

        /**
         * 
         */
        private final Processor<T, Boolean> processor;

        /**
         * 
         * @return 
         */
        @Override
        protected T computeNext() {
            if (iterator == null) {
                throw new NoSuchElementException();
            }
            if (!iterator.hasNext()) {
                return endOfData();
            }
            final T input = iterator.next();
            if (processor != null) {
                if (!isContinue(processor.process(input))) {
                    return endOfData();
                }
            }
            return input;
        }

    }

    /**
     * 
     */
    private final static Consumer<?> NOOP_CONSUMER = (t) -> {};

    /**
     * 
     */
    private final static BiConsumer<?, ?> NOOP_BICONSUMER = (t, e) -> {};
    
}
