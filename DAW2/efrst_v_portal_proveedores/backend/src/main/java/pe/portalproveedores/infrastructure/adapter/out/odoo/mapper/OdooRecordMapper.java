package pe.portalproveedores.infrastructure.adapter.out.odoo.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OdooRecordMapper {

    private OdooRecordMapper() {}

    public static Map<String, Object> toMap(JsonNode node) {
        Map<String, Object> map = new HashMap<>();
        if (node == null || !node.isObject()) {
            return map;
        }
        node.fields().forEachRemaining(entry -> {
            JsonNode value = entry.getValue();
            if (value.isNull()) {
                map.put(entry.getKey(), null);
            } else if (value.isBoolean()) {
                map.put(entry.getKey(), value.booleanValue());
            } else if (value.isInt()) {
                map.put(entry.getKey(), value.intValue());
            } else if (value.isLong()) {
                map.put(entry.getKey(), value.longValue());
            } else if (value.isDouble() || value.isFloat()) {
                map.put(entry.getKey(), value.decimalValue());
            } else if (value.isTextual()) {
                map.put(entry.getKey(), value.textValue());
            } else if (value.isArray()) {
                map.put(entry.getKey(), toList(value));
            } else {
                map.put(entry.getKey(), value.toString());
            }
        });
        return map;
    }

    public static List<Map<String, Object>> toMapList(JsonNode arrayNode) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (arrayNode == null || !arrayNode.isArray()) {
            return list;
        }
        arrayNode.forEach(node -> list.add(toMap(node)));
        return list;
    }

    private static List<Object> toList(JsonNode arrayNode) {
        List<Object> list = new ArrayList<>();
        arrayNode.forEach(node -> {
            if (node.isTextual()) {
                list.add(node.textValue());
            } else if (node.isNumber()) {
                list.add(node.decimalValue());
            } else if (node.isBoolean()) {
                list.add(node.booleanValue());
            } else {
                list.add(node.toString());
            }
        });
        return list;
    }

    public static String getString(Map<String, Object> record, String field) {
        Object value = record.get(field);
        if (value == null) {
            return null;
        }
        if (value instanceof List<?> list && !list.isEmpty()) {
            return String.valueOf(list.get(1));
        }
        return String.valueOf(value);
    }

    public static Integer getInt(Map<String, Object> record, String field) {
        Object value = record.get(field);
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof List<?> list && !list.isEmpty()) {
            Object id = list.get(0);
            if (id instanceof Number number) {
                return number.intValue();
            }
        }
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
