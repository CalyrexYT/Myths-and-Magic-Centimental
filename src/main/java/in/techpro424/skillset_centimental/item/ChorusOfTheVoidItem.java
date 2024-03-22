package in.techpro424.skillset_centimental.item;

import in.techpro424.skillset_centimental.SkillsetCentimental;
import in.techpro424.skillset_centimental.entity.BlackHoleEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ChorusOfTheVoidItem extends SwordItem {

    public ChorusOfTheVoidItem() {
        super(ToolMaterials.NETHERITE, 9, 1.5f, new FabricItemSettings());
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int chance = Random.create().nextBetween(1, 100);
        if (chance <= 20) {
            World world = target.getWorld();
            if(!world.isClient()) world.spawnEntity(new BlackHoleEntity(SkillsetCentimental.BLACK_HOLE, world));

        }
        return super.postHit(stack, target, attacker);
    }

}