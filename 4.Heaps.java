*
 * ==========================================================================================
 *                       TOPIC: HEAPS, PRIORITY QUEUES, HEAP SORT, DIJKSTRA
 * ==========================================================================================
 *
 * 1) WHAT IS A HEAP?
 * ------------------------------------------------------------------------------------------
 * - A heap is a COMPLETE BINARY TREE stored compactly in an array and satisfying:
 (A complete binary tree is a binary tree that is:
-Filled level by level from left to right, and
-All levels are completely full except possibly the last one,
-In the last level, all nodes are as far left as possible.)
 *      Max-Heap:  parent >= children
 *      Min-Heap:  parent <= children
 *
 * - Array representation (0-based index):
 *      left(i)   = 2*i + 1
 *      right(i)  = 2*i + 2
 *      parent(i) = (i - 1) / 2
 *
 * - Because of completeness, the height h ≈ floor(log2 n) for n elements.
 *
 *
 * 2) BASIC HEAP OPERATIONS (for both min- and max-heap)
 * ------------------------------------------------------------------------------------------
 * INSERT (push / enqueue):
 *   1. Put new element at the end of the array (index = size).
 *   2. "Heapify up" (bubble up): while heap[parent] violates heap property, swap with parent.
 *   3. Complexity:
 *        - Time:  O(log n)  (height of tree)
 *        - Space: O(1) extra
 *
 * DELETE ROOT (pop / extractMin / extractMax):
 *   1. Save root value to return.
 *   2. Move last element to root position, reduce size.
 *   3. "Heapify down": while node has children and heap property violated, swap with
 *      the *better* child (larger child in max-heap, smaller in min-heap).
 *   4. Complexity:
 *        - Time:  O(log n)
 *        - Space: O(1) extra
 *
 * BUILD HEAP from arbitrary array:
 *   - Bottom-up algorithm:
 *        for i from lastNonLeaf down to 0:
 *            heapifyDown(i)
 *   - Complexity:
 *        - Time:  O(n)  (important result)
 *        - Space: O(1) extra (in-place)
 *
 *
 * 3) EXAMPLE: MIN-HEAP INSERT + DELETE
 * ------------------------------------------------------------------------------------------
 * Start: [10, 15, 20, 25, 30, 35]   (min-heap)
 *
 * Insert 18:
 *   - Append: [10, 15, 20, 25, 30, 35, 18]
 *   - Heapify up: compare 18 with parent 20 → swap
 *                 [10, 15, 18, 25, 30, 35, 20]
 *   Final heap after insert: [10, 15, 18, 25, 30, 35, 20]
 *
 * Delete root (10):
 *   - Replace root with last element 20: [20, 15, 18, 25, 30, 35]
 *   - Heapify down:
 *        20 vs children 15,18 → swap with 15
 *        [15, 20, 18, 25, 30, 35]
 *        20 vs children 25,30 → ok (20 smaller), stop
 *   Final heap: [15, 20, 18, 25, 30, 35]
 *
 *
 * 4) SIMPLE MAX-HEAP IMPLEMENTATION (ARRAY-BASED)
 * ------------------------------------------------------------------------------------------
 * class HeapImplementation {
 *     private int[] heap;  // array storage
 *     private int size;    // # of elements in heap
 *
 *     public HeapImplementation(int capacity) {
 *         heap = new int[capacity];
 *         size = 0;
 *     }
 *
 *     // Insert value into max-heap
 *     public void insert(int value) {
 *         if (size == heap.length) resize();
 *         heap[size] = value;
 *         heapifyUp(size);   // restore heap property
 *         size++;
 *     }
 *
 *     // Remove and return max (root) from heap
 *     public int delete() {
 *         if (size == 0) throw new IllegalStateException("Heap empty");
 *         int root = heap[0];
 *         heap[0] = heap[size - 1];
 *         size--;
 *         heapifyDown(0);    // restore heap property
 *         return root;
 *     }
 *
 *     // ---------- helpers (indices, heapify, swap) ----------
 *     private void heapifyUp(int i) {
 *         while (hasParent(i) && parent(i) < heap[i]) {
 *             swap(i, getParentIndex(i));
 *             i = getParentIndex(i);
 *         }
 *     }
 *
 *     private void heapifyDown(int i) {
 *         while (hasLeftChild(i)) {
 *             int largerChild = getLeftChildIndex(i);
 *             if (hasRightChild(i) && rightChild(i) > leftChild(i)) {
 *                 largerChild = getRightChildIndex(i);
 *             }
 *             if (heap[i] >= heap[largerChild]) break;
 *             swap(i, largerChild);
 *             i = largerChild;
 *         }
 *     }
 *
 *     // index math
 *     private int getParentIndex(int i) { return (i - 1) / 2; }
 *     private int getLeftChildIndex(int i) { return 2 * i + 1; }
 *     private int getRightChildIndex(int i) { return 2 * i + 2; }
 *     private boolean hasParent(int i) { return getParentIndex(i) >= 0; }
 *     private boolean hasLeftChild(int i) { return getLeftChildIndex(i) < size; }
 *     private boolean hasRightChild(int i) { return getRightChildIndex(i) < size; }
 *     private int parent(int i) { return heap[getParentIndex(i)]; }
 *     private int leftChild(int i) { return heap[getLeftChildIndex(i)]; }
 *     private int rightChild(int i) { return heap[getRightChildIndex(i)]; }
 *
 *     private void swap(int i, int j) {
 *         int tmp = heap[i];
 *         heap[i] = heap[j];
 *         heap[j] = tmp;
 *     }
 *
 *     private void resize() {
 *         int[] bigger = new int[heap.length * 2];
 *         System.arraycopy(heap, 0, bigger, 0, heap.length);
 *         heap = bigger;
 *     }
 * }
 *
 *
 * 5) PRIORITY QUEUES USING BINARY HEAPS
 * ------------------------------------------------------------------------------------------
 * Priority Queue ADT:
 *   - Like a queue, but each element has a priority.
 *   - Operations:
 *       enqueue(x) / insert(x)      – add item with priority
 *       dequeue() / extractMax/Min  – remove highest-priority item
 *       peek()                      – look at highest-priority item
 *
 * Binary Heap is a natural implementation:
 *   - Max-heap → highest numerical value = highest priority.
 *   - Min-heap → smallest numerical value = highest priority.
 *
 * Wrapper class on top of max-heap:
 *
 *   public class PriorityQueueImplementation {
 *       private HeapImplementation heap;
 *
 *       public PriorityQueueImplementation(int capacity) {
 *           heap = new HeapImplementation(capacity);
 *       }
 *
 *       public void enqueue(int item) {
 *           heap.insert(item);   // O(log n)
 *       }
 *
 *       public int dequeue() {
 *           return heap.delete(); // O(log n)
 *       }
 *
 *       public void printQueue() { heap.printHeap(); }
 *   }
 *
 * Complexity (binary heap priority queue):
 *   - enqueue / insert        : O(log n)
 *   - dequeue / extractMax    : O(log n)
 *   - peek / findMax          : O(1)
 *   - build from n elements   : O(n)
 *
 *
 * 6) HEAP SORT (IN-PLACE, COMPARISON SORT)
 * ------------------------------------------------------------------------------------------
 * Idea (ascending order using max-heap):
 *   1. Build a max-heap from the array (O(n)).
 *   2. Repeat for i from n-1 down to 1:
 *        - swap a[0] (max) with a[i] (last element in heap)
 *        - reduce heap size by 1
 *        - heapifyDown(0) on the reduced heap (O(log n))
 *
 *   public static void heapSort(int[] a) {
 *       int n = a.length;
 *
 *       // Build max-heap
 *       for (int i = n / 2 - 1; i >= 0; i--) {
 *           heapifyDown(a, n, i);
 *       }
 *
 *       // Extract max one by one
 *       for (int end = n - 1; end > 0; end--) {
 *           swap(a, 0, end);          // put max at correct final position
 *           heapifyDown(a, end, 0);   // restore heap on a[0..end-1]
 *       }
 *   }
 *
 *   private static void heapifyDown(int[] a, int size, int i) {
 *       while (true) {
 *           int left = 2 * i + 1, right = 2 * i + 2, largest = i;
 *           if (left  < size && a[left]  > a[largest]) largest = left;
 *           if (right < size && a[right] > a[largest]) largest = right;
 *           if (largest == i) break;
 *           swap(a, i, largest);
 *           i = largest;
 *       }
 *   }
 *
 *   private static void swap(int[] a, int i, int j) {
 *       int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
 *   }
 *
 * QUICK COMPARISON TABLE (HEAP SORT)
 *   +-------------+-------------+-------------+-------------+----------+---------+
 *   | Algorithm   | Best Case   | Average     | Worst Case  | Stable?  | In-Place|
 *   +-------------+-------------+-------------+-------------+----------+---------+
 *   | Heap Sort   | O(n log n)  | O(n log n)  | O(n log n)  |   No     |   Yes   |
 *   +-------------+-------------+-------------+-------------+----------+---------+
 *
 * Notes:
 *   - Always O(n log n), regardless of input order.
 *   - In-place (O(1) extra space) but NOT stable (equal keys can change relative order).
 *
 *
 *
 * 7) SUMMARY OF COMPLEXITIES
 * ------------------------------------------------------------------------------------------
 * HEAP OPERATIONS (binary heap, n elements):
 *   - insert / enqueue          : O(log n) time, O(1) extra space
 *   - delete root / extractMax  : O(log n) time, O(1) extra space
 *   - peek root                 : O(1) time,   O(1) extra space
 *   - build heap from array     : O(n) time,   O(1) extra space
 *
 * PRIORITY QUEUE VIA BINARY HEAP:
 *   - Same as heap operations above.
 *
 * HEAP SORT:
 *   - Time:  O(n log n)  (best, average, worst)
 *   - Space: O(1) extra   (in-place)
 *   - Stable: NO
 *
 * ==========================================================================================
 */

