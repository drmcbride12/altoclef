package adris.altoclef.mixins;

import adris.altoclef.eventbus.EventBus;
import adris.altoclef.eventbus.events.SlotClickChangedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@Mixin(AbstractContainerMenu.class)
public class SlotClickMixin {

    @Shadow
    public NonNullList<Slot> slots;

    private List<ItemStack> altoclef$beforeStacks;

    @Inject(
            method = "clicked",
            at = @At("HEAD")
    )
    private void altoclef$beforeSlotClick(int slotIndex, int button, ContainerInput actionType, Player player, CallbackInfo ci) {
        altoclef$beforeStacks = new ArrayList<>(slots.size());
        for (Slot slot : slots) {
            altoclef$beforeStacks.add(slot.getItem().copy());
        }
    }

    @Inject(
            method = "clicked",
            at = @At("RETURN")
    )
    private void altoclef$afterSlotClick(int slotIndex, int button, ContainerInput actionType, Player player, CallbackInfo ci) {
        if (altoclef$beforeStacks == null) {
            return;
        }
        for (int i = 0; i < altoclef$beforeStacks.size(); ++i) {
            ItemStack before = altoclef$beforeStacks.get(i);
            ItemStack after = slots.get(i).getItem();
            if (!ItemStack.matches(before, after)) {
                adris.altoclef.util.slots.Slot slot = adris.altoclef.util.slots.Slot.getFromCurrentScreen(i);
                EventBus.publish(new SlotClickChangedEvent(slot, before, after));
            }
        }
        altoclef$beforeStacks = null;
    }
}
