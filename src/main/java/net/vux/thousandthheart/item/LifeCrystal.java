package net.vux.thousandthheart.item;

import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vux.thousandthheart.util.ModConfig;
import net.vux.thousandthheart.util.ModPlayerHealth;

import java.util.Objects;

public class LifeCrystal extends Item {

  public LifeCrystal(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand hand) {
    if (level.isClientSide()) {return InteractionResult.PASS;}

    ModConfig CONFIG = new ModConfig();

    MinecraftServer minecraftServer = level.getServer();
    ModPlayerHealth modPlayerHealth = ModPlayerHealth.get(minecraftServer);
    int currentMaxHealth = modPlayerHealth.getMaxHealth(player.getUUID());
    if (currentMaxHealth >= CONFIG.maxHeartPoints) {return InteractionResult.PASS;}

    int newMaxHealth = currentMaxHealth + 2;
    final ItemStack stack = player.getItemInHand(hand);
    stack.consume(1, player);
    modPlayerHealth.setMaxHeath(player.getUUID(), newMaxHealth);
    Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(newMaxHealth);
    player.heal(5f);
    player.awardStat(Stats.ITEM_USED.get(this));
    return InteractionResult.SUCCESS;
  }
}
