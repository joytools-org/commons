/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class PathFileFilters {
    
    /**
     * 
     * @return 
     */
    public static PathFileFilter alwaysTrue() {
        return ALWAYS_TRUE;
    }
    
    /**
     * 
     * @return 
     */
    public static PathFileFilter alwaysFalse() {
        return ALWAYS_FALSE;
    }

    /**
     * 
     * @return 
     */
    public static PathFileFilter isFile() {
        return IS_FILE;
    }

    /**
     * 
     * @return 
     */
    public static PathFileFilter isDirectory() {
        return IS_DIRECTORY;
    }

    public static PathFileFilter like(
            final CharSequence likeExpr,
            final boolean ignoreCase) {
        final Pattern pattern = StringPredicates.likeToPattern(likeExpr, ignoreCase);
        return new PathFileFilter() {
            @Override
            public boolean accept(final File file) {
                return pattern.matcher(file.getName()).matches();
            }

            @Override
            public boolean accept(final File dir, final String name) {
                return pattern.matcher(name).matches();
            }

            @Override
            public boolean matches(final Path path) {
                return pattern.matcher(FilenameUtils.getName(path.toString())).matches();
            }
        };
    };

    /**
     * 
     * @param likeExpr
     * @return 
     */
    public static PathFileFilter likeIgnoreCase(
            final CharSequence likeExpr) {
        return like(likeExpr, true);
    };

    /**
     * 
     * @param extensions
     * @return 
     */
    public static PathFileFilter forExtensions(final Iterable<? extends CharSequence> extensions) {
        Objects.requireNonNull(extensions, "Extensions");
        final Predicate<CharSequence> p = (final CharSequence cs) -> {
            for (final CharSequence ext : extensions) {
                final String dottedExt = FilenameUtils.toDottedExtension(ext);
                if (StringUtils.endsWithIgnoreCase(cs, dottedExt)) {
                    return true;
                }
            }
            return false;
        };
        return fromCharSequencePredicate(p);
    };
    
    /**
     * 
     * @param extensions
     * @return 
     */
    public static PathFileFilter forExtensions(final String... extensions) {
        return PathFileFilters.forExtensions(Arrays.asList(extensions));
    }

    /**
     * 
     * @param <S>
     * @param predicate
     * @return 
     */
    public static PathFileFilter fromStringPredicate(final Predicate<String> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        if (predicate instanceof PathFileFilter filter) {
            return filter;
        }
        return new PathFileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return predicate.test(pathname.getPath());
            }

            @Override
            public boolean accept(final File dir, final String name) {
                final File f = new File(dir, name);
                return accept(f);
            }

            @Override
            public boolean matches(final Path path) {
                return predicate.test(path.toString());
            }            
        };
    }

    /**
     * 
     * @param predicate
     * @return 
     */
    public static PathFileFilter fromCharSequencePredicate(final Predicate<CharSequence> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        if (predicate instanceof PathFileFilter filter) {
            return filter;
        }
        return new PathFileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return predicate.test(pathname.getPath());
            }

            @Override
            public boolean accept(final File dir, final String name) {
                final File f = new File(dir, name);
                return accept(f);
            }

            @Override
            public boolean matches(final Path path) {
                return predicate.test(path.toString());
            }
        };
    }

    /**
     * 
     * @param predicate
     * @return 
     */
    public static PathFileFilter fromPathPredicate(final Predicate<Path> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        if (predicate instanceof PathFileFilter filter) {
            return filter;
        }
        return new PathFileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return predicate.test(pathname.toPath());
            }

            @Override
            public boolean accept(final File dir, final String name) {
                final File f = new File(dir, name);
                return accept(f);
            }

            @Override
            public boolean matches(final Path path) {
                return predicate.test(path);
            }
        };
    }

    /**
     * 
     * @param predicate
     * @return 
     */
    public static PathFileFilter fromFilePredicate(final Predicate<File> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        if (predicate instanceof PathFileFilter filter) {
            return filter;
        }
        return new PathFileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return predicate.test(pathname);
            }

            @Override
            public boolean accept(final File dir, final String name) {
                final File f = new File(dir, name);
                return accept(f);
            }

            @Override
            public boolean matches(final Path path) {
                return predicate.test(path.toFile());
            }
        };
    }
    
    /**
     * 
     * @param filter
     * @return 
     */
    public static PathFileFilter fromFileFilter(final FileFilter filter) {
        Objects.requireNonNull(filter, "Filter");
        if (filter instanceof PathFileFilter pff) {
            return pff;
        }
        return new PathFileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return filter.accept(pathname);
            }

            @Override
            public boolean accept(final File dir, final String name) {
                return accept(new File(dir, name));
            }

            @Override
            public boolean matches(final Path path) {
                return accept(path.toFile());
            }
        };
    }
    
    /**
     * 
     * @param filter
     * @return 
     */
    public static PathFileFilter fromFilenameFilter(final FilenameFilter filter) {
        Objects.requireNonNull(filter, "Filter");
        if (filter instanceof PathFileFilter pff) {
            return pff;
        }
        return new PathFileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return accept(pathname.getParentFile(), pathname.getName());
            }

            @Override
            public boolean accept(final File dir, final String name) {
                return filter.accept(dir, name);
            }

            @Override
            public boolean matches(final Path path) {
                return accept(path.toFile());
            }
        };
    }

    /**
     * 
     */
    private final static PathFileFilter ALWAYS_TRUE = new PathFileFilter() {
        @Override
        public boolean accept(final File file) {
            return true;
        }

        @Override
        public boolean accept(final File dir, final String name) {
            return true;
        }

        @Override
        public boolean matches(final Path path) {
            return true;
        }
    };

    /**
     * 
     */
    private final static PathFileFilter ALWAYS_FALSE = new PathFileFilter() {
        @Override
        public boolean accept(final File file) {
            return false;
        }

        @Override
        public boolean accept(final File dir, final String name) {
            return false;
        }

        @Override
        public boolean matches(final Path path) {
            return false;
        }
    };

    /**
     * 
     */
    private final static PathFileFilter IS_FILE = new PathFileFilter() {
        @Override
        public boolean accept(final File file) {
            return file.isFile();
        }

        @Override
        public boolean accept(final File dir, final String name) {
            return accept(new File(dir, name));
        }

        @Override
        public boolean matches(final Path path) {
            return accept(path.toFile());
        }
    };
    
    private final static PathFileFilter IS_DIRECTORY = new PathFileFilter() {
        @Override
        public boolean accept(final File file) {
            return file.isDirectory();
        }

        @Override
        public boolean accept(final File dir, final String name) {
            return accept(new File(dir, name));
        }

        @Override
        public boolean matches(final Path path) {
            return Files.isDirectory(path);
        }
    };

}
