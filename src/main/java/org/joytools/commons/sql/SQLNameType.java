/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

/**
 *
 * @author AndreaR
 */
public enum SQLNameType {
    
    // https://askgarth.com/blog/what-are-the-four-part-names-of-a-sql-server-object/
    
    COLUMN,
    TABLE,
    VIEW,
    SCHEMA,
    CATALOG,
    SERVER,
    PRIMARY_KEY,
    FOREIGN_KEY,
    INDEX,
    PROCEDURE,
    FUNCTION,
    TRIGGER;
    
    /**
     * 
     * @return 
     */
    public boolean isColumn() {
        return this == COLUMN;
    }

    /**
     * 
     * @return 
     */
    public boolean isTable() {
        return this == TABLE;
    }

    /**
     * 
     * @return 
     */
    public boolean isView() {
        return this == VIEW;
    }

    /**
     * 
     * @return 
     */
    public boolean isTableOrView() {
        return this == TABLE || this == VIEW;
    }

    /**
     * 
     * @return 
     */
    public boolean isSchema() {
        return this == SCHEMA;
    }

    /**
     * 
     * @return 
     */
    public boolean isCatalog() {
        return this == CATALOG;
    }

    /**
     * 
     * @return 
     */
    public boolean isServer() {
        return this == SERVER;
    }

    /**
     * 
     * @return 
     */
    public boolean isProcedure() {
        return this == PROCEDURE;
    }

    /**
     * 
     * @return 
     */
    public boolean isFunction() {
        return this == FUNCTION;
    }

    /**
     * 
     * @return 
     */
    public boolean isIndex() {
        return this == INDEX;
    }

    /**
     * 
     * @return 
     */
    public boolean isPrimaryKey() {
        return this == PRIMARY_KEY;
    }

    /**
     * 
     * @return 
     */
    public boolean isForeignKey() {
        return this == FOREIGN_KEY;
    }

}
