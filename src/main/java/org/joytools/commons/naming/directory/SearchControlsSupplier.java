/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.directory;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import javax.naming.directory.SearchControls;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class SearchControlsSupplier implements Supplier<SearchControls> {
    
    /**
     * 
     */
    protected SearchControlsSupplier() {
    }
    
    /**
     * 
     * @return 
     */
    public static SearchControlsSupplier of() {
        return new SearchControlsSupplier();
    }
    
    /**
     * 
     * @param scope
     * @return 
     */
    public static SearchControlsSupplier of(final SearchScope scope) {
        return of().searchScope(scope);
    }

    /**
     * 
     * @param retAttrs
     * @return 
     */
    public static SearchControlsSupplier of(final String... retAttrs) {
        return of().returnAttributes(retAttrs);
    }

    /**
     * 
     * @param count
     * @return 
     */
    public static SearchControlsSupplier of(final long count) {
        return of().countLimit(count);
    }

    /**
     * 
     * @param retAttrs
     * @return 
     */
    public static SearchControlsSupplier of(final Collection retAttrs) {
        return of().returnAttributes(retAttrs);
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public SearchControlsSupplier searchScope(final SearchScope scope) {
        Objects.requireNonNull(sc, "scope");
        sc.setSearchScope(scope.value());
        return this;
    }
    
    /**
     * 
     * @param scope
     * @return 
     */
    public SearchControlsSupplier searchScope(final int scope) {
        sc.setSearchScope(scope);
        return this;
    }

    /**
     * 
     * @param limit
     * @return 
     */
    public SearchControlsSupplier countLimit(final long limit) {
        sc.setCountLimit(limit);
        return this;
    }

    /**
     * 
     * @param attrs
     * @return 
     */
    public SearchControlsSupplier returnAttributes(final String... attrs) {
        sc.setReturningAttributes(attrs);
        return this;
    }

    /**
     * 
     * @param attrs
     * @return 
     */
    public SearchControlsSupplier returnAttributes(final Iterable<? extends CharSequence> attrs) {
        sc.setReturningAttributes(attrs == null ? null : StringUtils.toArray(attrs));
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public SearchControlsSupplier returnObject(final boolean flag) {
        sc.setReturningObjFlag(flag);
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public SearchControlsSupplier derefLink(final boolean flag) {
        sc.setDerefLinkFlag(flag);
        return this;
    }

    /**
     * 
     * @return 
     */
    public SearchControls get() {
        return sc;
    }
    
    /**
     * 
     */
    private final SearchControls sc = new SearchControls();
}
