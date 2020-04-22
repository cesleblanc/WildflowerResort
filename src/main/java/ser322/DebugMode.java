package main.java.ser322;

public class DebugMode {
    private static boolean debug = false;

    public static void debug(String print){
        if(debug)
            System.out.println("[DEBUG] " + print);
    }
}
