/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.CheckedFunction0;
import io.vavr.Tuple2;
import io.vavr.Value;
import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Iterator;
import io.vavr.collection.PriorityQueue;
import io.vavr.collection.Queue;
import io.vavr.collection.SortedMap;
import io.vavr.collection.SortedSet;
import io.vavr.collection.Tree;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.joytools.commons.util.Delegated;

/**
 *
 * @author AndreaR
 */
public abstract class DelegatedTaggedValue<E, T, V extends Value<E>> implements TaggedValue<E, T>, Delegated<V> {
    
    @Override
    public V delegated() {
        throw new UnsupportedOperationException();
    }

    public static <A, B> TaggedValue<A, B> of(final Value<A> value) {
        return of(value, None());
    }

    public static <A, B> TaggedValue<A, B> of(final Value<A> value,
            final B tag) {
        Objects.requireNonNull(tag, "Tag");
        return of(value, Some(tag));
    }

    public static <A, B> TaggedValue<A, B> of(final Value<A> value, final Option<B> tag) {
        Objects.requireNonNull(tag, "Tag");
        return new DelegatedTaggedValue<A, B, Value<A>>() {
            @Override
            public Value<A> delegated() {
                return value;
            }
            @Override
            public Option<B> getTag() {
                return tag;
            }
        };
    }
    
    @Override
    final public <U> TaggedValue<U, T> map(Function<? super E, ? extends U> mapper) {
        return of(delegated().map(mapper), getTag());
    }

    @Override
    final public TaggedValue<E, T> peek(Consumer<? super E> action) {
        return of(delegated().peek(action), getTag());
    }

    @Override
    final public <R, A> R collect(Collector<? super E, A, R> collector) {
        return delegated().collect(collector);
    }

    @Override
    final public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super E> accumulator, BiConsumer<R, R> combiner) {
        return delegated().collect(supplier, accumulator, combiner);
    }

    @Override
    final public boolean contains(E element) {
        return delegated().contains(element);
    }

    @Override
    final public <U> boolean corresponds(Iterable<U> that, BiPredicate<? super E, ? super U> predicate) {
        return delegated().corresponds(that, predicate);
    }

    @Override
    final public boolean eq(Object o) {
        return delegated().eq(o);
    }

    @Override
    final public boolean exists(Predicate<? super E> predicate) {
        return delegated().exists(predicate);
    }

    @Override
    final public boolean forAll(Predicate<? super E> predicate) {
        return delegated().forAll(predicate);
    }

    @Override
    final public void forEach(Consumer<? super E> action) {
        delegated().forEach(action);
    }

    @Override
    final public E get() {
        return delegated().get();
    }

    @Override
    final public E getOrElse(E other) {
        return delegated().getOrElse(other);
    }

    @Override
    final public E getOrElse(Supplier<? extends E> supplier) {
        return delegated().getOrElse(supplier);
    }

    @Override
    final public <X extends Throwable> E getOrElseThrow(Supplier<X> supplier) throws X {
        return delegated().getOrElseThrow(supplier);
    }

    @Override
    final public E getOrElseTry(CheckedFunction0<? extends E> supplier) {
        return delegated().getOrElseTry(supplier);
    }

    @Override
    final public E getOrNull() {
        return delegated().getOrNull();
    }

    @Override
    final public boolean isAsync() {
        return delegated().isAsync();
    }

    @Override
    final public boolean isEmpty() {
        return delegated().isEmpty();
    }

    @Override
    final public boolean isLazy() {
        return delegated().isLazy();
    }

    @Override
    final public boolean isSingleValued() {
        return delegated().isSingleValued();
    }

    @Override
    final public String stringPrefix() {
        return delegated().stringPrefix();
    }

    @Override
    final public void out(PrintStream out) {
        delegated().out(out);
    }

    @Override
    final public void out(PrintWriter writer) {
        delegated().out(writer);
    }

    @Override
    final public void stderr() {
        delegated().stderr();
    }

    @Override
    final public void stdout() {
        delegated().stdout();
    }

    @Override
    final public Iterator<E> iterator() {
        return delegated().iterator();
    }

    @Override
    final public Array<E> toArray() {
        return delegated().toArray();
    }

    @Override
    final public CharSeq toCharSeq() {
        return delegated().toCharSeq();
    }

    @Override
    final public CompletableFuture<E> toCompletableFuture() {
        return delegated().toCompletableFuture();
    }

    @Override
    final public <U> Validation<E, U> toInvalid(U value) {
        return delegated().toInvalid(value);
    }

    @Override
    final public <U> Validation<E, U> toInvalid(Supplier<? extends U> valueSupplier) {
        return delegated().toInvalid(valueSupplier);
    }

    @Override
    final public Object[] toJavaArray() {
        return delegated().toJavaArray();
    }

    @Override
    final public E[] toJavaArray(Class<E> componentType) {
        return delegated().toJavaArray(componentType);
    }

    @Override
    final public E[] toJavaArray(IntFunction<E[]> arrayFactory) {
        return delegated().toJavaArray(arrayFactory);
    }

    @Override
    final public <C extends Collection<E>> C toJavaCollection(Function<Integer, C> factory) {
        return delegated().toJavaCollection(factory);
    }

    @Override
    final public List<E> toJavaList() {
        return delegated().toJavaList();
    }

    @Override
    final public <LIST extends List<E>> LIST toJavaList(Function<Integer, LIST> factory) {
        return delegated().toJavaList(factory);
    }

    @Override
    final public <K, V> Map<K, V> toJavaMap(Function<? super E, ? extends Tuple2<? extends K, ? extends V>> f) {
        return delegated().toJavaMap(f);
    }

    @Override
    final public <K, V, MAP extends Map<K, V>> MAP toJavaMap(Supplier<MAP> factory, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
        return delegated().toJavaMap(factory, keyMapper, valueMapper);
    }

    @Override
    final public <K, V, MAP extends Map<K, V>> MAP toJavaMap(Supplier<MAP> factory, Function<? super E, ? extends Tuple2<? extends K, ? extends V>> f) {
        return delegated().toJavaMap(factory, f);
    }

    @Override
    final public Optional<E> toJavaOptional() {
        return delegated().toJavaOptional();
    }

    @Override
    final public Set<E> toJavaSet() {
        return delegated().toJavaSet();
    }

    @Override
    final public <SET extends Set<E>> SET toJavaSet(Function<Integer, SET> factory) {
        return TaggedValue.super.toJavaSet(factory); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    final public Stream<E> toJavaStream() {
        return TaggedValue.super.toJavaStream(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    final public Stream<E> toJavaParallelStream() {
        return TaggedValue.super.toJavaParallelStream(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    final public <R> Either<E, R> toLeft(R right) {
        return delegated().toLeft(right);
    }

    @Override
    final public <R> Either<E, R> toLeft(Supplier<? extends R> right) {
        return delegated().toLeft(right);
    }

    @Override
    final public io.vavr.collection.List<E> toList() {
        return delegated().toList();
    }

    @Override
    final public <K, V> io.vavr.collection.Map<K, V> toMap(Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
        return delegated().toMap(keyMapper, valueMapper);
    }

    @Override
    final public <K, V> io.vavr.collection.Map<K, V> toMap(Function<? super E, ? extends Tuple2<? extends K, ? extends V>> f) {
        return delegated().toMap(f);
    }

    @Override
    final public <K, V> io.vavr.collection.Map<K, V> toLinkedMap(Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
        return delegated().toLinkedMap(keyMapper, valueMapper);
    }

    @Override
    final public <K, V> io.vavr.collection.Map<K, V> toLinkedMap(Function<? super E, ? extends Tuple2<? extends K, ? extends V>> f) {
        return delegated().toLinkedMap(f);
    }

    @Override
    final public <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
        return delegated().toSortedMap(keyMapper, valueMapper);
    }

    @Override
    final public <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(Function<? super E, ? extends Tuple2<? extends K, ? extends V>> f) {
        return delegated().toSortedMap(f);
    }

    @Override
    final public <K, V> SortedMap<K, V> toSortedMap(Comparator<? super K> comparator, Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
        return delegated().toSortedMap(comparator, keyMapper, valueMapper);
    }

    @Override
    final public <K, V> SortedMap<K, V> toSortedMap(Comparator<? super K> comparator, Function<? super E, ? extends Tuple2<? extends K, ? extends V>> f) {
        return delegated().toSortedMap(comparator, f);
    }

    @Override
    final public Option<E> toOption() {
        return delegated().toOption();
    }

    @Override
    final public <L> Either<L, E> toEither(L left) {
        return delegated().toEither(left);
    }

    @Override
    final public <L> Either<L, E> toEither(Supplier<? extends L> leftSupplier) {
        return delegated().toEither(leftSupplier);
    }

    @Override
    final public <X> Validation<X, E> toValidation(X invalid) {
        return delegated().toValidation(invalid);
    }

    @Override
    final public <X> Validation<X, E> toValidation(Supplier<? extends X> invalidSupplier) {
        return delegated().toValidation(invalidSupplier);
    }

    @Override
    final public Queue<E> toQueue() {
        return delegated().toQueue();
    }

    @Override
    final public PriorityQueue<E> toPriorityQueue() {
        return delegated().toPriorityQueue();
    }

    @Override
    final public PriorityQueue<E> toPriorityQueue(Comparator<? super E> comparator) {
        return delegated().toPriorityQueue(comparator);
    }

    @Override
    final public <L> Either<L, E> toRight(L left) {
        return delegated().toRight(left);
    }

    @Override
    final public <L> Either<L, E> toRight(Supplier<? extends L> left) {
        return delegated().toRight(left);
    }

    @Override
    final public io.vavr.collection.Set<E> toSet() {
        return delegated().toSet();
    }

    @Override
    final public io.vavr.collection.Set<E> toLinkedSet() {
        return delegated().toLinkedSet();
    }

    @Override
    final public SortedSet<E> toSortedSet() throws ClassCastException {
        return delegated().toSortedSet();
    }

    @Override
    final public SortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return delegated().toSortedSet();
    }

    @Override
    final public io.vavr.collection.Stream<E> toStream() {
        return delegated().toStream();
    }

    @Override
    final public Try<E> toTry() {
        return delegated().toTry();
    }

    @Override
    final public Try<E> toTry(Supplier<? extends Throwable> ifEmpty) {
        return delegated().toTry(ifEmpty);
    }

    @Override
    final public Tree<E> toTree() {
        return delegated().toTree();
    }

    @Override
    final public <ID> io.vavr.collection.List<Tree.Node<E>> toTree(Function<? super E, ? extends ID> idMapper, Function<? super E, ? extends ID> parentMapper) {
        return delegated().toTree(idMapper, parentMapper);
    }

    @Override
    final public <X> Validation<X, E> toValid(X error) {
        return delegated().toValid(error);
    }

    @Override
    final public <X> Validation<X, E> toValid(Supplier<? extends X> errorSupplier) {
        return delegated().toValid(errorSupplier);
    }

    @Override
    final public Vector<E> toVector() {
        return delegated().toVector();
    }

    @Override
    final public Spliterator<E> spliterator() {
        return delegated().spliterator();
    }

}
