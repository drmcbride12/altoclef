package adris.altoclef.util.helpers;

import adris.altoclef.AltoClef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Helper functions to interpret entity state
 */
public class EntityHelper {

    public static final double ENTITY_GRAVITY = 0.08; // per second

    public static boolean isAngryAtPlayer(AltoClef mod, Entity mob) {
        boolean hostile = isGenerallyHostileToPlayer(mod, mob);
        if (mob instanceof LivingEntity entity) {
            return hostile && entity.hasLineOfSight(mod.getPlayer());
        }
        return hostile;
    }

    public static boolean isGenerallyHostileToPlayer(AltoClef mod, Entity hostile) {
        // TODO: Ignore on Peaceful difficulty.
        LocalPlayer player = Minecraft.getInstance().player;
        // NOTE: These do not work.
        if (hostile instanceof EnderMan enderman) {
            return enderman.isCreepy();
        }
        if (hostile instanceof Piglin) {
            // Angry if we're not wearing gold
            return !StorageHelper.isArmorEquipped(mod, ItemHelper.GOLDEN_ARMORS);
        }
        if (hostile instanceof ZombifiedPiglin zombie) {
            // Will ALWAYS be false.
            return zombie.isAngry();
        }
        return !isTradingPiglin(hostile);
    }

    public static boolean isTradingPiglin(Entity entity) {
        if (entity instanceof Piglin pig) {
            for (ItemStack stack : new ItemStack[]{pig.getMainHandItem(), pig.getOffhandItem()}) {
                if (stack.getItem().equals(Items.GOLD_INGOT)) {
                    // We're trading with this one, ignore it.
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculate the resulting damage dealt to a player as a result of some damage.
     * If this player were to receive this damage, the player's health will be subtracted by the resulting value.
     */
    public static double calculateResultingPlayerDamage(Player player, DamageSource source, double damageAmount) {
        // Copied logic from `PlayerEntity.applyDamage`

        // Armor Base
        if (!source.is(DamageTypeTags.BYPASSES_ARMOR)) {
            damageAmount = CombatRules.getDamageAfterAbsorb(player, (float) damageAmount, source, (float) player.getArmorValue(), (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        // Enchantments & Potions
        if (!source.is(DamageTypeTags.BYPASSES_EFFECTS)) {
            int k;
            if (player.hasEffect(MobEffects.RESISTANCE) && !source.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
                //noinspection ConstantConditions
                k = (player.getEffect(MobEffects.RESISTANCE).getAmplifier() + 1) * 5;
                int j = 25 - k;
                double f = damageAmount * (double)j;
                double g = damageAmount;
                damageAmount = Math.max(f / 25.0F, 0.0F);
            }
        }

        // Absorption
        damageAmount = Math.max(damageAmount - player.getAbsorptionAmount(), 0.0F);
        return damageAmount;
    }
}
