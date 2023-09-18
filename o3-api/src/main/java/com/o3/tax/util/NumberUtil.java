package com.o3.tax.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class NumberUtil {

    public String numberFormatter(BigDecimal number) {
        Locale locale = Locale.getDefault();
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        return formatter.format(number.setScale(0, RoundingMode.FLOOR));
    }

    public BigDecimal parseBigDecimal(String formattedNumber) {
        if (StringUtils.hasText(formattedNumber)) {
            return new BigDecimal(formattedNumber.replace(",", ""));
        }
        return BigDecimal.ZERO;
    }

}
