package in.kairoku.skillset_centimental.datagen;

import in.kairoku.skillset_centimental.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(ModItems.MJOLNIR), RecipeCategory.COMBAT, ModItems.MJOLNIR, 30000.0f, 100)
                .criterion(hasItem(ModItems.MJOLNIR), conditionsFromItem(ModItems.MJOLNIR))
                .offerTo(exporter, new Identifier(getRecipeName((ModItems.MJOLNIR))));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHORUS_OF_THE_VOID, 1)
                .pattern("OCO")
                .pattern("OCO")
                .pattern(" R ")
                .input('O', Items.OBSIDIAN)
                .input('C', Items.END_CRYSTAL)
                .input('R', Items.END_ROD)
                .criterion(hasItem(Items.OBSIDIAN), conditionsFromItem(Items.OBSIDIAN))
                .criterion(hasItem(Items.END_CRYSTAL), conditionsFromItem(Items.END_CRYSTAL))
                .criterion(hasItem(Items.END_ROD), conditionsFromItem(Items.END_ROD))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHORUS_OF_THE_VOID)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PRISON_OF_SOULS, 1)
                .pattern("I I")
                .pattern("ISI")
                .pattern(" W ")
                .input('I', Items.IRON_BLOCK)
                .input('S', Items.SOUL_SOIL)
                .input('W', Items.STICK)
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(Items.SOUL_SOIL), conditionsFromItem(Items.SOUL_SOIL))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PRISON_OF_SOULS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.THANOTASIAN_SCYTHE, 1)
                .pattern("III")
                .pattern(" W ")
                .pattern("W  ")
                .input('I', Items.IRON_BLOCK)
                .input('W', Items.STICK)
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.THANOTASIAN_SCYTHE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BLADE_OF_DIMENSIONS, 1)
                .pattern(" I ")
                .pattern(" P ")
                .pattern(" W ")
                .input('I', Items.IRON_BLOCK)
                .input('W', Items.STICK)
                .input('P', Items.ENDER_PEARL)
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.BLADE_OF_DIMENSIONS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHORUS_HELMET, 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("   ")
                .input('C', ModItems.CHORUS_ORE)
                .criterion(hasItem(ModItems.CHORUS_ORE), conditionsFromItem(ModItems.CHORUS_ORE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHORUS_HELMET)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHORUS_LEGGINGS, 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .input('C', ModItems.CHORUS_ORE)
                .criterion(hasItem(ModItems.CHORUS_ORE), conditionsFromItem(ModItems.CHORUS_ORE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHORUS_LEGGINGS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHORUS_CHESTPLATE, 1)
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .input('C', ModItems.CHORUS_ORE)
                .criterion(hasItem(ModItems.CHORUS_ORE), conditionsFromItem(ModItems.CHORUS_ORE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHORUS_CHESTPLATE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHORUS_BOOTS, 1)
                .pattern("C C")
                .pattern("C C")
                .pattern("   ")
                .input('C', ModItems.CHORUS_ORE)
                .criterion(hasItem(ModItems.CHORUS_ORE), conditionsFromItem(ModItems.CHORUS_ORE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHORUS_BOOTS)));

    }
    
}
