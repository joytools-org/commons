/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public class KeyedCompletionService<K, V> {
    
    /**
     * 
     * @param cs 
     */
    protected KeyedCompletionService(final CompletionService<V> cs) {
        this.cs = Objects.requireNonNull(cs, "Completion Service");
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param cs
     * @return 
     */
    public static <K, V> KeyedCompletionService<K, V> on(final CompletionService<V> cs) {
        return new KeyedCompletionService(cs);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param es
     * @return 
     */
    public static <K, V> KeyedCompletionService<K, V> on(final ExecutorService es) {
        Objects.requireNonNull(es, "ExecutorService");
        final ExecutorCompletionService<V> ecs = new ExecutorCompletionService(es);
        return new KeyedCompletionService(ecs);
    }

    /**
     * 
     * @param key
     * @param task
     * @return 
     */
    public Tuple2<K, Future<V>> submit(final K key, final Callable<V> task) {
        Objects.requireNonNull(key, "Key");
        Objects.requireNonNull(task, "Task");
        return submit0(key, c -> c.submit(task));
        /* synchronized(future2Keys) {
            if (keys.contains(key)) {
                throw new IllegalArgumentException("Duplicated key: " + key);
            }
            final Future<V> f = cs.submit(task);
            future2Keys.put(f, key);
            keys.add(key);
            return Pair.of(key, f);
        } */
    }

    /**
     * 
     * @param key
     * @param task
     * @param result
     * @return 
     */
    public Tuple2<K, Future<V>> submit(final K key, final Runnable task, V result) {
        Objects.requireNonNull(key, "Key");
        Objects.requireNonNull(task, "Task");
        return submit0(key, c -> c.submit(task, result));
        /*
        synchronized(future2Keys) {
            if (keys.contains(key)) {
                throw new IllegalArgumentException("Duplicated key: " + key);
            }
            final Future<V> f = cs.submit(task, result);
            future2Keys.put(f, key);
            keys.add(key);
            return Pair.of(key, f);
        } */
    }
    
    /**
     * 
     * @param key
     * @param submitter
     * @return 
     */
    protected Tuple2<K, Future<V>> submit0(final K key, final Function<CompletionService<V>, Future<V>> submitter) {
        synchronized(future2Keys) {
            if (keys.contains(key)) {
                throw new IllegalArgumentException("Duplicated key: " + key);
            }
            final Future<V> f = submitter.apply(cs);
            future2Keys.put(f, key);
            keys.add(key);
            return Tuple(key, f);
        }
    }

    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    public Tuple2<K, Future<V>> poll() throws InterruptedException {
        final Future<V> f = cs.poll();
        if (f == null) {
            return null;
        }
        return remove0(f);
    }
    
    /**
     * 
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException 
     */
    public Tuple2<K, Future<V>> poll(final long timeout, final TimeUnit unit) throws InterruptedException {
        final Future<V> f = cs.poll(timeout, unit);
        if (f == null) {
            return null;
        }
        return remove0(f);
    }

    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    public Tuple2<K, Future<V>> take() throws InterruptedException {
        final Future<V> f = cs.take();
        return remove0(f);
    }
    
    /**
     * 
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @throws TimeoutException 
     */
    public Tuple2<K, Future<V>> take(final long timeout, final TimeUnit unit) throws InterruptedException, TimeoutException {
        final Future<V> f = cs.poll(timeout, unit);
        if (f == null) {
            throw new TimeoutException();
        }
        return remove0(f);
    }

    /**
     * 
     * @param f
     * @return
     * @throws InterruptedException 
     */
    protected Tuple2<K, Future<V>> remove0(final Future<V> f) throws InterruptedException {
        synchronized(future2Keys) {
            final K key = future2Keys.remove(f);
            if (key == null) {
                throw new UnsupportedOperationException("Future not found");
            }
            return Tuple(key, f);
        }
    }

    /**
     * 
     * @return 
     */
    public int pendingTasks() {
        synchronized(future2Keys) {
            return future2Keys.size();
        }
    }

    /**
     * 
     * @return 
     */
    public boolean isEmpty() {
        return pendingTasks() == 0;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isNotEmpty() {
        return pendingTasks() > 0;
    }

    /**
     * 
     */
    private final CompletionService<V> cs;   
    
    /**
     * 
     */
    private final Set<K> keys = new HashSet<>();

    /**
     * 
     */
    private final IdentityHashMap<Future<V>, K> future2Keys = new IdentityHashMap<>();
    
}
