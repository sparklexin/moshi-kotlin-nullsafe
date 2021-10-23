package xin.sparkle.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * {@link com.squareup.moshi.StandardJsonAdapters}
 */
public class NullSafeStandardJsonAdapters {
    public static final JsonAdapter.Factory FACTORY = new JsonAdapter.Factory() {
        @Override
        public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            if (!annotations.isEmpty()) return null;
            if (type == boolean.class) return BOOLEAN_JSON_ADAPTER;
            if (type == byte.class) return BYTE_JSON_ADAPTER;
            if (type == char.class) return CHARACTER_JSON_ADAPTER;
            if (type == double.class) return DOUBLE_JSON_ADAPTER;
            if (type == float.class) return FLOAT_JSON_ADAPTER;
            if (type == int.class) return INTEGER_JSON_ADAPTER;
            if (type == long.class) return LONG_JSON_ADAPTER;
            if (type == short.class) return SHORT_JSON_ADAPTER;
            return null;
        }
    };

    private static final String ERROR_FORMAT = "Expected %s but was %s at path %s";

    static int rangeCheckNextInt(JsonReader reader, String typeMessage, int min, int max)
            throws IOException {
        int value = reader.nextInt();
        if (value < min || value > max) {
            throw new JsonDataException(
                    String.format(ERROR_FORMAT, typeMessage, value, reader.getPath()));
        }
        return value;
    }

    static final JsonAdapter<Boolean> BOOLEAN_JSON_ADAPTER = new JsonAdapter<Boolean>() {
        @Override
        public Boolean fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return false;
            } else {
                return reader.nextBoolean();
            }
        }

        @Override
        public void toJson(JsonWriter writer, Boolean value) throws IOException {
            writer.value(value != null && value);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Boolean)";
        }
    };

    static final JsonAdapter<Byte> BYTE_JSON_ADAPTER = new JsonAdapter<Byte>() {
        @Override
        public Byte fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return (byte) 0;
            } else {
                return (byte) rangeCheckNextInt(reader, "a byte", Byte.MIN_VALUE, 0xff);
            }
        }

        @Override
        public void toJson(JsonWriter writer, Byte value) throws IOException {
            writer.value(value != null ? value.intValue() & 0xff : 0);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Byte)";
        }
    };

    static final JsonAdapter<Character> CHARACTER_JSON_ADAPTER = new JsonAdapter<Character>() {
        @Override
        public Character fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return Character.MIN_VALUE;
            } else {
                String value = reader.nextString();
                if (value.length() > 1) {
                    throw new JsonDataException(
                            String.format(ERROR_FORMAT, "a char", '"' + value + '"', reader.getPath()));
                }
                return value.charAt(0);
            }
        }

        @Override
        public void toJson(JsonWriter writer, Character value) throws IOException {
            writer.value(value != null ? value.toString() : Character.toString(Character.MIN_VALUE));
        }


        @Override
        public String toString() {
            return "JsonAdapter(Character)";
        }
    };

    static final JsonAdapter<Double> DOUBLE_JSON_ADAPTER = new JsonAdapter<Double>() {
        @Override
        public Double fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return 0.0;
            } else {
                return reader.nextDouble();
            }
        }

        @Override
        public void toJson(JsonWriter writer, Double value) throws IOException {
            writer.value(value != null ? value : 0.0);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Double)";
        }
    };

    static final JsonAdapter<Float> FLOAT_JSON_ADAPTER = new JsonAdapter<Float>() {
        @Override
        public Float fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return 0f;
            } else {
                float value = (float) reader.nextDouble();
                // Double check for infinity after float conversion; many doubles > Float.MAX
                if (!reader.isLenient() && Float.isInfinite(value)) {
                    throw new JsonDataException(
                            "JSON forbids NaN and infinities: " + value + " at path " + reader.getPath());
                }
                return value;
            }
        }

        @Override
        public void toJson(JsonWriter writer, Float value) throws IOException {
            // Manual null pointer check for the float.class adapter.
            // Use the Number overload so we write out float precision instead of double precision.
            writer.value(value != null ? value : 0f);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Float)";
        }
    };

    static final JsonAdapter<Integer> INTEGER_JSON_ADAPTER = new JsonAdapter<Integer>() {
        @Override
        public Integer fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return 0;
            } else {
                return reader.nextInt();
            }
        }

        @Override
        public void toJson(JsonWriter writer, Integer value) throws IOException {
            writer.value(value != null ? value : 0);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Integer)";
        }
    };

    static final JsonAdapter<Long> LONG_JSON_ADAPTER = new JsonAdapter<Long>() {
        @Override
        public Long fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return 0L;
            } else {
                return reader.nextLong();
            }
        }

        @Override
        public void toJson(JsonWriter writer, Long value) throws IOException {
            writer.value(value != null ? value : 0L);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Long)";
        }
    };

    static final JsonAdapter<Short> SHORT_JSON_ADAPTER = new JsonAdapter<Short>() {
        @Override
        public Short fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return 0;
            } else {
                return (short) rangeCheckNextInt(reader, "a short", Short.MIN_VALUE, Short.MAX_VALUE);
            }
        }

        @Override
        public void toJson(JsonWriter writer, Short value) throws IOException {
            writer.value(value != null ? value.intValue() : 0);
        }


        @Override
        public String toString() {
            return "JsonAdapter(Short)";
        }
    };
}
