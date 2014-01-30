package kendzi.math.geometry.skeleton.utils;

public class ValidateUtil {
    public static void validateNotNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalStateException("object can't be null: " + msg);
        }
    }

    public static void validateNull(Object obj, String msg) {
        if (obj != null) {
            throw new IllegalStateException("object should be null: " + msg);
        }
    }

}
