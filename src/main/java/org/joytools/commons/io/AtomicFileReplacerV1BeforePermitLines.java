/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.joytools.commons.function.FailableProcessor;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class AtomicFileReplacerV1BeforePermitLines {

    /**
     * 
     * @param destFile 
     */
    public AtomicFileReplacerV1BeforePermitLines(final File destFile) {
        this.destFile = Objects.requireNonNull(destFile, "Destination file");
    }
    
    /**
     * 
     * @param destFile
     * @return 
     */
    public static AtomicFileReplacerV1BeforePermitLines of(final File destFile) {
        return new AtomicFileReplacerV1BeforePermitLines(destFile);
    }
    
    /**
     * 
     * @param destFile
     * @return 
     */
    public static AtomicFileReplacerV1BeforePermitLines of(final String destFile) {
        return new AtomicFileReplacerV1BeforePermitLines(new File(destFile));
    }

    /**
     * 
     * @param dir
     * @param name
     * @return 
     */
    public static AtomicFileReplacerV1BeforePermitLines of(final File dir, final String name) {
        return new AtomicFileReplacerV1BeforePermitLines(new File(dir, name));
    }

    /**
     * 
     * @param processor
     * @param charset
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines with(final FailableProcessor<List<String>, Boolean, IOException> processor, final String charset) {
        this.linesProcessor = checkedLinesProcessor(processor);
        this.fileProcessor = null;
        this.tempFile = null;
        this.charset = StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
        return this;
    }
    
    /**
     * 
     * @param processor
     * @param charset
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines with(final FailableProcessor<List<String>, Boolean, IOException> processor, final Charset charset) {
        this.linesProcessor = checkedLinesProcessor(processor);
        this.fileProcessor = null;
        this.tempFile = null;
        this.charset = ObjectUtils.coalesce(charset, Charset.defaultCharset());
        return this;
    }

    /**
     * 
     * @param processor
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines with(final FailableProcessor<File, Boolean, IOException> processor) {
        this.fileProcessor = checkedFileProcessor(processor);
        this.linesProcessor = null;
        this.tempFile = null;
        return this;
    }

    /**
     * 
     * @param newFile
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines with(final File newFile) {
        this.tempFile = Objects.requireNonNull(newFile, "New file");
        this.linesProcessor = null;
        return this;
    }
    
    /**
     * 
     * @param tempDir
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines tempDir(final File tempDir) {
        this.tempDir = tempDir;
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines skipOnContentEquals(final boolean flag) {
        this.skipOnContentEquals = flag;
        return this;
    }

    /**
     * 
     * @param min
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines permitFileSize(
            final long min) {
        return permitFileSize(min, -1);
    }

    /**
     * 
     * @param min
     * @param max
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines permitFileSize(
            final long min, 
            final long max) {
        if (max < min) {
            throw new IllegalArgumentException("Max file size " + max + " is lower than minimun " + min);
        }
        this.minFileSize = min < 0 ? 0 : min;
        this.maxFileSize = max < 0 ? Long.MAX_VALUE : max;
        return this;
    }

    /**
     * 
     * @param min
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines permitFileLines(
            final long min) {
        return permitFileLines(min, -1);
    }

    /**
     * 
     * @param min
     * @param max
     * @return 
     */
    public AtomicFileReplacerV1BeforePermitLines permitFileLines(
            final long min, 
            final long max) {
        if (max < min) {
            throw new IllegalArgumentException("Max file lines " + max + " is lower than minimun " + min);
        }
        this.minFileLines = min < 0 ? 0 : min;
        this.maxFileLines = max < 0 ? Long.MAX_VALUE : max;
        return this;
    }

    /**
     * 
     * @param processor 
     * @return  
     */
    protected FailableProcessor<List<String>, Boolean, IOException> checkedLinesProcessor(final FailableProcessor<List<String>, Boolean, IOException> processor) {
        return Objects.requireNonNull(processor, "New file processor");
    }
    
    /**
     * 
     * @param processor
     * @return 
     */
    protected FailableProcessor<File, Boolean, IOException> checkedFileProcessor(final FailableProcessor<File, Boolean, IOException> processor) {
        return Objects.requireNonNull(processor, "New file processor");
    }

    /**
     * 
     * @return
     * @throws java.io.IOException 
     */
    public boolean replace() throws IOException {
        File replaceWithFile = tempFile;
        try {
            if (tempFile == null && linesProcessor == null && fileProcessor == null) {
                throw new IllegalArgumentException("No temporary file specified");
            }
            if (tempFile != null) {
                return replace0(replaceWithFile, null);
            }
            final File workDir = FileUtils.getExistingDirectory(tempDir != null ? tempDir : destFile.getParentFile());
            replaceWithFile = File.createTempFile("temp-", "-" + destFile.getName(), workDir);
            if (fileProcessor != null) {
                if (!fileProcessor.process(replaceWithFile)) {
                    return false;
                }
            } else {
                final List<String> lines = FileUtils.readLines(destFile, charset);
                if (!linesProcessor.process(lines)) {
                    return false;
                }
                FileUtils.writeLines(replaceWithFile, charset.toString(), lines);
            }
            return replace0(replaceWithFile, null);
        } finally {
            if (replaceWithFile != null) {
                FileUtils.deleteQuietly(replaceWithFile);
            }
        }
    }

    /**
     * 
     * @param newFile
     * @param archiver
     * @return
     * @throws IOException 
     */
    protected boolean replace0(
            final File newFile,
            final Function<File, File> archiver) throws IOException {
        if (!newFile.exists() || !newFile.isFile()) {
            throw new IOException("File " + newFile + " does not exist or is a directory");
        }
        final long newFileSize = FileUtils.sizeOf(newFile);
        if (newFileSize < minFileSize) {
            throw new IOException("New file size is lower that expected");
        }
        if (newFileSize > maxFileSize) {
            throw new IOException("New file size is greater that expected");
        }
        return FileUtils.replaceByRename(destFile, newFile, skipOnContentEquals);
    }
    
    /**
     * 
     */
    private final File destFile;
    
    /**
     * 
     */
    private File tempFile = null;
    
    /**
     * 
     */
    private Charset charset = null;
    
    /**
     * 
     */
    private long minFileLines = -1;

    /**
     * 
     */
    private long maxFileLines = -1;

    /**
     * 
     */
    private long minFileSize = 1;
    
    /**
     * 
     */
    private long maxFileSize = Long.MAX_VALUE;
    
    /**
     * 
     */
    private boolean skipOnContentEquals = true;

    /**
     * 
     */
    private File tempDir = null;

    /**
     * 
     */
    private FailableProcessor<File, Boolean, IOException> fileProcessor = null;

    /**
     * 
     */
    private FailableProcessor<List<String>, Boolean, IOException> linesProcessor = null;
        
}
