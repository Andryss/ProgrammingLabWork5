//3119910

import MovieObjects.FieldException;
import ReadersExecutors.CMDShell;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CMDShell reader = CMDShell.getInstance();
        try {
            reader.run();
        } catch (FieldException e) {
            System.out.println("Problems with file contains: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problems with file: " + e.getMessage());
            e.printStackTrace();
        } catch (SecurityException e) {
            System.out.println("Some problems with security: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
