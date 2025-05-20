/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkState;
import static io.vavr.API.Left;
import static io.vavr.API.None;
import static io.vavr.API.Right;
import static io.vavr.API.Some;
import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.text.TextStringBuilder;

/**
 *
 * @author AndreaR
 */
public class ComposedResults {
    
    public final static class BasicUtils {
        
        private BasicUtils() {
        }

        public <V> BasicComposedResult<V> empty() {
            return (BasicComposedResult<V>)EMPTY_INSTANCE;
        }
        
        private final static BasicComposedResult EMPTY_INSTANCE = new BasicComposedResultImpl();
        
    }
    
    public final static class MapUtils {
        
        private MapUtils() {
        }
        
        public <K, V> MapComposedResult<K, V> empty() {
            return (MapComposedResult<K, V>)EMPTY_INSTANCE;
        }
        
        public <V> MapComposedResult<Integer, V> of(final BasicComposedResult<V> other) {
            Objects.requireNonNull(other, "Other");
            final Iterable<Tuple2<Integer, Either<Exception, V>>> entries = other.entriesSeq().zipWithIndex().map(t -> t.swap());
            return new MapComposedResultImpl<>(LinkedHashMap.ofEntries(entries));
        }

        private final static MapComposedResult EMPTY_INSTANCE = new MapComposedResultImpl();

    }

    public final static class SeqUtils {
        
        private SeqUtils() {
        }
        
        public <V> SeqComposedResult<V> empty() {
            return (SeqComposedResult<V>)EMPTY_INSTANCE;
        }
        
        public <V> SeqComposedResult<V> of(final BasicComposedResult<V> other) {
            Objects.requireNonNull(other, "Other");
            return new SeqComposedResultImpl<>(other.entriesSeq().toVector());
        }
        
        private final static SeqComposedResult EMPTY_INSTANCE = new SeqComposedResultImpl();

    }

    public final static BasicUtils basic = new BasicUtils();
            
    public final static MapUtils map  = new MapUtils();

    public final static SeqUtils seq = new SeqUtils();

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> MapComposedResult<K, V> ofMap(final Map<K, Either<Exception, V>> map) {
        Objects.requireNonNull(map, "Map");
        if (map instanceof LinkedHashMap lhm) {
            return new MapComposedResultImpl<>(lhm);
        }
        return new MapComposedResultImpl<>(LinkedHashMap.ofEntries(map.iterator()));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */ /*
    public static <K, V> MapComposedResult<K, V> ofMapEmpty() {
        return ofMap(LinkedHashMap.empty());
    } */

  
    /**
     * 
     * @param <V>
     * @param other
     * @return 
     */
    public static <V> MapComposedResult<Integer, V> ofMap(final SeqComposedResult<V> other) {
        Objects.requireNonNull(other, "Other");
        final Iterable<Tuple2<Integer, Either<Exception, V>>> entries = other.entriesSeq().zipWithIndex().map(t -> t.swap());
        return new MapComposedResultImpl<>(LinkedHashMap.ofEntries(entries));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> MapComposedResult<K, V> ofMapValue(final K key, final V value) {
        Objects.requireNonNull(key, "Key");
        Objects.requireNonNull(value, "Value");
        return new MapComposedResultImpl<>(LinkedHashMap.of(key, Right(value)));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param exception
     * @return 
     */
    public static <K, V> MapComposedResult<K, V> ofMapException(final K key, final Exception exception) {
        Objects.requireNonNull(key, "Key");
        Objects.requireNonNull(exception, "Exception");
        return new MapComposedResultImpl<>(LinkedHashMap.of(key, Left(exception)));
    }

    /**
     * 
     * @param <V>
     * @param seq
     * @return 
     */
    public static <V> SeqComposedResult<V> ofSeq(final Iterable<Either<Exception, V>> seq) {
        Objects.requireNonNull(seq, "Seq");
        if (seq instanceof Vector vec) {
            return new SeqComposedResultImpl<>(vec);
        }
        return new SeqComposedResultImpl<>(Vector.ofAll(seq));
    }

    /**
     * 
     * @param <V>
     * @param other
     * @return 
     */
    public static <V> SeqComposedResult<V> ofSeq(final SeqComposedResult<V> other) {
        Objects.requireNonNull(other, "Other");
        return new SeqComposedResultImpl<>(other.entriesSeq().toVector());
    }

    /**
     * 
     * @param <V>
     * @return 
     */ /*
    public static <V> SeqComposedResult<V> ofSeqEmpty() {
        return ofSeq(Vector.empty());
    } */

    /**
     * 
     * @param <V>
     * @param value
     * @return 
     */
    public static <V> SeqComposedResult<V> ofSeqValue(final V value) {
        Objects.requireNonNull(value, "Value");
        return new SeqComposedResultImpl<>(Vector.of(Right(value)));
    }

    /**
     * 
     * @param <V>
     * @param exception
     * @return 
     */
    public static <V> SeqComposedResult<V> ofSeqException(final Exception exception) {
        Objects.requireNonNull(exception, "Exception");
        return new SeqComposedResultImpl<>(Vector.of(Left(exception)));
    }

    /**
     * 
     * @param <T>
     * @param futures
     * @return 
     */
    public static <T> BasicComposedResult<T> forFutures(final Iterable<? extends Future<T>> futures) {
        return forEntries(buildEntriesFromFutures(futures));
    }
    
    /**
     * 
     * @param <T>
     * @param futures
     * @return 
     */
    public static <T> SeqComposedResult<T> ofSeqFutures(final Iterable<? extends Future<T>> futures) {
        return ofSeqEntries(buildEntriesFromFutures(futures));
    }

    /**
     * 
     * @param <T>
     * @param futures
     * @return 
     */
    public static <T> SeqComposedResult<T> ofSeqCompletedFutures(final Iterable<? extends Future<T>> futures) {
        return ofSeqEntries(buildEntriesFromCompletedFutures(futures));
    }

    /**
     * 
     * @param <T>
     * @param futures
     * @return 
     */
    public static <T> BasicComposedResult<T> forCompletedFutures(final Iterable<? extends Future<T>> futures) {
        return forEntries(buildEntriesFromCompletedFutures(futures));
    }

    /**
     * 
     * @param <T>
     * @param entries
     * @return 
     */
    public static <T> BasicComposedResult<T> forEntries(final Iterable<Either<Exception, T>> entries) {
        Objects.requireNonNull(entries, "Entries");
        if (entries instanceof Vector vec) {
            return new BasicComposedResultImpl(vec);
        }
        return new BasicComposedResultImpl(Vector.ofAll(entries));
    }

    /**
     * 
     * @param <T>
     * @param entries
     * @return 
     */
    public static <T> SeqComposedResult<T> ofSeqEntries(final Iterable<Either<Exception, T>> entries) {
        Objects.requireNonNull(entries, "Entries");
        if (entries instanceof Vector vec) {
            return new SeqComposedResultImpl(vec);
        }
        return new SeqComposedResultImpl(Vector.ofAll(entries));
    }

    /**
     * 
     * @param <T>
     * @param values
     * @return 
     */
    public static <T> BasicComposedResult<T> forValues(final Iterable<T> values) {
        return forEntries(buildEntriesFromValues(values));
    }

    /**
     * 
     * @param <T>
     * @param cr
     * @return 
     */ /*
    protected static <T> String computeDiffMessage(final BasicComposedResult<T> cr) {
        final SeqComposedResult<T> seq = cr.toSeqComposedResult();
        final MapComposedResult<Integer, T> map = cr.toMapComposedResult();
        final TextStringBuilder sb = new TextStringBuilder();
        appendDiff(sb, "size", cr.entriesSeq().size(), seq.size(), map.size());
        appendDiff(sb, "entriesSeq", cr.entriesSeq(), seq.entriesSeq(), map.entriesSeq());
        appendDiff(sb, "entriesMap", cr.entriesMap(), seq.entriesMap(), map.entriesMap());
        appendDiff(sb, "succeededSeq", cr.succeededSeq(), seq.succeededSeq(), map.succeededSeq());
        appendDiff(sb, "succeededCount", cr.succeededCount(), seq.succeededCount(), map.succeededCount());
        appendDiff(sb, "succeededRatio", cr.succeededRatio(), seq.succeededRatio(), map.succeededRatio());
        appendDiff(sb, "failedSeq", cr.failedSeq(), seq.failedSeq(), map.failedSeq());
        appendDiff(sb, "failedCount", cr.failedCount(), seq.failedCount(), map.failedCount());
        appendDiff(sb, "failedRatio", cr.failedRatio(), seq.failedRatio(), map.failedRatio());
        appendDiff(sb, "isEmpty", cr.isEmpty(), seq.isEmpty(), map.isEmpty());
        appendDiff(sb, "isNotEmpty", cr.isNotEmpty(), seq.isNotEmpty(), map.isNotEmpty());
        appendDiff(sb, "isAllFailed", cr.isAllFailed(), seq.isAllFailed(), map.isAllFailed());
        appendDiff(sb, "isAllSucceeded", cr.isAllSucceeded(), seq.isAllSucceeded(), map.isAllSucceeded());
        appendDiff(sb, "isAllSucceededOrEmpty", cr.isAllSucceededOrEmpty(), seq.isAllSucceededOrEmpty(), map.isAllSucceededOrEmpty());
        appendDiff(sb, "isAnyFailed", cr.isAnyFailed(), seq.isAnyFailed(), map.isAnyFailed());
        appendDiff(sb, "isAnySucceeded", cr.isAnySucceeded(), seq.isAnySucceeded(), map.isAnySucceeded());
        appendDiff(sb, "isAnySucceededOrEmpty", cr.isAnySucceededOrEmpty(), seq.isAnySucceededOrEmpty(), map.isAnySucceededOrEmpty());
        if (sb.isNotEmpty()) {
            final TextStringBuilder header = new TextStringBuilder()
                    .append("*** ComposedResultDiff ***").appendNewLine()
                    .append("  ").append(cr.entriesSeq()).appendNewLine();
            sb.insert(0, header);
        }
        return sb.toString();
    } */
    
    /**
     * 
     * @param dest
     * @param field
     * @param crVal
     * @param seqVal
     * @param mapVal 
     */ /*
    static void appendDiff(final TextStringBuilder dest, 
            final String field, 
            final Object crVal, 
            final Object seqVal, 
            final Object mapVal) {
        final Object cr = normalized(crVal);
        final Object seq = normalized(seqVal);
        final Object map = normalized(mapVal);
        if (Objects.equals(cr, seq) && Objects.equals(cr, map)) {
            return;
        }
        dest.append("    ").append(field).append(":").appendNewLine()
                .append("       cr:").append(cr).appendNewLine()
                .append("      seq:").append(seq).appendNewLine()
                .append("      map:").append(map).appendNewLine();
    } */
    
    /**
     * 
     * @param val
     * @return 
     */
    static Object normalized(final Object val) {
        if (val == null) {
            return val;
        }
        if (val instanceof Iterable itr) {
            return Vector.ofAll(itr);
        }
        return val;
    }
    
    /**
     * 
     * @param <T>
     * @param f
     * @return 
     */
    public static <T> Either<Exception, T> newEntryFromFuture(final Future<T> f) {
        try {
            return Right(f.get(0, TimeUnit.MILLISECONDS));
        } catch (final TimeoutException e) {
            return Left(new IllegalStateException("Task not started or not completed"));
        } catch (final InterruptedException e) {
            return Left(e);
        } catch (final ExecutionException e) {
            return Left(unwrapped(e));
        }
    }

    /**
     * 
     * @param <T>
     * @param f
     * @return 
     */
    public static <T> Option<Either<Exception, T>> newEntryFromCompletedFuture(final Future<T> f) {
        try {
            return Some(Right(f.get(0, TimeUnit.MILLISECONDS)));
        } catch (final TimeoutException e) {
            return None();
        } catch (final InterruptedException e) {
            return Some(Left(e));
        } catch (final ExecutionException e) {
            return Some(Left(unwrapped(e)));
        }
    }
    
    /**
     * 
     * @param e
     * @return 
     */
    static Exception unwrapped(final ExecutionException e) {
        final Throwable t = e.getCause();
        if (t != null && t instanceof Exception ex) {
            return ex;
        }
        return e;
    }

    /**
     * 
     * @param <T>
     * @param futures
     * @return 
     */
    static <T> Seq<Either<Exception, T>> buildEntriesFromFutures(final Iterable<? extends Future<T>> futures) {
        final List<Either<Exception, T>> b = new ArrayList();
        for (final Future<T> f : futures) {
            b.add(newEntryFromFuture(f));
        }
        return Vector.ofAll(b);
    }

    /**
     * 
     * @param <T>
     * @param futures
     * @return 
     */
    static <T> Seq<Either<Exception, T>> buildEntriesFromCompletedFutures(final Iterable<? extends Future<T>> futures) {
        final List<Either<Exception, T>> b = new ArrayList();
        int timeouts = 0;
        for (final Future<T> f : futures) {
            final Option<Either<Exception, T>> e = newEntryFromCompletedFuture(f);
            if (e.isEmpty()) {
                timeouts++;
            } else {
                b.add(e.get());
            }
        }
        checkState(timeouts == 0, "There are still %s running threads, please wait for completion and retry.", timeouts);
        return Vector.ofAll(b);
    }
    
    static <T> Seq<Either<Exception, T>> buildEntriesFromValues(final Iterable<T> values) {
        /* final List<Either<Exception, T>> b = new ArrayList();
        for (final T value : values) {
            b.add(Right(value));
        } */
        return Vector.ofAll(values).map(v -> Right(v));
    }

}
