package in.kairoku.skillset_centimental.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor("LOYALTY")
    public static TrackedData<Byte> getLoyalty() {
        throw new AssertionError();
    }
} 
