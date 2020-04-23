package com.lagou.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converter<S, T>
 *     S: source 源数据的数据类型
 *     T: target 目标数据的数据类型
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            //这里为了解决url中传入的日期格式不属于yyyy-MM-dd时，在catch中再次进行转换
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat1.parse(s);
                return parse;
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
        return null;
    }
}
