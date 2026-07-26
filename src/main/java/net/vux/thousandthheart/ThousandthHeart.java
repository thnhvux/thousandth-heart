package net.vux.thousandthheart;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.Identifier;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.vux.thousandthheart.item.ModItems;
import net.vux.thousandthheart.util.ModConfig;
import net.vux.thousandthheart.util.ModPlayerHealth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Objects;

public class ThousandthHeart implements ModInitializer {
	public static final String MOD_ID = "thousandthheart";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ModConfig CONFIG = new ModConfig();

	@Override
	public void onInitialize() {
		CONFIG = ModConfig.createToml(Paths.get("config"), "", "thousandth-heart", ModConfig.class);
		ModItems.registerModItems();

		final int minHealthPoints = CONFIG.minHeartPoints >= 1 ? CONFIG.minHeartPoints : 10;
		ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> {
			MinecraftServer minecraftServer = newPlayer.level().getServer();
			ModPlayerHealth modPlayerHealth = ModPlayerHealth.get(minecraftServer);
			int oldMaxHealth = modPlayerHealth.getMaxHealth(oldPlayer.getUUID());
			int newMaxHealth = Math.max(minHealthPoints, oldMaxHealth - 2);
			if (!alive) modPlayerHealth.setMaxHeath(newPlayer.getUUID(), newMaxHealth);
			Objects.requireNonNull(newPlayer.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(newMaxHealth);
		}));
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
