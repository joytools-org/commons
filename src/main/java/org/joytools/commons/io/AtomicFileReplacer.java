/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.io.LineIterator;
import org.joytools.commons.function.FailableProcessor;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class AtomicFileReplacer {

    /**
     * 
     * @param destFile 
     */
    public AtomicFileReplacer(final File destFile) {
        this.destFile = Objects.requireNonNull(destFile, "Destination file");
    }
    
    /**
     * 
     * @param destFile
     * @return 
     */
    public static AtomicFileReplacer of(final File destFile) {
        return new AtomicFileReplacer(destFile);
    }
    
    /**
     * 
     * @param destFile
     * @return 
     */
    public static AtomicFileReplacer of(final String destFile) {
        return new AtomicFileReplacer(new File(destFile));
    }

    /**
     * 
     * @param dir
     * @param name
     * @return 
     */
    public static AtomicFileReplacer of(final File dir, final String name) {
        return new AtomicFileReplacer(new File(dir, name));
    }

    /**
     * 
     * @param processor
     * @param charset
     * @return 
     */
    public AtomicFileReplacer with(final FailableProcessor<List<String>, Boolean, IOException> processor, final String charset) {
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
    public AtomicFileReplacer with(final FailableProcessor<List<String>, Boolean, IOException> processor, final Charset charset) {
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
    public AtomicFileReplacer with(final FailableProcessor<File, Boolean, IOException> processor) {
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
    public AtomicFileReplacer with(final File newFile) {
        this.tempFile = Objects.requireNonNull(newFile, "New file");
        this.linesProcessor = null;
        return this;
    }
    
    /**
     * 
     * @param tempDir
     * @return 
     */
    public AtomicFileReplacer tempDir(final File tempDir) {
        this.tempDir = tempDir;
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public AtomicFileReplacer skipOnContentEquals(final boolean flag) {
        this.skipOnContentEquals = flag;
        return this;
    }

    /**
     * 
     * @param min
     * @return 
     */
    public AtomicFileReplacer permitFileSize(
            final long min) {
        return permitFileSize(min, -1);
    }

    /**
     * 
     * @param min
     * @param max
     * @return 
     */
    public AtomicFileReplacer permitFileSize(
            final long min, 
            final long max) {
        minFileSize = min < 0 ? 0 : min;
        maxFileSize = max < 0 ? Long.MAX_VALUE : max;
        if (maxFileSize < minFileSize) {
            throw new IllegalArgumentException("Max file size " + maxFileSize + " is lower than minimun " + minFileSize);
        }
        return this;
    }

    /**
     * 
     * @param min
     * @return 
     */
    public AtomicFileReplacer permitFileLines(
            final long min) {
        return permitFileLines(min, -1);
    }

    /**
     * 
     * @param min
     * @param max
     * @return 
     */
    public AtomicFileReplacer permitFileLines(
            final long min, 
            final long max) {
        minFileLines = min < 0 ? 0 : min;
        maxFileLines = max < 0 ? Long.MAX_VALUE : max;
        if (maxFileLines < minFileLines) {
            throw new IllegalArgumentException("Max file lines " + maxFileSize + " is lower than minimun " + minFileSize);
        }
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
                return replace0(replaceWithFile, null, null);
            }
            final File workDir = FileUtils.getExistingDirectory(tempDir != null ? tempDir : destFile.getParentFile());
            replaceWithFile = File.createTempFile("temp-", "-" + destFile.getName(), workDir);
            Integer lineCount = null;
            if (fileProcessor != null) {
                if (!fileProcessor.process(replaceWithFile)) {
                    return false;
                }
            } else {
                final List<String> lines = FileUtils.readLines(destFile, charset);
                if (!linesProcessor.process(lines)) {
                    return false;
                }
                lineCount = lines.size();
                FileUtils.writeLines(replaceWithFile, charset.toString(), lines);
            }
            return replace0(replaceWithFile, lineCount, null);
        } finally {
            if (replaceWithFile != null) {
                FileUtils.deleteQuietly(replaceWithFile);
            }
        }
    }

    /**
     * 
     * @param newFile
     * @param newFileLines
     * @param archiver
     * @return
     * @throws IOException 
     */
    protected boolean replace0(
            final File newFile,
            Integer newFileLines,
            final Function<File, File> archiver) throws IOException {
        if (!newFile.exists() || !newFile.isFile()) {
            throw new IOException("File " + newFile + " does not exist or is a directory");
        }
        final long newFileSize = FileUtils.sizeOf(newFile);
        if (newFileSize < minFileSize) {
            throw new IOException("New file size (" + newFileSize + ") is lower that expected (" + minFileSize + ")");
        }
        if (newFileSize > maxFileSize) {
            throw new IOException("New file size (" + newFileSize + ") is greater that expected (" + maxFileSize + ")");
        }
        if (minFileLines >= 0 || maxFileLines >= 0) {
            if (newFileLines == null) {
                int lines = 0;
                try (final LineIterator itr = new LineIterator(new FileReader(newFile))) {
                    while (itr.hasNext()) {
                        lines++;
                        itr.next();
                    }
                }
                newFileLines = lines;
            }
            if (minFileLines >= 0 && newFileLines < minFileLines) {
                throw new IOException("New file line number (" + newFileLines + ") is lower that expected (" + minFileLines + ")");
            }
            if (maxFileLines >= 0 && newFileLines > maxFileLines) {
                throw new IOException("New file line number (" + newFileLines + ") is greater that expected (" + maxFileLines + ")");
            }
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
