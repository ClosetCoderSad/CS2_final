//TOPIC 1 : SORTING

/*Selection Sort*/
//Selection sort picks an element and compares it with all elements after it, and if a smaller one is found, it swaps them — repeating this for every position in the list.
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            // Assume the smallest value to be at position 'i'
            int minIndex = i;
            
            // Iterate over the array to find the actual minimum value in the unsorted sublist
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap the found minimum element with the first element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    
/*Bubble Sort*/
//Bubble sort repeatedly compares each pair of adjacent elements and swaps them if they’re in the wrong order, slowly “bubbling” the largest elements to the end with each pass.
    public static void bubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
/*Insertion Sort*/
//Insertion sort takes each element and inserts it into its correct position among the already-sorted elements to its left, shifting items as needed.
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than the key
            // to one position ahead of their current position
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
    
 
 
 /*Merge Sort*/
//Merge sort splits the list into halves, recursively sorts each half, and then merges the sorted pieces back together.
     public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Sort the halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data
        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];

        // Merge
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy any remaining elements
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    
/*Quick Sort*/
//Quick sort picks a pivot, partitions the list into elements smaller and larger than the pivot, and then recursively sorts those partitions.
    public static void quickSort(int arr[], int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    
    private static int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    


 *
 * TIME COMPLEXITY (n = number of elements)
 * +----------------+-------------------------+-------------------------+-------------------------+
 * |   Algorithm    |         Best Case       |       Average Case      |        Worst Case       |
 * +----------------+-------------------------+-------------------------+-------------------------+
 * | Bubble Sort    | O(n)*                   | O(n^2)                  | O(n^2)                  |
 * | Selection Sort | O(n^2)                  | O(n^2)                  | O(n^2)                  |
 * | Insertion Sort | O(n)                    | O(n^2)                  | O(n^2)                  |
 * | Quick Sort     | O(n log n)              | O(n log n)              | O(n^2)**                |
 * | Merge Sort     | O(n log n)              | O(n log n)              | O(n log n)              |
 * +----------------+-------------------------+-------------------------+-------------------------+
 *







/*
 * ==========================================================================================
 *                PRACTICAL COMPARISON — WHEN TO USE WHICH SORTING ALGORITHM
 * ==========================================================================================
 *
 * BUBBLE SORT
 *    • When to use: Rarely. Mostly for teaching or extremely tiny datasets.
 *    • Strengths:
 *         - Easy to understand
 *         - Stable
 *         - Adaptive when optimized (O(n) best case)
 *    • Weaknesses:
 *         - Very slow: O(n²)
 *         - Not used in production
 *
 * SELECTION SORT
 *    • When to use: When minimizing swaps matters (e.g., EEPROM/flash memory writes).
 *    • Strengths:
 *         - Always performs exactly n swaps — minimal write operations
 *         - Simple implementation
 *         - In-place
 *    • Weaknesses:
 *         - Not stable
 *         - Still O(n²) in all cases
 *
 * INSERTION SORT
 *    • When to use:
 *         - Arrays that are *nearly sorted*
 *         - Very small datasets (n < 30)
 *         - As a base case inside Quick Sort / Merge Sort
 *    • Strengths:
 *         - Adaptive: O(n) best case
 *         - Stable
 *         - Excellent for small inputs (outperforms quick/merge for tiny n)
 *    • Weaknesses:
 *         - O(n²) worst-case
 *
 * MERGE SORT
 *    • When to use:
 *         - Large datasets
 *         - Linked lists
 *         - When stability is required
 *         - External sorting (data too large to fit in RAM)
 *    • Strengths:
 *         - Always O(n log n) — predictable performance
 *         - Stable
 *         - Good for parallelization
 *    • Weaknesses:
 *         - Requires O(n) extra memory (not in-place)
 *
 * QUICK SORT
 *    • When to use:
 *         - General-purpose fast sorting
 *         - Large in-memory arrays
 *         - When average performance matters more than worst-case guarantee
 *    • Strengths:
 *         - Fastest average case: O(n log n)
 *         - Great cache locality
 *         - In-place (except recursive stack)
 *    • Weaknesses:
 *         - Worst case O(n²) if pivot choices are bad
 *         - Unstable
 *         - Sensitive to input order unless pivot is randomized
 *
 * HEAP SORT (BONUS — often asked in interviews)
 *    • When to use:
 *         - When you need guaranteed O(n log n) *and* O(1) space
 *         - For embedded systems or memory-constrained environments
 *    • Strengths:
 *         - Worst-case O(n log n)
 *         - In-place
 *    • Weaknesses:
 *         - Not stable
 *         - Worse constant factors than quick sort
 *
 * ==========================================================================================
 *                       WHICH SORT IS “BEST” FOR WHICH SITUATION?
 * ==========================================================================================
 *
 * • Small / nearly sorted arrays ............. INSERTION SORT
 * • Teaching / educational demos ............. BUBBLE SORT or SELECTION SORT
 * • Large arrays, general-purpose ............ QUICK SORT (randomized)
 * • Worst-case performance must be good ...... MERGE SORT or HEAP SORT
 * • Stability required ....................... MERGE SORT / INSERTION SORT / BUBBLE SORT
 * • Low memory usage required ................ QUICK SORT / SELECTION SORT / HEAP SORT
 * • Sorting linked lists ..................... MERGE SORT (no random access needed)
 * • Minimizing write operations .............. SELECTION SORT
 *
 * ==========================================================================================
 */
     
 
    
    
    
