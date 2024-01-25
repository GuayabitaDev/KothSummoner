package me.guayabita.random;

import me.guayabita.random.utils.Cooldown;
import me.guayabita.random.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	private FileConfig settingsFile;

	@Override
	public void onEnable() {
		settingsFile = new FileConfig(this, "settings.yml");
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	private void onInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = event.getItem();
			if (item == null || !item.getType().equals(Material.REDSTONE_TORCH_ON)) return;
			if (Cooldown.hasCooldown("Koth", event.getPlayer())) {
				event.getPlayer().sendMessage(settingsFile.getString("COOLDOWN-MESSAGE")
						.replace("{time}", Cooldown.getCooldown("Koth", event.getPlayer())));
			} else {
				Cooldown.setCooldown("Koth", event.getPlayer(), settingsFile.getInt("ITEM-COOLDOWN") * 1000L);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "koth start " + settingsFile.getString("KOTH-NAME") + " " + settingsFile.getInt("KOTH-DURATION" + "s"));

			}
		}
	}
}
