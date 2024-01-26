import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);
        keys = new ArrayList<Integer>(); // create empty
        children = new ArrayList<CengTreeNode>();
        this.type = CengNodeType.Internal;

        // TODO: Extra initializations, if necessary.
    }
    public CengTreeNodeInternal(CengTreeNode parent, ArrayList<Integer> keys, ArrayList<CengTreeNode> children)
    {
        //for initialize arrays according to values we enter
        super(parent);
        this.keys = keys; // create empty
        this.children = children;
        for(int i=0; i<this.children.size(); i++){
            this.children.get(i).setParent(this);
        }
        this.type = CengNodeType.Internal;
        // TODO: Extra initializations, if necessary.
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }
    public void addKey(int index, Integer key){//??
        keys.add(index, key);
    }
    public ArrayList<Integer> getKey(){//??
        return this.keys;
    }
    public void setChildren(int index, CengTreeNodeLeaf newleaf1){
        if(this.children.isEmpty()) this.children.add(0, newleaf1);
        this.children.set(index, newleaf1);
    }
    public void addChildren(int index, CengTreeNodeLeaf newleaf2){
        this.children.add(index, newleaf2);
    }
    public void setChildren(int index, CengTreeNodeInternal newleaf1){
        if(this.children.isEmpty()) this.children.add(0, newleaf1);
        this.children.set(index, newleaf1);
    }
    public void addChildren(int index, CengTreeNodeInternal newleaf2){
        this.children.add(index, newleaf2);
    }


    // Extra Functions
}
