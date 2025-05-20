/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.exec;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.joytools.commons.concurrent.TimeValueUnit;
import org.joytools.commons.concurrent.TimeValueUnits;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.text.TextLinesBuffer;

/**
 *
 * @author AndreaR
 */
public class FluentExecutor {
    
    /**
     * 
     */
    private FluentExecutor() {
    }
    
    /**
     * 
     * @param cmdLine 
     */
    private FluentExecutor(final CommandLine cmdLine) {
        this.cmdLine = cmdLine;
    }

    /**
     * 
     * @param executor 
     */
    private FluentExecutor(final Executor executor) {
        this.executor = executor;
    }

    /**
     * 
     * @return 
     */
    public static FluentExecutor of() {
        return new FluentExecutor();
    }
    
    /**
     * 
     * @param cmdLine
     * @return 
     */
    public static FluentExecutor of(final CommandLine cmdLine) {
        return new FluentExecutor(cmdLine);
    }

    /**
     * 
     * @param cmdLine
     * @return 
     */
    public FluentExecutor commandLine(final CommandLine cmdLine) {
        this.cmdLine = cmdLine;
        return this;
    };
    
    /**
     * 
     * @param seconds
     * @return 
     */
    @Deprecated
    public FluentExecutor timeout(final int seconds) {
        this.timeoutMillis = seconds <= 0 ? -1 : seconds * 1000;
        return this;
    }
    
    /**
     * 
     * @param timeout
     * @return 
     */
    public FluentExecutor timeout(final TimeValueUnit timeout) {
        this.timeoutMillis = timeout != null ? timeout.toMillis() : -1;
        return this;
    }

    /**
     * 
     * @param timeout
     * @return 
     */
    public FluentExecutor timeout(final Duration timeout) {
        this.timeoutMillis = timeout != null ? timeout.toMillis() : -1;
        return this;
    }

    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public FluentExecutor timeout(final long value, final TimeUnit unit) {
        return timeout(TimeValueUnits.of(value, unit));
    }

    /**
     * 
     * @param charset
     * @return 
     */
    public FluentExecutor charset(final CharSequence charset) {
        final String name = StringUtils.trimToNull(charset);
        this.charset = name == null ? null : Charset.forName(name);
        return this;
    }

    /**
     * 
     * @param charset
     * @return 
     */
    public FluentExecutor charset(final Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 
     * @param executor
     * @return 
     */
    public FluentExecutor executor(final Executor executor) {
        this.executor = executor;
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public FluentExecutor echo() {
        this.echo = true;
        return this;
    }

    /**
     * 
     * @param echo
     * @return 
     */
    public FluentExecutor echo(final boolean echo) {
        this.echo = echo;
        return this;
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public FluentExecutor input(final CharSequence cs) {
        this.stdin = cs;
        return this;
    }
    
    /**
     * 
     * @param cs
     * @param charSet
     * @return 
     */
    public FluentExecutor input(final CharSequence cs, final CharSequence charSet) {
        input(cs);
        return charset(charSet);
    }

    /**
     * 
     * @param cs
     * @param charSet
     * @return 
     */
    public FluentExecutor input(final CharSequence cs, final Charset charSet) {
        input(cs);
        return charset(charSet);
    }

    /**
     * 
     * @param in
     * @return 
     */
    public FluentExecutor input(final InputStream in) {
        this.stdin = in;
        return this;
    }
    
    /**
     * 
     * @param in
     * @return 
     */
    public FluentExecutor input(final File in) {
        this.stdin = in;
        return this;
    }

    /**
     * 
     * @param in
     * @return 
     */
    public FluentExecutor input(final Object[] in) {
        this.stdin = StringUtils.join(in, System.lineSeparator());
        return this;
    }

    /**
     * 
     * @param in
     * @return 
     */
    public FluentExecutor input(final String[] in) {
        this.stdin = StringUtils.join(in, System.lineSeparator());
        return this;
    }

    /**
     * 
     * @param in
     * @return 
     */
    public FluentExecutor input(final Iterable<? extends CharSequence> in) {
        this.stdin = StringUtils.join(in, System.lineSeparator());
        return this;
    }
    
    /**
     * 
     * @param out
     * @return 
     */
    public FluentExecutor output(final OutputStream out) {
        this.stdout = out;
        return this;
    }

    /**
     * 
     * @param out
     * @return 
     */
    public FluentExecutor output(final File out) {
        this.stdout = out;
        return this;
    }

    /**
     * 
     * @param out
     * @return 
     */
    public FluentExecutor output(final Appendable out) {
        this.stdout = out;
        return this;
    }

    /**
     * 
     * @param out
     * @return 
     */
    public FluentExecutor output(final List out) {
        this.stdout = out;
        return this;
    }

    /**
     * 
     * @param err
     * @return 
     */
    public FluentExecutor error(final OutputStream err) {
        this.stderr = err;
        return this;
    }

    /**
     * 
     * @param err
     * @return 
     */
    public FluentExecutor error(final File err) {
        this.stderr = err;
        return this;
    }

    /**
     * 
     * @param err
     * @return 
     */
    public FluentExecutor error(final Appendable err) {
        this.stderr = err;
        return this;
    }

    /**
     * 
     * @param err
     * @return 
     */
    public FluentExecutor error(final List err) {
        this.stderr = err;
        return this;
    }

    /**
     * 
     * @return 
     */
    protected Executor defaultExecutor() {
        final Executor exc = DefaultExecutor.builder().get();
        exc.setExitValues(null);
        return exc;
    }
    
    /**
     * 
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException 
     */
    public int execute() throws FileNotFoundException, UnsupportedEncodingException, IOException {
	if (cmdLine == null || cmdLine.toStrings().length == 0) {
            throw new IllegalArgumentException("Command line is null or empty");
	}

        // EXECUTOR
        final Executor currExecutor = executor != null ? executor : defaultExecutor();
        // currExecutor.setExitValues(null); MOVED TO getDefaultExecutor()
        
        // TIMEOUT
	if (timeoutMillis >= 0) {
            final ExecuteWatchdog watchdog = ExecuteWatchdog.builder()
                    .setTimeout(Duration.ofMillis(timeoutMillis))
                    .get();
            currExecutor.setWatchdog(watchdog);
	}
        
        try ( // INPUT STREAM
            final InputStream inputStream = toInputStream(stdin)) {

            // OUTPUT & ERROR STREAMS     
            checkOutput(stdout);
            checkOutput(stderr);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            final ByteArrayOutputStream byteArrayErrorStream = stderr == null ? null : new ByteArrayOutputStream(4096);

            final OutputStream outputStream = echo ? 
                new TeeOutputStream(byteArrayOutputStream, System.out) : 
                byteArrayOutputStream;
        
            final OutputStream errorStream = stderr == null ? outputStream : 
                (echo ? new TeeOutputStream(byteArrayErrorStream, System.err) : byteArrayErrorStream);

            final ExecuteStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream, inputStream);
            currExecutor.setStreamHandler(streamHandler);

            try {
            
                return currExecutor.execute(cmdLine);
            
            } finally {
            
                if (stdout != null) {
                    safeCopy(byteArrayOutputStream.toByteArray(), stdout);
                }
        
                if (byteArrayErrorStream != null && stderr != null) {
                    safeCopy(byteArrayErrorStream.toByteArray(), stderr);
                }
            
            }
        }

    }
    
    /**
     * 
     * @param stdin
     * @return 
     * @throws java.io.FileNotFoundException 
     * @throws java.io.UnsupportedEncodingException 
     */
    protected InputStream toInputStream(final Object stdin) throws FileNotFoundException, UnsupportedEncodingException {
        String inputString = StringUtils.EMPTY;
        if (stdin != null) {
            if (stdin instanceof CharSequence) {
                inputString = stdin.toString();
            } else
            if (stdin instanceof InputStream in) {
                return in;
            } else 
            if (stdin instanceof File f) {
                return new FileInputStream(f);
            } else {
                throw new IllegalArgumentException("Invalid input class: " + stdin.getClass().getName());
            }
        }
        if (charset == null) {
            return new ByteArrayInputStream(inputString.getBytes());
        }
        return new ByteArrayInputStream(inputString.getBytes(charset));
    }
    
    /**
     * 
     * @param o 
     */
    private static void checkOutput(final Object o) {
        if (o == null) {
            return;
        }
        if (o instanceof OutputStream) {
            return;
        }
        if (o instanceof List) {
            return;
        }
        if (o instanceof Appendable) {
            return;
        }
        if (o instanceof File) {
            return;
        }
        throw new IllegalArgumentException("Invalid output class: " + o.getClass().getName());
    }
    
    /**
     * 
     * @param source
     * @param destination 
     * @throws java.io.IOException 
     */
    protected void safeCopy(final byte[] source, 
        final Object destination) throws IOException {
        
        final InputStream inputStream = new ByteArrayInputStream(source);
        
        if (destination instanceof OutputStream outputStream) {
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            return;
        }
        
        if (destination instanceof File file) {
            try (final OutputStream outputStream = new FileOutputStream(file)) {
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
                return;
            }
        }

        if (destination instanceof Appendable appendable) {
            final StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, charset);
            appendable.append(writer.getBuffer());
            return;
        } 
        
        if (destination instanceof List lines) {
            // final List lines = List.class.cast(destination);
            lines.addAll(IOUtils.readLines(inputStream, charset));
            return;
        }
        
        throw new UnsupportedOperationException("Unknown destination class: " + destination.getClass().getName());
    }

    private CommandLine cmdLine = null;
    
    private long timeoutMillis = -1;
    
    private Object stdin = null;
    
    private Object stdout = null;

    private Object stderr = null;
    
    private Executor executor = null;
    
    private boolean echo = false;
    
    private Charset charset = null;
    
}
