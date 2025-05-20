/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import org.joytools.commons.naming.directory.SearchParameters;
import com.google.common.collect.AbstractIterator;
import static io.vavr.Predicates.instanceOf;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.function.FailableAutoCloseable;
import org.joytools.commons.function.FailableAutoCloseables;
import org.joytools.commons.interop.Java;

// import org.joytools.commons.collections.StreamIterables;
import java.io.UncheckedIOException;

import org.joytools.commons.naming.NamingEnumerationSupplier;
import org.joytools.commons.naming.UncheckedNamingException;
import org.joytools.commons.util.EarlyFailableDisposer;
import org.joytools.commons.util.FailableAutoCloseableContainer;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class LdapSearch extends FailableAutoCloseableContainer<NamingException> {

    /**
     * 
     * @param ldapCtxSource 
     */
    protected LdapSearch(final LdapContextSource ldapCtxSource) {
        this.m_ldapCtxSource = Objects.requireNonNull(ldapCtxSource, "Ldap Context Source");
    }

    /**
     * 
     * @param ldapCtxSource
     * @return 
     */
    public static LdapSearch on(final LdapContextSource ldapCtxSource) {
        return new LdapSearch(ldapCtxSource);
    }

    /**
     * 
     * @param ldapCtx
     * @return 
     */
    public static LdapSearch on(final LdapContext ldapCtx) {
        return new LdapSearch(LdapContextSource.of(ldapCtx));
    }
    
    /**
     * 
     * @param ldapCtxBuilder
     * @return 
     */
    public static LdapSearch on(final LdapContextBuilder ldapCtxBuilder) {
        return new LdapSearch(LdapContextSource.of(ldapCtxBuilder));
    }
    
    /**
     * 
     * @return 
     */
    protected int pageSize() {
        if (m_ldapSearchOpts != null) {
            return m_ldapSearchOpts.pageSize();
        }
        return -1;
    }
    
    /**
     * 
     * @return 
     */
    protected long fetchLimit() {
        if (m_ldapSearchOpts != null) {
            return m_ldapSearchOpts.fetchLimit();
        }
        return -1;
    }

    /**
     * 
     * @param facade
     * @return
     * @throws NamingException 
     */
    public IterableAccessor<SearchResult> search(final SearchParameters facade) throws NamingException {
        Objects.requireNonNull(facade);
        return handleSearch(facade.supplierOf(ldapContext()));
    }

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes, and retrieves selected attributes.
     * The search is performed using the default
     * <code>SearchControls</code> settings.
     * <p>
     * For an object to be selected, each attribute in
     * <code>matchingAttributes</code> must match some attribute of the
     * object.  If <code>matchingAttributes</code> is empty or
     * null, all objects in the target context are returned.
     *<p>
     * An attribute <em>A</em><sub>1</sub> in
     * <code>matchingAttributes</code> is considered to match an
     * attribute <em>A</em><sub>2</sub> of an object if
     * <em>A</em><sub>1</sub> and <em>A</em><sub>2</sub> have the same
     * identifier, and each value of <em>A</em><sub>1</sub> is equal
     * to some value of <em>A</em><sub>2</sub>.  This implies that the
     * order of values is not significant, and that
     * <em>A</em><sub>2</sub> may contain "extra" values not found in
     * <em>A</em><sub>1</sub> without affecting the comparison.  It
     * also implies that if <em>A</em><sub>1</sub> has no values, then
     * testing for a match is equivalent to testing for the presence
     * of an attribute <em>A</em><sub>2</sub> with the same
     * identifier.
     *<p>
     * The precise definition of "equality" used in comparing attribute values
     * is defined by the underlying directory service.  It might use the
     * <code>Object.equals</code> method, for example, or might use a schema
     * to specify a different equality operation.
     * For matching based on operations other than equality (such as
     * substring comparison) use the version of the <code>search</code>
     * method that takes a filter argument.
     * <p>
     * When changes are made to this <tt>DirContext</tt>,
     * the effect on enumerations returned by prior calls to this method
     * is undefined.
     *<p>
     * If the object does not have the attribute
     * specified, the directory will ignore the nonexistent attribute
     * and return the requested attributes that the object does have.
     *<p>
     * A directory might return more attributes than was requested
     * (see <strong>Attribute Type Names</strong> in the class description),
     * but is not allowed to return arbitrary, unrelated attributes.
     *<p>
     * See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for.  If empty or null,
     *          all objects in the target context are returned.
     * @param attributesToReturn
     *          the attributes to return.  null indicates that
     *          all attributes are to be returned;
     *          an empty array indicates that none are to be returned.
     * @return
     *          a non-null enumeration of <tt>SearchResult</tt> objects.
     *          Each <tt>SearchResult</tt> contains the attributes
     *          identified by <code>attributesToReturn</code>
     *          and the name of the corresponding object, named relative
     *          to the context named by <code>name</code>.
     * @throws  NamingException if a naming exception is encountered
     *
     * @see SearchControls
     * @see SearchResult
     * @see #search(Name, String, Object[], SearchControls)
     */
    public IterableAccessor<SearchResult> search(final Name name,
               final Attributes matchingAttributes,
               final String[] attributesToReturn) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, matchingAttributes, attributesToReturn)));
    }

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes, and retrieves selected attributes.
     * See {@link #search(Name, Attributes, String[])} for details.
     *
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for
     * @param attributesToReturn
     *          the attributes to return
     * @return  a non-null enumeration of <tt>SearchResult</tt> objects
     * @throws  NamingException if a naming exception is encountered
     */
    public IterableAccessor<SearchResult> search(final String name,
               final Attributes matchingAttributes,
               final String[] attributesToReturn) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, matchingAttributes, attributesToReturn)));
    }

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes.
     * This method returns all the attributes of such objects.
     * It is equivalent to supplying null as
     * the <tt>atributesToReturn</tt> parameter to the method
     * <code>search(Name, Attributes, String[])</code>.
     * <br>
     * See {@link #search(Name, Attributes, String[])} for a full description.
     *
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for
     * @return  an enumeration of <tt>SearchResult</tt> objects
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #search(Name, Attributes, String[])
     */
    public IterableAccessor<SearchResult> search(final Name name, 
            final Attributes matchingAttributes) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, matchingAttributes)));
    }

    /**
     * Searches in a single context for objects that contain a
     * specified set of attributes.
     * See {@link #search(Name, Attributes)} for details.
     *
     * @param name
     *          the name of the context to search
     * @param matchingAttributes
     *          the attributes to search for
     * @return  an enumeration of <tt>SearchResult</tt> objects
     * @throws  NamingException if a naming exception is encountered
     */
    public IterableAccessor<SearchResult> search(final String name, 
            final Attributes matchingAttributes) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, matchingAttributes)));
    }

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     * <p>
     * The format and interpretation of <code>filter</code> follows RFC 2254
     * with the
     * following interpretations for <code>attr</code> and <code>value</code>
     * mentioned in the RFC.
     * <p>
     * <code>attr</code> is the attribute's identifier.
     * <p>
     * <code>value</code> is the string representation the attribute's value.
     * The translation of this string representation into the attribute's value
     * is directory-specific.
     * <p>
     * For the assertion "someCount=127", for example, <code>attr</code>
     * is "someCount" and <code>value</code> is "127".
     * The provider determines, based on the attribute ID ("someCount")
     * (and possibly its schema), that the attribute's value is an integer.
     * It then parses the string "127" appropriately.
     *<p>
     * Any non-ASCII characters in the filter string should be
     * represented by the appropriate Java (Unicode) characters, and
     * not encoded as UTF-8 octets.  Alternately, the
     * "backslash-hexcode" notation described in RFC 2254 may be used.
     *<p>
     * If the directory does not support a string representation of
     * some or all of its attributes, the form of <code>search</code> that
     * accepts filter arguments in the form of Objects can be used instead.
     * The service provider for such a directory would then translate
     * the filter arguments to its service-specific representation
     * for filter evaluation.
     * See <code>search(Name, String, Object[], SearchControls)</code>.
     * <p>
     * RFC 2254 defines certain operators for the filter, including substring
     * matches, equality, approximate match, greater than, less than.  These
     * operators are mapped to operators with corresponding semantics in the
     * underlying directory. For example, for the equals operator, suppose
     * the directory has a matching rule defining "equality" of the
     * attributes in the filter. This rule would be used for checking
     * equality of the attributes specified in the filter with the attributes
     * of objects in the directory. Similarly, if the directory has a
     * matching rule for ordering, this rule would be used for
     * making "greater than" and "less than" comparisons.
     *<p>
     * Not all of the operators defined in RFC 2254 are applicable to all
     * attributes.  When an operator is not applicable, the exception
     * <code>InvalidSearchFilterException</code> is thrown.
     * <p>
     * The result is returned in an enumeration of <tt>SearchResult</tt>s.
     * Each <tt>SearchResult</tt> contains the name of the object
     * and other information about the object (see SearchResult).
     * The name is either relative to the target context of the search
     * (which is named by the <code>name</code> parameter), or
     * it is a URL string. If the target context is included in
     * the enumeration (as is possible when
     * <code>cons</code> specifies a search scope of
     * <code>SearchControls.OBJECT_SCOPE</code> or
     * <code>SearchControls.SUBSTREE_SCOPE</code>), its name is the empty
     * string. The <tt>SearchResult</tt> may also contain attributes of the
     * matching object if the <tt>cons</tt> argument specified that attributes
     * be returned.
     *<p>
     * If the object does not have a requested attribute, that
     * nonexistent attribute will be ignored.  Those requested
     * attributes that the object does have will be returned.
     *<p>
     * A directory might return more attributes than were requested
     * (see <strong>Attribute Type Names</strong> in the class description)
     * but is not allowed to return arbitrary, unrelated attributes.
     *<p>
     * See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * @param name
     *          the name of the context or object to search
     * @param filter
     *          the filter expression to use for the search; may not be null
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     * @return  an enumeration of <tt>SearchResult</tt>s of
     *          the objects that satisfy the filter; never null
     *
     * @throws  InvalidSearchFilterException if the search filter specified is
     *          not supported or understood by the underlying directory
     * @throws  InvalidSearchControlsException if the search controls
     *          contain invalid settings
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #search(Name, String, Object[], SearchControls)
     * @see SearchControls
     * @see SearchResult
     */
    public IterableAccessor<SearchResult> search(final Name name,
               final String filter,
               final SearchControls cons) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, filter, cons)));
    }

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     * See {@link #search(Name, String, SearchControls)} for details.
     *
     * @param name
     *          the name of the context or object to search
     * @param filter
     *          the filter expression to use for the search; may not be null
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     *
     * @return  an enumeration of <tt>SearchResult</tt>s for
     *          the objects that satisfy the filter.
     * @throws  InvalidSearchFilterException if the search filter specified is
     *          not supported or understood by the underlying directory
     * @throws  InvalidSearchControlsException if the search controls
     *          contain invalid settings
     * @throws  NamingException if a naming exception is encountered
     */
    public IterableAccessor<SearchResult> search(final String name,
               final String filter,
               final SearchControls cons) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, filter, cons)));
    }

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     *<p>
     * The interpretation of <code>filterExpr</code> is based on RFC
     * 2254.  It may additionally contain variables of the form
     * <code>{i}</code> -- where <code>i</code> is an integer -- that
     * refer to objects in the <code>filterArgs</code> array.  The
     * interpretation of <code>filterExpr</code> is otherwise
     * identical to that of the <code>filter</code> parameter of the
     * method <code>search(Name, String, SearchControls)</code>.
     *<p>
     * When a variable <code>{i}</code> appears in a search filter, it
     * indicates that the filter argument <code>filterArgs[i]</code>
     * is to be used in that place.  Such variables may be used
     * wherever an <em>attr</em>, <em>value</em>, or
     * <em>matchingrule</em> production appears in the filter grammar
     * of RFC 2254, section 4.  When a string-valued filter argument
     * is substituted for a variable, the filter is interpreted as if
     * the string were given in place of the variable, with any
     * characters having special significance within filters (such as
     * <code>'*'</code>) having been escaped according to the rules of
     * RFC 2254.
     *<p>
     * For directories that do not use a string representation for
     * some or all of their attributes, the filter argument
     * corresponding to an attribute value may be of a type other than
     * String.  Directories that support unstructured binary-valued
     * attributes, for example, should accept byte arrays as filter
     * arguments.  The interpretation (if any) of filter arguments of
     * any other type is determined by the service provider for that
     * directory, which maps the filter operations onto operations with
     * corresponding semantics in the underlying directory.
     *<p>
     * This method returns an enumeration of the results.
     * Each element in the enumeration contains the name of the object
     * and other information about the object (see <code>SearchResult</code>).
     * The name is either relative to the target context of the search
     * (which is named by the <code>name</code> parameter), or
     * it is a URL string. If the target context is included in
     * the enumeration (as is possible when
     * <code>cons</code> specifies a search scope of
     * <code>SearchControls.OBJECT_SCOPE</code> or
     * <code>SearchControls.SUBSTREE_SCOPE</code>),
     * its name is the empty string.
     *<p>
     * The <tt>SearchResult</tt> may also contain attributes of the matching
     * object if the <tt>cons</tt> argument specifies that attributes be
     * returned.
     *<p>
     * If the object does not have a requested attribute, that
     * nonexistent attribute will be ignored.  Those requested
     * attributes that the object does have will be returned.
     *<p>
     * A directory might return more attributes than were requested
     * (see <strong>Attribute Type Names</strong> in the class description)
     * but is not allowed to return arbitrary, unrelated attributes.
     *<p>
     * If a search filter with invalid variable substitutions is provided
     * to this method, the result is undefined.
     * When changes are made to this DirContext,
     * the effect on enumerations returned by prior calls to this method
     * is undefined.
     *<p>
     * See also <strong>Operational Attributes</strong> in the class
     * description.
     *
     * @param name
     *          the name of the context or object to search
     * @param filterExpr
     *          the filter expression to use for the search.
     *          The expression may contain variables of the
     *          form "<code>{i}</code>" where <code>i</code>
     *          is a nonnegative integer.  May not be null.
     * @param filterArgs
     *          the array of arguments to substitute for the variables
     *          in <code>filterExpr</code>.  The value of
     *          <code>filterArgs[i]</code> will replace each
     *          occurrence of "<code>{i}</code>".
     *          If null, equivalent to an empty array.
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     * @return  an enumeration of <tt>SearchResult</tt>s of the objects
     *          that satisfy the filter; never null
     *
     * @throws  ArrayIndexOutOfBoundsException if <tt>filterExpr</tt> contains
     *          <code>{i}</code> expressions where <code>i</code> is outside
     *          the bounds of the array <code>filterArgs</code>
     * @throws  InvalidSearchControlsException if <tt>cons</tt> contains
     *          invalid settings
     * @throws  InvalidSearchFilterException if <tt>filterExpr</tt> with
     *          <tt>filterArgs</tt> represents an invalid search filter
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #search(Name, Attributes, String[])
     * @see java.text.MessageFormat
     */
    public IterableAccessor<SearchResult> search(final Name name,
               final String filterExpr,
               final Object[] filterArgs,
               final SearchControls cons) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, filterExpr, filterArgs, cons)));
    }

    /**
     * Searches in the named context or object for entries that satisfy the
     * given search filter.  Performs the search as specified by
     * the search controls.
     * See {@link #search(Name, String, Object[], SearchControls)} for details.
     *
     * @param name
     *          the name of the context or object to search
     * @param filterExpr
     *          the filter expression to use for the search.
     *          The expression may contain variables of the
     *          form "<code>{i}</code>" where <code>i</code>
     *          is a nonnegative integer.  May not be null.
     * @param filterArgs
     *          the array of arguments to substitute for the variables
     *          in <code>filterExpr</code>.  The value of
     *          <code>filterArgs[i]</code> will replace each
     *          occurrence of "<code>{i}</code>".
     *          If null, equivalent to an empty array.
     * @param cons
     *          the search controls that control the search.  If null,
     *          the default search controls are used (equivalent
     *          to <tt>(new SearchControls())</tt>).
     * @return  an enumeration of <tt>SearchResult</tt>s of the objects
     *          that satisfy the filter; never null
     *
     * @throws  ArrayIndexOutOfBoundsException if <tt>filterExpr</tt> contains
     *          <code>{i}</code> expressions where <code>i</code> is outside
     *          the bounds of the array <code>filterArgs</code>
     * @throws  InvalidSearchControlsException if <tt>cons</tt> contains
     *          invalid settings
     * @throws  InvalidSearchFilterException if <tt>filterExpr</tt> with
     *          <tt>filterArgs</tt> represents an invalid search filter
     * @throws  NamingException if a naming exception is encountered
     */
    public IterableAccessor<SearchResult> search(final String name,
               final String filterExpr,
               final Object[] filterArgs,
               final SearchControls cons) throws NamingException {
        return handleSearch(() -> (ldapContext().search(name, filterExpr, filterArgs, cons)));
    }

    /**
     * 
     * @return 
     */
    public LdapSearch lenient() {
        this.m_lenient = true;
        return this;
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public LdapSearch lenient(final boolean flag) {
        this.m_lenient = flag;
        return this;
    }

    /**
     * 
     * @param controls
     * @return 
     */
    public LdapSearch requestControls(final Control... controls) {
        this.m_requestControls = controls;
        return this;
    }

    /**
     * 
     * @param ldapSearchOpts
     * @return 
     */
    public LdapSearch options(final LdapSearchOptions ldapSearchOpts) {
        this.m_ldapSearchOpts = ldapSearchOpts;
        return this;
    }
    
    /**
     * 
     * @param resultSupplier 
     * @return  
     * @throws javax.naming.NamingException 
     */
    protected IterableAccessor<SearchResult> handleSearch(final NamingEnumerationSupplier<SearchResult> resultSupplier) throws NamingException {
        if (m_searchResultSupplier != null) {
            throw new IllegalStateException("A search is already in progress");
        }
        m_searchResultSupplier = resultSupplier;
        
        Control[] ctrls = LdapUtils.append(ldapContext().getRequestControls(), m_requestControls);
        final int pageSize = pageSize();
        if (pageSize > 0) {
            try {
                if (IterableAccessors.from(ctrls)
                        .findFirst(instanceOf(PagedResultsControl.class))
                        .isEmpty()) {
                    ctrls = LdapUtils.append(ctrls, new PagedResultsControl(pageSize, Control.NONCRITICAL));
                }
            } catch (final IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }
        ldapContext().setRequestControls(ctrls);
        
        // Perform the search
        m_searchResultEnum = resultSupplier.get();
        earlyDispose(m_searchResultEnum);
        
        final Iterator<SearchResult> itr = new AbstractIterator<SearchResult>() {
            @Override
            protected SearchResult computeNext() {
                try {
                    final SearchResult n = nextSearchResult();
                    if (n != null) {
                        return n;
                    }
                    return endOfData();
                } catch (final NamingException ex) {
                    throw new UncheckedNamingException(ex);
                }
            }
        };

        return IterableAccessors.from(itr);
    }
    
    /**
     * 
     * @param <T>
     * @param ne
     * @return
     * @throws NamingException 
     */
    protected <T> NamingEnumeration<T> earlyDispose(final NamingEnumeration<T> ne) throws NamingException {
        earlyDisposer().dispose(ne, FailableAutoCloseables.of(ne::close));
        return ne;
    }

    /**
     * 
     * @param <T>
     * @param ne
     * @throws NamingException 
     */
    protected <T> void earlyClose(final NamingEnumeration<T> ne) throws NamingException {
        earlyDisposer().earlyClose(ne);
    }

    /**
     * 
     * @return 
     */
    public Integer estimatedSize() {
        return m_estimatedSize;
    }
    
    /**
     * 
     * @return
     * @throws NamingException 
     */
    protected LdapContext ldapContext() throws NamingException {
        if (m_ldapCtx == null) {
            final LdapContextSession cts = dispose(m_ldapCtxSource.newSession());
            m_ldapCtx = cts.ldapContext();
        }
        return m_ldapCtx;
    }
    
    /**
     * 
     * @return 
     * @throws javax.naming.NamingException 
     */
    protected SearchResult nextSearchResult() throws NamingException {
        if (m_searchResultEnum == null) {
            throw new IllegalStateException("No search performed");
        }
        if (fetchLimitReached()) {
            return null;
        }
        if (m_lenient) {
            if (m_searchResultEnum.hasMoreElements()) {
                m_rowCount++;
                return fetchLimitReached() ? null : m_searchResultEnum.nextElement();
            }
        } else {
            if (m_searchResultEnum.hasMore()) {
                m_rowCount++;
                return fetchLimitReached() ? null : m_searchResultEnum.next();
            }
        }
        try {
            earlyClose(m_searchResultEnum);
        } catch (final NamingException e) { }
        m_searchResultEnum = null;

        // Check for pagination
        if (pageSize() <= 0) {
            return null;
        }
        
        // Check if there's a next page
        if (!nextPage()) {
            return null;
        }
        
        // Re-perform the search on the next page
        m_searchResultEnum = m_searchResultSupplier.get();
        earlyDispose(m_searchResultEnum);
        if (m_lenient) {
            if (m_searchResultEnum.hasMoreElements()) {
                m_rowCount++;
                return fetchLimitReached() ? null : m_searchResultEnum.nextElement();
            }
        } else {
            if (m_searchResultEnum.hasMore()) {
                m_rowCount++;
                return fetchLimitReached() ? null : m_searchResultEnum.next();
            }
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    protected boolean fetchLimitReached() {
        final long ff = fetchLimit();
        return ff >= 0 && m_rowCount > ff;
    }
    
    /**
     * 
     * @return
     * @throws NamingException 
     */
    protected boolean nextPage() throws NamingException {
        byte[] cookie = null;
        final Control[] controls = ldapContext().getResponseControls();
        if (controls != null) {
            for (final Control control : controls) {
                if (control instanceof PagedResultsResponseControl prrc) {
                    m_estimatedSize = prrc.getResultSize();
                    cookie = prrc.getCookie();
                }
            }
        } 
        // Re-activate paged results
        try {
            final Control[] ctrls = IterableAccessors.from(ldapContext().getRequestControls())
                    .omit(instanceOf(PagedResultsControl.class))
                    .append(new PagedResultsControl(pageSize(), cookie, Control.CRITICAL))
                    .to(Java.array(Control[]::new));
            ldapContext().setRequestControls(ctrls);
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        return cookie != null;
    }
    
    /**
     * 
     */
    private LdapContext m_ldapCtx = null;
    
    /**
     * 
     */
    private final LdapContextSource m_ldapCtxSource;

    /**
     * 
     */
    private NamingEnumerationSupplier<SearchResult> m_searchResultSupplier = null;

    /**
     * 
     */
    private boolean m_lenient = false;
    
    /**
     * 
     */
    private NamingEnumeration<SearchResult> m_searchResultEnum = null;

    /**
     * 
     */
    private LdapSearchOptions m_ldapSearchOpts = null;
            
    /**
     * 
     */
    private Integer m_estimatedSize = null;
    
    /**
     * 
     */
    private Control[] m_requestControls;
    
    /**
     * 
     */
    private long m_rowCount = 0;
    
    protected EarlyFailableDisposer<NamingEnumeration, NamingException> earlyDisposer() {
        if (m_earlyDisposer == null) {
            m_earlyDisposer = new EarlyFailableDisposer(disposer());
        }
        return m_earlyDisposer;
    }

    private EarlyFailableDisposer<NamingEnumeration, NamingException> m_earlyDisposer;
       
}
