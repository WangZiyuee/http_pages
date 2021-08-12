package cc.sivir.errorpagegenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 序列化别名{@link com.fasterxml.jackson.annotation.JsonProperty}
 * 反序列化别名{@link com.fasterxml.jackson.annotation.JsonAlias}
 *
 * @author qingke
 * @date 2021-07-26 15:29
 **/
public class JacksonUtil {

    private static final ObjectMapper MAPPER;

    static {
        ObjectMapper objectMapper = new ObjectMapper();

        // 关闭序列化日期为时间戳配置
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 关闭无访问器异常抛出
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 关闭反序列化未知属性解析异常抛出
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 序列化Long类型转换String 防止在JS中丢失精度
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        // 日期序列化设置
        String defaultDateTimeFormatterPattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(defaultDateTimeFormatterPattern);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat(defaultDateTimeFormatterPattern)));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        objectMapper.registerModule(javaTimeModule);

        MAPPER = objectMapper;
    }

    private JacksonUtil() {
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    public static JsonNode parseJsonNode(String content)
            throws JsonProcessingException {
        return MAPPER.readTree(content);
    }

    public static <T> T parseJavaObject(String content, Class<T> tClass)
            throws JsonProcessingException {
        return MAPPER.readValue(content, tClass);
    }

    public static String toJsonString(Object object)
            throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T transferToJavaObject(TreeNode jsonNode, Class<T> tClass)
            throws JsonProcessingException {
        return MAPPER.treeToValue(jsonNode, tClass);
    }

    public static JsonNode transferToJsonNode(Object object) {
        return MAPPER.valueToTree(object);
    }

    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(MAPPER.getNodeFactory());
    }

    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(MAPPER.getNodeFactory());
    }
}
