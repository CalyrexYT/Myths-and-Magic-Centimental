package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.entity.BlackHoleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ChorusOfTheVoidItem extends SwordItem {

    public ChorusOfTheVoidItem() {
        super(CustomToolMaterial.INSTANCE, new Item.Settings().maxCount(1).attributeModifiers(createAttributeModifiers(CustomToolMaterial.INSTANCE, 9, -2.0f)));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int chance = Random.create().nextBetween(1, 100);
        if (chance <= 10) {
            World world = target.getWorld();
            if(!world.isClient()) {
                BlackHoleEntity entity;
                if(attacker instanceof PlayerEntity player) entity = new BlackHoleEntity(player, SkillsetCentimental.BLACK_HOLE, world);
                else entity = new BlackHoleEntity(SkillsetCentimental.BLACK_HOLE, world);
                entity.setPosition(target.getX()+Random.create().nextBetween(0, 5), target.getY()+Random.create().nextBetween(0, 3), target.getZ()+Random.create().nextBetween(0, 5));
                world.spawnEntity(entity);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
