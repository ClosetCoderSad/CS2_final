/*
 * ==========================================================================================
 *   TOPIC 2 NOTES: Implementing Lists, Stacks, Queues, Priority Queues & ADTs (Java)
 * ==========================================================================================
 *
 * 1) LINKED LISTS
 * ------------------------------------------------------------------------------------------
 * - A linked list is a sequence of nodes; each node stores data + reference to next node.
 * - Dynamic size: grows/shrinks at runtime (no fixed capacity like arrays).
 * - Fast insert/delete at known position when you have a reference to the node (O(1)).
 * - Slow random access: to reach index i you must traverse from head (O(n)).
 *
 *   // Node structure used for most examples below:
 *   class Node {
 *       int data;
 *       Node next;
 *       Node(int data) { this.data = data; this.next = null; }
 *   }
 *
 *
 * 2) SINGLY LINKED LIST
 * ------------------------------------------------------------------------------------------
 * - Only points forward: each Node has "next" but no "prev".
 * - Typical operations:
 *      insertAtHead(x)         -> O(1)
 *      insertAtTail(x)         -> O(n) unless we keep a tail pointer (then O(1))
 *      deleteFirst(x)          -> O(n) (need to search)
 *      search(x)               -> O(n)
 * - Simple implementation sketch:
 */
 *   class SinglyLinkedList {
 *       private Node head;
 *
 *       // Insert new node before current head
 *       void insertAtHead(int x) {
 *           Node n = new Node(x);
 *           n.next = head;
 *           head = n;
 *       }
 *
 *       // Append new node at the end
 *       void insertAtEnd(int x) {
 *           Node n = new Node(x);
 *           if (head == null) { head = n; return; }
 *           Node curr = head;
 *           while (curr.next != null) curr = curr.next;
 *           curr.next = n;
 *       }
 *   }
 *
 *
/* 3) STACKS AND QUEUES (ABSTRACT VIEW)
 * ------------------------------------------------------------------------------------------
 * STACK (LIFO: Last-In First-Out)
 *   push(x)      -> insert at top
 *   pop()        -> remove and return top
 *   peek()       -> return top without removing
 *   isEmpty()
 *   Typical complexity: O(1) per operation.
 *
 * QUEUE (FIFO: First-In First-Out)
 *   enqueue(x)   -> add at rear
 *   dequeue()    -> remove from front
 *   front()/peek()
 *   isEmpty()
 *   Typical complexity: O(1) per operation when implemented correctly.
 *
 *
 * 4) IMPLEMENTING STACKS USING LINKED LISTS
 * ------------------------------------------------------------------------------------------
 * Idea:
 *   - Use the HEAD of a singly linked list as the TOP of the stack.
 *   - push(x)  = insert new node at head
 *   - pop()    = remove node from head
 */
 *   class StackLL {
 *       private Node top; // head of linked list
 *
 *       void push(int x) {
 *           Node n = new Node(x);
 *           n.next = top;
 *           top = n;
 *       }
 *
 *       int pop() {
 *           if (top == null) throw new RuntimeException("Stack underflow");
 *           int val = top.data;
 *           top = top.next;
 *           return val;
 *       }
 *
 *       int peek() { return top.data; }
 *       boolean isEmpty() { return top == null; }
 *   }
 *
 /* Complexity:
 *   - push, pop, peek, isEmpty -> O(1) time, O(1) extra space.
 *
 *
 * 5) IMPLEMENTING QUEUES USING LINKED LISTS
 * ------------------------------------------------------------------------------------------
 * Idea:
 *   - Keep references to both FRONT and REAR nodes.
 *   - enqueue(x)  = add new node after rear; move rear pointer
 *   - dequeue()   = remove node from front; move front pointer
 */
 *   class QueueLL {
 *       private Node front, rear;
 *
 *       void enqueue(int x) {
 *           Node n = new Node(x);
 *           if (rear == null) { front = rear = n; return; }
 *           rear.next = n;
 *           rear = n;
 *       }
 *
 *       int dequeue() {
 *           if (front == null) throw new RuntimeException("Queue underflow");
 *           int val = front.data;
 *           front = front.next;
 *           if (front == null) rear = null; // queue became empty
 *           return val;
 *       }
 *
 *       int peek() { return front.data; }
 *       boolean isEmpty() { return front == null; }
 *   }
 *
 /* Complexity:
 *   - enqueue, dequeue, peek, isEmpty -> O(1) time, O(1) extra space.
 *
 *
 * 6) PRIORITY QUEUES
 * ------------------------------------------------------------------------------------------
 * - A priority queue removes elements based on PRIORITY instead of insertion order.
 * - Operations (min-heap view):
 *      insert(x)          -> add element with priority
 *      findMin() / peek() -> smallest element
 *      deleteMin()        -> remove smallest
 * - Typical and efficient implementation: BINARY HEAP.
 *
 *
 * 7) IMPLEMENTING PRIORITY QUEUES USING HEAPS
 * ------------------------------------------------------------------------------------------
 * Binary heap (array-based):
 *   - Almost complete binary tree.
 *   - For index i:
 *        parent(i) = (i - 1) / 2
 *        left(i)   = 2*i + 1
 *        right(i)  = 2*i + 2
 *   - Min-heap property: heap[parent(i)] <= heap[i] for all i.
 */
 *   class MinHeapPriorityQueue {
 *       private int[] heap;
 *       private int size;
 *
 *       MinHeapPriorityQueue(int capacity) {
 *           heap = new int[capacity];
 *           size = 0;
 *       }
 *
 *       void insert(int x) {
 *           if (size == heap.length) throw new RuntimeException("Full");
 *           int i = size++;
 *           heap[i] = x;
 *           // bubble up
 *           while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
 *               int tmp = heap[i];
 *               heap[i] = heap[(i - 1) / 2];
 *               heap[(i - 1) / 2] = tmp;
 *               i = (i - 1) / 2;
 *           }
 *       }
 *
 *       int deleteMin() {
 *           if (size == 0) throw new RuntimeException("Empty");
 *           int min = heap[0];
 *           heap[0] = heap[--size];
 *           heapifyDown(0);
 *           return min;
 *       }
 *
 *       private void heapifyDown(int i) {
 *           while (true) {
 *               int left  = 2 * i + 1;
 *               int right = 2 * i + 2;
 *               int smallest = i;
 *               if (left  < size && heap[left]  < heap[smallest]) smallest = left;
 *               if (right < size && heap[right] < heap[smallest]) smallest = right;
 *               if (smallest == i) break;
 *               int tmp = heap[i];
 *               heap[i] = heap[smallest];
 *               heap[smallest] = tmp;
 *               i = smallest;
 *           }
 *       }
 *   }
 /*
 * Complexity:
 *   - insert, deleteMin -> O(log n)
 *   - peek (min element) -> O(1)
 *
 *
 * 8) ABSTRACT DATA TYPES (ADTs)
 * ------------------------------------------------------------------------------------------
 * Definition:
 *   - ADT = a logical description of a data structure that specifies:
 *       * what operations are supported
 *       * what each operation does (behavior / contract)
 *     but NOT how it is implemented.
 *
 * Importance:
 *   - Separates interface (WHAT) from implementation (HOW).
 *   - Allows multiple implementations of same ADT (e.g. Stack using array, list).
 *   - Makes code easier to reason about, test, and change.
 *
 * Example: Stack ADT in Java using an interface:
 */
 *   interface StackADT {
 *       void push(int x);
 *       int pop();
 *       int peek();
 *       boolean isEmpty();
 *   }
 *
 *   // We can now implement StackADT with different underlying structures:
 *   //   - ArrayStack implements StackADT  (array-based)
 *   //   - ListStack  implements StackADT  (linked-list-based)
 *
 * In practice:
 *   - java.util.List, java.util.Queue, java.util.Deque, java.util.Map, etc.
 *     are ADT-like interfaces; ArrayList, LinkedList, HashMap, etc. are
 *     concrete implementations.
 *
 * ==========================================================================================
 */
