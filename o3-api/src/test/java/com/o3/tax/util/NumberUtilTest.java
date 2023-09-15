package com.o3.tax.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberUtilTest {

    @Test
    @DisplayName("Long 타입 숫자 포맷 적용 테스트")
    void success_whenParseLong() {
        // given
        String num1 = NumberUtil.numberFormatter(10L);
        String num2 = NumberUtil.numberFormatter(1_000L);
        String num3 = NumberUtil.numberFormatter(100_000L);
        String num4 = NumberUtil.numberFormatter(10_000_000L);

        // then
        assertThat(num1).isEqualTo("10");
        assertThat(num2).isEqualTo("1,000");
        assertThat(num3).isEqualTo("100,000");
        assertThat(num4).isEqualTo("10,000,000");
    }

}