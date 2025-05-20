/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author AndreaR
 */
public class ExecutorServiceShutdownThread extends Thread {

    /**
     * 
     * @param es 
     */
    protected ExecutorServiceShutdownThread(final ExecutorServiceShutdown es) {
        this.esShutdown = es;
    }
    
    /**
     * 
     * @return 
     */
    public boolean maybeStart() {
        try {
            super.start();
            return true;
        } catch (final IllegalThreadStateException ex) {
            return false;
        }
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        System.out.println("[ESShutdownThread] run started");
        try {
            esShutdown.execute();
            System.out.println("[ESShutdownThread] run finished");
        } catch (final RuntimeException ex) {
            System.out.println("[ESShutdownThread] run failed: " + ex);
            exc = Left(ex);
        } catch (final TimeoutException ex) {
            System.out.println("[ESShutdownThread] run failed: " + ex);
            exc = Right(ex);
        }
    }
    
    /**
     * 
     * @throws InterruptedException
     * @throws TimeoutException 
     */
    public void maybeThrow() throws InterruptedException, TimeoutException {
        if (exc != null) {
            if (exc.isLeft()) {
                throw exc.getLeft();
            } else {
                throw exc.get();
            }
        }
    }

    /**
     * 
     */
    private Either<RuntimeException, TimeoutException> exc;

    /**
     * 
     */
    private final ExecutorServiceShutdown esShutdown;

}
