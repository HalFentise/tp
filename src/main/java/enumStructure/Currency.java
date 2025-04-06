package enumStructure;

public enum Currency {
    USD("USD", 0.74),
    SGD("SGD", 1),
    CNY("CNY", 5.41),
    EUR("Euro", 0.69),
    JPY("Japanese Yen", 111.14),
    GBP("British Pound", 0.58);

    private final String fullName;
    private double rate;

    // Constructor
    Currency(String fullName, double rate) {
        this.fullName = fullName;
        this.rate = rate;
    }

    // Display full name
    public String getFullName() {
        return fullName;
    }

    // Display rate
    public double getRate() {
        return rate;
    }

    // ✅ Setter for dynamic update
    public void setRate(double rate) {
        this.rate = rate;
    }

    // Convert method
    public double convertTo(double money, Currency target) {
        double sgMoney = money / rate;
        return target.getRate() * sgMoney;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
