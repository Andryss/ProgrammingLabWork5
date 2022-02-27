package Commands;

import MovieObjects.*;
import ReadersExecutors.Executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Type of command, which can read elements
 * @see HashTableCommand
 */
public abstract class ElementCommand extends HashTableCommand {
    /**
     * Map with examples of commands (for example, "insert" - "insert 12")
     * @see FieldSetter
     */
    private static final Map<String, String> fieldExamples = new HashMap<>();
    /**
     * Map with methods we need to invoke to get fields of Movie
     * @see FieldSetter
     * @see Movie
     * @see Coordinates
     * @see Person
     */
    private static final Map<String, Method> methodsSetters = new HashMap<>();
    /**
     * Map with the right order of fields we need to read
     * @see FieldSetter
     */
    private static final Map<Integer, String> order = new HashMap<>();
    /**
     * Do we need to ask a questions "Enter *something*:"?
     */
    private final boolean askQuestions;

    /**
     * Scanner for reading elements from something
     */
    protected Scanner reader;

    static {
        fillMethodsSetters(methodsSetters, Movie.class);
    }

    /**
     * Here we analyze class and fill Maps: fieldExamples, methodsSetters, order
     * @param emptyMethodsSetters not fully filled methodsSetters
     * @param cls class we need to analyze
     * @see FieldSetter
     * @see Movie
     * @see Coordinates
     * @see Person
     */
    private static void fillMethodsSetters(Map<String, Method> emptyMethodsSetters, Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().startsWith("set")) {
                if (method.getParameters()[0].getType().equals(String.class)) {
                    FieldSetter annotation = method.getAnnotation(FieldSetter.class);
                    String fieldName = cls.getSimpleName() + " " + annotation.fieldName();
                    emptyMethodsSetters.put(fieldName, method);
                    fieldExamples.put(fieldName, annotation.example());
                    order.put(annotation.index(), fieldName);
                } else {
                    fillMethodsSetters(emptyMethodsSetters, method.getParameters()[0].getType());
                }
            }
        }
    }

    /**
     * Constructor with name, Hashtable, Scanner and boolean
     * @param scanner for reading elements from something
     * @param askQuestions flag for asking questions
     * @see HashTableCommand
     */
    public ElementCommand(String commandName, Scanner scanner, Hashtable<Integer, Movie> movieHashtable, boolean askQuestions) {
        super(commandName, movieHashtable);
        reader = scanner;
        this.askQuestions = askQuestions;
    }

    /**
     * Method, which read one field (line)
     * @param fieldName name of field we read
     * @return string - user input or null
     */
    protected String readOneField(String fieldName) {
        if (askQuestions) {
            System.out.print("Enter " + fieldName + " (" + fieldExamples.get(fieldName) + "): ");
        }
        String command = reader.nextLine();
        return (command.equals("") ? null : command);
    }

    /**
     * Method, which tries to read and set only one field
     * @param object object, which field we set
     * @param fieldName name of field we set
     * @param method setter we invoke
     */
    protected void setOneField(Object object, String fieldName, Method method) {
        while (true) {
            try {
                method.invoke(object, readOneField(fieldName));
                break;
            } catch (InvocationTargetException e) {
                System.out.println(e.getCause().getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                throw new RuntimeException("the file ended before the element was read", e);
            }
        }
    }

    /**
     * Main method of reading one element
     * @param state are we validating of executing
     * @return reader Movie element
     */
    protected Movie readMovie(Executor.ExecuteState state) {
        if (state == Executor.ExecuteState.EXECUTE) {
            System.out.println("*reading Movie object starts*");
        }
        Movie newMovie = new Movie();
        Coordinates newCoordinates = new Coordinates();
        Person newScreenwriter = new Person();
        for (Integer step : order.keySet()) {
            String fieldName = order.get(step);
            Method method = methodsSetters.get(fieldName);
            if (method.getDeclaringClass() == Movie.class) {
                setOneField(newMovie, fieldName, method);
            } else if (method.getDeclaringClass() == Coordinates.class) {
                setOneField(newCoordinates, fieldName, method);
            } else if (method.getDeclaringClass() == Person.class) {
                setOneField(newScreenwriter, fieldName, method);
            } else {
                throw new RuntimeException("Some problems with \"methodsSetters\" (find new class?)");
            }
        }
        newMovie.setCoordinates(newCoordinates);
        newMovie.setScreenwriter(newScreenwriter);
        if (state == Executor.ExecuteState.EXECUTE) {
            System.out.println("*reading Movie object complete*");
        }
        return newMovie;
    }
}
