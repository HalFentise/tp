package enumstructure;

public enum Currency {
    USD("United State Dollar", 0.74),
    SGD("Singapore Dollar", 1),
    CNY("Chinese Yuan", 5.41),
    EUR("Euro", 0.69),
    JPY("Japanese Yen", 111.14),
    GBP("British Pound", 0.58);

    private final String fullName; // full name
    private final double rate;

    //Constructor
    Currency(String fullName, double rate) {
        this.fullName = fullName;
        this.rate = rate;
    }

    //Display full name
    String getFullName() {
        return fullName;
    }

    //Display rate
    public double getRate() {
        return rate;
    }

    //Convert method
    public double convertTo(double money, Currency target) {
        double sgMoney = money * 1/rate;
        return target.getRate() * sgMoney;
    }

    @Override
    public String toString() {
        return fullName;
    }

    public static Currency valueOfFullName(String fullName) {
        for (Currency currency : Currency.values()) {
            if (currency.getFullName().equals(fullName)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Can not load the following currency: " + fullName);
    }
}
