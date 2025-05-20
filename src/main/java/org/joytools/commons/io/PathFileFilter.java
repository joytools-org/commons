/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.function.Predicate;
import org.apache.commons.io.file.PathFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 *
 * @author AndreaR
 */
public interface PathFileFilter extends IOFileFilter, PathMatcher {

    /**
     * 
     * @return 
     */
    default PathMatcher toPathMatcher() {
        return this;
    } 
       
    /**
     * 
     * @return 
     */
    default PathFilter toPathFilter() {
        return this;
    } 

    /**
     * 
     * @return 
     */
    default FileFilter toFileFilter() {
        return this;
    } 

    /**
     * 
     * @return 
     */
    default FilenameFilter toFilenameFilter() {
        return this;
    } 

    /**
     * 
     * @return 
     */
    default IOFileFilter toIOFileFilter() {
        return this;
    } 

    /**
     * 
     * @return 
     */
    default Predicate<File> toFilePredicate() {
        return (final File f) -> {
            return accept(f);
        };
    }

    /**
     * 
     * @return 
     */
    default Predicate<Path> toPathPredicate() {
        return (final Path p) -> {
            return matches(p);
        };
    }

}
