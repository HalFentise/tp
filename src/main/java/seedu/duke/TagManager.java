package seedu.duke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TagManager {
    private HashSet<String> predefinedTags;
    private ArrayList<String> customTags;

    // constructor
    public TagManager() {
        this.predefinedTags = new HashSet<>(Arrays.asList(
                "Food", "Shopping", "Groceries", "Bills", "Entertainment", "Travel"
        ));
        this.customTags = new ArrayList<>();
    }

    // add custom tag
    public boolean addTag(String tag) {
        if (predefinedTags.contains(tag)) {
            System.out.println("Tag added from predefined tags: " + tag);
            return true;
        } else {
            if (!customTags.contains(tag)) {
                customTags.add(tag);
                System.out.println("Custom tag added: " + tag);
                return true;
            } else {
                System.out.println("Tag already exists in custom tags: " + tag);
                return false;
            }
        }
    }

    // delete custom tag
    public boolean removeCustomTag(String tag) {
        if (customTags.contains(tag)) {
            customTags.remove(tag);
            System.out.println("Custom tag removed: " + tag);
            return true;
        } else {
            System.out.println("Tag not found in custom tags: " + tag);
            return false;
        }
    }

    // access to all tags
    public List<String> getAllTags() {
        List<String> allTags = new ArrayList<>(predefinedTags);
        allTags.addAll(customTags);
        return allTags;
    }
}
