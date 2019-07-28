package eu.venthe.dddcore.api.exceptions.utils;

import org.apache.commons.lang3.StringUtils;

public final class MessageCode {
    private static final String CODE_DELIMITER = ".";

    public static String createMEssageCode(String... elements) {
        return StringUtils.join(elements, CODE_DELIMITER);
    }
}
