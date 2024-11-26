package com.management.project.commons.database;

import com.management.project.commons.ConvertUtil;
import com.management.project.commons.PatternConstants;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class CommonMapper<T> {
    public Supplier<T> supplier;

    public CommonMapper(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T newInstance() {
        return supplier.get();
    }

    public T mapToObject(Object[] objArray) {
        T object = newInstance();
        Field[] fields = object.getClass().getDeclaredFields();
        int index = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (index < objArray.length && objArray[index] != null) {
                setValueToObject(object, field.getName(), objArray[index].toString());
            } else {
                setValueToObject(object, field.getName(), "");
            }
            index++;
        }
        return object;
    }

    public String setValueToObject(T obj, String fieldName, String value) {
        try {
            if (Objects.isNull(obj) || !StringUtils.hasLength(fieldName)) {
                return "";
            }
            Field f = obj.getClass().getDeclaredField(fieldName);

            f.setAccessible(true);

            try {
                if (Integer.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Integer.valueOf(value));
                } else if (Double.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Double.valueOf(value));
                } else if (BigDecimal.class.isAssignableFrom(f.getType())) {
                    f.set(obj, new BigDecimal(value));
                } else if (Long.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Long.valueOf(value));
                } else if (Float.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Float.valueOf(value));
                } else if (LocalDate.class.isAssignableFrom(f.getType())) {
                    f.set(obj, ConvertUtil.toLocalDate(value));
                } else if (LocalTime.class.isAssignableFrom(f.getType())) {
                    f.set(obj, ConvertUtil.toLocalTime(value, PatternConstants.LOCAL_TIME_FORMAT));
                } else if (Boolean.class.isAssignableFrom(f.getType())) {
                    if (!StringUtils.hasText(value)) {
                        f.set(obj, null);
                    } else if (Arrays.asList("1", "0", "true", "false", "x").contains(value.toLowerCase())) {
                        f.set(obj, "1".equals(value) || "true".equals(value.toLowerCase()) || "x".equals(value.toLowerCase()));
                    } else {
                        throw new RuntimeException("Boolean is invalid.[Field：" + fieldName + "; Value：" + value + "]");
                    }
                } else {
                    f.set(obj, !StringUtils.hasText(value) ? null : f.getType().cast(value)); //-->return null to by pass validation
                }
            } catch (Throwable e) {
                if (StringUtils.hasText(value)) {
                    throw e;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return fieldName;
        }

        return null;
    }

    public <T> List<T> mapToObjects(List<Object[]> objArrays) {
        List<T> resultList = new ArrayList<>();
        if (!objArrays.isEmpty()) {
            for (Object[] obj : objArrays) {
                T object = (T) mapToObject(obj);
                resultList.add(object);
            }
        } else {
            return resultList;
        }
        return resultList;
    }
}
