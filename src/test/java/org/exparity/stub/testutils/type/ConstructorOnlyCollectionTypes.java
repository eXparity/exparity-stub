package org.exparity.stub.testutils.type;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConstructorOnlyCollectionTypes {

    private  int[] array;
    private final String[] stringArray;
    private final Collection<String> collection;
    private final List<String> list;
    private final Set<String> set;
    private final Map<Long, String> map;
    private final Map<String, String[]> arrayMap;

    public ConstructorOnlyCollectionTypes(final int[] array,
            final String[] stringArray,
            final Collection<String> collection,
            final List<String> list,
            final Set<String> set,
            final Map<Long, String> map,
            final Map<String, String[]> arrayMap) {
        this.array = array;
        this.stringArray = stringArray;
        this.collection = collection;
        this.list = list;
        this.set = set;
        this.map = map;
        this.arrayMap = arrayMap;
    }

    public int[] getArray() {
        return array;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public List<String> getList() {
        return list;
    }

    public Set<String> getSet() {
        return set;
    }

    public Map<Long, String> getMap() {
        return map;
    }
    
    public Map<String, String[]> getArrayMap() {
        return arrayMap;
    }

}
