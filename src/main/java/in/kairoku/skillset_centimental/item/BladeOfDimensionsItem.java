package in.kairoku.skillset_centimental.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.random.Random;

public class BladeOfDimensionsItem extends SwordItem {
    public BladeOfDimensionsItem() {
        super(CustomToolMaterial.INSTANCE,  7, -2.2f, new FabricItemSettings().maxCount(1));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        CommandManager commandManager = attacker.getServer().getCommandManager();
        int chance = Random.create().nextBetween(1, 100);
        if (chance <= 20) {
            int randomNum = Random.create().nextBetween(1, 4);
            int randomCoord = Random.create().nextBetween(2, 5);
            if (randomNum==1)
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/tp @p "+target.getX()+" "+target.getY()+" "+(target.getZ()+randomCoord)+" facing "+target.getX()+" "+target.getY()+" "+target.getZ());
            else if (randomNum==2)
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/tp @p "+(target.getX()-randomCoord)+" "+target.getY()+" "+target.getZ()+" facing "+target.getX()+" "+target.getY()+" "+target.getZ());
            else if (randomNum==3)
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/tp @p "+target.getX()+" "+target.getY()+" "+(target.getZ()-randomCoord)+" facing "+target.getX()+" "+target.getY()+" "+target.getZ());
            else if (randomNum==4)
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/tp @p "+(target.getX()+randomCoord)+" "+target.getY()+" "+target.getZ()+" facing "+target.getX()+" "+target.getY()+" "+target.getZ());
            commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/effect give @p resistance 2 5");
        }
        return super.postHit(stack, target, attacker);
    }
}
