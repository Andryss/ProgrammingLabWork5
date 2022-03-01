//3119910

import MovieObjects.FieldException;
import ReadersExecutors.CMDShell;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CMDShell reader = null;
        try {
            reader = CMDShell.getInstance();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        try {
            reader.run();
        } catch (FieldException e) {
            System.out.println("Problems with file contains: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problems with file: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Some problems with security: " + e.getMessage());
        }
    }
}
