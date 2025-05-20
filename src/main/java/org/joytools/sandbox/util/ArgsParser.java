/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.sandbox.util;

import org.joytools.commons.text.TextTokenizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.Predicate;
import org.joytools.commons.collections.IndexedHashMap;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapBuilder;

/**
 *
 */
public class ArgsParser {

    /**
     *
     */
    public ArgsParser() { }

    /**
     *
     * @param args
     * @param mergeAdjacent
     * @throws org.joytools.sandbox.util.ArgsParserException
     */
    public ArgsParser(final List<ArgDecl> args,
            final boolean mergeAdjacent) throws ArgsParserException {
        this();
        setArgsDecl(args);
        setMergeAdjacent(mergeAdjacent);
    }

    /**
     *
     * @param args
     */
    public void setArgsDecl(final List<ArgDecl> args) throws ArgsParserException {
        clearArgsDecl();
        for (final ArgDecl parameter : args) {

            // Process general parameter declaration
            final String paramName = parameter.getName();
            if (m_argsDecl.get(paramName) != null) {
                clearArgsDecl();
                throw new ArgsParserException("Duplicated parameter declaration: " + paramName);
            }
            m_argsDecl.put(paramName, parameter);

            // Process the required field
            if (parameter.isRequired()) {
                m_requiredArgs.add(parameter.getName());
            }

            // Process Switches
            final Set<String> parmSwitches = parameter.getSwitches();
            if (parmSwitches.isEmpty()) {
                parmSwitches.add(parameter.getName());
            }
            final Collection<String> duplicates = new ArrayList(m_switches.keySet());
            duplicates.retainAll(parmSwitches);
            if (!duplicates.isEmpty()) {
                clearArgsDecl();
                throw new ArgsParserException("Duplicated switches declaration: " +
                    StringUtils.join(duplicates, ", "));
            }
            for (final String switchName : parmSwitches) {
                m_switches.put(switchName, parameter);
            }
        }
    }

    /**
     *
     */
    private void clearArgsDecl() {
        m_requiredArgs.clear();
        m_argsDecl.clear();
        m_switches.clear();
        m_argsStringList = "";
    }

    /**
     * 
     * @param args
     * @throws ArgsParserException 
     */
    public final void parse(final String args) throws ArgsParserException {
        final String[] strings = new String[1];
        strings[0] = args;
        parse(strings);
    }

    /**
     *
     * @param args
     * @throws org.joytools.sandbox.util.ArgsParserException
     */
    public void parse(final String[] args) throws ArgsParserException {
        m_parsedSuccessfully = false;
        m_mapEnabled = false;
        m_argMap.clear();
        m_argList.clear();
        m_argList.addAll(parseAsList(args));
        if (!getDuplicatesAllowed()) {
            m_argMap.putAll(asMap(m_argList));
            m_mapEnabled = true;
        }
        m_parsedSuccessfully = true;
    }

    /**
     *
     * @return 
     */
    public List<ArgValue> getValuesList() {
        checkParsedSuccessfully();
        return m_argListReadOnly;
    }

    /**
     *
     * @return 
     */
    public Map<String, ArgValue> getValuesMap() {
        checkParsedSuccessfully();
        if (!m_mapEnabled) {
            throw new IllegalStateException(
                "Since the duplicatesAllowed property is set to true " +
                "the parsed parameters can not be set into a Map object.");
        }
        return m_argMapReadOnly;
    }

    /**
     *
     */
    protected void checkParsedSuccessfully() {
        if (!m_parsedSuccessfully) {
            throw new IllegalStateException(
                "The parsing has not been successfully done and no parameter can be retrieved.");
        }
    }

    /**
     *
     */
    private List<ArgValue> parseAsList(final String[] args) throws ArgsParserException {
        m_argsStringList = "";
        // final StringTokenizerIterator tokenizer = new StringTokenizerIterator(StringUtils.join(args, " "));
        // final List<String> argList = tokenizer.buildList();
        final List<String> argList = TextTokenizer
                .create()
                .tokenize(StringUtils.join(args, " "))
                .toList();
        final List<ArgValue> result = new ArrayList<>();
        final Set<String> resultSet = new HashSet<>();
        ArgDecl currentParameter = null;
        ArgDecl newParameter = null;
        boolean add = false;
        final List<String> argValues = new ArrayList<>();
        boolean iterate = true;
        final Iterator<String> i = argList.iterator();
        do {
            if (add) {
                final String parameterName = currentParameter.getName();
                if (parameterName.length() != 0 || !argValues.isEmpty()) {
                    if (!getDuplicatesAllowed() && resultSet.contains(parameterName)) {
                      throw new ArgsParserException("Duplicated parameter usage: " + parameterName);
                    }

                    checkValuesConsistency(currentParameter, argValues);

                    resultSet.add(parameterName);
                    result.add(new ArgValue(parameterName, argValues));
                }
                argValues.clear();
                add = false;
                currentParameter = null;
            }
            if (iterate) {
                if (newParameter != null) {
                    currentParameter = newParameter;
                    newParameter = null;
                } else {
                    if (currentParameter == null) {
                        currentParameter = ArgDecl.of("", "");
                    }
                }
                if (currentParameter.getMaxValuesCount() >= 0 && 
                        currentParameter.getMaxValuesCount() == argValues.size()) {
                    add = true; 
                } else {
                    if (i.hasNext()) {
                        final String arg = i.next();
                        boolean isSwitch = arg.length() > 1 && arg.charAt(0) == '-';
                        final String switchName = (isSwitch ? arg.substring(1, arg.length()) : 
                                currentParameter == null ? "" : null);
                        if (switchName != null) {
                            newParameter = getArgDeclBySwitch(switchName);
                            if (newParameter == null) {
                                if (switchName.length() == 0) {
                                    throw new ArgsParserException("Default parameter not allowed.");
                                } else {
                                    throw new ArgsParserException("Unknown switch: " + switchName + ".");
                                }
                            }
                            if (StringUtils.equals(newParameter.getName(), currentParameter.getName()) && getMergeAdjacent()) {
                                newParameter = null;
                            } else {
                                add = true;
                            }
                        } else {
                          argValues.add(arg);
                        }
                    } else {
                        iterate = false;
                        add = true;
                    }
                }
            }
        } while (add || iterate);

        // Process missing required args
        final Set<String> missingArgs = new HashSet(m_requiredArgs);
        missingArgs.removeAll(resultSet);
        if (missingArgs.size() > 0) {
            throw new ArgsParserException("Missing required args: " + 
                    StringUtils.join(missingArgs, ", "));
        }

        // Computes the argsStringList property
        final StringBuilder sb = new StringBuilder("");
        for (final ArgValue v : result) {
            final String n = v.getName().trim();
            if (n.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append("-").append(n);
            }
            final ArgDecl pp = getArgDeclByName(n);
            if (pp != null && pp.isPassword()) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append("****");
            } else {
                for (final Object objVal : v.getValues()) {
                    final String strVal = objVal.toString();
                    if (strVal.length() > 0) {
                        if (sb.length() > 0) {
                            sb.append(" ");
                        }
                        sb.append(strVal);
                    }
                }
            }
        }
        m_argsStringList = sb.toString();

        return result;
    }

    /**
     *
     * @param argDecl
     * @param argValues
     * @throws org.joytools.sandbox.util.ArgsParserException
     */
    protected void checkValuesConsistency(final ArgDecl argDecl, final List argValues) throws ArgsParserException {
        final String parameterName = argDecl.getName();

        if (argDecl.getMinValuesCount() >= 0 && argValues.size() < argDecl.getMinValuesCount()) {
            throw new ArgsParserException("Too few values for parameter: " + parameterName);
        }

        if (argDecl.getMaxValuesCount() >= 0 && argValues.size() > argDecl.getMaxValuesCount()) {
            throw new ArgsParserException("Too much values for parameter: " + parameterName);
        }

        final Set allowedValues = argDecl.getAllowedValues();
        final Predicate filter = argDecl.getFilter();
        final ArgValue value = new ArgValue(parameterName, argValues);
        if (!filter.evaluate(value)) {
            final StringBuilder msg = new StringBuilder(
                    "Invalid values for parameter " + parameterName);
            if (allowedValues != null && allowedValues.size() > 0) {
                final Set invalidArgs = new HashSet(argValues);
                invalidArgs.removeAll(allowedValues);
                msg.append(": ")
                        .append(StringUtils.join(invalidArgs, ", "))
                        .append(". Allowed values are: ")
                        .append(StringUtils.join(allowedValues, ", "));
            }
            throw new ArgsParserException(msg.toString());
        }
    }

    /**
     *
     * @return 
     */
    public String getArgsStringList() {
        return m_argsStringList;
    }

    /**
     *
     */
    private IndexedMap<String, ArgValue> asMap(final List<ArgValue> listResult) throws ArgsParserException {
        final IndexedMap<String, ArgValue> mapResult = IndexedMapBuilder.create();
        final Set<String> defaultArgs = new HashSet(m_argsDecl.keySet());

        for (final ArgValue value : listResult) {
            final String parameterName = value.getName();
            if (mapResult.containsKey(parameterName)) {
                throw new ArgsParserException("Duplicated parameter usage: " + parameterName);
            } else {
                mapResult.put(parameterName, value);
            }
            defaultArgs.remove(parameterName);
        }

        for (final String argName : defaultArgs) {
            final ArgDecl argDecl = getArgDeclByName(argName);
            if (argDecl.hasDefaultValues()) {
                final List defaultValues = argDecl.getDefaultValues();
                checkValuesConsistency(argDecl, defaultValues);
                final ArgValue argValue = new ArgValue(argName, defaultValues, true);
                mapResult.put(argName, argValue);
            }
        }

        return mapResult;
    }

    /**
     *
     * @param mergeAdjacent
     */
    public void setMergeAdjacent(boolean mergeAdjacent) {
        m_mergeAdjacent = mergeAdjacent;
    }

    /**
     *
     * @return 
     */
    public boolean getMergeAdjacent() {
        return m_mergeAdjacent;
    }

    /**
     *
     * @param duplicatesAllowed
     */
    public void setDuplicatesAllowed(final boolean duplicatesAllowed) {
        m_duplicatesAllowed = duplicatesAllowed;
    }

    /**
     *
     * @return 
     */
    public boolean getDuplicatesAllowed() {
        return m_duplicatesAllowed;
    }

    /**
     *
     * @param parameterName
     * @return 
     */
    public ArgDecl getArgDeclByName(final String parameterName) {
        final ArgDecl ad = m_argsDecl.get(parameterName);
        if (ad == null) {
            return null;
        }
        return ArgDecl.copyOf(ad);
    }

    /**
     *
     * @param switchName
     * @return 
     */
    public ArgDecl getArgDeclBySwitch(final String switchName) {
        final ArgDecl ad = m_switches.get(switchName);
        if (ad == null) {
            return null;
        }
        return ArgDecl.copyOf(ad);
    }

    /**
     *
     */
    private boolean m_mergeAdjacent = true;

    /**
     *
     */
    private boolean m_duplicatesAllowed = false;

    /**
     *
     */
    private final Set<String> m_requiredArgs = new HashSet();

    /**
     *
     */
    private String m_argsStringList = "";

    /**
     *
     */
    private final IndexedMap<String, ArgValue> m_argMap = IndexedMapBuilder.create();

    /**
     *
     */
    private final Map<String, ArgValue> m_argMapReadOnly = Collections.unmodifiableMap(m_argMap);

    /**
     *
     */
    private boolean m_parsedSuccessfully = false;

    /**
     *
     */
    private final List<ArgValue> m_argList = new ArrayList();

    /**
     *
     */
    private final List<ArgValue> m_argListReadOnly = Collections.unmodifiableList(m_argList);

    /**
     *
     */
    private boolean m_mapEnabled = false;

    /**
     *
     */
    private final Map<String, ArgDecl> m_argsDecl = new HashMap<>();

    /**
     *
     */
    private final IndexedMap<String, ArgDecl> m_switches = IndexedMapBuilder.create();

}
