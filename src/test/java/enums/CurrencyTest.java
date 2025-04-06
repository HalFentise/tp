package enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    void testValueOfFullNameValid() {
        assertEquals(Currency.SGD, Currency.valueOfFullName("Singapore Dollar"));
        assertEquals(Currency.USD, Currency.valueOfFullName("United State Dollar"));
        assertEquals(Currency.CNY, Currency.valueOfFullName("Chinese Yuan"));
        assertEquals(Currency.EUR, Currency.valueOfFullName("Euro"));
        assertEquals(Currency.JPY, Currency.valueOfFullName("Japanese Yen"));
        assertEquals(Currency.GBP, Currency.valueOfFullName("British Pound"));
    }

    @Test
    void testValueOfFullNameInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Currency.valueOfFullName("Unknown Currency");
        });
        assertTrue(exception.getMessage().contains("Can not load the following currency"));
    }

    @Test
    void testConvertTo() {
        // SGD -> USD
        double converted = Currency.SGD.convertTo(100, Currency.USD);
        // SGD is base (1), USD rate is 0.74, so result should be 100 * 0.74
        assertEquals(74.0, converted, 0.0001);

        // USD -> CNY
        double converted2 = Currency.USD.convertTo(10, Currency.CNY);
        // USD (0.74) -> SGD -> CNY (5.41)
        double expected = (10 / 0.74) * 5.41;
        assertEquals(expected, converted2, 0.0001);
    }

    @Test
    void testToStringReturnsFullName() {
        assertEquals("Singapore Dollar", Currency.SGD.toString());
        assertEquals("United State Dollar", Currency.USD.toString());
    }

    @Test
    void testGetFullName() {
        assertEquals("Chinese Yuan", Currency.CNY.getFullName());
    }

    @Test
    void testGetRate() {
        assertEquals(111.14, Currency.JPY.getRate());
    }
}

