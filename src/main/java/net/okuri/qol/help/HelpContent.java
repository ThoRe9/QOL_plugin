package net.okuri.qol.help;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.qolCraft.superCraft.SuperCraftController;
import net.okuri.qol.qolCraft.superCraft.SuperCraftRecipe;
import net.okuri.qol.superItems.SuperItemData;

import java.util.ArrayList;
import java.util.Map;

public class HelpContent {
    private final ArrayList<String> contents = new ArrayList<>();
    private String recipeID;

    public ArrayList<Component> getContents() {
        ArrayList<Component> contents = new ArrayList<>();
        for (String content : this.contents) {
            contents.add(Component.text(content).color(NamedTextColor.GRAY));
        }

        if (recipeID != null) {
            SuperCraftRecipe recipe = SuperCraftController.getRecipe(recipeID);
            String[] matrix = recipe.getShape();
            Map<Character, String> ingredients = new java.util.HashMap<>();
            Map<Character, SuperItemData> in = recipe.getIngredients();
            for (char c : in.keySet()) {
                ingredients.put(c, in.get(c).toString());
            }

            Component first = Component.text("定型レシピ : ").color(NamedTextColor.GRAY);

            ArrayList<Component> shapeComponents = new ArrayList<>();
            for (String row : matrix) {
                Component rowComponent = Component.empty();
                rowComponent = rowComponent.append(Component.text(" |").color(NamedTextColor.GRAY));
                for (char c : row.toCharArray()) {
                    if (c == ' ') {
                        rowComponent = rowComponent.append(Component.text("_").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));
                        rowComponent = rowComponent.append(Component.text(" ").color(NamedTextColor.GRAY));
                    } else {
                        rowComponent = rowComponent.append(Component.text(c).color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));
                        rowComponent = rowComponent.append(Component.text(" ").color(NamedTextColor.GRAY));
                    }
                }
                shapeComponents.add(rowComponent);
            }
            shapeComponents.set(1, shapeComponents.get(1)
                    .append(Component.text(" → ").color(NamedTextColor.GRAY)).append(Component.text(recipe.getResultData().toString()).color(NamedTextColor.GREEN)));

            ArrayList<Component> ingredientsComponents = new ArrayList<>();
            ingredientsComponents.add(Component.text("材料 : ").color(NamedTextColor.GRAY));
            for (char c : ingredients.keySet()) {
                Component ingredientComponent = Component.text(" |・").color(NamedTextColor.GRAY)
                        .append(Component.text(c).color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD))
                        .append(Component.text(" : ").color(NamedTextColor.GRAY))
                        .append(Component.text(ingredients.get(c)).color(NamedTextColor.GREEN));
                ingredientsComponents.add(ingredientComponent);
            }

            contents.add(first);
            contents.addAll(shapeComponents);
            contents.addAll(ingredientsComponents);
            contents.add(Component.text("----------").color(NamedTextColor.GRAY));
        }

        return contents;
    }

    public void addContent(String content) {
        if (recipeID != null) {
            if (contents.size() >= 2) {
                throw new IllegalStateException("10行以上(2行以上)の文字列をを1つのコンテンツに追加することはできません。(分割して登録してください)");
            }
        } else {
            if (contents.size() >= 10) {
                throw new IllegalStateException("10行以上の文字列をを1つのコンテンツに追加することはできません。(分割して登録してください)");
            }
        }

        contents.add(content);
    }

    public int getContentsSize() {
        int i = 0;
        if (recipeID != null) {
            i = 8;
        }
        return contents.size() + i;
    }

    public void setRecipeID(String recipeID) {
        if (this.recipeID == null) {
            if (contents.size() > 2) {
                throw new IllegalStateException("10行以上(2行以上)の文字列をを1つのコンテンツに追加することはできません。(分割して登録してください)");
            }
        }
        this.recipeID = recipeID;
    }

}
