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
import java.util.Objects;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.TimeValueUnit;
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
public class LdapSearchBuilder {
    
    /**
     * 
     */
    protected LdapSearchBuilder() {
    }
    
    /**
     * 
     * @param other 
     */
    protected LdapSearchBuilder(final LdapSearchBuilder other) {
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
    public static LdapSearchBuilder of() {
        return new LdapSearchBuilder();
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public static LdapSearchBuilder on(final LdapContext ctx) {
        return of().context(ctx);
    }

    /**
     * 
     * @param ctxBuilder
     * @return 
     */
    public static LdapSearchBuilder on(final LdapContextBuilder ctxBuilder) {
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
     * @return 
     * @throws java.lang.Exception 
     */
    public NamingEnumeration<SearchResult> buildEnumeration(final Disposer<? super NamingException> disp) throws Exception {
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
     * 
     * @param disp
     * @return
     * @throws Exception 
     */
    public IterableAccessor<SearchResult> buildIteration(final Disposer<? super NamingException> disp) throws Exception {
        final NamingEnumeration<SearchResult> data = buildEnumeration(disp);
        return NamingUtils.ofAll(data);
    }

    /**
     * 
     * @param ctx 
     * @return  
     */
    public LdapSearchBuilder context(final LdapContext ctx) {
        Objects.requireNonNull(ctx, "Ldap Context");
        m_ldapContext = Right(ctx);
        return this;
    }

    /**
     * 
     * @param ctxBuilder 
     * @return  
     */
    public LdapSearchBuilder context(final LdapContextBuilder ctxBuilder) {
        Objects.requireNonNull(ctxBuilder, "Ldap Context Builder");
        m_ldapContext = Left(ctxBuilder);
        return this;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public LdapSearchBuilder searchBase(final LdapName name) {
        checkArgument(name != null && name.size() > 0, "Empty search base");
        m_searchBase = Left(name);
        return this;
    }

    /**
     * 
     * @param sb 
     * @return  
     */
    public LdapSearchBuilder searchBase(final CharSequence sb) {
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
    public LdapSearchBuilder filter(final CharSequence sb) {
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
    public LdapSearchBuilder filterArgs(final Object[] filterArgs) {
        m_filterArgs = filterArgs == null ? null : Vector.of(filterArgs);
        return this;
    }

    /**
     * 
     * @param filterArgs
     * @return 
     */
    public LdapSearchBuilder filterArgs(final Iterable<?> filterArgs) {
        m_filterArgs = filterArgs == null ? null : Vector.of(filterArgs);
        return this;
    }

    /**
     * 
     * @param sc
     * @return 
     */
    public LdapSearchBuilder searchControls(final SearchControls sc) {
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
    public LdapSearchBuilder countLimit(final int limit) {
        m_sc.setCountLimit(limit);
        return this;
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public LdapSearchBuilder searchScope(final int scope) {
        m_sc.setSearchScope(scope);
        return this;
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public LdapSearchBuilder searchScope(final SearchScope scope) {
        Objects.requireNonNull(scope, "Search Scope");
        m_sc.setSearchScope(scope.value());
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearchBuilder derefLinkFlag(final boolean flag) {
        m_sc.setDerefLinkFlag(flag);
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearchBuilder returningObjFlag(final boolean flag) {
        m_sc.setReturningObjFlag(flag);
        return this;
    }

    /**
     * 
     * @param millis
     * @return 
     */
    public LdapSearchBuilder timeLimit(final int millis) {
        m_sc.setTimeLimit(millis);
        return this;
    }
    
    /**
     * 
     * @param duration
     * @return 
     */
    public LdapSearchBuilder timeLimit(final Duration duration) {
        m_sc.setTimeLimit(Math.toIntExact(duration.toMillis()));
        return this;
    }

    /**
     * 
     * @param duration
     * @return 
     */
    public LdapSearchBuilder timeLimit(final TimeValueUnit duration) {
        m_sc.setTimeLimit(Math.toIntExact(duration.toMillis()));
        return this;
    }

    /**
     * 
     * @param pageSize
     * @return 
     */
    public LdapSearchBuilder pageSize(final int pageSize) {
        m_pageSize = pageSize >= 0 ? Some(pageSize) : None();
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearchBuilder requestControls(final Control[] controls) {
        m_reqControls = controls == null ? null : Vector.of(controls);
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearchBuilder requestControls(final Iterable<Control> controls) {
        m_reqControls = controls == null ? null : Vector.ofAll(controls);
        return this;
    }

    /**
     * 
     * @param <S>
     * @param retAttrs
     * @return 
     */
    public <S extends CharSequence> LdapSearchBuilder returningAttributes(final S... retAttrs) {
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
    public <S extends CharSequence, I extends Iterable<S>> LdapSearchBuilder returningAttributes(final I retAttrs) {
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
