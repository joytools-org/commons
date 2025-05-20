/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.sandbox.util;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.TextStringBuilder;

/**
 *  This class formats in a fashion manner a List of ArgDecl
 *
 *@created  19 ottobre 2001
 */
public class ArgsDescriber {

    /**
     *  Constructor for the ArgDescriptor object
     */
    public ArgsDescriber() {
    }


    /**
     *  Constructor for the ArgDescriptor object
     *
     *@param  argsDecl  The List of Parameters
     */
    public ArgsDescriber(List argsDecl) {
        this();
        setArgsDecl(argsDecl);
    }


    /**
     *  Sets the ArgsDecl attribute of the ArgDescriptor object
     *
     *@param  argsDecl  The new ArgsDecl value
     */
    public void setArgsDecl(List<ArgDecl> argsDecl) {
        this.argsDecl.clear();
        if (argsDecl != null) {
            this.argsDecl.addAll(argsDecl);
        }
    }


    /**
     *  Gets the ArgsDecl attribute of the ArgsDescriptor object
     *
     *@return    The ArgsDecl value
     */
    public List getArgsDecl() {
        return new ArrayList(this.argsDecl);
    }


    /**
     *  Override of the Method toString()
     *
     *@return    Helpful description of the parameter's list
     */
    @Override
    public String toString() {
        final TextStringBuilder result = new TextStringBuilder(getClass().getName() + " ");
        final TextStringBuilder list = new TextStringBuilder("\n");
        if (this.argsDecl == null || this.argsDecl.isEmpty()) {
            return result.append(" No parameters needed ").toString();
        }
        for (final ArgDecl argDecl : argsDecl) {
            final Set switches = argDecl.getSwitches();
            final int min = argDecl.getMinValuesCount();
            final int max = argDecl.getMaxValuesCount();

            String cmd = StringUtils.join(switches, " | ");
            if (StringUtils.isEmpty(cmd)) {
                    cmd = argDecl.getName();
            }
            if (StringUtils.isEmpty(cmd)) {
                    cmd = "default";
            }
            if (cmd.equals("default")) {
                    if (argDecl.isRequired()) {
                            result.append("[").append(cmd).append("]");
                    }
                    else {
                            result.append(" [<").append(cmd).append(">]");
                    }
            }
            else {
                    if (argDecl.isRequired()) {
                            result.append("[-").append(cmd).append("]");
                    }
                    else {
                            result.append(" [-<").append(cmd).append(">]");
                    }

            }

            list.append("[").append(cmd).append("] ").append(argDecl.getDescription()).append("\n");
            if (min == max && min > 0) {
                list.append("Exactly ").append(min).append(" values are required \n");
            }
            else {
                if (min > 0) {
                        list.append("At minimum ").append(min).append(" values are required \n");
                }

                if (max > 0) {
                        list.append("At maximum ").append(max).append(" values are allowed \n");
                }
                if (min == 0 && max == 0) {
                        list.append("No required values\n");
                }
            }

            if (!argDecl.getAllowedValues().isEmpty()) {
                list.append("Accepted values: ")
                        .appendWithSeparators(argDecl.getAllowedValues(), " | ")
                        .append("\n");
            }

            if (argDecl.isRequired()) {
                list.append("Required\n");
            }
            else {
                list.append("Optional\n");
            }
            
            list.append("\n");
        }

        return result.append("\n").append(list).toString();
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     */
    protected List<ArgDecl> internalGetList() {
        return this.argsDecl;
    }

    /**
     *  The List of paramters
     */
    final protected List<ArgDecl> argsDecl = new ArrayList<>();

}
