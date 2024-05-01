package in.kairoku.skillset_centimental.rendering;

import in.kairoku.skillset_centimental.SkillsetCentimentalClient;
import in.kairoku.skillset_centimental.entity.PortalEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PortalRenderer extends EntityRenderer<PortalEntity> {
    final PortalModel model;

    public PortalRenderer(Context ctx) {
        super(ctx);
        this.model = new PortalModel(ctx.getPart(SkillsetCentimentalClient.MODEL_PORTAL_LAYER));
    }

    @Override
    public Identifier getTexture(PortalEntity var1) {
        return new Identifier("skillset-centimental", "textures/entity/portal.png");
    }

    @Override
    public void render(PortalEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        RenderLayer renderLayer = this.model.getLayer(this.getTexture(entity));
        if(renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

}