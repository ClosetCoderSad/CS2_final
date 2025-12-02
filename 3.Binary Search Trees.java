*
 * ==========================================================================================
 *                             TOPIC 3 NOTES: BINARY SEARCH TREES
 * ==========================================================================================
 *
 * 1) BASIC DEFINITIONS
 * ------------------------------------------------------------------------------------------
 * Tree:
 *   - Hierarchical data structure made of nodes.
 *   - Each node has:
 *       * value (data)
 *       * reference to left child
 *       * reference to right child
 *
 *   class TreeNode {
 *       int value;
 *       TreeNode left;
 *       TreeNode right;
 *
 *       TreeNode(int value) {
 *           this.value = value;
 *           this.left = null;
 *           this.right = null;
 *       }
 *   }
 *
 * Binary Tree:
 *   - Each node has AT MOST 2 children: left and right.
 *
 * Binary Search Tree (BST):
 *   - Special binary tree with ordering property:
 *       * left subtree values  < node.value
 *       * right subtree values > node.value
 *       * no duplicates (in our simple version)
 *       * both left and right subtrees are themselves BSTs
 *
 *
 * 2) USEFUL BINARY TREE PROPERTIES
 * ------------------------------------------------------------------------------------------
 * Let level of root = 1, height h = number of levels, and n = number of nodes.
 *
 *   - Max # of nodes on level ℓ:        2^(ℓ - 1)
 *   - Max # of nodes in tree of height h: 2^h - 1
 *   - # of internal nodes for binary tree with L leaves:  L - 1
 *   - Min height of binary tree with n nodes:  ceil( log2(n + 1) )
 *
 * Intuition:
 *   * A perfectly full binary tree packs nodes as tightly as possible, hitting these bounds.
 *
 *
 * 3) REPRESENTING A BST IN JAVA
 * ------------------------------------------------------------------------------------------
 *   class BinarySearchTree {
 *       TreeNode root; // reference to the root of the BST
 *
 *       BinarySearchTree() {
 *           root = null; // start with an empty tree
 *       }
 *   }
 *
 *
 * 4) INSERTION IN A BST
 * ------------------------------------------------------------------------------------------
 * Idea:
 *   - Recursively walk the tree:
 *       * if tree/subtree is empty → new node becomes root of that subtree
 *       * if value < node.value → go left
 *       * if value > node.value → go right
 *
 * Public insert method:
 *
 *   public void insert(int value) {
 *       root = insert(root, value); // delegate to recursive helper
 *   }
 *
 * Recursive helper:
 *
 *   private TreeNode insert(TreeNode node, int value) {
 *       // Base case: empty spot found → create and return new node
 *       if (node == null) {
 *           return new TreeNode(value);
 *       }
 *
 *       // Recur down the tree and re-link returned subtree roots
 *       if (value < node.value) {
 *           node.left = insert(node.left, value);
 *       } else if (value > node.value) {
 *           node.right = insert(node.right, value);
 *       }
 *       // If value == node.value we do nothing (no duplicates) in this simple version
 *
 *       return node;  // important: return unchanged node pointer up the recursion
 *   }
 *
 *
 * 5) SEARCHING IN A BST
 * ------------------------------------------------------------------------------------------
 *   public boolean search(int value) {
 *       return search(root, value);
 *   }
 *
 *   private boolean search(TreeNode node, int value) {
 *       if (node == null) return false;           // not found
 *       if (value == node.value) return true;     // found
 *       if (value < node.value) return search(node.left, value);
 *       else                     return search(node.right, value);
 *   }
 *
 * Complexity (assuming height = h):
 *   - Best / average case (balanced tree): O(log n)
 *   - Worst case (skewed tree / like a linked list): O(n)
 *
 *
 * 6) DELETION IN A BST
 * ------------------------------------------------------------------------------------------
 * Cases when deleting a node with key "value":
 *   1) Node has NO children (leaf):
 *        - Just remove it (return null to its parent).
 *
 *   2) Node has ONE child:
 *        - Return the non-null child to replace this node.
 *
 *   3) Node has TWO children:
 *        - Find in-order successor (smallest node in right subtree) OR
 *          in-order predecessor (largest node in left subtree).
 *        - Copy its value into the current node.
 *        - Recursively delete that successor/predecessor node.
 *
 * Helper to find in-order successor (minimum in right subtree):
 *
 *   private TreeNode minValNode(TreeNode node) {
 *       while (node.left != null) {
 *           node = node.left;
 *       }
 *       return node;
 *   }
 *
 * Public delete method:
 *
 *   public void delete(int value) {
 *       root = delete(root, value);
 *   }
 *
 * Recursive delete helper:
 *
 *   private TreeNode delete(TreeNode node, int value) {
 *       if (node == null) return node; // value not found
 *
 *       // Search down the tree
 *       if (value < node.value) {
 *           node.left = delete(node.left, value);
 *       } else if (value > node.value) {
 *           node.right = delete(node.right, value);
 *       } else {
 *           // Node to delete found
 *
 *           // Case 1 & 2: node with 0 or 1 child
 *           if (node.left == null)  return node.right;
 *           if (node.right == null) return node.left;
 *
 *           // Case 3: node with 2 children
 *           node.value = minValNode(node.right).value;   // copy successor's value
 *           node.right = delete(node.right, node.value); // delete successor
 *       }
 *       return node;
 *   }
 *
 *
 * 7) TREE TRAVERSALS (DEPTH-FIRST)
 * ------------------------------------------------------------------------------------------
 * Important for visiting all nodes in a defined order.
 *
 * In-order (Left, Root, Right):
 *   - For BSTs, this prints values in sorted (ascending) order.
 *
 *   public void inOrderTraversal(TreeNode node) {
 *       if (node != null) {
 *           inOrderTraversal(node.left);
 *           System.out.print(node.value + " ");   // "visit" node
 *           inOrderTraversal(node.right);
 *       }
 *   }
 *
 * Pre-order (Root, Left, Right):
 *   - Useful for copying the tree / prefix expressions.
 *
 *   public void preOrderTraversal(TreeNode node) {
 *       if (node != null) {
 *           System.out.print(node.value + " ");
 *           preOrderTraversal(node.left);
 *           preOrderTraversal(node.right);
 *       }
 *   }
 *
 * Post-order (Left, Right, Root):
 *   - Useful for deleting/freeing the whole tree (children before parent).
 *
 *   public void postOrderTraversal(TreeNode node) {
 *       if (node != null) {
 *           postOrderTraversal(node.left);
 *           postOrderTraversal(node.right);
 *           System.out.print(node.value + " ");
 *       }
 *   }
 *
 *
 * 8) DFS & BFS ON TREES
 * ------------------------------------------------------------------------------------------
 * DFS (Depth-First Search):
 *   - Goes from root to leaf following a branch, then backtracks.
 *   - Often implemented with recursion (implicit stack).
 *   - Pre-order traversal example is a DFS.
 *
 *   public void dfs() { dfs(root); }
 *
 *   private void dfs(TreeNode node) {
 *       if (node == null) return;
 *       System.out.print(node.value + " ");
 *       dfs(node.left);
 *       dfs(node.right);
 *   }
 *
 * BFS (Breadth-First Search) / Level-Order:
 *   - Visits nodes level by level from top to bottom.
 *   - Uses a queue to store nodes of current frontier.
 *
 *   public void bfs() {
 *       if (root == null) return;
 *       java.util.Queue<TreeNode> q = new java.util.LinkedList<>();
 *       q.add(root);
 *       while (!q.isEmpty()) {
 *           TreeNode node = q.poll();
 *           System.out.print(node.value + " ");
 *           if (node.left  != null) q.add(node.left);
 *           if (node.right != null) q.add(node.right);
 *       }
 *   }
 *
 * Time Complexity for DFS & BFS:
 *   - O(n) where n is the number of nodes (each node visited once).
 *
 * Space Complexity:
 *   - DFS: O(h) where h is tree height (recursion stack).
 *   - BFS: O(w) where w is max width of a level.
 *
 *
 * 9) ALGORITHM ANALYSIS FOR BST OPERATIONS
 * ------------------------------------------------------------------------------------------
 * Let h = height of BST, n = # of nodes.
 *
 * For search, insertion, deletion:
 *   - Time complexity is O(h) because we follow one path from root down.
 *   - For BALANCED BST (height ≈ log2(n)):
 *       * Best/average case: O(log n)
 *   - For UNBALANCED / SKEWED BST (like a linked list):
 *       * Worst case: O(n)
 *
 * Space:
 *   - Recursive implementations use O(h) stack space.
 *   - Iterative versions can be O(1) extra space.
 *
 *
 * 10) BALANCING BINARY SEARCH TREES
 * ------------------------------------------------------------------------------------------
 * Problem:
 *   - Plain BSTs can become unbalanced (skewed) after many inserts/deletes
 *     if values are inserted in sorted or nearly sorted order.
 *   - When that happens, height ≈ n → operations degrade from O(log n) to O(n).
 *
 * Idea of Balancing:
 *   - Keep tree height close to log2(n) by performing rotations/restructuring
 *     whenever the tree becomes too skewed.
 *
 * Common Self-Balancing BSTs (just names for now):
 *   - AVL Tree
 *   - Red-Black Tree
 *   - Splay Tree
 *
 * Trade-offs:
 *   - Extra complexity in code to maintain balance.
 *   - In return, we guarantee O(log n) search/insert/delete in the worst case.
 *
 *
 * 11) QUICK SAMPLE USAGE
 * ------------------------------------------------------------------------------------------
 *   public static void main(String[] args) {
 *       BinarySearchTree bst = new BinarySearchTree();
 *
 *       // Insert nodes
 *       bst.insert(6);
 *       bst.insert(4);
 *       bst.insert(8);
 *       bst.insert(2);
 *       bst.insert(5);
 *
 *       // Traversals
 *       System.out.print("In-order (sorted): ");
 *       bst.inOrderTraversal(bst.root);    // 2 4 5 6 8
 *       System.out.println();
 *
 *       System.out.print("Pre-order: ");
 *       bst.preOrderTraversal(bst.root);   // 6 4 2 5 8
 *       System.out.println();
 *
 *       System.out.print("Post-order: ");
 *       bst.postOrderTraversal(bst.root);  // 2 5 4 8 6
 *       System.out.println();
 *
 *       // BFS / level-order
 *       System.out.print("BFS: ");
 *       bst.bfs();                         // 6 4 8 2 5
 *       System.out.println();
 *
 *       // Delete an element (e.g., 4) and check structure
 *       bst.delete(4);
 *       System.out.print("In-order after deleting 4: ");
 *       bst.inOrderTraversal(bst.root);    // 2 5 6 8
 *       System.out.println();
 *   }
 *
 * ==========================================================================================
 *   These notes summarize:
 *      - Definition & properties of BSTs
 *      - Core operations (insert, search, delete)
 *      - Tree traversals (in-order, pre-order, post-order)
 *      - DFS & BFS on trees
 *      - Time/space analysis & importance of balancing
 * ==========================================================================================
 */
