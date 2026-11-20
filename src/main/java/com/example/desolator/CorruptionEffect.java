package com.example.desolator;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CorruptionEffect extends MobEffect {
    protected CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B0000);
        addAttributeModifier(Attributes.ARMOR,
                Identifier.fromNamespaceAndPath(DesolatorMod.MODID, "corruption_armor"),
                -6.0, AttributeModifier.Operation.ADD_VALUE);
    }
}
