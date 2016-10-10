package com.fcu.gtml.edx.utils;

public class JsonParserConfig {
    public static void config() {
        JsonParser.allowSingleQuote();
        JsonParser.allowUnquoteControlChars();
        //JsonParser.allowComments();
        //JsonParser.allowBackslashEscapingAnyCharacter();
    }
}
