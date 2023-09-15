package com.o3.tax.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class NumberUtil {

    public String numberFormatter(Long number) {
        Locale locale = Locale.getDefault();
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        return formatter.format(number);
    }

    public long parseLong(String formattedNumber) {
        if (StringUtils.hasText(formattedNumber)) {
            return Long.parseLong(formattedNumber.replace(",", ""));
        }
        return 0L;
    }

}
