import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.tuple.Tuples;

import java.util.Set;

/***
 * Basic collection types in Eclipse Collections are:
 *
 * ListIterable – an ordered collection that maintains insertion order and allows duplicate elements. Subinterfaces include:
 * MutableList, FixedSizeList and ImmutableList. The most common ListIterable implementation is FastList, which is a subclass of MutableList
 *
 * SetIterable – a collection that allows no duplicate elements. It can be sorted or unsorted. Subinterfaces include:
 * SortedSetIterable and UnsortedSetIterable. The most common unsorted SetIterable implementation is UnifiedSet
 *
 * MapIterable – a collection of key/value pairs. Subinterfaces include MutableMap, FixedSizeMap and ImmutableMap.
 * Two common implementations are UnifiedMap and MutableSortedMap. While UnifiedMap does not maintain any order,
 * MutableSortedMap maintains the natural order of elements
 *
 * BiMap – a collection of key/value pairs that can be iterated through in either direction.
 * BiMap extends the MapIterable interface
 *
 * Bag – an unordered collection that allows duplicates. Subinterfaces include MutableBag and FixedSizeBag.
 * The most common implementation is HashBag
 * StackIterable – a collection that maintains “last-in, first-out” order, iterating through
 * elements in reverse insertion order. Subinterfaces include MutableStack and ImmutableStack
 *
 * MultiMap – a collection of key/value pairs that allows multiple values for each key
 */
public class EclipseCollectionExample {


    public static void main(String[] args) {

        MutableList<String> list = FastList.newListWith(
                "Porsche", "Volkswagen", "Toyota", "Mercedes", "Toyota");

        Set<String> comparison = UnifiedSet.newSetWith(
                "Porsche", "Volkswagen", "Toyota", "Mercedes");

        MutableBag<String> bag = HashBag.newBagWith(
                "Porsche", "Volkswagen", "Toyota", "Porsche", "Mercedes");

        Pair<Integer, String> pair1 = Tuples.pair(1, "One");
        Pair<Integer, String> pair2 = Tuples.pair(2, "Two");
        Pair<Integer, String> pair3 = Tuples.pair(3, "Three");

        UnifiedMap<Integer, String> mapPair = new UnifiedMap<>(pair1, pair2, pair3);

        UnifiedMap<Integer, String> map = new UnifiedMap<>();

        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");


    }
}
