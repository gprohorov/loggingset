/*
  @author   george
  @project   loggingset
  @class  Main
  @version  1.0.0 
  @since 01.10.22 - 17.50
*/

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n");
        log.log(MyLevel.DEBUG, "debug level");
        Level debug = Level.parse("DEBUG");
        log.log(debug, "my debug message");
    }


    public static void main(String[] args) throws IllegalAccessException {
        System.out.println("Hello Logging!");
//        log.log(MyLevel.DEBUG, "debug level");
//        Level debug = Level.parse("DEBUG");
//        log.log(debug, "my debug message");
        setLevel(Level.ALL);
        Set<Level> levels = getAllLevels();
      //  levels.add(MyLevel.DEBUG);
        int i = 1;
        for (Level level : levels) {
        log.log(level, level.getName() + " - " + (i++));
        }
        log.finest("---------------------");
        log.setLevel(Level.FINE); //  take off FINEST and FINER levels
        log.finest("2.finest");
        log.finer("3.finer");
        log.fine("4.fine");
        log.config("5.config");
        log.info("6.info");
        log.warning("7.warning");
        log.severe("8.severe");

    }

    public static Set<Level> getAllLevels() throws IllegalAccessException {
        Class<Level> levelClass = Level.class;

        Set<Level> allLevels = new TreeSet<>(
                Comparator.comparingInt(Level::intValue));

        for (Field field : levelClass.getDeclaredFields()) {
            if (field.getType() == Level.class) {
                allLevels.add((Level) field.get(null));
            }
        }
        return allLevels;
    }

    public static void setLevel(Level targetLevel) {
        Logger root = Logger.getLogger("");

        root.setLevel(targetLevel);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(targetLevel);
        }
        System.out.println("level set: " + targetLevel.getName());
    }
}
class MyLevel extends Level {
    public static final Level DEBUG = new MyLevel("DEBUG", Level.FINE.intValue() + 1);

    public MyLevel(String name, int value) {
        super(name, value);
    }
}
