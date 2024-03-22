package in.techpro424.skillset_centimental.rendering;

import in.techpro424.skillset_centimental.SkillsetCentimentalClient;
import in.techpro424.skillset_centimental.entity.MjolnirEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class MjolnirRenderer extends EntityRenderer<MjolnirEntity> {
    final MjolnirModel model;

    public MjolnirRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MjolnirModel(context.getPart(SkillsetCentimentalClient.MODEL_MJOLNIR_LAYER));
    }

    @Override
    public Identifier getTexture(MjolnirEntity entity) {
        return new Identifier("skillset-centimental", "textures/entity/mjolnir.png");
    }

    @Override
    public void render(MjolnirEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp((float)tickDelta, (float)entity.prevYaw, (float)entity.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp((float)tickDelta, (float)entity.prevPitch, (float)entity.getPitch()) + 90.0f));

        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, entity.isEnchanted());
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}