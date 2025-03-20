package seedu.duke;

public class Parser {
    private final TransactionManager transactionManager;

    public Parser(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void parseSearchType(String term) {
        try {
            transactionManager.searchTransaction(Integer.parseInt(term));
        } catch (NumberFormatException e) {
            transactionManager.searchTransaction(term);
        }
    }
}
