/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.json;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
        
/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface JsonStructureBuilderHelper<T> {
    
    public void add(
            final JsonArrayBuilder builder, 
            final T obj,
            final JsonStructureBuilderHelper caller);

    public void add(
            final JsonObjectBuilder builder, 
            final String name,
            final T obj,
            final JsonStructureBuilderHelper caller);
    
    public JsonStructure toJsonStructure(final T obj,
            final JsonStructureBuilderHelper caller);
            
}
