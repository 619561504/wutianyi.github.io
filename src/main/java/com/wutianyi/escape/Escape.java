/**
 * 2015年10月8日hanjiewu
 */
package com.wutianyi.escape;

import org.apache.commons.lang.StringUtils;

import static java.lang.System.out;

/**
 * @author hanjiewu
 */
public class Escape {
    public static String escapeSql(String sql) {

        if (StringUtils.isBlank(sql)) {
            return sql;
        }

        int length = sql.length();
        int newLength = length;

        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            switch (c) {
                case '\\':
                case '\"':
                case '\'':
                case '\r':
                case '\n':
                case '\t':
                case '\b':
                case '\0': {
                    newLength += 1;
                }
                break;
            }
        }
        if (length == newLength) {
            return sql;
        }
        StringBuffer sb = new StringBuffer(newLength);
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            switch (c) {
                case '\\': {
                    sb.append("\\\\");
                }
                break;
                case '\"': {
                    sb.append("\\\"");
                }
                break;
                case '\'': {
                    sb.append("\'\'");
                }
                break;
                case '\0': {
                    sb.append("\\0");
                }
                break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\u200B':// ZERO WIDTH SPACE
                case '\uFEFF':// ZERO WIDTH NO-BREAK SPACE
                    break;
                default: {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        out.println(escapeSql("ADSL_1M~!@#$%^&*'<>/?\\"));

        System.out.println("Hello world");
    }

}
