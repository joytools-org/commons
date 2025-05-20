/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

import static com.google.common.base.Preconditions.checkArgument;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import static org.joytools.commons.lang.ObjectUtils.isEmpty;
import org.joytools.commons.lang.StringUtils;

/**
 * 
 */
public enum ProcessorOperation {

    FILTER() {
        @Override
        public String shortName() {
            return "FILTER";
        }
        
        @Override
        public boolean isChange() {
            return false;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with0() {
            return FILTER_0;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with1() {
            return FILTER_1;
        }
    },
    ADD() {
        @Override
        public String shortName() {
            return "ADD";
        }
        
        @Override
        public boolean isChange() {
            return true;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with0() {
            return ADD_0;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with1() {
            return ADD_1;
        }
    },
    MODIFY() {
        @Override
        public String shortName() {
            return "MOD";
        }
        
        @Override
        public boolean isChange() {
            return true;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with0() {
            return MODIFY_0;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with1() {
            return MODIFY_1;
        }
    },
    DELETE() {
        @Override
        public String shortName() {
            return "DEL";
        }
        
        @Override
        public boolean isChange() {
            return true;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with0() {
            return DELETE_0;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with1() {
            return DELETE_1;
        }
    },
    NOCHANGE() {
        @Override
        public String shortName() {
            return "NONE";
        }
        
        @Override
        public boolean isChange() {
            return false;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with0() {
            return NOCHANGE_0;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with1() {
            return NOCHANGE_1;
        }
    },
    ERROR() {
        @Override
        public String shortName() {
            return "ERROR";
        }
        
        @Override
        public boolean isChange() {
            return false;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with0() {
            return ERROR_0;
        }

        @Override
        public Tuple2<ProcessorOperation, Integer> with1() {
            return ERROR_1;
        }
    };

    private final static Tuple2 FILTER_0 = Tuple(FILTER, 0);
    private final static Tuple2 FILTER_1 = Tuple(FILTER, 1);
    private final static Tuple2 ADD_0 = Tuple(ADD, 0);
    private final static Tuple2 ADD_1 = Tuple(ADD, 1);
    private final static Tuple2 MODIFY_0 = Tuple(MODIFY, 0);
    private final static Tuple2 MODIFY_1 = Tuple(MODIFY, 1);
    private final static Tuple2 DELETE_0 = Tuple(DELETE, 0);
    private final static Tuple2 DELETE_1 = Tuple(DELETE, 1);
    private final static Tuple2 NOCHANGE_0 = Tuple(NOCHANGE, 0);
    private final static Tuple2 NOCHANGE_1 = Tuple(NOCHANGE, 1);
    private final static Tuple2 ERROR_0 = Tuple(ERROR, 0);
    private final static Tuple2 ERROR_1 = Tuple(ERROR, 1);
    
    /**
     * 
     * @return 
     */
    public abstract String shortName();

    /**
     * 
     * @return 
     */
    public final boolean isAdd() {
        return this == ADD;
    }

    /**
     * 
     * @return 
     */
    public final boolean isModify() {
        return this == MODIFY;
    }

    /**
     * 
     * @return 
     */
    public final boolean isDelete() {
        return this == DELETE;
    }

    /**
     * 
     * @return 
     */
    public final boolean isFilter() {
        return this == FILTER;
    }

    /**
     * 
     * @return 
     */
    public final boolean isFilterOrError() {
        return isFilter() || isError();
    }

    /**
     * 
     * @return 
     */
    public abstract boolean isChange();
    
    /**
     * 
     * @return 
     */
    public final boolean isChangeOrError() {
        return isChange() || isError();
    }

    /**
     * 
     * @return 
     */
    public final boolean isNoChange() {
        return this == NOCHANGE;
    }
    
    /**
     * 
     * @return 
     */
    public final boolean isError() {
        return this == ERROR;
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static ProcessorOperation from(final Object o) {
        if (o == null) {
            return ProcessorOperation.FILTER;
        }
        if (o instanceof ProcessorOperation po) {
            return po;
        }
        if (o instanceof Boolean flag) {
            return flag ? ProcessorOperation.MODIFY : ProcessorOperation.FILTER;
        }
        throw new ClassCastException("Unknown Processor result type: " + o.getClass().getName());
    }
    
    /**
     * 
     * @return 
     */
    public final boolean isContinue() {
        return isContinue(this);
    }
    
    /**
     * 
     * @param o
     * @return 
     */
    public static boolean isContinue(final Object o) {
        return !from(o).isFilter();
        /*
        if (o == null) {
            return false;
        }
        if (o instanceof ProcessorOperation) {
            return !ProcessorOperation.class.cast(o).isFilter();
        }
        if (o instanceof Boolean) {
            return Boolean.class.cast(o);
        }*/
    }

    /**
     * 
     * @param oldValue
     * @param newValue
     * @return 
     */
    public static ProcessorOperation of(final Object oldValue, final Object newValue) {
        final boolean oldEmpty = isEmpty(oldValue);
        final boolean newEmpty = isEmpty(newValue);
        if (oldEmpty && newEmpty) {
            return ProcessorOperation.NOCHANGE;
        }
        if (oldEmpty && !newEmpty) {
            return ProcessorOperation.ADD;
        }
        if (!oldEmpty && newEmpty) {
            return ProcessorOperation.DELETE;
        }
        if (oldValue instanceof CharSequence 
                && newValue instanceof CharSequence
                && StringUtils.equalsIgnoreCase(
                    CharSequence.class.cast(oldValue),
                    CharSequence.class.cast(newValue))) {
            return ProcessorOperation.NOCHANGE;
        }
        final String oldv = StringUtils.toString(oldValue);
        final String newv = StringUtils.toString(newValue);
        if (StringUtils.equals(oldv, newv)) {
            return ProcessorOperation.NOCHANGE;
        }
        return ProcessorOperation.MODIFY;
    }

    /**
     * 
     * @return 
     */
    public abstract Tuple2<ProcessorOperation, Integer> with0();

    /**
     * 
     * @return 
     */
    public abstract Tuple2<ProcessorOperation, Integer> with1();

    /**
     * 
     * @param count
     * @return 
     */
    public Tuple2<ProcessorOperation, Integer> withCount(final int count) {
        switch (count) {
            case 0: return with0();
            case 1: return with1();
        }
        checkArgument(count >= 0, "Count must not be negative");
        return Tuple(this, count);
    }

}
