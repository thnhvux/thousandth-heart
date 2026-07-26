package net.vux.thousandthheart.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.vux.thousandthheart.ThousandthHeart;

import java.util.function.Function;

public class ModItems {
  public static final Item LIFE_CRYSTAL = registerItem("life_crystal", LifeCrystal::new);

  private static Item registerItem(String name, Function<Item.Properties, Item> function) {
    return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(ThousandthHeart.MOD_ID, name),
            function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ThousandthHeart.MOD_ID, name)))));
  }

  public static void registerModItems() {
    ThousandthHeart.LOGGER.info("Registering Mod Items for " + ThousandthHeart.MOD_ID);
    CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> {
      output.accept(LIFE_CRYSTAL);
    });
  }
}
