/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.List;
import org.joytools.commons.concurrent.ComposedResults;
import org.joytools.commons.concurrent.ComposedResult;
import org.joytools.commons.concurrent.BasicComposedResult;


/**
 *
 * @author AndreaR
 */
public class TryComposedResult {
    
    public static void main(final String... args) throws InterruptedException {
        // dump(ComposedResults.empty().toSeqComposedResult());
        // dump(ComposedResults.empty().toMapComposedResult());
        ComposedResults.basic.empty();

        final List<Either<Exception, Integer>> entries = new ArrayList();
        entries.add(Right(0));
        entries.add(Left(new NullPointerException("Uno")));
        entries.add(Right(2));
        entries.add(Right(3));
        entries.add(Left(new NullPointerException("Quattro")));
        entries.add(Right(5));
        entries.add(Right(6));
        
        /*final BasicComposedResult<Integer> comp = ComposedResults.forEntries(entries);
        // dump(comp.toSeqComposedResult());
        // dump(comp.toMapComposedResult());
        comp.dumpDiffMessage();*/
    }
    
    static void dump(final BasicComposedResult<?> comp) {
        System.out.println("*** DUMP ******************************************************");
        System.out.println("toString: " + comp);
        System.out.println("isAllFailed: " + comp.isAllFailed());
        System.out.println("isAnyFailed: " + comp.isAnyFailed());
        System.out.println("count: " + comp.entriesSeq().size());
        System.out.println("succeeded: " + comp.succeededSeq());
        System.out.println("succeededRatio: " + comp.succeededRatio());
        System.out.println("failed: " + comp.failedSeq());
        System.out.println("failedRatio: " + comp.failedRatio());
        System.out.println("values: " + comp.valuesSeq());
    }
    
    static void dump(final ComposedResult<?, ?> comp) {
        System.out.println("*** DUMP ******************************************************");
        System.out.println("size: " + comp.size());
        System.out.println("toString: " + comp);
        System.out.println("entriesSeq: " + comp.entriesSeq());
        System.out.println("entriesMap: " + comp.entriesMap());
        System.out.println("isEmpty: " + comp.isEmpty());
        System.out.println("isNotEmpty: " + comp.isNotEmpty());
        System.out.println("isAllFailed: " + comp.isAllFailed());
        System.out.println("isAllSucceeded: " + comp.isAllSucceeded());
        System.out.println("isAllSucceededOrEmpty: " + comp.isAllSucceededOrEmpty());
        System.out.println("isAnyFailed: " + comp.isAnyFailed());
        System.out.println("isAnySucceeded: " + comp.isAnySucceeded());
        System.out.println("isAnySucceededOrEmpty: " + comp.isAnySucceededOrEmpty());
        System.out.println("succeededSeq: " + comp.succeededSeq());
        System.out.println("succeededCount: " + comp.succeededCount());
        System.out.println("succeededRatio: " + comp.succeededRatio());
        System.out.println("failedSeq: " + comp.failedSeq());
        System.out.println("failedCount: " + comp.failedCount());
        System.out.println("failedRatio: " + comp.failedRatio());
    }

}
