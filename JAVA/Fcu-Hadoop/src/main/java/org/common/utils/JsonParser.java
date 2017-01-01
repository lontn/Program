package org.common.utils;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonParser {
    private static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    static final void allowSingleQuote() {
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
    }

    static final void allowUnquoteControlChars() {
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    static final void allowComments() {
        mapper.configure(Feature.ALLOW_COMMENTS, true);
    }

    static final void allowBackslashEscapingAnyCharacter() {
        mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    }

    static final void allowNONNumericNumbers() {
        mapper.configure(Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
    }

    static final void allowUnquotedFieldNames() {
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static <T> T parse(String json, Class<T> clazz) throws ParseException {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ParseException("Parse JSON to [" + clazz.getName() + "] fail, " + e.getMessage(), json, e);
        }
    }

    public static <T> T parse(JsonNode json, Class<T> clazz) throws ParseException {
        try {
            return mapper.treeToValue(json, clazz);
        } catch (Exception e) {
            throw new ParseException("Parse JSON to [" + clazz.getName() + "] fail, " + e.getMessage(), json.toString(), e);
        }
    }

    public static HashMap<String, Object> parse(String json, TypeReference<HashMap<String, Object>> typeRef) throws ParseException {
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new ParseException("Parse JSON to [" + typeRef + "] fail, " + e.getMessage(), json, e);
        }
    }

    public static JsonNode parse(String json) throws ParseException {
        try {
            //mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
            //mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
            return mapper.readTree(json);
        } catch (Exception e) {
            throw new ParseException("parse JSON to JsonNode fail, " + e.getMessage(), json, e);
        }
    }

}
