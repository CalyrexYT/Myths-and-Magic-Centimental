package in.kairoku.skillset_centimental.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.random.Random;

public class ThanotasianScythe extends SwordItem {
    public ThanotasianScythe() {
        super(CustomToolMaterial.INSTANCE,  9, -2.8f, new FabricItemSettings().maxCount(1));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int chance = Random.create().nextBetween(1, 100);
        if (chance <= 50) {
            attacker.setHealth(attacker.getHealth()+(getAttackDamage()/5));
        } else {
            attacker.setHealth(attacker.getHealth()+(getAttackDamage()/4));
        }
        return super.postHit(stack, target, attacker);
    }
}
