package in.clouthink.daas.es6.repository;

import org.elasticsearch.common.Strings;

import java.lang.reflect.Array;

public class Fields {

    public static Fields empty() {
        return new Fields();
    }

    private String[] includedFields = Strings.EMPTY_ARRAY;

    private String[] excludedFields = Strings.EMPTY_ARRAY;

    public String[] getIncludedFields() {
        return this.includedFields;
    }

    public String[] getExcludedFields() {
        return this.excludedFields;
    }

    public boolean isEmpty() {
        return (includedFields == null || includedFields.length == 0) &&
                (excludedFields == null || excludedFields.length == 0);
    }

    public Fields includes(String... fields) {
        if (fields == null) {
            return this;
        }

        this.includedFields = addAll(this.includedFields, fields);

        return this;
    }

    public Fields excludes(String... fields) {
        if (fields == null) {
            return this;
        }

        this.excludedFields = addAll(this.excludedFields, fields);

        return this;
    }

    private <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    private <T> T[] addAll(final T[] array1, final T... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        final Class<?> type1 = array1.getClass().getComponentType();
        @SuppressWarnings("unchecked") // OK, because array is of type T
        final T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (final ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of "
                                                           + type1.getName(), ase);
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }


}
