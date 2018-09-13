package br.ufms.facom.home.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void encoderTest() {
        String encoded = Utils.getEncoder().encode("123");
        Assertions.assertThat(Utils.getEncoder().matches("123", encoded)).isEqualTo(true);
        Assertions.assertThat(Utils.getEncoder().matches("1234", encoded)).isEqualTo(false);
    }
}
