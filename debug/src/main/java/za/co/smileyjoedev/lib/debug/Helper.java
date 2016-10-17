package za.co.smileyjoedev.lib.debug;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for helper functions so they don't get in the way of external calls.
 *
 * Created by cody on 2016/10/15.
 */

public class Helper {

    private static final String LINE_BREAK = "\n";
    private static final String TEXT_NULL = "--NULL--";
    private static final String TEXT_UNKNOWN = "--UNKNOWN TYPE--";
    private static final String TEXT_FALSE = "FALSE";
    private static final String TEXT_TRUE = "TRUE";
    private static final String TEXT_BLANK = "--BLANK--";
    private static final String TEXT_DEFAULT_MESSAGE = "********************";
    private static final String TEXT_GLUE = ": ";

    /**
     * Converts an object to a String formatted to be output with logcat.
     * <p>
     * The following defaults apply.
     * <ul>
     *     <li>null is output as '--NULL--'</li>
     *     <li>any exception will output '--UNKNOWN TYPE--'</li>
     *     <li>toString() is called on any object that is not specifically handled</li>
     *     <li>if the resulting string is empty '--BLANK--' is returned to show something was there</li>
     * </ul>
     *
     * @param obj the object to convert
     * @return the formatted string of the object
     */
    private static String convertToString(Object obj) {
        String message = "";

        try {
            if (obj instanceof String) {
                message = (String) obj;
            } else if (obj instanceof Boolean) {
                if ((Boolean) obj) {
                    message = TEXT_TRUE;
                } else {
                    message = TEXT_FALSE;
                }
            } else if (obj instanceof Integer) {
                message = Integer.toString((Integer) obj);
            } else if (obj instanceof Long) {
                message = Long.toString((Long) obj);
            } else if (obj instanceof ArrayList) {
                for (int i = 0; i < ((ArrayList) obj).size(); i++) {
                    message += LINE_BREAK + "[" + i + "]: "
                            + convertToString(((ArrayList) obj).get(i));
                }

                message += LINE_BREAK;
            } else if (obj instanceof Object[]) {
                boolean first = true;
                for (int i = 0; i < ((Object[]) obj).length; i++) {
                    if (first) {
                        first = false;
                        message += LINE_BREAK;
                    } else {
                        message += "--";
                    }
                    message += "[" + i + "]: "
                            + convertToString(((Object[]) obj)[i]);
                }

                message += LINE_BREAK;
            } else if (obj.getClass().isArray()) {
                try {
                    message = LINE_BREAK + Arrays.asList(obj).toString()
                            + LINE_BREAK;
                } catch (Exception e) {
                    message = TEXT_UNKNOWN;
                }
            } else if (obj == null) {
                message = TEXT_NULL;
            } else {
                try {
                    message = obj.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    message = TEXT_UNKNOWN;
                }
            }
        } catch (NullPointerException e) {
            message = TEXT_NULL;
        }

        if (message.trim().equals("")) {
            message = TEXT_BLANK;
        }

        return message;
    }

    /**
     * Takes an undefined number of parameters and formats them into a String to be output
     * via logcat.
     * <p>
     * The following rules apply
     * <ul>
     *     <li>All params are concatenated together with ': '</li>
     *     <li>If there are no params a line of '*'s is returned</li>
     * </ul>
     *
     * @param params list of objects to be combined to a single formatted string.
     * @return string representation of the objects
     */
    public static String handleMessage(Object... params) {
        String message = "";
        boolean first = true;

        if (params == null || params.length == 0) {
            message = TEXT_DEFAULT_MESSAGE;
            return message;
        }

        for (int i = 0; i < params.length; i++) {
            Object param = params[i];

            if (param instanceof ArrayList || param instanceof Object[]) {
                first = true;
            }

            if (first) {
                first = false;
            } else {
                message += TEXT_GLUE;
            }

            // I don't remember why this happens ...
            if (param instanceof ArrayList || param instanceof Object[]) {
                first = true;
            }

            message += convertToString(param);
        }

        return message;
    }

}
