package com.eugenewyj.samples.springmvc.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @since 2017/9/14
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchPamas) {
        return StringUtils.capitalize(entity) + " 在参数{" + searchPamas + "}中不存在";
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1) {
            throw new IllegalArgumentException("参数entries数量必须为偶数！");
        }
        return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new, (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
    }
}
