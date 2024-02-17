package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Loreを生成するためのベースとなるクラスです。
 * 各種アイテムについてのLoreを生成するためのクラスはこのクラスを継承して書式を整えて、作成してください。
 */
public abstract class Lore {
    public static final TextColor BASE_COLOR = NamedTextColor.GRAY;
    public static final TextColor SUB_COLOR = NamedTextColor.DARK_GREEN;
    public static final TextColor ACCENT_COLOR = NamedTextColor.GOLD;
    public static final TextColor A_COLOR = NamedTextColor.RED;
    public static final TextColor B_COLOR = NamedTextColor.AQUA;
    public static final TextColor C_COLOR = NamedTextColor.YELLOW;
    public static final TextColor D_COLOR = NamedTextColor.GREEN;
    public static final TextColor IMPORTANT_COLOR = NamedTextColor.DARK_RED;
    public final int priority;
    public Component title;
    private ArrayList<Component> lore = new ArrayList<>();

    /**
     * Loreを生成するためのベースとなるクラスです。
     *
     * @param priority   このLoreの優先度
     * @param title      このLoreのタイトル
     * @param titleColor このLoreのタイトルの色
     */
    public Lore(int priority, String title, TextColor titleColor) {
        this.priority = priority;
        // 基本1行は30文字に。
        int separateLineLength = (28 - title.length()) / 2;
        this.title = Component.text(StringUtils.repeat("-", separateLineLength)).color(BASE_COLOR).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" " + title + " ").color(TextColor.color(titleColor)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD))
                .append(Component.text(StringUtils.repeat("-", separateLineLength)).color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
        if (title.length() % 2 == 1) {
            this.title = this.title.append(Component.text("-").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
        }
    }

    private String getFixedName(String name) {
        // すべて8文字にして返す
        return StringUtils.rightPad(name, 8);
    }

    void addInfoLore(String info) {
        lore.add(Component.text(info).color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
    }

    void addInfoLore(Component component) {
        lore.add(component);
    }

    /**
     * おおよそ0~1.0のパラメータをLoreに追加します。
     *
     * @param name  パラメータ名(半角英数字8文字以内)
     * @param color パラメータ名の色
     * @param param パラメータの値 (0.01ごとに"|" 10こで区切られる)
     */
    void addParamBarLore(String name, TextColor color, double param) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        String bar = StringUtils.repeat("|||||||||| ", (int) Math.floor(param * 10));
        bar = bar + StringUtils.repeat("|", (int) Math.floor((param * 100)) % 10);

        this.lore.add(Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(bar).color(color).decoration(TextDecoration.ITALIC, false)));
    }

    /**
     * おおよそ0~1.0のパラメータをLoreに追加します。
     *
     * @param name  パラメータ名(半角英数字8文字以内)
     * @param color パラメータ名の色
     * @param param パラメータの値
     */
    void addParamLore(String name, TextColor color, double param) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        this.lore.add(Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(String.format("%.2f", param)).color(color).decoration(TextDecoration.ITALIC, false)));
    }

    /**
     * おおよそ0~1.0のパラメータと、濃度をLoreに追加します。
     *
     * @param name  パラメータ名(半角英数字8文字以内)
     * @param color パラメータ名の色
     * @param param パラメータの値
     * @param all   全体量
     */
    void addParamLore(String name, TextColor color, double param, double all) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        this.lore.add(Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(String.format("%.2f", param)).color(color).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" / ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(String.format("%.2f", param / all * 100)).color(color).decoration(TextDecoration.ITALIC, false))
                .append(Component.text("%").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false)));
    }

    /**
     * おおよそ0~1.0のパラメータと、単位をLoreに追加します。
     *
     * @param name  パラメータ名(半角英数字8文字以内)
     * @param color パラメータ名の色
     * @param param パラメータの値
     * @param unit  単位
     */

    void addParamLore(String name, TextColor color, double param, String unit) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        this.lore.add(Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(String.format("%.2f", param)).color(color).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(unit).color(BASE_COLOR).decoration(TextDecoration.ITALIC, false)));
    }

    /**
     * 濃度をLoreに追加します。
     *
     * @param name    パラメータ名(半角英数字8文字以内)
     * @param color   パラメータ名の色
     * @param percent パラメータの値
     */
    void addPercentLore(String name, TextColor color, double percent) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        this.lore.add(Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(String.format("%.2f", percent)).color(color).decoration(TextDecoration.ITALIC, false))
                .append(Component.text("%").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false)));
    }

    /**
     * 倍率補正のパラメータをLoreに追加します。
     *
     * @param name  パラメータ名(半角英数字8文字以内)
     * @param color パラメータ名の色
     * @param param パラメータの値
     */
    void addAmpLore(String name, TextColor color, double param) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        assert param >= 0;
        this.lore.add(Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false))
                .append(Component.text("×" + String.format("%.2f", param)).color(color).decoration(TextDecoration.ITALIC, false)));
    }

    /**
     * 倍率補正のパラメータをLoreに追加します。
     *
     * @param name   パラメータ名(半角英数字8文字以内)
     * @param color  パラメータ名の色
     * @param params パラメータの値
     * @param units  パラメータの単位
     */
    void addDoubleArrayLore(String name, TextColor color, double[] params, String[] units) {
        assert name.length() <= 8;
        assert name.matches("[a-zA-Z0-9]+");
        assert params.length == units.length;
        Component comp = Component.text(getFixedName(name)).color(color).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(" : ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
        for (int i = 0; i < params.length; i++) {
            comp = comp.append(Component.text(String.format("%.2f", params[i])).color(color).decoration(TextDecoration.ITALIC, false))
                    .append(Component.text(units[i]).color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
            if (i != params.length - 1) {
                comp = comp.append(Component.text(" / ").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
            }
        }
        this.lore.add(comp);
    }

    void addSeparator() {
        this.lore.add(Component.text("--------------------").color(BASE_COLOR).decoration(TextDecoration.ITALIC, false));
    }

    public abstract void generateLore();

    public ArrayList<Component> getLore() {
        return lore;
    }

    void setLore(ArrayList<Component> lore) {
        this.lore = lore;
    }

    void addLore(Component component) {
        lore.add(component);
    }

    void removeLore(Component component) {
        lore.remove(component);
    }

    void clearLore() {
        lore.clear();
    }

}
