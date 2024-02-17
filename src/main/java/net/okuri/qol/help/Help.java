package net.okuri.qol.help;

import com.google.gson.Gson;
import net.okuri.qol.QOL;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Help {
    private static Map<String, Page> pages = new HashMap<>();

    public static void addPage(Page page) {
        pages.put(page.getTitle(), page);
    }

    public static Page getPage(String title) {
        return pages.get(title);
    }

    public static Map<String, Page> getPages() {
        return pages;
    }

    public static void deletePage(String title) {
        pages.remove(title);
    }

    public static void updatePage(String title, Page page) {
        pages.replace(title, page);
    }

    public static void savePage() throws IOException {
        Gson gson = new Gson();
        File file = new File(QOL.getPlugin().getDataFolder().getAbsolutePath() + "/help.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        Pages pages = new Pages();

        for (Page page : Help.getPages().values()) {
            pages.getPages().add(page);
        }
        gson.toJson(pages, writer);
        writer.flush();
        writer.close();
        Bukkit.getLogger().info("Saved help.json");
    }

    public static void loadPage() throws IOException {
        Gson gson = new Gson();
        File file = new File(QOL.getPlugin().getDataFolder().getAbsolutePath() + "/help.json");
        if (!file.exists()) {
            file.getParentFile().mkdir();
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            gson.toJson(pages, writer);
            writer.flush();
            writer.close();
            Bukkit.getLogger().info("Created help.json");
        }
        Reader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
        Pages pages = gson.fromJson(reader, Pages.class);
        Help.pages.clear();
        for (Page page : pages.getPages()) {
            Help.pages.put(page.getTitle(), page);
        }
        reader.close();

        Bukkit.getLogger().info("Loaded help.json");
    }

    public static void setDefaultPages() throws IOException {
        File file = new File(QOL.getPlugin().getDataFolder().getAbsolutePath() + "/help.json");
        if (file.exists()) {
            file.delete();
        }

        // resource/help.jsonをコピーする
        file.getParentFile().mkdir();
        file.createNewFile();
        InputStream inputStream = QOL.getPlugin().getResource("help.json");
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        Bukkit.getLogger().info("Created help.json");
        loadPage();


    }

}
