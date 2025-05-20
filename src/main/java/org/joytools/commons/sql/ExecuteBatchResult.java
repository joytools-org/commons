/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author AndreaR
 */
public abstract class ExecuteBatchResult extends AbstractList<Either<SQLException, Integer>> {
    
    /**
     * 
     */
    protected ExecuteBatchResult() {
    }
    
    /**
     * 
     * @param index
     * @param statCode
     * @return 
     */
    public Either<SQLException, Integer> setResult(final int index, final int statCode) {
        return set(index, Right(statCode));
    }
    
    /**
     * 
     * @param index
     * @param error
     * @return 
     */
    public Either<SQLException, Integer> setResult(final int index, final SQLException error) {
        return set(index, Left(error));
    }

    /**
     * 
     * @throws SQLException 
     */
    public void throwFailed() throws SQLException {
        final int failed = failedCount();
        if (failed > 0) {
            final String msg = stream()
                    .filter(IS_LEFT)
                    .map(x -> x.getLeft())
                    .collect(Collectors.groupingBy(x -> x.getMessage(), Collectors.toList()))
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + " [Count: " + e.getValue().size() + "]")
                    .collect(Collectors.joining(", "));
            throw new SQLException(failed + " batch statements failed: " + msg);
        }
    }
    
    /**
     * 
     * @return 
     */
    public int succeededCount() {
        return stream()
                .filter(IS_RIGHT)
                .collect(Collectors.counting())
                .intValue();
    }
    
    /**
     * 
     * @return 
     */
    public int failedCount() {
        return stream()
                .filter(IS_LEFT)
                .collect(Collectors.counting())
                .intValue();
    }
    
    /**
     * 
     * @return 
     */
    public long modifiedCount() {
        return stream()
                .filter(IS_RIGHT)
                .collect(Collectors.summarizingLong(e -> Math.max(0, e.get())))
                .getSum();
    }

    /**
     * 
     */
    private final static Predicate<Either> IS_LEFT = Either::isLeft;
    
    /**
     * 
     */
    private final static Predicate<Either> IS_RIGHT = Either::isRight;

    /**
     * 
     * @param len
     * @param value
     * @param defaultSQLException
     * @return 
     */
    public static ExecuteBatchResult of(final int len, 
            final int value, 
            final SQLException defaultSQLException) {
        final int[] statusCodes = new int[len];
        for (int i = 0; i < len; i++) {
            statusCodes[i] = value;
        }
        return of(statusCodes, defaultSQLException);
    }

    /**
     * 
     * @param results
     * @return 
     */
    public static ExecuteBatchResult of(final List<Either<SQLException, Integer>> results) {
        Objects.requireNonNull(results, "Results");
        return new ExecuteBatchResult() {
            @Override
            public Either<SQLException, Integer> set(final int index,
                    final Either<SQLException, Integer> value) {
                return results.set(index, Objects.requireNonNull(value, "Value"));
            }
            
            @Override
            public Either<SQLException, Integer> get(int index) {
                return results.get(index);
            }

            @Override
            public int size() {
                return results.size();
            }
        };
    }
    
    /**
     * 
     * @param statusCodes
     * @param defaultSQLException
     * @return 
     */
    public static ExecuteBatchResult of(final int[] statusCodes,
            final SQLException defaultSQLException) {
        final List<Either<SQLException, Integer>> results = new ArrayList();
        for (int index = 0; index < statusCodes.length; index++) {
            Either<SQLException, Integer> value;
            Either<SQLException, Integer> errorValue = null;
            final int statCode = statusCodes[index];
            if (statCode == Statement.EXECUTE_FAILED) {
                if (errorValue == null) {
                    final SQLException sqle = defaultSQLException != null ? defaultSQLException : new UnspecifiedSQLException();
                    errorValue = Left(sqle);
                }
                value = errorValue;
            }  else {
                value = Right(statCode);
            }
            results.add(value);
        }
        return of(results);
    }
    
    /**
     * 
     * @return 
     */
    public static ExecuteBatchResult empty() {
        return EMPTY;
    }
    
    /**
     * 
     */
    private final static ExecuteBatchResult EMPTY = new ExecuteBatchResult() {
        @Override
        public Either<SQLException, Integer> get(final int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 0;
        }
    };
    
}

