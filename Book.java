import java.util.*;

public class Book 
{
    public static final String[] bookCode = {"Author", "Genre","List of Book"};
    public static final String[] bookName = {"Jane Eyre", "Love, Theoretically", "Book Lovers", "The Diary of a Young Girl", 
                                            "Meditations", "Journals", "The Art of War", "Thinking, Fast and Slow", "Eats, Shoots & Leaves"};
    public static final String[] author = {"Charlotte Brontë", "Ali Hazelwood", "Emily Henry", "Anne Frank", "Marcus Aurelius", 
                                          "Kurt Cobain", "Sun Tzu", "Kahneman", "Lynne Truss"};
    public static final String[] genre = {"Romance", "Journal", "Reference"};
    
    // Parallel array for ISBNs
    public static final String[] isbn = {
        "978", // Jane Eyre
        "979", // Love, Theoretically
        "980", // Book Lovers
        "981", // The Diary of a Young Girl
        "982", // Meditations
        "983", // Journals
        "984", // The Art of War
        "985", // Thinking, Fast and Slow
        "986"  // Eats, Shoots & Leaves
    };
    
    public static boolean[] available = new boolean[bookName.length];
    
    // Static block to initialize book availability to true
    static {
        for (int i = 0; i < available.length; i++) {
            available[i] = true;
        }
    }

    // Default constructor
    public Book(){}

    // method #1: displayByAuthor() with interactive drill-down
    public void displayByAuthor(){
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nAUTHORS AVAILABLE");
            System.out.println("========================");
            for (int i = 0; i < author.length; i++) {
                System.out.println((i + 1) + ". " + author[i]);
            }
            System.out.println("0. Back to View Menu");
            System.out.println("========================");
            System.out.print("Select an author number to view their books: ");
            
            int choice = in.nextInt();
            in.nextLine(); // Clear buffer
            
            if (choice == 0) {
                return; // Go back to displayBookInfo menu
            }
            
            int index = choice - 1;
            if (index >= 0 && index < author.length) {
                String selectedAuthor = author[index];
                System.out.println("\nBOOKS BY: " + selectedAuthor);
                System.out.println("------------------------------------");
                boolean found = false;
                for (int i = 0; i < bookName.length; i++) {
                    if (author[i].equals(selectedAuthor)) {
                        String status = available[i] ? "Available" : "Not Available";
                        System.out.println("- " + bookName[i] + " (ISBN: " + isbn[i] + ") [" + status + "]");
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No books found for this author.");
                }
                System.out.println("------------------------------------");
                System.out.print("Press Enter to return to Author list...");
                in.nextLine();
            } else {
                System.out.println("Invalid choice! Please select a valid author number.");
            }
        }
    }
    
    // method #2: displayByGenre() with interactive drill-down
    public void displayByGenre(){
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nGENRES AVAILABLE");
            System.out.println("========================");
            for (int i = 0; i < genre.length; i++) {
                System.out.println((i + 1) + ". " + genre[i]);
            }
            System.out.println("0. Back to View Menu");
            System.out.println("========================");
            System.out.print("Select a genre number to view its books: ");
            
            int choice = in.nextInt();
            in.nextLine(); // Clear buffer
            
            if (choice == 0) {
                return; // Go back to displayBookInfo menu
            }
            
            int index = choice - 1;
            if (index >= 0 && index < genre.length) {
                String selectedGenre = genre[index];
                System.out.println("\nBOOKS UNDER GENRE: " + selectedGenre);
                System.out.println("------------------------------------");
                
                // Map indexes to genres: Romance (0-2), Journal (3-5), Reference (6-8)
                for (int i = 0; i < bookName.length; i++) {
                    int genreIndex = i / 3;
                    if (genreIndex == index) {
                        String status = available[i] ? "Available" : "Not Available";
                        System.out.println("- " + bookName[i] + " (ISBN: " + isbn[i] + ") by " + author[i] + " [" + status + "]");
                    }
                }
                System.out.println("------------------------------------");
                System.out.print("Press Enter to return to Genre list...");
                in.nextLine();
            } else {
                System.out.println("Invalid choice! Please select a valid genre number.");
            }
        }
    }
    
    // method #3: displayByTitle() with interactive drill-down
    public void displayByTitle(){
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nBOOKS AVAILABLE");
            System.out.println("========================");
            for (int i = 0; i < bookName.length; i++) {
                System.out.println((i + 1) + ". " + bookName[i]);
            }
            System.out.println("0. Back to View Menu");
            System.out.println("========================");
            System.out.print("Select a book number to view full details: ");
            
            int choice = in.nextInt();
            in.nextLine(); // Clear buffer
            
            if (choice == 0) {
                return; // Go back to displayBookInfo menu
            }
            
            int index = choice - 1;
            if (index >= 0 && index < bookName.length) {
                int genreIndex = index / 3; // Derive genre from index
                String status = available[index] ? "Available" : "Not Available";
                
                System.out.println("\n--- BOOK DETAIL CARD ---");
                System.out.println("Title       : " + bookName[index]);
                System.out.println("Author      : " + author[index]);
                System.out.println("Genre       : " + genre[genreIndex]);
                System.out.println("ISBN        : " + isbn[index]);
                System.out.println("Status      : " + status);
                System.out.println("------------------------");
                
                System.out.print("Press Enter to return to Book list...");
                in.nextLine();
            } else {
                System.out.println("Invalid choice! Please select a valid book number.");
            }
        }
    }
    
    // method #4: displayBookInfo()
    public void displayBookInfo(){
        char sentinel;  
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\nM A G A Z I N E");
            System.out.println("====================================\n");
            
            for (int i = 0; i < bookCode.length; i++) {
                System.out.println((i + 1) + ". " + bookCode[i]);
            }
            System.out.println("0. Back\n");
            
            System.out.print("Enter code: ");
            int code = in.nextInt();
            in.nextLine(); // Clear buffer
           
            switch (code) {
                case 1:
                    displayByAuthor();
                    break;
                case 2:
                    displayByGenre();
                    break;
                case 3:
                    displayByTitle();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid code!");
            }

            System.out.print("\nBack to Book Menu? [Y/N]: ");
            sentinel = in.next().toUpperCase().charAt(0);

        } while (sentinel == 'Y');
    }
    
    // method #5: updateAvailability()
    public void updateAvailability(){
        Scanner in = new Scanner(System.in);
        char again;
    
        do {
            System.out.println("\n--- BOOK AVAILABILITY ---");
            for (int i = 0; i < bookName.length; i++) {
                System.out.println((i+1) + ". " + bookName[i] + " [" + (available[i]?"Available":"Not Available") + "]");
            }
            
            System.out.print("\nPick book number: ");
            int pick = in.nextInt() - 1;
            in.nextLine(); // Clear buffer
            
            if (pick >= 0 && pick < bookName.length) {
                available[pick] = !available[pick];  
                System.out.println("Updated: " + bookName[pick] + " is now " + (available[pick]?"Available":"Not Available"));
            }
            
            System.out.print("Again? [Y/N]: ");
            again = in.next().toUpperCase().charAt(0);
            
        } while (again == 'Y');
    }
    
    // =====================================
    //          SEARCHING METHODS
    // =====================================
    
    public void searchByTitle(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter book title keyword: ");
        String query = in.nextLine().trim().toLowerCase();
        
        System.out.println("\nSEARCH RESULTS (BY TITLE)");
        System.out.println("======================================");
        boolean found = false;
        
        for (int i = 0; i < bookName.length; i++) {
            if (bookName[i].toLowerCase().contains(query)) {
                String status = available[i] ? "Available" : "Not Available";
                System.out.println("- " + bookName[i] + " (ISBN: " + isbn[i] + ") by " + author[i] + " [" + status + "]");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No matching books found.");
        }
        System.out.println("======================================");
    }
    
    public void searchByAuthor(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter author name keyword: ");
        String query = in.nextLine().trim().toLowerCase();
        
        System.out.println("\nSEARCH RESULTS (BY AUTHOR)");
        System.out.println("======================================");
        boolean found = false;
        
        for (int i = 0; i < bookName.length; i++) {
            if (author[i].toLowerCase().contains(query)) {
                String status = available[i] ? "Available" : "Not Available";
                System.out.println("- " + bookName[i] + " (ISBN: " + isbn[i] + ") by " + author[i] + " [" + status + "]");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No books found for author matching \"" + query + "\".");
        }
        System.out.println("======================================");
    }
    
    public void searchByGenre(){
        Scanner in = new Scanner(System.in);
        System.out.println("SELECT GENRE:");
        for (int i = 0; i < genre.length; i++) {
            System.out.println((i + 1) + ". " + genre[i]);
        }
        System.out.print("Enter choice (1-" + genre.length + "): ");
        int choice = in.nextInt();
        
        if (choice >= 1 && choice <= genre.length) {
            String selectedGenre = genre[choice - 1];
            System.out.println("\nSEARCH RESULTS (GENRE: " + selectedGenre + ")");
            System.out.println("======================================");
            
            for (int i = 0; i < bookName.length; i++) {
                int genreIndex = i / 3; 
                if (genreIndex == (choice - 1)) {
                    String status = available[i] ? "Available" : "Not Available";
                    System.out.println("- " + bookName[i] + " (ISBN: " + isbn[i] + ") by " + author[i] + " [" + status + "]");
                }
            }
        } else {
            System.out.println("Invalid choice!");
        }
        System.out.println("======================================");
    }

    // Search by ISBN keyword (exact or partial matching)
    public void searchByIsbn(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter book ISBN (e.g. 9xx): ");
        String query = in.nextLine().trim().replace("-", ""); // Strip dashes for user convenience
        
        System.out.println("\nSEARCH RESULTS (BY ISBN)");
        System.out.println("======================================");
        boolean found = false;
        
        for (int i = 0; i < bookName.length; i++) {
            String cleanIsbn = isbn[i].replace("-", "");
            if (cleanIsbn.contains(query)) {
                String status = available[i] ? "Available" : "Not Available";
                System.out.println("- " + bookName[i] + " (ISBN: " + isbn[i] + ") by " + author[i] + " [" + status + "]");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No books found matching ISBN: \"" + query + "\"");
        }
        System.out.println("======================================");
    }

    // Interactive menu for searching books
    public void displaySearchMenu() {
        Scanner in = new Scanner(System.in);
        char sentinel;
        do {
            System.out.println("\nSEARCH MENU");
            System.out.println("==============================");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by Genre");
            System.out.println("4. Search by ISBN");
            System.out.println("0. Back to Main Menu");
            System.out.println("==============================");
            System.out.print("Enter choice: ");
            int choice = in.nextInt();
            in.nextLine(); 

            switch (choice) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByAuthor();
                    break;
                case 3:
                    searchByGenre();
                    break;
                case 4:
                    searchByIsbn();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid code!");
            }

            System.out.print("\nPerform another search? [Y/N]: ");
            sentinel = in.next().toUpperCase().charAt(0);
        } while (sentinel == 'Y');
    }

    public static void main(String[] args) {
        Book app = new Book();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- MAIN CATALOG MENU ---");
            System.out.println("1. View Book Catalog (Drill-down)");
            System.out.println("2. Search Books Menu");
            System.out.println("3. Update Book Availability");
            System.out.println("0. Exit");
            System.out.print("Enter Choice: ");
            int choice = in.nextInt();
            
            if (choice == 1) {
                app.displayBookInfo();
            } else if (choice == 2) {
                app.displaySearchMenu();
            } else if (choice == 3) {
                app.updateAvailability();
            } else {
                break;
            }
        }
    }
}
