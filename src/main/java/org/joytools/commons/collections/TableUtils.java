/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import static io.vavr.API.Tuple;
import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 */
public class TableUtils {
    
    /**
     * 
     */
    public TableUtils() {
    }
    
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <U>
     * @param <T>
     * @param table
     * @param data 
     * @return  
     */
    public static <R, C, V, U extends V, T extends Table<R, C, V>> T insertAll(final T table, 
            final Table<R, C, U> data) {
        return merge(table, data, null);
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <U>
     * @param <T>
     * @param table
     * @param data
     * @param merge 
     * @return  
     */
    public static <R, C, V, U extends V, T extends Table<R, C, V>> T merge(final T table, 
            final Table<R, C, U> data,
            final Function3<? super Tuple2<R, C>, ? super V, ? super V, ? extends V> merge) {
        return merge(table, IterableAccessors.ofAll(data.cellSet()).map(cellToTuple()), merge);
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @return 
     */
    public static <R, C, V> Function<Cell<R, C, V>, Tuple3<R, C, V>> cellToTuple() {
        return (Function<Cell<R, C, V>, Tuple3<R, C, V>>)(Function)CELL_TO_TUPLE;
    }
    
    /**
     * 
     */
    private static final Function<Cell<Object, Object, Object>, Tuple3<Object, Object, Object>> CELL_TO_TUPLE = c -> Tuple(c.getRowKey(), c.getColumnKey(), c.getValue());
            
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <U>
     * @param <T>
     * @param table
     * @param data 
     * @return  
     */
    public static <R, C, V, U extends V, T extends Table<R, C, V>> T insertAll(final T table, 
            final Iterable<Tuple3<R, C, U>> data) {
        return merge(table, data, null);
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <U>
     * @param <T>
     * @param table
     * @param row
     * @param col
     * @param value
     * @param merger
     * @return 
     */
    public static <R, C, V, U extends V, T extends Table<R, C, V>> T merge(final T table, 
            final R row, 
            final C col,
            final U value,
            final Function3<? super Tuple2<R, C>, ? super V, ? super V, ? extends V> merger) {
        // final Function3<Tuple2<R, C>, V, V, V> mrg = coalesce(merge, Mergers.mergeNone());
        if (table.contains(row, col)) {
            final V prevValue = table.get(row, col);
            if (prevValue == null && table instanceof ArrayTable) {    // Special case for ArrayTable
                table.put(row, col, value);
            } else {
                if (merger == null) {
                    return Mergers.throwDuplicates(Tuple(row, col), prevValue, value); // Will throw exception
                } else {
                    final V newValue = merger.apply(Tuple(row, col), prevValue, value);
                    if (newValue != prevValue) {
                        table.put(row, col, newValue);
                    }
                }
            }
        } else {
            table.put(row, col, value);
        }
        return table;
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <U>
     * @param <T>
     * @param table
     * @param data
     * @param merger 
     * @return  
     */
    public static <R, C, V, U extends V, T extends Table<R, C, V>> T merge(final T table, 
            final Iterable<Tuple3<R, C, U>> data, 
            final Function3<? super Tuple2<R, C>, ? super V, ? super V, ? extends V> merger) {
        // final Function3<Tuple2<R, C>, V, V, V> mrg = coalesce(merge, Mergers.mergeNone());
        for (final Tuple3<R, C, U> e : data) {
            merge(table, e._1, e._2, e._3, merger);
        }
        return table;
    }
    
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <T>
     * @param table
     * @param row
     * @param col
     * @param value
     * @return 
     */
    public static <R, C, V, T extends Table<R, C, V>> T insert(final T table, final R row, final C col, final V value) {
        return merge(table, row, col, value, null);
    }
    
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param from
     * @param to
     * @return 
     */
    public static <R, C, V> TableDiff<R, C, V> diff(final Table<R, C, V> from, final Table<R, C, V> to) {
        final IterableAccessor<R> rows = IterableAccessors.intersection(from.rowKeySet(), to.rowKeySet()).eval();
        final IterableAccessor<R> rowsRemoved = IterableAccessors.subtraction(from.rowKeySet(), to.rowKeySet()).eval();
        final IterableAccessor<R> rowsAdded = IterableAccessors.subtraction(to.rowKeySet(), from.rowKeySet()).eval();
        final IterableAccessor<C> cols = IterableAccessors.intersection(from.columnKeySet(), to.columnKeySet()).eval();
        final IterableAccessor<C> colsRemoved = IterableAccessors.subtraction(from.columnKeySet(), to.columnKeySet()).eval();
        final IterableAccessor<C> colsAdded = IterableAccessors.subtraction(to.columnKeySet(), from.columnKeySet()).eval();
        final Table<R, C, Tuple2<V, V>> valuesChanged = HashBasedTable.create();
        for (final R row : rows) {
            for (final C col : cols) {
                final V fv = from.get(row, col);
                final V tv = to.get(row, col);
                if (!Objects.equals(fv, tv)) {
                    valuesChanged.put(row, col, Tuple(fv, tv));
                }
            }
        }
        return new TableDiff<R, C, V>() {
            @Override
            public IterableAccessor<R> commonRows() {
                return rows;
            }
            
            @Override
            public IterableAccessor<R> rowsAdded() {
                return rowsAdded;
            }

            @Override
            public IterableAccessor<R> rowsRemoved() {
                return rowsRemoved;
            }

            @Override
            public IterableAccessor<C> commonColumns() {
                return cols;
            }

            @Override
            public IterableAccessor<C> columnsAdded() {
                return colsAdded;
            }

            @Override
            public IterableAccessor<C> columnsRemoved() {
                return colsRemoved;
            }

            @Override
            public Table<R, C, Tuple2<V, V>> valuesChanged() {
                return valuesChanged;
            }
        };
    }
    
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @return 
     */
    final static <R, C, V> Merger<Tuple2<R, C>, V> arrayTableMerger0() {
        return (Merger<Tuple2<R, C>, V>)(Merger)ARRAY_TABLE_MERGER;
    }
    
    /**
     * 
     */
    private final static Merger<Object, Object> ARRAY_TABLE_MERGER = new Merger() {
        @Override
        public Object apply(final Object k, final Object v1, final Object v2) {
            if (v1 == null) {
                return v2;
            }
            return Mergers.throwDuplicates(k, v1, v2);
        }
    };
    
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V> 
     */
    public interface TableDiff<R, C, V> {
        public IterableAccessor<R> commonRows();
        public IterableAccessor<R> rowsAdded();
        public IterableAccessor<R> rowsRemoved();
        public IterableAccessor<C> commonColumns();
        public IterableAccessor<C> columnsAdded();
        public IterableAccessor<C> columnsRemoved();
        public Table<R, C, Tuple2<V, V>> valuesChanged();
        // public Iteration<Cell<R, C, V>> cellsChanged();
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @return 
     */
    public static <R, C, V> Table<R, C, V> empty() {
        return (Table<R, C, V>)EMPTY;
    }
    
    private final static Table EMPTY = ImmutableTable.builder().build();
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final Table<Integer, String, Object> a = HashBasedTable.create();
        insert(a, 0, "a", "from");
        insert(a, 0, "a", "from");
        insert(a, 0, "b", "from");
        insert(a, 1, "a", "from");
        insert(a, 1, "b", "from");
        
        final Table<Integer, String, Object> b = HashBasedTable.create();
        insert(b, 1, "b", "to");
        insert(b, 1, "c", "to");
        insert(b, 2, "b", "to");
        insert(b, 2, "c", "to");
        
        final TableDiff<Integer, String, Object> diff = diff(a, b);
        System.out.println("rowsAdded: " + diff.rowsAdded().mkString(", "));
        System.out.println("rowsRemoved: " + diff.rowsRemoved().mkString(", "));
        System.out.println("columnsAdded: " + diff.columnsAdded().mkString(", "));
        System.out.println("columnsRemoved: " + diff.columnsRemoved().mkString(", "));
        System.out.println("valuesChanged: " + diff.valuesChanged());
    }
    
}
