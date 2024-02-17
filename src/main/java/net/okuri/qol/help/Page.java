package net.okuri.qol.help;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class Page {
    private final String title;
    private final String description;
    private final ArrayList<ArrayList<HelpContent>> contents = new ArrayList<>();

    public Page(String title, String description) {
        this.title = title;
        this.description = description;
        contents.add(new ArrayList<>());
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Component> getContents(int page) {
        ArrayList<Component> strings = new ArrayList<>();
        for (HelpContent content : contents.get(page)) {
            strings.addAll(content.getContents());
        }
        return strings;
    }

    public void addContent(HelpContent content) {
        int cursor = contents.size() - 1;
        int c = 0;
        for (HelpContent h : contents.get(cursor)) {
            c += h.getContents().size();
        }
        if (c + content.getContentsSize() > 10) {
            HelpContent voidContent = new HelpContent();
            for (int i = 0; i < 10 - c; i++) {
                voidContent.addContent("");
            }
            contents.get(cursor).add(voidContent);
            contents.add(new ArrayList<>());
            cursor++;
        }
        contents.get(cursor).add(content);
    }

    public int getPages() {
        return contents.size();
    }

    public String getDescription() {
        return description;
    }


}
