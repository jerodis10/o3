package com.o3.tax.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberUtilTest {

    @Test
    @DisplayName("BigDecimal 타입 숫자 포맷 적용 테스트")
    void numberFormatter() {
        // given
        String num1 = NumberUtil.numberFormatter(BigDecimal.valueOf(10L));
        String num2 = NumberUtil.numberFormatter(BigDecimal.valueOf(1_000L));
        String num3 = NumberUtil.numberFormatter(BigDecimal.valueOf(100_000L));
        String num4 = NumberUtil.numberFormatter(BigDecimal.valueOf(10_000_000L));
        String num5 = NumberUtil.numberFormatter(new BigDecimal("10.1346"));
        String num6 = NumberUtil.numberFormatter(new BigDecimal("1002.5"));
        String num7 = NumberUtil.numberFormatter(new BigDecimal("1000000.97921"));

        // then
        assertThat(num1).isEqualTo("10");
        assertThat(num2).isEqualTo("1,000");
        assertThat(num3).isEqualTo("100,000");
        assertThat(num4).isEqualTo("10,000,000");
        assertThat(num5).isEqualTo("10");
        assertThat(num6).isEqualTo("1,002");
        assertThat(num7).isEqualTo("1,000,000");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "1000", "1000000", "1,000", "10,000,000", "10.1346", "1,002.5", "1,000,000.97921"})
    @DisplayName("포맷 적용된 문자열을 숫자 타입으로 변환")
    void success_whenRightNum(String s) {
        assertThat(NumberUtil.parseBigDecimal(s)).isInstanceOf(BigDecimal.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "1a", "1a1", "1 a", "1  ", "ewr34", "as9223372036854775807000df"})
    @DisplayName("BigDecimal 타입으로 변경할 수 없는 값 입력시 예외 발생")
    void throwException_whenWrongNum(String s) {
        assertThatThrownBy(() -> NumberUtil.parseBigDecimal(s)).isInstanceOf(NumberFormatException.class);
    }

}