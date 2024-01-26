import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename) {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        try (BufferedReader file_reader = new BufferedReader(new FileReader(filename))) {
            String file;
            while ((file = file_reader.readLine()) != null) {
                String[] line = file.split("\\|");
                Integer bookID = Integer.parseInt(line[0]);
                String bookTitle = line[1];
                String author = line[2];
                String genre = line[3];
                bookList.add(new CengBook(bookID, bookTitle, author, genre));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        boolean is_quit = true;
        try(BufferedReader system_reader = new BufferedReader(new InputStreamReader(System.in))){
            while(is_quit){
                String[] line = system_reader.readLine().split("\\|"); //
                String system_input = line[0];
                if(system_input.equalsIgnoreCase("quit")){
                    is_quit = false;
                    break;

                }
                else if(system_input.equalsIgnoreCase("add")){
                    Integer bookID = Integer.parseInt(line[1]);
                    String bookTitle = line[2];
                    String author = line[3];
                    String genre = line[4];
                    CengBookRunner.addBook(new CengBook(bookID,bookTitle,author,genre));

                }
                else if(system_input.equalsIgnoreCase("search")){
                    CengBookRunner.searchBook(Integer.parseInt(line[1]));
                }
                else if(system_input.equalsIgnoreCase("print")){
                    CengBookRunner.printTree();
                }


            }
        }




        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.
    }
}
