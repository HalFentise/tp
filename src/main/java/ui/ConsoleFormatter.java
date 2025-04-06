package ui;

public class ConsoleFormatter {
    private static final int DEFAULT_WIDTH = 121;

    public static void printLine() {
        System.out.println("+" + "-".repeat(DEFAULT_WIDTH - 2) + "+");
    }

    public static void printCenteredLine(String text) {
        int contentWidth = DEFAULT_WIDTH - 2;
        int padding = Math.max((contentWidth - text.length()) / 2, 0);
        StringBuilder line = new StringBuilder();
        line.append("|");
        line.append(" ".repeat(padding));
        line.append(text);
        line.append(" ".repeat(contentWidth - padding - text.length()));
        line.append("|");
        System.out.println(line);
    }

    public static void printLeftAlignedLine(String text) {
        final int DEFAULT_WIDTH = 121;
        final int contentWidth = DEFAULT_WIDTH - 2;
        final int indentSpaces = 14;
        final String indent = " ".repeat(indentSpaces);

        int firstLineContentWidth = contentWidth - 2;
        int otherLineContentWidth = contentWidth - 2 - indentSpaces;

        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        boolean firstLine = true;

        for (String word : words) {
            int maxWidth = firstLine ? firstLineContentWidth : otherLineContentWidth;
            if ((currentLine.length() + word.length() + 1) > maxWidth) {

                printFormattedLine(currentLine.toString(), firstLine, contentWidth, indent);
                currentLine = new StringBuilder(word); // 开新行
                firstLine = false;
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }

        printFormattedLine(currentLine.toString(), firstLine, contentWidth, indent);

        if (text.isEmpty()) {
            System.out.println("|" + " ".repeat(contentWidth) + "|");
        }
    }

    private static void printFormattedLine(String lineContent, boolean isFirstLine, int contentWidth, String indent) {
        StringBuilder line = new StringBuilder();
        line.append("| ");
        if (!isFirstLine) {
            line.append(indent);
        }
        line.append(lineContent);
        int padding = contentWidth - 1 - (isFirstLine ? 0 : indent.length()) - lineContent.length();
        line.append(" ".repeat(Math.max(0, padding)));
        line.append("|");
        System.out.println(line);
    }


    public static void printCenteredTitle(String title) {
        String bracketedTitle = "[ " + title + " ]";
        int dashCount = DEFAULT_WIDTH - bracketedTitle.length() - 2;
        int leftDash = dashCount / 2;
        int rightDash = dashCount - leftDash;

        StringBuilder line = new StringBuilder();
        line.append("+");
        line.append("-".repeat(leftDash));
        line.append(bracketedTitle);
        line.append("-".repeat(rightDash));
        line.append("+");

        System.out.println(line);
    }
}
