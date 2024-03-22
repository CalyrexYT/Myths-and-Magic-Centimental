package in.techpro424.skillset_centimental.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor("LOYALTY")
    public static TrackedData<Byte> getLoyalty() {
        throw new AssertionError();
    }

    @Accessor("DEFAULT_STACK")
    public static void setDefaultStack(ItemStack stack) {
        throw new AssertionError();
    }    
} 
