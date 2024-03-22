package in.techpro424.skillset_centimental.rendering;

import in.techpro424.skillset_centimental.SkillsetCentimental;
import in.techpro424.skillset_centimental.entity.MjolnirEntity;
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

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class MjolnirModel extends EntityModel<MjolnirEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(SkillsetCentimental.MOD_ID, "mjolnir"), "bone");
    private final ModelPart bone;

    public MjolnirModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData partdefinition = modelData.getRoot();

        ModelPartData bone = partdefinition.addChild("bone", ModelPartBuilder.create().uv(14, 12).cuboid(-9.0F, -14.0F, 7.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F))
                .uv(6, 0).mirrored().cuboid(-12.5F, -19.0F, 5.0F, 9.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
                .uv(12, 0).mirrored().cuboid(-13.1F, -18.38F, 5.0F, 0.0F, 4.13F, 6.0F, new Dilation(0.0F)).mirrored(false)
                .uv(6, 0).cuboid(-2.82F, -18.72F, 5.0F, 0.0F, 4.45F, 6.0F, new Dilation(0.0F))
                .uv(6, 22).mirrored().cuboid(-3.6F, -18.75F, 5.0F, 0.75F, 4.5F, 6.0F, new Dilation(0.0F)).mirrored(false)
                .uv(18, 20).cuboid(-13.1F, -18.75F, 5.0F, 0.75F, 4.5F, 6.0F, new Dilation(0.0F))
                .uv(7, 14).cuboid(-8.37F, -3.1F, 7.75F, 0.62F, 0.3F, 0.62F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-8.32F, -0.05F, 7.52F, 0.57F, 0.0F, 1.1F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -8.0F));

        bone.addChild("String4_r1", ModelPartBuilder.create().uv(6, 0).cuboid(-0.5283F, 0.5305F, -0.55F, 0.0F, 1.5F, 1.1F, new Dilation(0.0F))
                .uv(25, 0).cuboid(1.0467F, -0.3395F, -0.55F, 0.0F, 1.5F, 1.1F, new Dilation(0.0F)), ModelTransform.of(-8.5967F, -2.1105F, 8.07F, 0.0F, 0.0F, -0.3927F));

        bone.addChild("String3_r1", ModelPartBuilder.create().uv(22, 0).cuboid(1.5567F, 0.0755F, -0.55F, 0.0F, 1.5F, 1.1F, new Dilation(0.0F))
                .uv(8, 0).cuboid(0.0F, -0.75F, -0.55F, 0.0F, 1.5F, 1.1F, new Dilation(0.0F)), ModelTransform.of(-8.5967F, -2.1105F, 8.07F, 0.0F, 0.0F, 0.3927F));

        bone.addChild("Slant4_r1", ModelPartBuilder.create().uv(0, 22).cuboid(2.1F, -14.28F, 5.0F, 0.75F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        bone.addChild("Slant3_r1", ModelPartBuilder.create().uv(0, 15).cuboid(-0.222F, -0.1818F, -3.0F, 0.6F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-12.788F, -18.6782F, 8.0F, 0.0F, 0.0F, -0.3927F));

        bone.addChild("Slant2_r1", ModelPartBuilder.create().uv(0, 22).cuboid(-17.55F, -8.15F, 5.0F, 0.65F, 0.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-10.51F, -16.21F, 5.0F, 0.75F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(MjolnirEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}