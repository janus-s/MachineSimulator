package services;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * User: janus
 * Date: 12-11-03
 * Time: 21:19
 */
public class BundleService {

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    public static String message(String key) {
        return bundle.getString(key);
    }

    public static String message(String key, Object... params) {
        String msg = bundle.getString(key);
        return MessageFormat.format(msg, params);
    }

}
