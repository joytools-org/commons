/*
 * ____  ______________  ________________________  __________
 * \   \/   /      \   \/   /   __/   /      \   \/   /      \
 *  \______/___/\___\______/___/_____/___/\___\______/___/\___\
 *
 * Copyright 2021 Vavr, https://vavr.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joytools.vavr;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.PartialFunction;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.vavr.PartialFunction;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Replacement for {@link java.util.Optional}.
 * <p>
 * TaggedOption is a <a href="http://stackoverflow.com/questions/13454347/monads-with-java-8">monadic</a> container type which
 * represents an optional value. Instances of TaggedOption are either an instance of {@link TaggedSome} or the
 * singleton {@link TaggedNone}.
 * <p>
 * Most of the API is taken from {@link java.util.Optional}. A similar type can be found in <a
 * href="http://hackage.haskell.org/package/base-4.6.0.1/docs/Data-Maybe.html">Haskell</a> and <a
 * href="http://www.scala-lang.org/api/current/#scala.TaggedOption">Scala</a>.
 *
 * @param <O> The type of the optional value.
 */
@SuppressWarnings("deprecation")
public abstract class TaggedOption<O, T> implements Iterable<O>, TaggedValue<O, T>, Serializable {

    private static final long serialVersionUID = 1L;

    // sealed
    private TaggedOption() {}

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @return 
     */
    public static <E, T> TaggedOption<E, T> of(E value) {
        return of(value, null);
    }

    /**
     * Creates a new {@code TaggedOption} of a given value.
     *
     * <pre>{@code
     * // = Some(3), an TaggedOption which contains the value 3
     * TaggedOption<Integer> option = TaggedOption.of(3);
     *
     * // = None, the empty TaggedOption
     * TaggedOption<Integer> none = TaggedOption.of(null);
     * }</pre>
     *
     * @param value A value
     * @param <E>   type of the value
     * @return {@code Some(value)} if value is not {@code null}, {@code None} otherwise
     */
    public static <E, T> TaggedOption<E, T> of(E value, T tag) {
        return (value == null) ? none() : some(value, tag);
    }

    /**
     * Reduces many {@code TaggedOption}s into a single {@code TaggedOption} by transforming an
     * {@code Iterable<TaggedOption<? extends T>>} into a {@code TaggedOption<Seq<T>>}. If any of
     * the TaggedOptions are {@link TaggedOption.TaggedNone}, then this returns {@link TaggedOption.TaggedNone}.
     *
     * <pre>{@code
     * Seq<TaggedOption<Integer>> seq = Vector.of(TaggedOption.of(1), TaggedOption.of(2), TaggedOption.of(3));
     *
     * // = Some(Seq(1, 2, 3))
     * TaggedOption<Seq<Integer>> option = TaggedOption.sequence(seq);
     *
     * Seq<TaggedOption<Integer>> seq = Vector.of(TaggedOption.of(1), TaggedOption.none());
     *
     * // = None since some elements in the Iterable are None
     * TaggedOption<Seq<Integer>> option = TaggedOption.sequence(seq);
     * }</pre>
     * @param values An {@code Iterable} of {@code TaggedOption}s
     * @param <E>    type of the TaggedOptions
     * @return An {@code TaggedOption} of a {@link Seq} of results
     * @throws NullPointerException if {@code values} is null
     */
    public static <E, T> TaggedOption<Seq<E>, Seq<Option<T>>> sequence(Iterable<? extends TaggedOption<? extends E, T>> values) {
        Objects.requireNonNull(values, "values is null");
        Vector<E> newValues = Vector.empty();
        Vector<Option<T>> newTags = Vector.empty();
        for (TaggedOption<? extends E, T> value : values) {
            if (value.isEmpty()) {
                return TaggedOption.none();
            }
            newValues = newValues.append(value.get());
            newTags = newTags.append(value.getTag());
        }
        return TaggedOption.some(newValues, newTags);
    }

    /**
     * Maps the values of an iterable to a sequence of mapped values into a single {@code TaggedOption} by
     * transforming an {@code Iterable<? extends T>} into a {@code TaggedOption<Seq<U>>}.
     *
     * <pre>{@code
     * Function<Integer, TaggedOption<String>> mapper = i -> {
     *      if (i <= 0) {
     *          return TaggedOption.none();
     *      }
     *      return TaggedOption.of("a" = i.toString());
     * }
     *
     * // = Some(Seq("a1", "a2", "a3"))
     * TaggedOption<Seq<String>> option = traverse(Vector.of(1, 2, 3), mapper);
     *
     * // = None
     * TaggedOption<Seq<Integer>> none = traverse(Vector.of(-1, 0, 1), mapper);
     * }</pre>
     *
     * @param values   An {@code Iterable} of values.
     * @param mapper   A mapper of values to TaggedOptions
     * @param <E>      The type of the given values.
     * @param <U>      The mapped value type.
     * @return A {@code TaggedOption} of a {@link Seq} of results.
     * @throws NullPointerException if values or f is null.
     */
    public static <E, T, U> TaggedOption<Seq<U>, T> traverse(Iterable<? extends E> values, Function<? super E, ? extends TaggedOption<? extends U, T>> mapper) {
        throw new UnsupportedOperationException();
        /* Objects.requireNonNull(values, "values is null");
        Objects.requireNonNull(mapper, "mapper is null");
        return sequence(Iterator.ofAll(values).map(mapper)); */
    }

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @return 
     */
    public static <E, T> TaggedOption<E, T> some(E value) {
        return new TaggedSome<>(value);
    }

    /**
     * Creates a new {@code Some} of a given value.
     * <p>
     * The only difference to {@link TaggedOption#of(Object)} is, when called with argument {@code null}.
     *
     * <pre>{@code
     * // = Some(3)
     * TaggedOption.some(3);
     *
     * // = Some(null)
     * TaggedOption.some(null);
     * }</pre>
     *
     * @param value A value
     * @param <E>   type of the value
     * @return {@code Some(value)}
     */
    public static <E, T> TaggedOption<E, T> some(E value, T tag) {
        return new TaggedSome<>(value, tag);
    }

    public static <E, T> TaggedOption<E, T> some(E value, Option<T> tag) {
        return new TaggedSome<>(value, tag);
    }

    /**
     * Returns the single instance of {@code None}
     *
     * <pre>{@code
     * // = None
     * TaggedOption<String> none = TaggedOption.none();
     * }</pre>
     *
     * @param <E> component type
     * @return the single instance of {@code None}
     */
    public static <E, T> TaggedOption<E, T> none() {
        @SuppressWarnings("unchecked")
        final TaggedNone<E, T> none = (TaggedNone<E, T>) TaggedNone.INSTANCE;
        return none;
    }

    /**
     * Narrows a widened {@code TaggedOption<? extends T>} to {@code TaggedOption<T>}
     * by performing a type-safe cast. This is eligible because immutable/read-only
     * collections are covariant.
     *
     * <pre>{@code
     * TaggedOption<Integer> option = TaggedOption.of(3);
     * // Narrow to an TaggedOption of Number
     * TaggedOption<Number> narrowed = TaggedOption.narrow(option);
     * }</pre>
     *
     * @param option A {@code TaggedOption}.
     * @param <E>    Component type of the {@code TaggedOption}.
     * @return the given {@code option} instance as narrowed type {@code TaggedOption<T>}.
     */
    @SuppressWarnings("unchecked")
    public static <E, T> TaggedOption<E, T> narrow(TaggedOption<? extends E, T> option) {
        return (TaggedOption<E, T>) option;
    }

    public static <E, T> TaggedOption<E, T> when(boolean condition, Supplier<? extends E> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return condition ? some(supplier.get()) : none();
    }

    /**
     * Creates {@code Some} of suppliers value if condition is true, or {@code None} in other case
     *
     * <pre>{@code
     * Supplier<String> supplier = () -> "supplied";
     *
     * // = Some("supplied")
     * TaggedOption<String> supplied = TaggedOption.when(true, supplier);
     *
     * // = None
     * TaggedOption<String> none = TaggedOption.when(false, supplier);
     * }</pre>
     *
     * @param <E>       type of the optional value
     * @param condition A boolean value
     * @param supplier  An optional value supplier, may supply {@code null}
     * @return return {@code Some} of supplier's value if condition is true, or {@code None} in other case
     * @throws NullPointerException if the given {@code supplier} is null
     */
    public static <E, T> TaggedOption<E, T> when(boolean condition, Supplier<? extends E> supplier, T tag) {
        Objects.requireNonNull(supplier, "supplier is null");
        return condition ? some(supplier.get(), tag) : none();
    }

    /**
     * 
     * @param <E>
     * @param <T>
     * @param condition
     * @param value
     * @return 
     */
    public static <E, T> TaggedOption<E, T> when(boolean condition, E value) {
        return condition ? some(value, null) : none();
    }

    /**
     * Creates {@code Some} of value if condition is true, or {@code None} in other case
     *
     * <pre>{@code
     * // = Some(5)
     * TaggedOption<Integer> option = TaggedOption.when(true, 5);
     *
     * // = None
     * TaggedOption<Integer> none = TaggedOption.when(false, 5);
     * }</pre>
     *
     * @param <T>       type of the optional value
     * @param condition A boolean value
     * @param value     An optional value, may be {@code null}
     * @return return {@code Some} of value if condition is true, or {@code None} in other case
     */
    public static <E, T> TaggedOption<E, T> when(boolean condition, E value, T tag) {
        return condition ? some(value, tag) : none();
    }

    /**
     * Collects value that is in the domain of the given {@code partialFunction} by mapping the value to type {@code R}.
     *
     * <pre>{@code
     * partialFunction.isDefinedAt(value)
     * }</pre>
     *
     * If the element makes it through that filter, the mapped instance is wrapped in {@code TaggedOption}
     *
     * <pre>{@code
     * R newValue = partialFunction.apply(value)
     * }</pre>
     *
     *
     * @param partialFunction A function that is not necessarily defined on value of this option.
     * @param <R> The new value type
     * @return A new {@code TaggedOption} instance containing value of type {@code R}
     * @throws NullPointerException if {@code partialFunction} is null
     */
    public final <R, T> TaggedOption<R, T> collect(PartialFunction<? super O, ? extends R> partialFunction) {
        throw new UnsupportedOperationException();
        /* Objects.requireNonNull(partialFunction, "partialFunction is null");
        return flatMap(partialFunction.lift()); */
    }

    /**
     * Returns true, if this is {@code None}, otherwise false, if this is {@code Some}.
     *
     * <pre>{@code
     * // Prints "false"
     * System.out.println(TaggedOption.of(10).isEmpty());
     *
     * // Prints "true"
     * System.out.println(TaggedOption.none().isEmpty());
     * }</pre>
     *
     * @return true, if this {@code TaggedOption} is empty, false otherwise
     */
    @Override
    public abstract boolean isEmpty();

    /**
     * Runs a Java Runnable passed as parameter if this {@code TaggedOption} is empty.
     *
     * <pre>{@code
     * Runnable print = () -> System.out.println("TaggedOption is empty");
     *
     * // Prints nothing
     * TaggedOption.of("value").onEmpty(print);
     *
     * // Prints "TaggedOption is empty"
     * TaggedOption.none().onEmpty(print);
     * }</pre>
     *
     * @param action a given Runnable to be run
     * @return this {@code TaggedOption}
     */
    public final TaggedOption<O, T> onEmpty(Runnable action) {
        Objects.requireNonNull(action, "action is null");
        if (isEmpty()) {
            action.run();
        }
        return this;
    }

    /**
     * An {@code TaggedOption}'s value is computed synchronously.
     *
     * <pre>{@code
     * // Prints "false"
     * System.out.println(TaggedOption.of(1).isAsync());
     *
     * // Prints "false"
     * System.out.println(TaggedOption.none().isAsync());
     * }</pre>
     *
     * @return false
     */
    @Override
    public final boolean isAsync() {
        return false;
    }

    /**
     * Returns true, if this is {@code Some}, otherwise false, if this is {@code None}.
     * <p>
     * Please note that it is possible to create {@code new Some(null)}, which is defined.
     *
     * <pre>{@code
     * // Prints "true"
     * System.out.println(TaggedOption.of(10).isDefined());
     *
     * // Prints "false"
     * System.out.println(TaggedOption.none().isDefined());
     *
     * // Prints "true
     * System.out.println(TaggedOption.of(null).isDefined());
     * }</pre>
     *
     * @return true, if this {@code TaggedOption} has a defined value, false otherwise
     */
    public final boolean isDefined() {
        return !isEmpty();
    }

    /**
     * An {@code TaggedOption}'s value is computed eagerly.
     *
     * <pre>{@code
     * // Prints "false"
     * System.out.println(TaggedOption.of(3.14).isLazyOpen());
     *
     * // Prints "false"
     * System.out.println(TaggedOption.none().isLazyOpen());
     * }</pre>
     *
     * @return false
     */
    @Override
    public final boolean isLazy() {
        return false;
    }

    /**
     * An {@code TaggedOption} is single-valued.
     *
     * <pre>{@code
     * // Prints "true"
     * System.out.println(TaggedOption.of("value").isSingleValued());
     *
     * // Prints "true"
     * System.out.println(TaggedOption.none().isSingleValued());
     * }</pre>
     *
     * @return {@code true}
     */
    @Override
    public final boolean isSingleValued() {
        return true;
    }

    /**
     * Gets the value if this is a {@code Some} or throws if this is a {@code None}.
     *
     * <pre>{@code
     * // Prints "57"
     * System.out.println(TaggedOption.of(57).get());
     *
     * // Throws a NoSuchElementException
     * TaggedOption.none().get();
     * }</pre>
     *
     * @return the value
     * @throws NoSuchElementException if this is a {@code None}.
     */
    @Override
    public abstract O get();

    /**
     * Returns the value if this is a {@code Some} or the {@code other} value if this is a {@code None}.
     * <p>
     * Please note, that the other value is eagerly evaluated.
     *
     * <pre>{@code
     * // Prints "Hello"
     * System.out.println(TaggedOption.of("Hello").getOrElse("World"));
     *
     * // Prints "World"
     * TaggedOption.none().getOrElse("World");
     * }</pre>
     *
     * @param other An alternative value
     * @return This value, if this TaggedOption is defined or the {@code other} value, if this TaggedOption is empty.
     */
    @Override
    public final O getOrElse(O other) {
        return isEmpty() ? other : get();
    }

    /**
     * Returns this {@code TaggedOption} if it is nonempty, otherwise return the alternative.
     *
     * <pre>{@code
     * TaggedOption<String> other = TaggedOption.of("Other");
     *
     * // = Some("Hello World")
     * TaggedOption.of("Hello World").orElse(other);
     *
     * // = Some("Other")
     * TaggedOption.none().orElse(other);
     * }</pre>
     *
     * @param other An alternative {@code TaggedOption}
     * @return this {@code TaggedOption} if it is nonempty, otherwise return the alternative.
     */
    @SuppressWarnings("unchecked")
    public final TaggedOption<O, T> orElse(TaggedOption<? extends O, T> other) {
        Objects.requireNonNull(other, "other is null");
        return isEmpty() ? (TaggedOption<O, T>) other : this;
    }

    /**
     * Returns this {@code TaggedOption} if it is nonempty, otherwise return the result of evaluating supplier.
     *
     * <pre>{@code
     * Supplier<TaggedOption<Integer>> supplier = () -> TaggedOption.of(5);
     *
     * // = Some(2)
     * TaggedOption.of(2).orElse(supplier);
     *
     * // = Some(5)
     * TaggedOption.none().orElse(supplier);
     * }</pre>
     *
     * @param supplier An alternative {@code TaggedOption} supplier
     * @return this {@code TaggedOption} if it is nonempty, otherwise return the result of evaluating supplier.
     */
    @SuppressWarnings("unchecked")
    public final TaggedOption<O, T> orElse(Supplier<? extends TaggedOption<? extends O, T>> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return isEmpty() ? (TaggedOption<O, T>) supplier.get() : this;
    }

    /**
     * Returns this {@code TaggedOption} if this is defined, or {@code null} if it is empty.
     *
     * <pre>{@code
     * // = Some("Hello World")
     * TaggedOption.of("Hello World").orNull();
     *
     * // = null
     * TaggedOption.none().orNull();
     * }</pre>
     *
     * @return this value if it is defined, or {@code null} if it is empty.
     */
    public final O orNull() {
        return isEmpty() ? null : get();
    }

    /**
     * Returns the value if this is a {@code Some}, otherwise {@code supplier.get()} is returned.
     * <p>
     * Please note, that the alternate value is lazily evaluated.
     *
     * <pre>{@code
     * Supplier<Double> supplier = () -> 5.342;
     *
     * // = 1.2
     * TaggedOption.of(1.2).getOrElse(supplier);
     *
     * // = 5.342
     * TaggedOption.none().getOrElse(supplier);
     * }</pre>
     *
     * @param supplier An alternative value supplier
     * @return This value, if this TaggedOption is defined or the {@code other} value, if this TaggedOption is empty.
     */
    @Override
    public final O getOrElse(Supplier<? extends O> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return isEmpty() ? supplier.get() : get();
    }

    /**
     * Returns the value if this is a {@code Some}, otherwise throws an exception.
     *
     * <pre>{@code
     * Supplier<RuntimeException> supplier = () -> new RuntimeException();
     *
     * // = 12
     * TaggedOption.of(12).getOrElseThrow(supplier);
     *
     * // throws RuntimeException
     * TaggedOption.none().getOrElseThrow(supplier);
     * }</pre>
     *
     * @param exceptionSupplier An exception supplier
     * @param <X>               A throwable
     * @return This value, if this TaggedOption is defined, otherwise throws X
     * @throws X a throwable
     */
    @Override
    public final <X extends Throwable> O getOrElseThrow(Supplier<X> exceptionSupplier) throws X {
        Objects.requireNonNull(exceptionSupplier, "exceptionSupplier is null");
        if (isEmpty()) {
            throw exceptionSupplier.get();
        } else {
            return get();
        }
    }

    /**
     * Returns {@code Some(value)} if this is a {@code Some} and the value satisfies the given predicate.
     * Otherwise {@code None} is returned.
     *
     * <pre>{@code
     * Predicate<Integer> isLessThanTen = i -> i < 10;
     *
     * // = Some(8)
     * TaggedOption.some(8).filter(isLessThanTen);
     *
     * // = None
     * TaggedOption.some(12).filter(isLessThanTen);
     *
     * // = None
     * TaggedOption.<Integer>none().filter(isLessThanTen);
     * }</pre>
     *
     * @param predicate A predicate which is used to test an optional value
     * @return {@code Some(value)} or {@code None} as specified
     */
    public final TaggedOption<O, T> filter(Predicate<? super O> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return isEmpty() || predicate.test(get()) ? this : none();
    }

    /**
     * Returns {@code Some(value)} if this is a {@code Some} and the value not satisfies the given predicate.
     * Otherwise {@code None} is returned.
     *
     * <pre>{@code
     * Predicate<Integer> isEven = i -> (i & 1) == 0;
     *
     * // = Some(5)
     * TaggedOption.some(5).filterNot(isEven);
     *
     * // = None
     * TaggedOption.some(12).filterNot(isEven);
     *
     * // = None
     * TaggedOption.<Integer>none().filterNot(isEven);
     * }</pre>
     *
     * @param predicate A predicate which is used to test an optional value
     * @return {@code Some(value)} or {@code None} as specified
     */
    public final TaggedOption<O, T> filterNot(Predicate<? super O> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return filter(predicate.negate());
    }

    /**
     * Maps the value to a new {@code TaggedOption} if this is a {@code Some}, otherwise returns {@code None}.
     *
     * <pre>{@code
     * Function<Integer, TaggedOption<Integer>> mapper = i -> i < 10 ? TaggedOption.of(i * 2) : TaggedOption.none();
     *
     * // = Some(14)
     * TaggedOption.of(7).flatMap(mapper);
     *
     * // = None
     * TaggedOption.of(11).flatMap(mapper);
     *
     * // = None
     * TaggedOption.<Integer>none().flatMap(mapper);
     * }</pre>
     *
     * @param mapper A mapper
     * @param <U>    Component type of the resulting TaggedOption
     * @return a new {@code TaggedOption}
     */
    @SuppressWarnings("unchecked")
    public final <U> TaggedOption<U, T> flatMap(Function<? super O, ? extends TaggedOption<? extends U, T>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return isEmpty() ? none() : (TaggedOption<U, T>) mapper.apply(get());
    }

    /**
     * Maps the value and wraps it in a new {@code Some} if this is a {@code Some}, otherwise returns a {@code None}.
     *
     * <pre>{@code
     * Function<String, String> mapper = s -> s + " World!";
     *
     * // = Some("Hello World!")
     * TaggedOption.of("Hello").map(mapper);
     *
     * // = None
     * TaggedOption.<String>none().map(mapper);
     * }</pre>
     *
     * @param mapper A value mapper
     * @param <U>    The new value type
     * @return a new {@code Some} containing the mapped value if this TaggedOption is defined, otherwise {@code None}, if this is empty.
     */
    @Override
    public final <U> TaggedOption<U, T> map(Function<? super O, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return isEmpty() ? none() : some(mapper.apply(get()), getTag());
    }

    /**
     * Folds either the {@code None} or the {@code Some} side of the TaggedOption value.
     *
     * <pre>{@code
     * Supplier<Double> ifNone = () -> 3.14;
     * Function<String, Double> mapper = s -> Double.valueOf(s) + 0.98;
     *
     * // = Some(4.98)
     * TaggedOption.of("4").fold(ifNone, mapper);
     *
     * // = Some(3.14)
     * TaggedOption.<String>none().fold(ifNone, mapper);
     * }</pre>
     *
     * @param ifNone  maps the left value if this is a None
     * @param f maps the value if this is a Some
     * @param <U>         type of the folded value
     * @return A value of type U
     */
    public final <U> U fold(Supplier<? extends U> ifNone, Function<? super O, ? extends U> f) {
        return this.<U>map(f).getOrElse(ifNone);
    }

    /**
     * Performs the given {@code noneAction} if this option is not defined.
     * Performs the given {@code someAction} to this value, if this option is defined.
     *
     * @param noneAction The action that will be performed on the left element
     * @param someAction The action that will be performed on the right element
     * @return this instance
     */
    public final TaggedOption<O, T> peek(Runnable noneAction, Consumer<? super O> someAction) {
        Objects.requireNonNull(noneAction, "noneAction is null");
        Objects.requireNonNull(someAction, "someAction is null");

        if (isEmpty()) {
            noneAction.run();
        } else {
            someAction.accept(get());
        }

        return this;
    }

    /**
     * Applies an action to this value, if this option is defined, otherwise does nothing.
     *
     * <pre>{@code
     * Consumer<Integer> print = i -> System.out.println(i);
     *
     * // Prints 5 and creates Some(8)
     * TaggedOption.of(5).peek(print).map(i -> i + 3);
     *
     * // Does not print anything
     * TaggedOption.<Integer>none().peek(print);
     * }</pre>
     *
     * @param action An action which can be applied to an optional value
     * @return this {@code TaggedOption}
     */
    @Override
    public final TaggedOption<O, T> peek(Consumer<? super O> action) {
        Objects.requireNonNull(action, "action is null");
        if (isDefined()) {
            action.accept(get());
        }
        return this;
    }

    /**
     * Transforms this {@code TaggedOption}.
     *
     * <pre>{@code
     * Function<TaggedOption<Integer>, String> f = o -> o.getOrElse(3).toString().concat("-transformed"));
     *
     * // Prints "1-transformed"
     * System.out.println(TaggedOption.of(1).transform(f));
     *
     * // Prints "3-transformed"
     * System.out.println(TaggedOption.<Integer>none().transform(f));
     * }</pre>
     *
     * @param f   A transformation
     * @param <U> Type of transformation result
     * @return An instance of type {@code U}
     * @throws NullPointerException if {@code f} is null
     */
    public final <U> U transform(Function<? super TaggedOption<O, T>, ? extends U> f) {
        Objects.requireNonNull(f, "f is null");
        return f.apply(this);
    }

    @Override
    public final Iterator<O> iterator() {
        return isEmpty() ? Iterator.empty() : Iterator.of(get());
    }

    /**
     * Some represents a defined {@link TaggedOption}. It contains a value which may be null. However, to
     * create an TaggedOption containing null, {@code new Some(null)} has to be called. In all other cases
     * {@link TaggedOption#of(Object)} is sufficient.
     *
     * @param <O> The type of the optional value.
     * @deprecated will be removed from the public API
     */
    @Deprecated
    public static final class TaggedSome<O, T> extends TaggedOption<O, T> implements Serializable {

        private static final long serialVersionUID = 1L;

        private final O value;

        private final Option<T> tag;

        /**
         * Creates a new Some containing the given value.
         *
         * @param value A value, may be null
         */
        private TaggedSome(O value, T tag) {
            this.value = value;
            this.tag = Some(tag);
        }
        
        private TaggedSome(O value, Option<T> tag) {
            this.value = value;
            this.tag = Objects.requireNonNull(tag, "Tag");
        }

        private TaggedSome(O value) {
            this.value = value;
            this.tag = None();
        }

        @Override
        public Option<T> getTag() {
            return tag;
        }

        @Override
        public O get() {
            return value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj == this) || (obj instanceof TaggedSome 
                    && Objects.equals(value, ((TaggedSome<?, ?>) obj).value)
                    && Objects.equals(tag, ((TaggedSome<?, ?>) obj).tag));
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException();
            // return Objects.hashCode(value);
        }

        @Override
        public String stringPrefix() {
            return "Some";
        }

        @Override
        public String toString() {
            return stringPrefix() + "(" + value + ")";
        }
    }

    /**
     * None is a singleton representation of the undefined {@link TaggedOption}.
     *
     * @param <O> The type of the optional value.
     * @deprecated will be removed from the public API
     */
    @Deprecated
    public static final class TaggedNone<O, T> extends TaggedOption<O, T> implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The singleton instance of None.
         */
        private static final TaggedNone<?, ?> INSTANCE = new TaggedNone<>();

        /**
         * Hidden constructor.
         */
        private TaggedNone() {
        }

        @Override
        public Option<T> getTag() {
            return None();
        }

        @Override
        public O get() {
            throw new NoSuchElementException("No value present");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            return o == this;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String stringPrefix() {
            return "None";
        }

        @Override
        public String toString() {
            return stringPrefix();
        }

        // -- Serializable implementation

        /**
         * Instance control for object serialization.
         *
         * @return The singleton instance of None.
         * @see Serializable
         */
        private Object readResolve() {
            return INSTANCE;
        }
    }
}
