package com.example.desolator;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class DesolatorMod implements ModInitializer {
    public static final String MODID = "desolator";

    public static Holder<MobEffect> CORRUPTION;
    public static Item DESOLATOR;

    private static final ToolMaterial MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 8.0f, 4.0f, 15,
            ItemTags.NETHERITE_TOOL_MATERIALS);

    @Override
    public void onInitialize() {
        CORRUPTION = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                Identifier.fromNamespaceAndPath(MODID, "corruption"), new CorruptionEffect());

        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(MODID, "desolator"));
        DESOLATOR = Registry.register(BuiltInRegistries.ITEM, key,
                new Item(new Item.Properties()
                        .setId(key)
                        .sword(MATERIAL, 5.0f, -2.4f)
                        .rarity(Rarity.RARE)
                        .fireResistant()));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register(output -> output.accept(DESOLATOR));

        // Эффект при ударе мечом в ближнем бою
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamage, damage, blocked) -> {
            if (blocked) return;
            if (source.getDirectEntity() instanceof LivingEntity attacker
                    && attacker.getMainHandItem().is(DESOLATOR)) {
                entity.addEffect(new MobEffectInstance(CORRUPTION, 140, 0));
            }
        });
    }
}
