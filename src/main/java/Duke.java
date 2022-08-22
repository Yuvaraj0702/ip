import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Duke IP for CS2103T AY 2022/2023
 *
 * @author Yuvaraj Kumaresan
 */
public class Duke {

    public static ArrayList<Task> storage = new ArrayList<Task>();
    public static String divider = "----------------------------------------------------------------------------------------------------------------";

    /**
     * Method list
     *
     * @throws DukeException Description: Asks for user input using the scanner utility,
     *                       Adds input to memory if input is todo <description>, deadline <description> /by <time> or event <description> /at <time>,
     *                       if input is bye exit message is displayed and program exits,
     *                       if input is list program lists out the stored inputs,
     *                       if input is mark <number> program marks the task as complete and displays it,
     *                       if input is unmark <number> program marks the task as incomplete and displays it.
     *                       if input is delete <number> program deletes the task from the list.
     */
    public static void list() throws DukeException, IOException {

        Scanner input = new Scanner(System.in);
        try {
            File myObj = new File("duke.txt");
            if (myObj.createNewFile()) {
            } else {
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        while (input.hasNext()) {
            PrintStream fileOut = new PrintStream("duke.txt");
            PrintStream console = System.out;
            String text = input.nextLine();

            if (text.equalsIgnoreCase("list")) {
                if (storage.size() == 0) {
                    System.out.println(divider);
                    System.out.println("The list is empty, please add more items to display them here.");
                    System.out.println(divider);
                } else {
                    System.out.println(divider);
                    System.out.println("Here are the tasks in your list : ");
                    for (int i = 0; i < storage.size(); i++) {
                        System.out.println((i + 1) + ". " + storage.get(i).toString());
                    }
                    System.out.println(divider);
                }

            } else if (text.equalsIgnoreCase("bye")) {
                System.out.println(divider);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(divider);
                writeToFile();
                System.exit(0);

            } else if (text.startsWith("delete")) {
                try {
                    if (text.equalsIgnoreCase("delete") || text.equalsIgnoreCase("delete ")) {
                        System.out.println(divider);
                        System.out.println("Please provide a task number to delete");
                        System.out.println(divider);
                    } else {
                        int deletable = Integer.parseInt(text.replace("delete ", "")) - 1;
                        if (deletable < storage.size() && deletable >= 0) {
                            Task deleted = storage.get(deletable);
                            storage.remove(deletable);
                            System.out.println(divider);
                            System.out.println("Noted. I have removed this task:\n" +
                                    deleted.toString() +
                                    "\nNow you have " + storage.size() + " tasks in the list");
                            System.out.println(divider);
                        } else {
                            System.out.println(divider);
                            System.out.println("The task you want to delete does not exist.");
                            System.out.println(divider);
                        }

                    }
                } catch (NumberFormatException error) {
                    System.out.println(divider);
                    System.out.println("Invalid command please add a space between delete and the list item you would like to interact with. \n" +
                            "Additionally ensure you have entered a number after delete.");
                    System.out.println(divider);
                }

            } else if (text.startsWith("todo")) {
                if (text.equalsIgnoreCase("todo") || text.equalsIgnoreCase("todo ") || text.replace("todo ", "").trim().length() < 1) {
                    try {
                        throw new DukeException.DukeToDoException("Please provide a description for your todo task.");
                    } catch (DukeException.DukeToDoException error) {
                        System.out.println(divider);
                        System.out.println(error.getMessage());
                        System.out.println(divider);
                        list();
                    }
                } else {
                    ToDo item = new ToDo(text.replace("todo ", ""));
                    storage.add(item);
                    System.out.println(divider);
                    System.out.println("Got it. I've added this task. \n" + item.toString() + "\nNow you have " + storage.size() + " tasks in the list");
                    System.out.println(divider);
                }

            } else if (text.startsWith("deadline")) {
                try {
                    String[] description = text.replace("deadline ", "").split("/by ");
                    Deadline item = new Deadline(description[0], description[1]);
                    storage.add(item);
                    System.out.println(divider);
                    System.out.println("Got it. I've added this task. \n" + item.toString() + "\nNow you have " + storage.size() + " tasks in the list");
                    System.out.println(divider);
                } catch (ArrayIndexOutOfBoundsException error) {
                    System.out.println(divider);
                    System.out.println("Please provide a deadline and a by time e.g. deadline <description of the deadline> /by <time of the deadline>");
                    System.out.println(divider);
                }

            } else if (text.startsWith("event")) {
                try {
                    String[] description = text.replace("event ", "").split("/at ");
                    Event item = new Event(description[0], description[1]);
                    storage.add(item);
                    System.out.println(divider);
                    System.out.println("Got it. I've added this task. \n" + item.toString() + "\nNow you have " + storage.size() + " tasks in the list");
                    System.out.println(divider);
                } catch (ArrayIndexOutOfBoundsException error) {
                    System.out.println(divider);
                    System.out.println("Please provide a event and an at time e.g. event <description of the event> /at <time of the event>");
                    System.out.println(divider);
                }

            } else if (text.startsWith("mark")) {
                try {
                    if (text.equalsIgnoreCase("mark") || text.equalsIgnoreCase("mark ")) {
                        System.out.println(divider);
                        System.out.println("Please provide a task number to mark as done");
                        System.out.println(divider);
                    } else {
                        if (Integer.parseInt(text.replace("mark ", "")) - 1 < storage.size() && Integer.parseInt(text.replace("mark ", "")) > 0) {
                            if (!storage.get(Integer.parseInt(text.replace("mark ", "")) - 1).getIsDone()) {
                                storage.get(Integer.parseInt(text.replace("mark ", "")) - 1).setIsDone(true);
                                System.out.println(divider);
                                System.out.println("Nice! I've marked this task as done \n" +
                                        storage.get(Integer.parseInt(text.replace("mark ", "")) - 1).toString() +
                                        "\nNow you have " + storage.size() + " tasks in the list");
                                System.out.println(divider);
                            } else {
                                System.out.println(divider);
                                System.out.println("This task is already marked done");
                                System.out.println(divider);
                            }
                        } else {
                            System.out.println(divider);
                            System.out.println("Such an item does not exist");
                            System.out.println(divider);
                        }
                    }
                } catch (NumberFormatException error) {
                    System.out.println(divider);
                    System.out.println("Invalid command please add a space between mark and the list item you would like to interact with. \n" +
                            "Additionally ensure you have entered a number after mark.");
                    System.out.println(divider);
                }

            } else if (text.startsWith("unmark")) {
                try {
                    if (text.equalsIgnoreCase("unmark") || text.equalsIgnoreCase("unmark ")) {
                        System.out.println(divider);
                        System.out.println("Please provide a task number to mark as not done");
                        System.out.println(divider);
                    } else {
                        if (Integer.parseInt(text.replace("unmark ", "")) - 1 < storage.size() && Integer.parseInt(text.replace("unmark ", "")) > 0) {
                            if (storage.get(Integer.parseInt(text.replace("unmark ", "")) - 1).getIsDone()) {
                                storage.get(Integer.parseInt(text.replace("unmark ", "")) - 1).setIsDone(false);
                                System.out.println(divider);
                                System.out.println("Ok, I've marked this task as not done yet \n" +
                                        storage.get(Integer.parseInt(text.replace("unmark ", "")) - 1).toString() +
                                        "\nNow you have " + storage.size() + " tasks in the list");
                                System.out.println(divider);
                            } else {
                                System.out.println(divider);
                                System.out.println("This task has already been marked not done");
                                System.out.println(divider);
                            }
                        } else {
                            System.out.println(divider);
                            System.out.println("Such an item does not exist");
                            System.out.println(divider);
                        }
                    }
                } catch (NumberFormatException error) {
                    System.out.println(divider);
                    System.out.println("Invalid command please add a space between unmark and the list item you would like to interact with. \n" +
                            "Additionally ensure you have entered a number after unmark.");
                    System.out.println(divider);
                }

            } else if (text.startsWith("help")) {
                System.out.println(divider);
                System.out.println("Welcome to the user guide. This guide has all the commands that are necessary to operate Duke\n\n" +
                        "Main commands : \n\n" +
                        "Todo : adds a todo task to the task list :-> todo <description of the task> \n" +
                        "Deadline : adds a deadline task to the task list :-> deadline <description of the deadline> /by <time of the deadline> \n" +
                        "Event : adds an event task to the task list :-> event <description of the event> /at <time of the event> \n\n" +
                        "Other commands: \n\n" +
                        "Mark : marks a task as done :-> mark <task number> \n" +
                        "Unmark : marks a task as not done :-> unmark <task number> \n" +
                        "Delete : deletes a task :-> delete <task number>\n" +
                        "Help : brings up this display :-> help\n" +
                        "Bye : closes Duke :-> bye \n\n" +
                        "Please enter one of the above to continue.");
                System.out.println(divider);

            } else {
                try {
                    throw new DukeException.DukeCommandException("Invalid command");
                } catch (DukeException.DukeCommandException error) {
                    System.out.println(divider);
                    System.out.println(error.getMessage());
                    System.out.println("Please provide a proper command. Formats are as follows: \n\n" +
                            "Main commands : \n\n" +
                            "Todo : adds a todo task to the task list :-> todo <description of the task> \n" +
                            "Deadline : adds a deadline task to the task list :-> deadline <description of the deadline> /by <time of the deadline> \n" +
                            "Event : adds an event task to the task list :-> event <description of the event> /at <time of the event> \n\n" +
                            "Other commands: \n\n" +
                            "Mark : marks a task as done :-> mark <task number> \n" +
                            "Unmark : marks a task as not done :-> unmark <task number> \n" +
                            "Delete : deletes a task :-> delete <task number>\n" +
                            "Help : brings up this display :-> help\n" +
                            "Bye : closes Duke :-> bye\n\n" +
                            "Please enter one of the above to continue.");
                    System.out.println(divider);
                }
            }
            writeToFile();
        }
    }

    /**
     * Method writeToFile.
     * Writes the list to the text file.
     *
     * @throws IOException
     */
    public static void writeToFile() throws IOException {
        FileWriter dukeWriter = new FileWriter("duke.txt");
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getClass() == ToDo.class) {
                dukeWriter.write("ToDo / " + storage.get(i).getIsDone() + " / " + storage.get(i).getDescription() + "\n");
            }
            if (storage.get(i).getClass() == Event.class) {
                dukeWriter.write("Event / " + ((Event) storage.get(i)).getIsDone() + " / " + ((Event) storage.get(i)).getDescription() + " / " + ((Event) storage.get(i)).getAt() + "\n");
            }
            if (storage.get(i).getClass() == Deadline.class) {
                dukeWriter.write("Deadline / " + ((Deadline) storage.get(i)).getIsDone() + " / " + ((Deadline) storage.get(i)).getDescription() + " / " + ((Deadline) storage.get(i)).getBy() + "\n");
            }
        }
        dukeWriter.close();
    }

    /**
     * Method Main.
     *
     * @param args
     * @throws DukeException
     */
    public static void main(String[] args) throws DukeException, IOException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(divider);
        System.out.println("Hello I am \n" + logo + "\nType help to see user guide.\n\nWhat can I do for you?");
        System.out.println(divider);
        list();
    }
}

