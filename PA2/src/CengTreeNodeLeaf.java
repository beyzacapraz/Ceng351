import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);
        books = new ArrayList<CengBook>();
        this.type = CengNodeType.Leaf;
        // TODO: Extra initializations
    }
    public CengTreeNodeLeaf(CengTreeNode parent, ArrayList<CengBook> books)
    {
        super(parent);
        this.books = books;
        this.type = CengNodeType.Leaf;
        // TODO: Extra initializations
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public ArrayList<CengBook> getBook()
    {
        return this.books;
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }
    public String bookTitleAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return null;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookTitle();
        }
    }
    public String bookAuthorAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return null;
        } else {
            CengBook book = this.books.get(index);

            return book.getAuthor();
        }
    }
    public String bookGenreAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return null;
        } else {
            CengBook book = this.books.get(index);

            return book.getGenre();
        }
    }
    public void addBook(CengBook book, int index)
    {
        if(index > this.bookCount()) {

        } else {
            this.books.add(index,book);


        }
    }

    // Extra Functions
}
