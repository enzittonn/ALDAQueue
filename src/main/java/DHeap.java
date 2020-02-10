// nyna2000 Naranbaatar Nyamgarig
public class DHeap<AnyType extends Comparable<? super AnyType>> {

    public static final int DEFAULT_CHILD_NUM = 2;

    private int currentSize;      // Number of elements in heap
    private AnyType[] array; // The heap array
    private int dimension;


    /**
     * Construct the binary heap.
     */
    public DHeap() {
        this(DEFAULT_CHILD_NUM);
    }

    /**
     * Construct the binary heap.
     *
     * @param d the capacity of the binary heap.
     */
    public DHeap(int d) {
        if (d < 2)
            throw new IllegalArgumentException();
        this.dimension = d;
        currentSize = 0;
        array = (AnyType[]) new Comparable[d + 1];
    }

    /**
     * Construct the binary heap given an array of items.
     */
    public DHeap(AnyType[] items) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

        int i = 1;
        for (AnyType item : items)
            array[i++] = item;
        buildHeap();
    }


    public int parentIndex(int index) {
        if (index < 2)
            throw new IllegalArgumentException();
        return (index + dimension - 2) / dimension;
    }

    public int firstChildIndex(int index) {
        if (index < 1)
            throw new IllegalArgumentException();
        return (index - 1) * dimension + 2;
    }

    public int size() {
        return currentSize;
    }

    public AnyType get(int index) {
        return array[index];
    }

    public int smallestChildIndex(int index) {
        int firstChild = firstChildIndex(index);
        if (firstChild > currentSize)
            return currentSize + 1;

        int childVar = firstChild;
        for (int i = 1; i < dimension; i++) {
            if (firstChild + i <= currentSize) {
                if (array[firstChild + i] != null && array[childVar].compareTo(array[firstChild + i]) > 0) {
                    childVar = firstChild + i;
                }
            }
        }
        return childVar;
    }

    public AnyType smallestChild(int index) {
        return get(smallestChildIndex(index));
    }

    /**
     * Internal method to percolate down in the heap.
     *
     * @param hole the index at which the percolate begins.
     */

    public void percolateDown(int hole) {
        AnyType tmp = array[hole];
        for (; smallestChildIndex(hole) <= currentSize && tmp.compareTo(smallestChild(hole)) >= 0; hole = smallestChildIndex(hole))
            array[hole] = smallestChild(hole);
        array[hole] = tmp;
    }


    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     *
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        // Percolate up
        int hole = ++currentSize;
        for (; (hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0); hole = parentIndex(hole)) {
            array[hole] = array[parentIndex(hole)];
        }
        array[hole] = x;
    }


    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin() {
        if (isEmpty())
            throw new UnderflowException();

        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }




    // Test program
    public static void main(String[] args) {
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>();
        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems)
            h.insert(i);
        for (i = 1; i < numItems; i++)
            if (h.deleteMin() != i)
                System.out.println("Oops! " + i);
    }
}