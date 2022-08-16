import java.util.ArrayList;
import java.util.Scanner;

/*
Duke IP for CS2103T by Yuvaraj Kumaresan AY2022/23
 */
public class Duke {

    public static ArrayList<String> storage = new ArrayList<String>();

    /*
    Method list
    Description: Asks for user input using the scanner utility,
                 Adds input to memory if input is not bye or list,
                 if input is bye exit message is displayed and program exits,
                 if input is list program lists out the stored inputs.
     */
    public static void list() {
        Scanner input = new Scanner(System.in);
        String text = input.nextLine();
        if (text.equalsIgnoreCase("list")) {
            if (storage.size() == 0) {
                System.out.println("No items have been added to the list");
            } else {
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println((i + 1) + ". " + storage.get(i));
                }
            }
            list();
        } else if (text.equalsIgnoreCase("bye")) {
            System.out.println("Bye. Hope to see you again soon!\n");
        } else {
            System.out.println("Added:" + text);
            storage.add(text);
            list();
        }

    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello I am \n" + logo + "\n What can I do for you?\n");
        list();
    }
}

