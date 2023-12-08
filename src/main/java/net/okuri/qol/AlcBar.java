package net.okuri.qol;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AlcBar extends BukkitRunnable {
    private Player player;
    private Audience audience;
    private BossBar bossBar;

    public AlcBar(Player player) {
        this.audience = Audience.audience(player);
        this.player = player;
        this.bossBar = BossBar.bossBar(
                Component.text("Alcohol Level").decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_GREEN),
                1.0f,
                BossBar.Color.GREEN,
                BossBar.Overlay.PROGRESS
        );
    }

    @Override
    public void run() {
        if (PDCC.has(player, PDCKey.HAS_ALC_BAR)) {
            if (PDCC.get(player, PDCKey.HAS_ALC_BAR)) {
                if (!PDCC.has(player, PDCKey.ALCOHOL_LEVEL)) {
                    audience.hideBossBar(bossBar);
                    return;
                }
                double alcLv = PDCC.get(player, PDCKey.ALCOHOL_LEVEL);
                bossBar.progress((float) (alcLv / 0.4));

                if (alcLv >= 0.02 && alcLv < 0.05) {
                    bossBar.color(BossBar.Color.BLUE);
                } else if (alcLv >= 0.05 && alcLv < 0.10) {
                    bossBar.color(BossBar.Color.GREEN);
                } else if (alcLv >= 0.10 && alcLv < 0.15) {
                    bossBar.color(BossBar.Color.YELLOW);
                } else if (alcLv >= 0.15 && alcLv < 0.30) {
                    bossBar.color(BossBar.Color.RED);
                } else if (alcLv >= 0.30 && alcLv < 0.40) {
                    bossBar.color(BossBar.Color.PURPLE);
                }

                audience.showBossBar(bossBar);
                return;
            }
        }
        audience.hideBossBar(bossBar);
        this.cancel();
    }
}
