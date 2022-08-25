package duke;


/**
 * Has similar functionality to duke.Task
 *
 * @author Yuvaraj Kumaresan
 */
public class ToDo extends Task {

    /**
     * Constructor
     *
     * @param description String describing the todo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Method toString()
     *
     * @return String representation of the todo object.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}