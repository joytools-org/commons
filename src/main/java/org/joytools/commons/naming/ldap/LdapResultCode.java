/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

/**
 * https://ldapwiki.com/wiki/LDAP%20Result
 * https://ldapwiki.com/wiki/LDAP%20Result%20Codes
 * 
 * @author AndreaR
 */
public enum LdapResultCode {

    LDAP_SUCCESS(0, "Indicates the requested client operation completed successfully."),
    LDAP_OPERATIONS_ERROR(1, "NamingException"),
    LDAP_PROTOCOL_ERROR(2, "CommunicationException"),
    LDAP_TIMELIMIT_EXCEEDED(3, "TimeLimitExceededException"),
    LDAP_SIZELIMIT_EXCEEDED(4, "SizeLimitExceededException"),
    LDAP_COMPARE_FALSE(5, "Used by DirContext.search()"),
    LDAP_COMPARE_TRUE(6, "Used by DirContext.search(). Does not generate an exception."),
    LDAP_AUTH_METHOD_NOT_SUPPORTED(7, "AuthenticationNotSupportedException"),
    LDAP_STRONG_AUTH_REQUIRED(8, "AuthenticationNotSupportedException"),
    LDAP_REFERRAL(10, "If the environment property \"java.naming.referral\" is set to \"ignore\" or the contents of the error do not contain a referral, throw a PartialResultException. Otherwise, use contents to build a referral."),
    LDAP_ADMINLIMIT_EXCEEDED(11, "Indicates that an LDAP server limit set by an administrative authority has been exceeded."),
    LDAP_UNAVAILABLE_CRITICAL_EXTENSION(12, "Indicates that the LDAP server was unable to satisfy a request because one or more critical extensions were not available. Either the server does not support the control or the control is not appropriate for the operation type."),
    LDAP_CONFIDENTIALITY_REQUIRED(13, "Indicates that the session is not protected by a protocol such as Transport Layer Security (TLS), which provides session confidentiality."),
    LDAP_SASL_BIND_IN_PROGRESS(14, "Does not indicate an error condition, but indicates that the server is ready for the next step in the process. The client must send the server the same SASL mechanism to continue the process."),
    LDAP_NO_SUCH_ATTRIBUTE(16, "Indicates that the attribute specified in the modify or compare operation does not exist in the entry."),
    LDAP_UNDEFINED_TYPE(17, "Indicates that the attribute specified in the modify or add operation does not exist in the LDAP server's schema."),
    LDAP_INAPPROPRIATE_MATCHING(18, "Indicates that the matching rule specified in the search filter does not match a rule defined for the attribute's syntax."),
    LDAP_CONSTRAINT_VIOLATION(19, "Indicates that the attribute value specified in a modify, add, or modify DN operation violates constraints placed on the attribute. The constraint can be one of size or content (string only, no binary)."),
    LDAP_TYPE_OR_VALUE_EXISTS(20, "Indicates that the attribute value specified in a modify or add operation already exists as a value for that attribute."),
    LDAP_INVALID_SYNTAX(21, "Indicates that the attribute value specified in an add, compare, or modify operation is an unrecognized or invalid syntax for the attribute."),
    LDAP_NO_SUCH_OBJECT(32, "Indicates the target object cannot be found. This code is not returned on following operations:" + System.lineSeparator()
            + "* Search operations that find the search base but cannot find any entries that match the search filter." + System.lineSeparator()
            + "* Bind operations."),
    LDAP_ALIAS_PROBLEM(33, "NamingException"),
    LDAP_INVALID_DN_SYNTAX(34, "Indicates that the syntax of the DN is incorrect. (If the DN syntax is correct, but the LDAP server's structure rules do not permit the operation, the server returns LDAP_UNWILLING_TO_PERFORM.)"),
    LDAP_IS_LEAF(35, "Indicates that the specified operation cannot be performed on a leaf entry. (This code is not currently in the LDAP specifications, but is reserved for this constant.)"),
    LDAP_ALIAS_DEREF_PROBLEM(36, "NamingException"),
    LDAP_INAPPROPRIATE_AUTH(48, "Indicates that during a bind operation, the client is attempting to use an authentication method that the client cannot use correctly. For example, either of the following cause this error:" + System.lineSeparator()
            + "* The client returns simple credentials when strong credentials are required." + System.lineSeparator()
            + "* The client returns a DN and a password for a simple bind when the entry does not have a password defined."),
    LDAP_INVALID_CREDENTIALS(49, "Indicates that during a bind operation one of the following occurred:" + System.lineSeparator()
            + "* The client passed either an incorrect DN or password." + System.lineSeparator()
            + "* The password is incorrect because it has expired, intruder detection has locked the account, or some other similar reason."),
    LDAP_INSUFFICIENT_ACCESS(50, "Indicates that the caller does not have sufficient rights to perform the requested operation."),
    LDAP_BUSY(51, "Indicates that the LDAP server is too busy to process the client request at this time but if the client waits and resubmits the request, the server may be able to process it then."),
    LDAP_UNAVAILABLE(52, "Indicates that the LDAP server cannot process the client's bind request, usually because it is shutting down."),
    LDAP_UNWILLING_TO_PERFORM(53, "Indicates that the LDAP server cannot process the request because of server-defined restrictions. This error is returned for the following reasons:" + System.lineSeparator()
            + "* The add entry request violates the server's structure rules." + System.lineSeparator()
            + "* The modify attribute request specifies attributes that users cannot modify." + System.lineSeparator()
            + "* Password restrictions prevent the action." + System.lineSeparator()
            + "* Connection restrictions prevent the action."),
    LDAP_LOOP_DETECT(54, "NamingException"),
    LDAP_NAMING_VIOLATION(64, "Indicates that the add or modify DN operation violates the schema's structure rules. For example:" + System.lineSeparator()
            + "* The request places the entry subordinate to an alias." + System.lineSeparator()
            + "* The request places the entry subordinate to a container that is forbidden by the containment rules." + System.lineSeparator()
            + "* The RDN for the entry uses a forbidden attribute type."),
    LDAP_OBJECT_CLASS_VIOLATION(65, "Indicates that the add, modify, or modify DN operation violates the object class rules for the entry. For example, the following types of request return this error:" + System.lineSeparator()
            + "* The add or modify operation tries to add an entry without a value for a required attribute." + System.lineSeparator()
            + "* The add or modify operation tries to add an entry with a value for an attribute which the class definition does not contain." + System.lineSeparator()
            + "* The modify operation tries to remove a required attribute without removing the auxiliary class that defines the attribute as required."),
    LDAP_NOT_ALLOWED_ON_NONLEAF(66, "Indicates that the requested operation is permitted only on leaf entries. For example, the following types of requests return this error:" + System.lineSeparator()
            + "* The client requests a delete operation on a parent entry." + System.lineSeparator()
            + "* The client request a modify DN operation on a parent entry."),
    LDAP_NOT_ALLOWED_ON_RDN(67, "Indicates that the modify operation attempted to remove an attribute value that forms the entry's relative distinguished name."),
    LDAP_ALREADY_EXISTS(68, "Indicates that the add operation attempted to add an entry that already exists, or that the modify operation attempted to rename an entry to the name of an entry that already exists."),
    LDAP_NO_OBJECT_CLASS_MODS(69, "Indicates that the modify operation attempted to modify the structure rules of an object class."),
    LDAP_RESULTS_TOO_LARGE(70, "Reserved for CLDAP."),
    LDAP_AFFECTS_MULTIPLE_DSAS(71, "NamingException"),
    LDAP_OTHER(80, "NamingException"),
    LDAP_SERVER_DOWN(81, "client-side result code that indicates that the LDAP libraries cannot establish an initial connection with the LDAP server. Either the LDAP server is down or the specified host name or port number is incorrect."),
    LDAP_LOCAL_ERROR(82, "client-side result code Indicates that the LDAP client has an error. This is usually a failed dynamic memory allocation error."),
    LDAP_ENCODING_ERROR(83, "client-side result code Indicates that the LDAP client encountered errors when encoding an LDAP request intended for the LDAP server."),
    LDAP_DECODING_ERROR(84, "client-side result code Indicates that the LDAP client encountered errors when decoding an LDAP response from the LDAP server."),
    LDAP_TIMEOUT(85, "client-side result code that indicates that the time limit of the LDAP client was exceeded while waiting for a result."),
    LDAP_AUTH_UNKNOWN(86, "client-side result code Indicates that a bind method was called with an unknown authentication method."),
    LDAP_FILTER_ERROR(87, "client-side result code Indicates that the search method was called with an invalid search filter."),
    LDAP_USER_CANCELLED(88, "client-side result code Indicates that the user cancelled the LDAP operation."),
    LDAP_PARAM_ERROR(89, "client-side result code Indicates that an invalid parameter was supplied"),
    LDAP_NO_MEMORY(90, "client-side result code Indicates that a dynamic memory allocation method failed when calling an LDAP method."),
    LDAP_CONNECT_ERROR(91, "client-side result code that indicates that the LDAP client has lost either its connection or cannot establish a connection to the LDAP server."),
    LDAP_NOT_SUPPORTED(92, "client-side result code Indicates that the requested functionality is not supported by the client. For example, if the LDAP client is established as an LDAPv2 client, the libraries set this error code when the client requests LDAPv3 functionality."),
    LDAP_CONTROL_NOT_FOUND(93, "client-side result code Indicates that the client requested a control that the libraries cannot find in the list of supported controls sent by the LDAP server."),
    LDAP_NO_RESULTS_RETURNED(94, "A client-side result code Indicates that the LDAP server sent no results."),
    LDAP_MORE_RESULTS_TO_RETURN(95, "client-side result code that indicates that more results are chained in the result message."),
    LDAP_CLIENT_LOOP(96, "client-side result code that indicates the LDAP libraries detected a loop. Usually this happens when following referrals."),
    LDAP_REFERRAL_LIMIT_EXCEEDED(97, "client-side result code that indicates that the referral exceeds the hop limit. The default hop limit is ten."),
    INVALID_RESPONSE(100, "This is a client-side result code that is used to indicate that the result received from the server was ambiguous (for example, there was more than one response received fro the associated operation)."),
    AMBIGUOUS_RESPONSE(101, "This is a client-side result code that is used to indicate that the result received from the server was ambiguous (for example, there was more than one response received fro the associated operation)."),
    TLS_NOT_SUPPORTED(112, "Indicates that TLS is not supported on the server."),
    LCUP_RESOURCES_EXHAUSTED(113, "The server is running out of resources. LDAP Client Update Protocol"),
    LCUP_SECURITY_VIOLATION(114, "the client is suspected of malicious actions. LDAP Client Update Protocol"),
    LCUP_INVALID_DATA(115, "invalid cookie was supplied by the client - both/either the scheme and/or the value part was invalid . LDAP Client Update Protocol"),
    LCUP_UNSUPPORTED_SCHEME(116, "The scheme part of the cookie is a valid OID but is not supported by this server. LDAP Client Update Protocol"),
    LCUP_RELOAD_REQUIRED(117, "indicates that client data needs to be reinitialized. This reason is returned if the server does not synchronize the client or if the server's data was reloaded since the last synchronization session. LDAP Client Update Protocol"),
    CANCELED(118, "The Cancel request is an ExtendedRequest with the requestName field containing 1.3.6.1.1.8 and a requestValue field which contains a BER-encoded cancelRequestValue value."),
    NO_SUCH_OPERATION(119, "Returned if the server has no knowledge of the operation requested for cancellation."),
    TOO_LATE(120, "Returned to indicate that it is too late to cancel the outstanding operation."),
    CANNOT_CANCEL(121, "Returned if the identified operation does not support cancellation or the cancel operation could not be performed."),
    ASSERTION_FAILED(122, "When the control is attached to an LDAP request, the processing of the request is conditional on the evaluation of the Filter as applied against the target of the operation. If the Filter evaluates to TRUE, then the request is processed normally. If the Filter evaluates to FALSE or Undefined, then assertionFailed (122) resultCode is returned, and no further processing is performed."),
    AUTHORIZATION_DENIED(123, "Used to indicate that the server does not allow the client to assume the asserted identity."),
    E_SYNC_REFRESH_REQUIRED(4096, "Specification describes the LDAP allowing a DUA to maintain a copy of a fragment of the DIT.");

    /**
     * 
     * @param code
     * @param description 
     */
    LdapResultCode(final int code,
            final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @return 
     */
    public int getCode() {
        return code;
    }

    /**
     * 
     * @return 
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @param errCode
     * @return 
     */
    public static LdapResultCode lookup(final int errCode) {
        for (final LdapResultCode e : LdapResultCode.values()) {
            if (e.getCode() == errCode) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown LDAP error code: " + errCode);
    }
    
    /**
     * 
     * @param errCode
     * @return 
     */
    public static LdapResultCode lookup(final String errCode) {
        return lookup(Integer.parseInt(errCode));
    }

    private final int code;
    
    private final String description;
    
}
