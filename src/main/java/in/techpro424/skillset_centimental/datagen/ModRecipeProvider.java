package in.techpro424.skillset_centimental.datagen;

import in.techpro424.skillset_centimental.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(ModItems.MJOLNIR), RecipeCategory.COMBAT, ModItems.MJOLNIR, 2.0f, 100).criterion(hasItem(ModItems.MJOLNIR), conditionsFromItem(ModItems.MJOLNIR)).offerTo(exporter);
    }

}