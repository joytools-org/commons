/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.directory;

import static com.google.common.base.Preconditions.checkArgument;
import static io.vavr.API.Some;
import static io.vavr.API.None;
import io.vavr.control.Option;
import java.util.Collection;
import java.util.Objects;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Java;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.naming.NamingEnumerationSupplier;

/**
 *
 * @author AndreaR
 */
public class SearchParameters {
   
    /**
     * 
     */
    protected SearchParameters() {
    }
    
    /**
     * 
     * @param name 
     * @return  
     */
    public SearchParameters searchBase(final Name name) {
        this.name = Some(Objects.requireNonNull(name, "name"));
        return this;
    }

    /**
     * 
     * @param nameStr 
     * @return  
     */
    public SearchParameters searchBase(final CharSequence nameStr) {
        final String s = StringUtils.toEmptyString(nameStr);
        checkArgument(!s.isEmpty(), "SearchBase is empty");
        this.nameStr = Some(s);
        return this;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static SearchParameters of(final Name name) {
        return empty().searchBase(name);
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static SearchParameters of(final CharSequence name) {
        return empty().searchBase(name);
    }

    /**
     * 
     * @return 
     */
    public static SearchParameters empty() {
        return new SearchParameters();
    }
    
    /**
     * 
     * @param matchAttrs
     * @return 
     */
    public SearchParameters matchingAttributes(final Attributes matchAttrs) {
        this.matchAttrs = Some(matchAttrs);
        return this;
    }

    /**
     * 
     * @param returnAttrs
     * @return 
     */
    public SearchParameters returnAttributes(final String... returnAttrs) {
        this.returnAttrs = Some(returnAttrs);
        return this;
    }

    /**
     * 
     * @param returnAttrs
     * @return 
     */
    public SearchParameters returnAttributes(final Iterable<? extends CharSequence> returnAttrs) {
        this.returnAttrs = Some(returnAttrs == null ? null : StringUtils.toArray(returnAttrs));
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public SearchParameters controls(final SearchControls controls) {
        this.controls = Some(controls);
        return this;
    }

    /**
     * 
     * @param supplier
     * @return 
     */
    public SearchParameters controls(final SearchControlsSupplier supplier) {
        Objects.requireNonNull(supplier, "controls supplier");
        this.controls = Some(supplier.get());
        return this;
    }

    /**
     * 
     * @param filter
     * @return 
     */
    public SearchParameters filter(final CharSequence filter) {
        this.filter = Some(StringUtils.toString(filter));
        return this;
    }

    /**
     * 
     * @param filterArgs
     * @return 
     */
    public SearchParameters filterArguments(final Object[] filterArgs) {
        this.filterArgs = Some(filterArgs);
        return this;
    }

    /**
     * 
     * @param filterArgs
     * @return 
     */
    public SearchParameters filterArguments(final Iterable<?> filterArgs) {
        final Iterable<Object> i = (Iterable<Object>)filterArgs;
        this.filterArgs = Some(filterArgs == null ? null : IterableAccessors.ofAll(i).to(Java.array(Object.class)));
        return this;
    }

    /**
     * 
     * @param ctx
     * @return
     * @throws NamingException 
     */
    public NamingEnumeration<SearchResult> search(final DirContext ctx) throws NamingException {
        return supplierOf(ctx).get();
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public NamingEnumerationSupplier<SearchResult> supplierOf(final DirContext ctx) {
        Objects.requireNonNull(ctx, "Ldap Context");
        if (name.isEmpty() && nameStr.isEmpty()) {
            throw new IllegalArgumentException("No name specified");
        }
        if (name.isDefined() && nameStr.isDefined()) {
            throw new IllegalArgumentException("Name has been specified twice");
        }
        if (matchAttrs.isEmpty() && filter.isEmpty()) {
            throw new IllegalArgumentException("No filter or matching attibutes criteria specified");
        }
        if (matchAttrs.isDefined() && filter.isDefined()) {
            throw new IllegalArgumentException("Filter and matching attributes criteria could not be specified together");
        }
        if (matchAttrs.isDefined()) {
            if (filterArgs.isDefined()) {
                throw new IllegalArgumentException("Filter arguments could not be specified with matching attributes criteria");
            }
            if (controls.isDefined()) {
                throw new IllegalArgumentException("Search controls could not be specified with matching attributes criteria");
            }
            if (returnAttrs.isDefined()) {
                if (name.isDefined()) {
                    return () -> (ctx.search(name.get(), matchAttrs.get(), returnAttrs.get()));
                } else {
                    return () -> (ctx.search(nameStr.get(), matchAttrs.get(), returnAttrs.get()));
                }
            } else {
                if (name.isDefined()) {
                    return () -> (ctx.search(name.get(), matchAttrs.get()));
                } else {
                    return () -> (ctx.search(nameStr.get(), matchAttrs.get()));
                }
            }
        }
        if (returnAttrs.isDefined()) {
            throw new IllegalArgumentException("Return attributes must be specified in controls when filter is used");
        }
        final SearchControls ctls = controls.getOrNull();
        if (filterArgs.isDefined()) {
            if (name.isDefined()) {
                return () -> (ctx.search(name.get(), filter.get(), filterArgs.get(), ctls));
            } else {
                return () -> (ctx.search(nameStr.get(), filter.get(), filterArgs.get(), ctls));
            }
        }
        if (name.isDefined()) {
            return () -> (ctx.search(name.get(), filter.get(), ctls));
        } else {
            return () -> (ctx.search(nameStr.get(), filter.get(), ctls));
        }
    }
    
    private Option<Name> name = None();

    private Option<String> nameStr = None();

    private Option<Attributes> matchAttrs = None();

    private Option<String[]> returnAttrs = None();

    private Option<String> filter = None();

    private Option<Object[]> filterArgs = None();

    private Option<SearchControls> controls = None();
               
}
