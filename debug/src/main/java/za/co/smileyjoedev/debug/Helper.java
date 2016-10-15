package za.co.smileyjoedev.debug;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cody on 2016/10/15.
 */

public class Helper {

    private static final String LINE_BREAK = "\n";

    private static String convertToString(Object obj) {
        String message = "";

        try {
            if (obj instanceof String) {
                message = (String) obj;
            } else if (obj instanceof Boolean) {
                if ((Boolean) obj) {
                    message = "TRUE";
                } else {
                    message = "FALSE";
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
                    // TODO: Find a way to hadle arrays of primitive type //
                    message = LINE_BREAK + Arrays.asList(obj).toString()
                            + LINE_BREAK;
                } catch (Exception e) {
                    message = "UNKNOWN TYPE";
                }
            } else if (obj == null) {
                message = "--NULL--";
            } else {
                try {
                    message = obj.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "UNKNOWN TYPE";
                }
            }
        } catch (NullPointerException e) {
            message = "--NULL--";
        }

        if (message.trim().equals("")) {
            message = "--BLANK--";
        }

        return message;
    }

    public static String handleMessage(Object... params) {
        String message = "";
        boolean first = true;

        if (params.length == 0) {
            message = "*******************";
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
                message += ": ";
            }

            if (param instanceof ArrayList || param instanceof Object[]) {
                first = true;
            }

            message += convertToString(param);
        }

        return message;
    }

}
