import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class BinarySearchWithoutIfStatements {


    public interface Function<T> {
        T apply();
    }


    static interface MyBoolean {
        public int trueFunctionFalseFunction(final Function<Integer> t, final Function<Integer> f);
    }


    final static Map<Boolean, MyBoolean> bools = new HashMap<Boolean, MyBoolean>() {
        {
            put(Boolean.TRUE,
                    new MyBoolean() {
                        public int trueFunctionFalseFunction(final Function<Integer> t, final Function<Integer> f) {
                            return t.apply();
                        }
                    });
            put(Boolean.FALSE,
                    new MyBoolean() {
                        public int trueFunctionFalseFunction(final Function<Integer> t, final Function<Integer> f) {
                            return f.apply();
                        }
                    });
        }
    };


    final static List<Integer> list = new ArrayList<Integer>() {
        {
            add(7);
            add(8);
            add(9);
            add(10);
            add(11);
        }
    };

    @Test
       public void testMinusOneIsReturnedWhenValueIsNotInTheListButWouldBeAfter() {
           Integer index = binarySearch(list, 0, list.size() - 1, 12);
           assertEquals(-1, index.intValue());
       }

    @Test
    public void testMinusOneIsReturnedWhenValueIsNotInTheListButWouldBeBefore() {
        Integer index = binarySearch(list, 0, list.size() - 1, 5);
        assertEquals(-1, index.intValue());
    }

    @Test
    public void testThatWeCanFindTheLastElement() {
        Integer index = binarySearch(list, 0, list.size() - 1, 11);
        assertEquals(4, index.intValue());
    }

    @Test
    public void testThatWeCanFindTheFirstElement() {
        Integer index = binarySearch(list, 0, list.size() - 1, 7);
        assertEquals(0, index.intValue());
    }

    static int eval(boolean b, final Function<Integer> t, final Function<Integer> f) {
        return (bools.get(new Boolean(b))).trueFunctionFalseFunction(t, f);
    }


    static public int binarySearch(final List<Integer> sorted, final int low, final int high, final int key) {
        return eval(high < low, new Function<Integer>() {
                    public Integer apply() {
                        return -1;
                    }
                }, new Function<Integer>() {
                    public Integer apply() {
                        final int mid = (low + high) / 2;
                        return eval(key < sorted.get(mid),
                                new Function<Integer>() {
                                    public Integer apply() {
                                        return binarySearch(sorted, low, mid - 1, key);
                                    }
                                },
                                new Function<Integer>() {
                                    public Integer apply() {
                                        return
                                                eval(key > sorted.get(mid),
                                                        new Function<Integer>() {
                                                            public Integer apply() {
                                                                return binarySearch(sorted, mid + 1, high, key);
                                                            }
                                                        },
                                                        new Function<Integer>() {
                                                            public Integer apply() {
                                                                return mid;
                                                            }
                                                        }
                                                );
                                    }
                                }
                        );
                    }
                }
        );


    }

}
