package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.random.Random;

public class ThanotasianScythe extends SwordItem {
    public ThanotasianScythe() {
        super(CustomToolMaterial.INSTANCE, new Item.Settings().maxCount(1).attributeModifiers(createAttributeModifiers(CustomToolMaterial.INSTANCE, 9, -2.8f)));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int chance = Random.create().nextBetween(1, 100);


        

        
        
        if (chance <= 50) {
            //getAttackDamage()
           
            attacker.setHealth(attacker.getHealth()+(this.getMaterial().getAttackDamage()/5));
        } else {
            //getAttackDamage()
            attacker.setHealth(attacker.getHealth()+(this.getMaterial().getAttackDamage()/4));
        }
        return super.postHit(stack, target, attacker);
    }
}
