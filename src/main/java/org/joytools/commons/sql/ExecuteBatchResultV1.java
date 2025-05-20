/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author AndreaR
 */
@Deprecated
interface ExecuteBatchResultV1 {
    
    public List<Integer> resultCodes();
    
    public SortedMap<Integer, Integer> succeeded();

    public SortedMap<Integer, SQLException> failed();

    public boolean hasFailed();

    public BatchUpdateException batchUpdateException();
    
}
