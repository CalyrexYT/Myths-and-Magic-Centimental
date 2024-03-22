package in.techpro424.skillset_centimental.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import in.techpro424.skillset_centimental.entity.MjolnirEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

@Mixin(ExplosionBehavior.class)
public class ExplosionBehaviorMixin {
    @Inject(method = "calculateDamage", at = @At("RETURN"), cancellable = true)
    private void setDamageTo0(Explosion explosion, Entity entity, CallbackInfoReturnable<Float> cir) {
        if(explosion.getEntity() instanceof MjolnirEntity mjolnir) if(mjolnir.getOwner().equals(entity)) {
            cir.setReturnValue(0.0f);
        }
    }
}
