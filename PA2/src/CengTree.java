import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        root = new CengTreeNodeLeaf(null); //??
        // TODO: Initialize the class
    }

    public void addBook(CengBook book)
    {
        // TODO: Insert Book to Tree
        CengTreeNode leaf = null;
        if(root != null){
            leaf = find_leaf(root, book.getBookID());

        }
        HelperAddBook(book, leaf);

    }
    public CengTreeNode find_leaf(CengTreeNode node, Integer bookID) {
        if (node.getType() == CengNodeType.Leaf) {
            return node;
        } else {
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            int key_size = internal.keyCount();
            int index = 0;
            for (int i = 0; i < key_size; i++) {
                if (bookID < internal.keyAtIndex(i)) {
                    break;
                }
                index++;
            }
            // Check for an empty internal node
            if (index == 0) {
                // If bookID is less than the first key, go to the leftmost child
                return find_leaf(internal.getAllChildren().get(0), bookID);
            } else {
                // Go to the right child of the key where bookID belongs
                return find_leaf(internal.getAllChildren().get(index), bookID);
            }
        }
    }
    public void HelperAddBook(CengBook book, CengTreeNode node)
    {
        // if leaf and not full
        // if leaf and full: split d and rest copy up the middle create 2 new leafs and add children array accordingly
        // if non leaf node overflows split the node and push up the middle element
        ArrayList<CengBook> books;
        int leaf_order = CengTreeNode.order;
        CengTreeNode parent = node.getParent();
        books = ((CengTreeNodeLeaf) node).getBook();
        int size = ((CengTreeNodeLeaf) node).bookCount();
        int index = 0;
        for(int i = 0; i< size; i++){
            if(book.getBookID() >= ((CengTreeNodeLeaf) node).bookKeyAtIndex(i)){
                index++;
            }
        }
        ((CengTreeNodeLeaf) node).addBook(book, index);

        if(size + 1 <= 2* leaf_order) return;
        else{
            Integer new_key = books.get(leaf_order).getBookID(); // doğru index mi?
            CengTreeNodeLeaf leaf1 = new CengTreeNodeLeaf(parent, new ArrayList<CengBook> (books.subList(0,leaf_order)));
            CengTreeNodeLeaf leaf2 = new CengTreeNodeLeaf(parent, new ArrayList<CengBook> (books.subList(leaf_order,size + 1)));


            parent = Copy_up(parent,new_key, leaf1, leaf2, leaf_order); // type sıkıntı çıkarabilir
            if(((CengTreeNodeInternal) parent).keyCount() > leaf_order * 2){
                parent = Move_up(parent,leaf_order);
            }
            if(parent.getParent() == null){
                root = parent;
            }

        }

    }

    public CengTreeNode Copy_up(CengTreeNode parent, Integer new_key, CengTreeNodeLeaf leaf1, CengTreeNodeLeaf leaf2, int leaf_order){
        if(parent == null) parent = new CengTreeNodeInternal(null);
        int size = ((CengTreeNodeInternal) parent).keyCount();
        int index = 0;
        for(; index < size && new_key > ((CengTreeNodeInternal) parent).keyAtIndex(index); index++);
        ((CengTreeNodeInternal) parent).addKey(index, new_key);
        ((CengTreeNodeInternal) parent).setChildren(index,leaf1);
        ((CengTreeNodeInternal) parent).addChildren(++index, leaf2);
        leaf1.setParent(parent);
        leaf2.setParent(parent);
        return parent;

    }
    public CengTreeNodeInternal Move_up(CengTreeNode node, int leaf_order){
        CengTreeNode parent = node.getParent();
        if(parent == null) parent = new CengTreeNodeInternal(null);

        Integer new_key = ((CengTreeNodeInternal) node).keyAtIndex(leaf_order);
        CengTreeNodeInternal internal1 = new CengTreeNodeInternal(parent, new ArrayList<Integer> (((CengTreeNodeInternal) node).getKey().subList(0,leaf_order)), new ArrayList<CengTreeNode> (((CengTreeNodeInternal) node).getAllChildren().subList(0,leaf_order + 1)));
        CengTreeNodeInternal internal2 = new CengTreeNodeInternal(parent, new ArrayList<Integer> (((CengTreeNodeInternal) node).getKey().subList(leaf_order + 1,((CengTreeNodeInternal) node).keyCount())), new ArrayList<CengTreeNode> (((CengTreeNodeInternal) node).getAllChildren().subList(leaf_order + 1,((CengTreeNodeInternal) node).getAllChildren().size())));
        // boundary?

        int size = ((CengTreeNodeInternal) parent).keyCount();
        int index = 0;
        for(; index < size && new_key > ((CengTreeNodeInternal) parent).keyAtIndex(index); index++);
        ((CengTreeNodeInternal) parent).addKey(index, new_key);
        ((CengTreeNodeInternal) parent).setChildren(index,internal1);
        ((CengTreeNodeInternal) parent).addChildren(++index, internal2);
        internal1.setParent(parent);
        internal2.setParent(parent);
        if(size + 1 > 2 * leaf_order)  return Move_up(parent, leaf_order); // parent internal parenti değiştirir mi?
        else return (CengTreeNodeInternal) parent;



    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        int tab = 0;
        CengTreeNodeLeaf leaf;
        leaf = (CengTreeNodeLeaf)find_leaf(root,bookID);
        int size = leaf.bookCount(); int i = 0;
        for(; i < size; i++){
            if(bookID.equals(leaf.bookKeyAtIndex(i))) break;
        }
        if(i >= size){
            System.out.print("Could not find " + bookID);
            return null;
        }
        if(root == null) {
            System.out.println("Could not find " + bookID);
            return null;
        }
        else{
            ArrayList<CengTreeNode> visitedNodes = new ArrayList<>();
            recursiveSearch(bookID, root, visitedNodes, tab);
            return visitedNodes;
        }


    }
    public void printTree()
    {
        if(root != null){

            int tab = 0;
            RecursivePrint(root, tab);
        }


        // TODO: Print the whole tree to console
    }


    public void recursiveSearch(Integer bookID, CengTreeNode node,ArrayList<CengTreeNode> visitedNodes, int tab) // tab değişir mi??
    {
        // root oluyor mu ??
        // if node is leaf we should search for the key and print <record>... else could not find key
        visitedNodes.add(node);
        if(node.getType() == CengNodeType.Leaf){
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            int size = leaf.bookCount();
            for(int i = 0; i < size; i++){
                if(bookID.equals(leaf.bookKeyAtIndex(i))){
                    printTabs(tab);
                    System.out.print("<record>" + leaf.bookKeyAtIndex(i) + "|" + leaf.bookTitleAtIndex(i) + "|" + leaf.bookAuthorAtIndex(i) + "|" + leaf.bookGenreAtIndex(i) + "</record>"+"\n");
                    return;
                }
            }
            System.out.print("Could not find " + bookID);

        }
        // if node is internal node than we should search the suitable key and recursively call with its children
        // for every node we should print <index> \n values than <index> \t recursive call
        else{
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            int key_size = internal.keyCount();
            int index = 0;
            printTabs(tab);
            for(int i = 0; i < key_size; i++){
                if(bookID >= internal.keyAtIndex(i)) index++; // büyük ya da eşitse sağ
                else break;
            }

            System.out.print("<index>\n"); // indentation ???
            for(int i = 0; i < key_size; i++){
                printTabs(tab);
                System.out.print(internal.keyAtIndex(i) + "\n");
            }
            printTabs(tab);
            System.out.print("</index>\n");

            recursiveSearch(bookID, internal.getAllChildren().get(index), visitedNodes, tab + 1);
        }

    }



    public void RecursivePrint(CengTreeNode node, int tab)
    {   // tab değeri sıkıntı çıkarır mı?
        if(node.getType() == CengNodeType.Leaf){
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            int size = leaf.bookCount();
            printTabs(tab);
            System.out.print("<data>\n");
            for(int i = 0; i < size; i++){
                printTabs(tab);
                System.out.print("<record>" + leaf.bookKeyAtIndex(i) + "|" + leaf.bookTitleAtIndex(i) + "|" + leaf.bookAuthorAtIndex(i) + "|" + leaf.bookGenreAtIndex(i) + "</record>\n");
            }
            printTabs(tab);
            System.out.print("</data>\n");

        }
        // if node we should print the keys of that node after that we should call its children recursively and this process is repetaed for every node
        else{
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            int key_size = internal.keyCount();
            printTabs(tab);
            System.out.print("<index>\n");
            for(int i = 0; i < key_size; i++){
                printTabs(tab);
                System.out.print(internal.keyAtIndex(i) + "\n");
            }
            printTabs(tab);
            System.out.print("</index>\n");
            int child_size = internal.getAllChildren().size();

            for(int i = 0; i < child_size; i++){
                RecursivePrint(internal.getAllChildren().get(i), tab + 1);
            }



        }
        // TODO: Print the whole tree to console
    }
    private void printTabs(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("\t"); // Use "\t" for each level of indentation
        }
    }


    // Any extra functions...
}
