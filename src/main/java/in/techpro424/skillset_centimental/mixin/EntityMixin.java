package in.techpro424.skillset_centimental.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import in.techpro424.skillset_centimental.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "hasNoGravity", at = @At("TAIL"), cancellable = true)
    private void cancelItemGravity(CallbackInfoReturnable<Boolean> cir) {
        Entity e = ((Entity)(Object)this);
        if(e.getType().equals(EntityType.ITEM)) {
            ItemEntity entity = ((ItemEntity)(Object)this);
            if(entity.getStack().isOf(ModItems.MJOLNIR)) if(entity.getPos().getY() <= -62) {
                cir.setReturnValue(true);
                entity.teleport(entity.getPos().getX(), 320, entity.getPos().getZ());
                
            }
        }
        
    }
}
