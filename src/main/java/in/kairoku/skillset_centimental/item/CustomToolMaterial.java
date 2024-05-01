package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class CustomToolMaterial implements ToolMaterial {
    public static final CustomToolMaterial INSTANCE = new CustomToolMaterial();

    @Override
    public int getDurability() {
        return 600;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 8.0F;
    }
    @Override
    public float getAttackDamage() {
        return 0.0F;
    }
    @Override
    public int getMiningLevel() {
        return 3;
    }
    @Override
    public int getEnchantability() {
        return 10;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.CHORUS_ORE);
    }
}
