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
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.time.Duration;
import java.util.Objects;
import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.TimeValueUnit;
import static org.joytools.commons.function.Functions.asString;
import org.joytools.commons.interop.Java;
import org.joytools.commons.lang.ArrayUtils;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.naming.ContextUtils;
import org.joytools.commons.naming.NamingUtils;
import org.joytools.commons.naming.directory.SearchScope;
import org.joytools.commons.util.DefaultDisposer;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class LdapSearchBuilderV1BeforeClone {
    
    /**
     * 
     */
    protected LdapSearchBuilderV1BeforeClone() {
    }
    
    protected LdapSearchBuilderV1BeforeClone(final LdapSearchBuilderV1BeforeClone other) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @return 
     */
    public static LdapSearchBuilderV1BeforeClone of() {
        return new LdapSearchBuilderV1BeforeClone();
    }
    
    /**
     * 
     * @param ctx
     * @return 
     */
    public static LdapSearchBuilderV1BeforeClone of(final LdapContext ctx) {
        return of().context(ctx);
    }

    /**
     * 
     * @param ctxBuilder
     * @return 
     */
    public static LdapSearchBuilderV1BeforeClone of(final LdapContextBuilder ctxBuilder) {
        return of().context(ctxBuilder);
    }

    /**
     * 
     * @param disp
     * @return 
     * @throws java.lang.Exception 
     */
    public NamingEnumeration<SearchResult> buildEnumeration(final DefaultDisposer disp) throws Exception {
        Objects.requireNonNull(m_ldapContext, "Ldap Context");
        LdapContext ldapCtx;
        if (m_ldapContext.isLeft()) {
            final LdapContextBuilder builder = m_ldapContext.getLeft();
            ldapCtx = builder.build();
            disp.dispose(ldapCtx, ContextUtils.closer());
            if (m_reqControls != null) {
                ldapCtx.setRequestControls(m_reqControls);
            }
        } else {
            ldapCtx = m_ldapContext.get();
            if (m_reqControls != null) {
                ldapCtx = ldapCtx.newInstance(m_reqControls);
                disp.dispose(ldapCtx, ContextUtils.closer());
            }
        }
        if (m_pageSize.isDefined()) {
            throw new UnsupportedOperationException("Page size is not supported");
        }
        final NamingEnumeration<SearchResult> data = ldapCtx.search(m_searchBase, m_filter, m_filterArgs, m_sc);
        disp.dispose(data, data::close);
        return data;
    }

    /**
     * 
     * @param disp
     * @return
     * @throws Exception 
     */
    public IterableAccessor<SearchResult> buildIteration(final DefaultDisposer disp) throws Exception {
        final NamingEnumeration<SearchResult> data = buildEnumeration(disp);
        return NamingUtils.ofAll(data);
    }

    /**
     * 
     * @param ctx 
     * @return  
     */
    public LdapSearchBuilderV1BeforeClone context(final LdapContext ctx) {
        Objects.requireNonNull(ctx, "Ldap Context");
        m_ldapContext = Right(ctx);
        return this;
    }

    /**
     * 
     * @param ctxBuilder 
     * @return  
     */
    public LdapSearchBuilderV1BeforeClone context(final LdapContextBuilder ctxBuilder) {
        Objects.requireNonNull(ctxBuilder, "Ldap Context Builder");
        m_ldapContext = Left(ctxBuilder);
        return this;
    }

    /**
     * 
     * @param sb 
     * @return  
     */
    public LdapSearchBuilderV1BeforeClone searchBase(final CharSequence sb) {
        final String s = StringUtils.trimToNull(sb);
        checkArgument(StringUtils.isNotEmpty(s), "Empty search base");
        m_searchBase = s;
        return this;
    }
    
    /**
     * 
     * @param sb
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone filter(final CharSequence sb) {
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
    public LdapSearchBuilderV1BeforeClone filterArgs(final Object[] filterArgs) {
        m_filterArgs = filterArgs;
        return this;
    }

    /**
     * 
     * @param sc
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone searchControls(final SearchControls sc) {
        Objects.requireNonNull(sc, "Search Controls");
        m_sc = sc;
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
    public LdapSearchBuilderV1BeforeClone countLimit(final int limit) {
        m_sc.setCountLimit(limit);
        return this;
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone searchScope(final int scope) {
        m_sc.setSearchScope(scope);
        return this;
    }

    /**
     * 
     * @param scope
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone searchScope(final SearchScope scope) {
        Objects.requireNonNull(scope, "Search Scope");
        m_sc.setSearchScope(scope.value());
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone derefLinkFlag(final boolean flag) {
        m_sc.setDerefLinkFlag(flag);
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone returningObjFlag(final boolean flag) {
        m_sc.setReturningObjFlag(flag);
        return this;
    }

    /**
     * 
     * @param millis
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone timeLimit(final int millis) {
        m_sc.setTimeLimit(millis);
        return this;
    }
    
    /**
     * 
     * @param duration
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone timeLimit(final Duration duration) {
        m_sc.setTimeLimit(Math.toIntExact(duration.toMillis()));
        return this;
    }

    /**
     * 
     * @param duration
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone timeLimit(final TimeValueUnit duration) {
        m_sc.setTimeLimit(Math.toIntExact(duration.toMillis()));
        return this;
    }

    /**
     * 
     * @param pageSize
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone pageSize(final int pageSize) {
        m_pageSize = pageSize >= 0 ? Some(pageSize) : None();
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone requestControls(final Control[] controls) {
        m_reqControls = ArrayUtils.isEmpty(controls) ? null : ArrayUtils.clone(controls);
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearchBuilderV1BeforeClone requestControls(final Iterable<Control> controls) {
        Control[] ctrls = null;
        if (controls != null) {
            ctrls = IterableAccessors.from(controls).to(Java.array(Control[]::new));
        }
        return requestControls(ctrls);
    }

    /**
     * 
     * @param <S>
     * @param retAttrs
     * @return 
     */
    public <S extends CharSequence> LdapSearchBuilderV1BeforeClone returningAttributes(final S... retAttrs) {
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
    public <S extends CharSequence, I extends Iterable<S>> LdapSearchBuilderV1BeforeClone returningAttributes(final I retAttrs) {
        return returningAttributes(IterableAccessors.from(retAttrs)
                    .map(asString())
                    .to(Java.array(String[]::new)));
    }

    private Either<LdapContextBuilder, LdapContext> m_ldapContext;
    
    private String m_searchBase;
    
    private Option<Integer> m_pageSize = None();

    private String m_filter;
    
    private Object[] m_filterArgs;
    
    private Control[] m_reqControls;

    private SearchControls m_sc = new SearchControls();
    
}
