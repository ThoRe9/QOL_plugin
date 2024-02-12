package net.okuri.qol.alcohol.taste;

import java.util.ArrayList;

/**
 * Taste を一元的に管理するシングルトンクラスです。
 * Taste を継承したクラスをここに登録すると使えます。
 */
public class TasteController {
    private static final TasteController instance = new TasteController();
    private static final ArrayList<Taste> tastes = new ArrayList<>();

    private TasteController() {
    }


    public static TasteController getController() {
        return instance;
    }

    /**
     * Taste を登録します。
     *
     * @param taste 　登録するTaste
     */
    public void registerTaste(Taste taste) {
        tastes.add(taste);
    }

    /**
     * 登録されたTasteを取得します。
     *
     * @param ID 取得するTasteのID (すべて小文字)
     * @return 取得したTaste
     */
    public Taste getTaste(String ID) {
        for (Taste taste : tastes) {

            if (taste.getID().equals(ID)) {
                return taste;
            }
        }
        return null;
    }

}
