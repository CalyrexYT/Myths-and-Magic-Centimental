package in.kairoku.skillset_centimental.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.random.Random;

public class PrisonOfSoulsSwordItem extends SwordItem {

    public PrisonOfSoulsSwordItem() {
        super(CustomToolMaterial.INSTANCE, new Item.Settings().attributeModifiers(createAttributeModifiers(CustomToolMaterial.INSTANCE, 5, -2.4f)));
 
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target == null || !(attacker instanceof PlayerEntity)) {
            return super.postHit(stack, target, attacker);
        }
        if (target.getHealth()<=0) {
            CommandManager commandManager = attacker.getServer().getCommandManager();
            NbtCompound nbt = stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt();
            int chance = Random.create().nextBetween(1, 100);
            long exponential = Math.round(100 * (Math.pow(0.84, nbt.getInt("damage"))));
            long exponential2 = Math.round(50 * (Math.pow(0.84, nbt.getInt("damage"))));
            if (exponential == 0&&(nbt.getInt("damage")<=50)) {
                exponential++;
            }
            if (exponential2 == 0&&(nbt.getInt("damage")<=50)) {
                exponential2++;
            }
            if (chance <= exponential2 && nbt.getInt("damage")>0){
                nbt.putInt("damage", nbt.getInt("damage") - 1);
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/item replace entity @p weapon.mainhand with skillset-centimental:prison_of_souls{damage:" + nbt.getInt("damage") + ",Enchantments:[{id:sharpness,lvl: " + (nbt.getInt("damage")) + "}]}");
            }
            else if (chance <= exponential) {
                nbt.putInt("damage", nbt.getInt("damage") + 1);
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/item replace entity @p weapon.mainhand with skillset-centimental:prison_of_souls{damage:" + nbt.getInt("damage") + ",Enchantments:[{id:sharpness,lvl: " + (nbt.getInt("damage")) + "}]}");
                commandManager.executeWithPrefix(attacker.getServer().getCommandSource(), "/particle enchanted_hit " + attacker.getX() + " " + attacker.getY() + " " + attacker.getZ() + " 1 1 1 4 1000 normal");
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
