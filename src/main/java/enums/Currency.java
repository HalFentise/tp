package enums;

/**
 * Represents supported currencies with their full names and exchange rates relative to SGD.
 * Provides methods for currency conversion and lookup by full name.
 */
public enum Currency {
    USD("USD", 0.74),
    SGD("SGD", 1),
    CNY("CNY", 5.41),
    EUR("EUR", 0.69),
    JPY("JPY", 111.14),
    GBP("GBP", 0.58);

    private final String fullName;
    private final double rate;

    /**
     * Constructs a Currency enum with full name and exchange rate to SGD.
     *
     * @param fullName Full name of the currency.
     * @param rate     Conversion rate to Singapore Dollar (SGD).
     */
    Currency(String fullName, double rate) {
        this.fullName = fullName;
        this.rate = rate;
    }

    /**
     * Returns the full name of the currency.
     *
     * @return Full name of the currency.
     */
    String getFullName() {
        return fullName;
    }

    /**
     * Returns the exchange rate to SGD.
     *
     * @return Exchange rate of this currency to SGD.
     */
    public double getRate() {
        return rate;
    }

    /**
     * Converts a monetary amount from this currency to the target currency.
     *
     * @param money  Amount in this currency.
     * @param target Target currency to convert to.
     * @return Converted amount in the target currency.
     */
    public double convertTo(double money, Currency target) {
        double sgMoney = money / rate;
        return target.getRate() * sgMoney;
    }

    /**
     * Returns the full name of the currency.
     *
     * @return Full name of this currency.
     */
    @Override
    public String toString() {
        return fullName;
    }

    /**
     * Retrieves a Currency enum by its full name.
     *
     * @param fullName Full name of the currency to match.
     * @return Corresponding Currency enum.
     * @throws IllegalArgumentException if no match is found.
     */
    public static Currency valueOfFullName(String fullName) {
        for (Currency currency : Currency.values()) {
            if (currency.getFullName().equals(fullName)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Can not load the following currency: " + fullName);
    }
}
