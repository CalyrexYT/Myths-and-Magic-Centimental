package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class RequiemItem extends SwordItem {
    private int hitsReceived = 0;

    public RequiemItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
        hitsReceived = 0;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        hitsReceived++;
        return super.postHit(stack, target, attacker);
    }

    public float getAdditionalDamage() {
        return hitsReceived * 0.01f * 9;
    }

    public void resetHits() {
        hitsReceived = 0;
    }
}
