package in.kairoku.skillset_centimental.rendering;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.entity.PortalEntity;

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PortalModel extends EntityModel<PortalEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(SkillsetCentimental.MOD_ID, "portal"), "everything");
    private final ModelPart everything;
    public PortalModel(ModelPart root) {
        this.everything = root.getChild("everything");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData everything = modelPartData.addChild("everything", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 9.0F, 0.0F));

        everything.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-25.0F, -11.0F, 0.0F, 32.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 1.5559F));

        everything.addChild("cube_r2", ModelPartBuilder.create().uv(28, 11).cuboid(-3.0F, -14.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 2.0359F));

        everything.addChild("cube_r3", ModelPartBuilder.create().uv(28, 17).cuboid(5.0F, -3.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 1.2068F));

        everything.addChild("cube_r4", ModelPartBuilder.create().uv(28, 29).cuboid(-7.0F, -3.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 1.425F));

        everything.addChild("cube_r5", ModelPartBuilder.create().uv(28, 23).cuboid(-13.0F, 0.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 1.7669F));

        everything.addChild("cube_r6", ModelPartBuilder.create().uv(0, 18).cuboid(-22.0F, -1.0F, 0.0F, 14.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 1.6407F));

        everything.addChild("cube_r7", ModelPartBuilder.create().uv(0, 11).cuboid(-31.0F, 3.0F, 0.0F, 14.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -14.0F, 0.0F, 0.0F, 0.0F, -1.4399F));

        everything.addChild("cube_r8", ModelPartBuilder.create().uv(0, 25).cuboid(-20.0F, 0.0F, 0.0F, 14.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -14.0F, 0.0F, 0.0F, 0.0F, -1.6581F));

        everything.addChild("cube_r9", ModelPartBuilder.create().uv(0, 32).cuboid(-13.0F, 0.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -14.0F, 0.0F, 0.0F, 0.0F, -1.4835F));

        everything.addChild("cube_r10", ModelPartBuilder.create().uv(28, 35).cuboid(-7.0F, -1.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -14.0F, 0.0F, 0.0F, 0.0F, -1.8326F));

        everything.addChild("cube_r11", ModelPartBuilder.create().uv(0, 38).cuboid(-13.0F, -7.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -20.0F, 0.0F, 0.0F, 0.0F, -1.3963F));

        everything.addChild("cube_r12", ModelPartBuilder.create().uv(0, 44).cuboid(-8.0F, -5.0F, 0.0F, 14.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(28, 41).cuboid(-9.0F, -3.0F, 0.0F, 14.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -20.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        everything.addChild("cube_r13", ModelPartBuilder.create().uv(28, 47).cuboid(-6.0F, -2.0F, 0.0F, 14.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -20.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(PortalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        everything.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}