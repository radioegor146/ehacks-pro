/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

/**
 *
 * @author radioegor146
 */
public class ExceptionUtils {

    public static String getStringException(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");
        for (int i = 0; i < Math.min(4, e.getStackTrace().length); i++) {
            sb.append("at ").append(e.getStackTrace()[i].toString()).append("\n");
        }
        if (e.getStackTrace().length > 4) {
            sb.append("and ").append(e.getStackTrace().length - 4).append(" more...");
        }
        return sb.toString();
    }
}
