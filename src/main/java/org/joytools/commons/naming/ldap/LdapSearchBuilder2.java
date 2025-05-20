/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming.ldap;

import static com.google.common.base.Preconditions.checkArgument;
import static io.vavr.API.Left;
import static io.vavr.API.None;
import static io.vavr.API.Right;
import static io.vavr.API.Some;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.time.Duration;
import java.util.Iterator;
import java.util.Objects;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.PagedResultsControl;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.TimeValueUnit;
import org.joytools.commons.function.Failable;
import org.joytools.commons.function.FailableIterator;
import static org.joytools.commons.function.Functions.asString;
import org.joytools.commons.interop.Java;
import org.joytools.commons.lang.ArrayUtils;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.naming.ContextUtils;
import org.joytools.commons.naming.NamingUtils;
import org.joytools.commons.naming.directory.SearchScope;
import org.joytools.commons.util.Disposer;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class LdapSearchBuilder2 {
    
    /**
     * 
     */
    protected LdapSearchBuilder2() {
    }
    
    /**
     * 
     * @param other 
     */
    protected LdapSearchBuilder2(final LdapSearchBuilder2 other) {
        Objects.requireNonNull(other, "Other");
        this.m_ldapContext = other.m_ldapContext;
        this.m_searchBase = other.m_searchBase;
        this.m_pageSize = other.m_pageSize;
        this.m_filter = other.m_filter;
        this.m_filterArgs = other.m_filterArgs;
        this.m_reqControls = other.m_reqControls;
        this.m_sc = LdapUtils.clone(other.m_sc);
    }

    /**
     * 
     * @return 
     */
    public static LdapSearchBuilder2 of() {
        return new LdapSearchBuilder2();
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public static LdapSearchBuilder2 on(final LdapContext ctx) {
        return of().context(ctx);
    }

    /**
     * 
     * @param ctxBuilder
     * @return 
     */
    public static LdapSearchBuilder2 on(final LdapContextBuilder ctxBuilder) {
        return of().context(ctxBuilder);
    }

    /**
     * 
     * @return 
     */
    protected Control[] computeRequestControls() {
        if (m_reqControls == null || m_reqControls.isEmpty()) {
            return null;
        }
        return m_reqControls.toJavaArray(size -> new Control[size]);
    }
    
    /**
     * 
     * @return 
     */
    protected Object[] computeFilterArgs() {
        if (m_filterArgs == null || m_filterArgs.isEmpty()) {
            return null;
        }
        return m_filterArgs.toJavaArray(size -> new Object[size]);
    }
            
    /**
     * 
     * @param disp
     * @param failable
     * @return
     * @throws Exception 
     */
    public Iterator<SearchResult> buildIterator(final Disposer<? super NamingException> disp,
            final Failable failable) throws Exception {
        return buildFailableIterator(disp).asIterator(failable);
    }

    /**
     * 
     * @param disp
     * @param failable
     * @return
     * @throws Exception 
     */
    public IterableAccessor<SearchResult> buildIteration(final Disposer<? super NamingException> disp,
            final Failable failable) throws Exception {
        return IterableAccessors.ofAll(buildIterator(disp, failable));
    }

    /**
     * 
     * @param disp
     * @return 
     * @throws java.lang.Exception 
     */
    public FailableIterator<SearchResult, NamingException> buildFailableIterator(final Disposer<? super NamingException> disp) throws Exception {
        final NamingEnumeration<SearchResult> data = buildNamingEnumeration(disp);
        return NamingUtils.toFailableIterator(data);
    }

    /**
     * 
     * @param disp
     * @return
     * @throws Exception 
     */
    public NamingEnumeration<SearchResult> buildNamingEnumeration(final Disposer<? super NamingException> disp) throws Exception {
        Objects.requireNonNull(m_ldapContext, "Ldap Context");
        if (m_pageSize.isDefined()) {
            throw new UnsupportedOperationException("paging");
        }
        LdapContext ldapCtx;
        final Control[] reqControls = computeRequestControls();
        if (m_ldapContext.isLeft()) {
            final LdapContextBuilder builder = m_ldapContext.getLeft();
            ldapCtx = builder.build();
            disp.dispose(ldapCtx, ContextUtils.closer());
            if (m_reqControls != null) {
                ldapCtx.setRequestControls(reqControls);
            }
        } else {
            ldapCtx = m_ldapContext.get();
            if (m_reqControls != null) {
                ldapCtx = ldapCtx.newInstance(reqControls);
                disp.dispose(ldapCtx, ContextUtils.closer());
            }
        }
        NamingEnumeration<SearchResult> data;
        final Object[] filterArgs = computeFilterArgs();
        if (filterArgs == null) {
            if (m_searchBase.isLeft()) {
                data = ldapCtx.search(m_searchBase.getLeft(), m_filter, m_sc);
            } else {
                data = ldapCtx.search(m_searchBase.get(), m_filter, m_sc);
            }
        } else {
            if (m_searchBase.isLeft()) {
                data = ldapCtx.search(m_searchBase.getLeft(), m_filter, filterArgs, m_sc);
            } else {
                data = ldapCtx.search(m_searchBase.get(), m_filter, filterArgs, m_sc);
            }
        }
        disp.dispose(data, data::close);
        return data;
    }

    /**
     * https://docs.oracle.com/javase/tutorial/jndi/newstuff/paged-results.html
     * 
     * @param disp
     * @return
     * @throws Exception 
     */
    protected NamingEnumeration<SearchResult> buildPagedEnumeration(final Disposer<? super NamingException> disp) throws Exception {
        if (Math.random() >= 0) {
            throw new UnsupportedOperationException("buildPagedEnumeration()");
        }
        Objects.requireNonNull(m_ldapContext, "Ldap Context");
        LdapContext ldapCtx;
        Control[] reqControls = computeRequestControls();
        if (m_pageSize.isDefined()) {
            reqControls = LdapUtils.append(reqControls, new PagedResultsControl(m_pageSize.get(), Control.CRITICAL));
        }
        if (m_ldapContext.isLeft()) {
            final LdapContextBuilder builder = m_ldapContext.getLeft();
            ldapCtx = builder.build();
            disp.dispose(ldapCtx, ContextUtils.closer());
            if (m_reqControls != null) {
                ldapCtx.setRequestControls(reqControls);
            }
        } else {
            ldapCtx = m_ldapContext.get();
            if (m_reqControls != null) {
                ldapCtx = ldapCtx.newInstance(reqControls);
                disp.dispose(ldapCtx, ContextUtils.closer());
            }
        }
        NamingEnumeration<SearchResult> data;
        final Object[] filterArgs = computeFilterArgs();
        if (filterArgs == null) {
            if (m_searchBase.isLeft()) {
                data = ldapCtx.search(m_searchBase.getLeft(), m_filter, m_sc);
            } else {
                data = ldapCtx.search(m_searchBase.get(), m_filter, m_sc);
            }
        } else {
            if (m_searchBase.isLeft()) {
                data = ldapCtx.search(m_searchBase.getLeft(), m_filter, filterArgs, m_sc);
            } else {
                data = ldapCtx.search(m_searchBase.get(), m_filter, filterArgs, m_sc);
            }
        }
        disp.dispose(data, data::close);
        return data;

        /*
        byte[] cookie = null;
            ctx.setRequestControls(new Control[]{
            new PagedResultsControl(pageSize, Control.NONCRITICAL) });
        int total;

        do {
                NamingEnumeration results =
            ctx.search("", "(objectclass=*)", new SearchControls());

                while (results != null && results.hasMore()) {
            SearchResult entry = (SearchResult) results.next();
            System.out.println(entry.getName());
            }

            // Examine the paged results control response
            Control[] controls = ctx.getResponseControls();
            if (controls != null) {
            for (int i = 0; i < controls.length; i++) {
                if (controls[i] instanceof PagedResultsResponseControl) {
                PagedResultsResponseControl prrc =
                                 (PagedResultsResponseControl)controls[i];
                total = prrc.getResultSize();
                if (total != 0) {
                    System.out.println("***************** END-OF-PAGE " +
                    "(total : " + total +
                    ") *****************\n");
                } else {
                    System.out.println("***************** END-OF-PAGE " +
                    "(total: unknown) ***************\n");
                }
                cookie = prrc.getCookie();
                }
            }
            } else {
            System.out.println("No controls were sent from the server");
            }
                // Re-activate paged results
                ctx.setRequestControls(new Control[]{
            new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });

        } while (cookie != null);
*/

    }

    /**
     * 
     * @param ctx 
     * @return  
     */
    public LdapSearchBuilder2 context(final LdapContext ctx) {
        Objects.requireNonNull(ctx, "Ldap Context");
        m_ldapContext = Right(ctx);
        return this;
    }

    /**
     * 
     * @param ctxBuilder 
     * @return  
     */
    public LdapSearchBuilder2 context(final LdapContextBuilder ctxBuilder) {
        Objects.requireNonNull(ctxBuilder, "Ldap Context Builder");
        m_ldapContext = Left(ctxBuilder);
        return this;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public LdapSearchBuilder2 searchBase(final LdapName name) {
        checkArgument(name != null && name.size() > 0, "Empty search base");
        m_searchBase = Left(name);
        return this;
    }

    /**
     * 
     * @param sb 
     * @return  
     */
    public LdapSearchBuilder2 searchBase(final CharSequence sb) {
        final String s = StringUtils.trimToNull(sb);
        checkArgument(StringUtils.isNotEmpty(s), "Empty search base");
        m_searchBase = Right(s);
        return this;
    }
    
    /**
     * 
     * @param sb
     * @return 
     */
    public LdapSearchBuilder2 filter(final CharSequence sb) {
        final String s = StringUtils.trimToNull(sb);
        checkArgument(StringUtils.isNotEmpty(s), "Empty filter");
        m_filter = s;
        return this;
    }

    /**
     * 
     * @param filterArgs
     * @return 
     */
    public LdapSearchBuilder2 filterArgs(final Object[] filterArgs) {
        m_filterArgs = filterArgs == null ? null : Vector.of(filterArgs);
        return this;
    }

    /**
     * 
     * @param filterArgs
     * @return 
     */
    public LdapSearchBuilder2 filterArgs(final Iterable<?> filterArgs) {
        m_filterArgs = filterArgs == null ? null : Vector.of(filterArgs);
        return this;
    }

    /**
     * 
     * @param sc
     * @return 
     */
    public LdapSearchBuilder2 searchControls(final SearchControls sc) {
        Objects.requireNonNull(sc, "Search Controls");
        m_sc = LdapUtils.clone(sc);
        return this;
    }

    /**
     * 
     * @return 
     */
    public SearchControls searchControls() {
        return m_sc;
    }

    /**
     * 
     * @param limit
     * @return 
     */
    public LdapSearchBuilder2 countLimit(final int limit) {
        m_sc.setCountLimit(limit);
        return this;
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public LdapSearchBuilder2 searchScope(final int scope) {
        m_sc.setSearchScope(scope);
        return this;
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public LdapSearchBuilder2 searchScope(final SearchScope scope) {
        Objects.requireNonNull(scope, "Search Scope");
        m_sc.setSearchScope(scope.value());
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearchBuilder2 derefLinkFlag(final boolean flag) {
        m_sc.setDerefLinkFlag(flag);
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearchBuilder2 returningObjFlag(final boolean flag) {
        m_sc.setReturningObjFlag(flag);
        return this;
    }

    /**
     * 
     * @param millis
     * @return 
     */
    public LdapSearchBuilder2 timeLimit(final int millis) {
        m_sc.setTimeLimit(millis);
        return this;
    }
    
    /**
     * 
     * @param duration
     * @return 
     */
    public LdapSearchBuilder2 timeLimit(final Duration duration) {
        m_sc.setTimeLimit(Math.toIntExact(duration.toMillis()));
        return this;
    }

    /**
     * 
     * @param duration
     * @return 
     */
    public LdapSearchBuilder2 timeLimit(final TimeValueUnit duration) {
        m_sc.setTimeLimit(Math.toIntExact(duration.toMillis()));
        return this;
    }

    /**
     * 
     * @param pageSize
     * @return 
     */
    public LdapSearchBuilder2 pageSize(final int pageSize) {
        m_pageSize = pageSize >= 0 ? Some(pageSize) : None();
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearchBuilder2 requestControls(final Control[] controls) {
        m_reqControls = controls == null ? null : Vector.of(controls);
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearchBuilder2 requestControls(final Iterable<Control> controls) {
        m_reqControls = controls == null ? null : Vector.ofAll(controls);
        return this;
    }

    /**
     * 
     * @param <S>
     * @param retAttrs
     * @return 
     */
    public <S extends CharSequence> LdapSearchBuilder2 returningAttributes(final S... retAttrs) {
        if (ArrayUtils.isEmpty(retAttrs)) {
            m_sc.setReturningAttributes(null);
        } else {
            m_sc.setReturningAttributes(StringUtils.toArray(retAttrs));
        }
        return this;
    }

    /**
     * 
     * @param <S>
     * @param <I>
     * @param retAttrs
     * @return 
     */
    public <S extends CharSequence, I extends Iterable<S>> LdapSearchBuilder2 returningAttributes(final I retAttrs) {
        return returningAttributes(IterableAccessors.from(retAttrs)
                    .map(asString())
                    .to(Java.array(String[]::new)));
    }

    private Either<LdapContextBuilder, LdapContext> m_ldapContext;
    
    private Either<LdapName, String> m_searchBase;
    
    private Option<Integer> m_pageSize = None();

    private String m_filter;
    
    private Seq<Object> m_filterArgs;
    
    private Seq<Control> m_reqControls;

    private SearchControls m_sc = new SearchControls();
    
}
