package org.common.utils;

public class JsonParserConfig {
    public static void config() {
        JsonParser.allowSingleQuote();
        JsonParser.allowUnquoteControlChars();
        JsonParser.allowComments();
        //JsonParser.allowBackslashEscapingAnyCharacter();
        //JsonParser.allowNONNumericNumbers();
        JsonParser.allowUnquotedFieldNames();
    }
}
