/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming.directory;

import java.util.ArrayList;
import java.util.List;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 *
 * @author AndreaR
 */
public class ModificationItemUtils {
    
    /**
     * 
     * @param mods
     * @param add
     * @param replace
     * @param remove
     * @return 
     */
    public static ModificationItem[] filter(final ModificationItem[] mods, 
            final boolean add, 
            final boolean replace, 
            final boolean remove) {
        if (mods.length == 0 || (add && replace && remove)) {
            return mods;
        }
        final List<ModificationItem> newMods = new ArrayList(mods.length);
        for (final ModificationItem mod : mods) {
            switch (mod.getModificationOp()) {
                case DirContext.ADD_ATTRIBUTE:
                    if (add) {
                        newMods.add(mod);
                        break;
                    }
                case DirContext.REPLACE_ATTRIBUTE:
                    if (replace) {
                        newMods.add(mod);
                        break;
                    }
                case DirContext.REMOVE_ATTRIBUTE:
                    if (remove) {
                        newMods.add(mod);
                        break;
                    }
                default:
                    throw new IllegalArgumentException("Unknown operation: " + mod.getModificationOp());
            }
        }
        if (mods.length == newMods.size()) {
            return mods;
        }
        return newMods.toArray(new ModificationItem[newMods.size()]);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] filterAdd(final ModificationItem[] mods) {
        return filter(mods, true, false, false);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] filterReplace(final ModificationItem[] mods) {
        return filter(mods, false, true, false);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] filterAddOrReplace(final ModificationItem[] mods) {
        return filter(mods, true, true, false);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] filterAddOrRemove(final ModificationItem[] mods) {
        return filter(mods, true, false, true);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] filterRemove(final ModificationItem[] mods) {
        return filter(mods, false, false, true);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] omitReplace(final ModificationItem[] mods) {
        return filter(mods, true, false, true);
    }

    /**
     * 
     * @param mods
     * @return 
     */
    public static ModificationItem[] omitRemove(final ModificationItem[] mods) {
        return filter(mods, true, true, false);
    }
    
}
