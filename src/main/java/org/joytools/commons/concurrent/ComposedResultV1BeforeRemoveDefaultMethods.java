/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkState;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.joytools.commons.lang.ExceptionUtils;
import static org.joytools.commons.function.Functions.getLeft;
import static org.joytools.commons.function.Functions.getRight;
import static org.joytools.commons.function.Predicates.isLeft;
import static org.joytools.commons.function.Predicates.isRight;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface ComposedResultV1BeforeRemoveDefaultMethods<T> extends ComposedResult<Integer, T> {
 
    /**
     * 
     * @return 
     */
    @Override
    public Seq<Either<Exception, T>> entriesSeq();   

    /**
     * 
     * @return 
     */
    @Override
    default Map<Integer, Either<Exception, T>> entriesMap() {
        return LinkedHashMap.ofEntries(entriesSeq().zipWithIndex().map(t -> t.swap()));
    }

    /**
     * 
     * @param value
     * @return 
     */
    public ComposedResultV1BeforeRemoveDefaultMethods<T> append(final Either<Exception, T> value);

    /**
     * 
     * @param values
     * @return 
     */
    public ComposedResultV1BeforeRemoveDefaultMethods<T> appendAll(final Iterable<Either<Exception, T>> values);

    /**
     * 
     * @return 
     */
    default ComposedResultV1BeforeRemoveDefaultMethods<T> dumpDiffMessage() {
        throw new UnsupportedOperationException();
        /*
        final String msg = ComposedResults.computeDiffMessage(this);
        if (msg.length() > 0) {
            System.out.println(msg);
        }
        return this; */
    }

    /**
     * 
     * @return 
     */ /*
    default String diffMessage() {
        return ComposedResults.computeDiffMessage(this);
    } */
    
    /**
     * 
     * @return 
     */
    default SeqComposedResult<T> toSeqComposedResult() {
        throw new UnsupportedOperationException();
        // return ComposedResults.ofSeq(this);
    }

    /**
     * 
     * @return 
     */
    default MapComposedResult<Integer, T> toMapComposedResult() {
        throw new UnsupportedOperationException();
        // return ComposedResults.ofMap(this);
    }

    /**
     * 
     * @return 
     */
    @Override
    default boolean isEmpty() {
        return entriesSeq().isEmpty();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    default boolean isNotEmpty() {
        return !entriesSeq().isEmpty();
    }

    /**
     * 
     * @return 
     */
    default Seq<T> values() {
        final int failures = failedSeq().size();
        checkState(failures == 0, "Values not available since there are %s errors.", failures);
        return succeededSeq();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    default Seq<T> succeededSeq() {
        return entriesSeq().filter(isRight()).map(getRight());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    default int succeededCount() {
        return succeededSeq().size();
    }

    /**
     * 
     * @return 
     */
    @Override
    default double succeededRatio() {
        final double size = entriesSeq().size();
        if (size == 0) {
            return 1;
        }
        return (double)succeededSeq().size() / size;
    }

    /**
     * 
     * @return 
     */
    @Override
    default Seq<Exception> failedSeq() {
        return entriesSeq().filter(isLeft()).map(getLeft());
    }

    /**
     * 
     * @return 
     */
    @Override
    default int failedCount() {
        return failedSeq().size();
    }

    /**
     * 
     * @return 
     */
    @Override
    default double failedRatio() {
        final double size = entriesSeq().size();
        if (size == 0) {
            return 0;
        }
        return (double)failedSeq().size() / size;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    default boolean isAnySucceeded() {
        return !succeededSeq().isEmpty();
    }

    /**
     * 
     * @return 
     */
    @Override
    default boolean isAnySucceededOrEmpty() {
        return (isEmpty() || !succeededSeq().isEmpty());
    }

    /**
     * 
     * @return 
     */
    @Override
    default boolean isAllFailed() {
        if (isEmpty()) {
            return false;
        }
        return succeededSeq().isEmpty();
    }
    
    @Override
    default boolean isAllSucceeded() {
        return succeededCount() > 0 && failedCount() == 0;
    }

    @Override
    default boolean isAllSucceededOrEmpty() {
        return failedCount() == 0;
    }

    /**
     * 
     * @return 
     */
    @Override
    default boolean isAnyFailed() {
        return !failedSeq().isEmpty();
    }
    
    /**
     * 
     * @param condition
     * @throws Exception 
     */
    default void throwIf(final Predicate<? super ComposedResultV1BeforeRemoveDefaultMethods<T>> condition) throws Exception {
        Objects.requireNonNull(condition, "Condition");
        if (condition.test(this)) {
            throwExceptions();
        }
    }

    /**
     * 
     * @param condition
     * @param mapper
     * @throws Exception 
     */
    default void throwIf(final Predicate<? super ComposedResultV1BeforeRemoveDefaultMethods<T>> condition,
            final Function<? super ComposedResultV1BeforeRemoveDefaultMethods<T>, ? extends Exception> mapper) throws Exception {
        Objects.requireNonNull(condition, "Condition");
        if (!condition.test(this)) {
            return;
        }
        if (mapper != null) {
            final Exception ex = mapper.apply(this);
            if (ex != null) {
                throw ex;
            }
        } 
        throwExceptions();
    }

    /**
     * 
     * @param <T>
     * @return
     * @throws Exception 
     */
    @Override
    default <T> T throwExceptions() throws Exception {
        return ExceptionUtils.throwMultipleException(failedSeq());
    }
        
}
