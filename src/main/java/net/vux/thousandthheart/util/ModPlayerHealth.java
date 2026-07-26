package net.vux.thousandthheart.util;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.vux.thousandthheart.ThousandthHeart;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ModPlayerHealth extends SavedData {
  private final Map<UUID, Integer> maxHeathXMap = new HashMap<>();
  private static final Codec<Map<UUID, Integer>> MAP_CODEC = Codec.unboundedMap(
          Codec.STRING.xmap(UUID::fromString, UUID::toString),
          Codec.INT
  );
  private static final Codec<ModPlayerHealth> MOD_PLAYER_HEALTH_CODEC = MAP_CODEC.xmap(
          uuidIntegerMap -> {
            var data = new ModPlayerHealth();
            data.maxHeathXMap.putAll(uuidIntegerMap);
            return data;
          },
          data -> data.maxHeathXMap
  );

  private static final SavedDataType<ModPlayerHealth> TYPE = new SavedDataType<>(
          Identifier.fromNamespaceAndPath(ThousandthHeart.MOD_ID, "player_max_health"),
          ModPlayerHealth::new,
          MOD_PLAYER_HEALTH_CODEC,
          null
  );

  public static ModPlayerHealth get(MinecraftServer minecraftServer) {
    ServerLevel serverLevel = minecraftServer.getLevel(ServerLevel.OVERWORLD);
    return Objects.requireNonNull(serverLevel).getDataStorage().computeIfAbsent(TYPE);
  }

  public int getMaxHealth(UUID uuid) {return maxHeathXMap.getOrDefault(uuid, 20);}

  public void setMaxHeath(UUID uuid, int v) {
    maxHeathXMap.put(uuid, v);
    setDirty();
  }

}
