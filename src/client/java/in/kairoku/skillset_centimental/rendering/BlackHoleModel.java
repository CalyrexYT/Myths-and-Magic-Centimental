package in.kairoku.skillset_centimental.rendering;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.entity.BlackHoleEntity;
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
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BlackHoleModel extends EntityModel<BlackHoleEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(SkillsetCentimental.MOD_ID, "black_hole"), "main");
	
	private final ModelPart main;

	public BlackHoleModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData ring = main.addChild("ring", ModelPartBuilder.create().uv(59, 178).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(177, 153).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(64, 117).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 109).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ring.addChild("hexadecagon_r1", ModelPartBuilder.create().uv(112, 100).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 117).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(177, 126).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(29, 178).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring.addChild("hexadecagon_r2", ModelPartBuilder.create().uv(0, 117).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 117).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(177, 170).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(39, 179).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring.addChild("hexadecagon_r3", ModelPartBuilder.create().uv(49, 177).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(178, 0).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring.addChild("hexadecagon_r4", ModelPartBuilder.create().uv(0, 178).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(69, 179).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring2 = main.addChild("ring2", ModelPartBuilder.create().uv(175, 99).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(175, 54).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(112, 82).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 55).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		ring2.addChild("hexadecagon_r5", ModelPartBuilder.create().uv(112, 46).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 73).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(175, 45).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(175, 90).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring2.addChild("hexadecagon_r6", ModelPartBuilder.create().uv(112, 64).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 91).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(175, 63).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(175, 108).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring2.addChild("hexadecagon_r7", ModelPartBuilder.create().uv(175, 36).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(175, 81).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring2.addChild("hexadecagon_r8", ModelPartBuilder.create().uv(175, 72).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(19, 177).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring3 = main.addChild("ring3", ModelPartBuilder.create().uv(175, 9).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(118, 172).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(112, 28).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 1).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

		ring3.addChild("hexadecagon_r9", ModelPartBuilder.create().uv(80, 109).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 19).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(108, 172).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(174, 135).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring3.addChild("hexadecagon_r10", ModelPartBuilder.create().uv(112, 10).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(112, 37).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(128, 172).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(175, 18).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring3.addChild("hexadecagon_r11", ModelPartBuilder.create().uv(167, 171).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(148, 172).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring3.addChild("hexadecagon_r12", ModelPartBuilder.create().uv(138, 172).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(175, 27).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring4 = main.addChild("ring4", ModelPartBuilder.create().uv(88, 171).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(9, 170).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(16, 109).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 108).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		ring4.addChild("hexadecagon_r13", ModelPartBuilder.create().uv(0, 108).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 108).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(58, 169).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(170, 117).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring4.addChild("hexadecagon_r14", ModelPartBuilder.create().uv(64, 108).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(48, 109).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(39, 170).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(98, 171).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring4.addChild("hexadecagon_r15", ModelPartBuilder.create().uv(29, 169).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(78, 170).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring4.addChild("hexadecagon_r16", ModelPartBuilder.create().uv(68, 170).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(171, 144).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring5 = main.addChild("ring5", ModelPartBuilder.create().uv(168, 0).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(165, 90).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(48, 100).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(64, 99).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

		ring5.addChild("hexadecagon_r17", ModelPartBuilder.create().uv(32, 99).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 100).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(165, 81).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(167, 153).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring5.addChild("hexadecagon_r18", ModelPartBuilder.create().uv(96, 99).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 100).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(165, 99).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(19, 168).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring5.addChild("hexadecagon_r19", ModelPartBuilder.create().uv(165, 72).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(167, 126).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring5.addChild("hexadecagon_r20", ModelPartBuilder.create().uv(165, 108).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(168, 162).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring6 = main.addChild("ring6", ModelPartBuilder.create().uv(165, 45).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(164, 135).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(96, 90).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 63).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

		ring6.addChild("hexadecagon_r21", ModelPartBuilder.create().uv(96, 54).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 81).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(158, 163).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(165, 36).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring6.addChild("hexadecagon_r22", ModelPartBuilder.create().uv(96, 72).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(0, 99).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(165, 9).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(165, 54).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring6.addChild("hexadecagon_r23", ModelPartBuilder.create().uv(148, 163).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(165, 27).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring6.addChild("hexadecagon_r24", ModelPartBuilder.create().uv(165, 18).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(165, 63).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring7 = main.addChild("ring7", ModelPartBuilder.create().uv(118, 163).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(161, 144).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(96, 36).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 9).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		ring7.addChild("hexadecagon_r25", ModelPartBuilder.create().uv(96, 0).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 27).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(78, 161).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(108, 163).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring7.addChild("hexadecagon_r26", ModelPartBuilder.create().uv(96, 18).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(96, 45).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(0, 162).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(128, 163).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring7.addChild("hexadecagon_r27", ModelPartBuilder.create().uv(68, 161).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(98, 162).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring7.addChild("hexadecagon_r28", ModelPartBuilder.create().uv(88, 162).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(138, 163).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring8 = main.addChild("ring8", ModelPartBuilder.create().uv(160, 117).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(157, 126).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(48, 91).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 90).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.2217F, 0.0F));

		ring8.addChild("hexadecagon_r29", ModelPartBuilder.create().uv(0, 90).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 91).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(155, 108).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(29, 160).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring8.addChild("hexadecagon_r30", ModelPartBuilder.create().uv(64, 90).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 91).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(157, 154).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(39, 161).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring8.addChild("hexadecagon_r31", ModelPartBuilder.create().uv(155, 99).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(10, 160).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring8.addChild("hexadecagon_r32", ModelPartBuilder.create().uv(158, 0).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(49, 161).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring9 = main.addChild("ring9", ModelPartBuilder.create().uv(155, 72).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(155, 27).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(48, 82).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 81).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.3963F, 0.0F));

		ring9.addChild("hexadecagon_r33", ModelPartBuilder.create().uv(0, 81).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 82).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(155, 18).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(155, 63).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring9.addChild("hexadecagon_r34", ModelPartBuilder.create().uv(64, 81).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 82).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(155, 36).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(155, 81).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring9.addChild("hexadecagon_r35", ModelPartBuilder.create().uv(155, 9).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(155, 54).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring9.addChild("hexadecagon_r36", ModelPartBuilder.create().uv(155, 45).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(155, 90).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring10 = main.addChild("ring10", ModelPartBuilder.create().uv(154, 135).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(87, 153).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(80, 64).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 37).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ring10.addChild("hexadecagon_r37", ModelPartBuilder.create().uv(80, 28).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 55).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(59, 153).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(127, 154).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring10.addChild("hexadecagon_r38", ModelPartBuilder.create().uv(80, 46).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 73).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(97, 153).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(137, 154).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring10.addChild("hexadecagon_r39", ModelPartBuilder.create().uv(0, 153).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(117, 154).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring10.addChild("hexadecagon_r40", ModelPartBuilder.create().uv(107, 154).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(147, 154).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring11 = main.addChild("ring11", ModelPartBuilder.create().uv(39, 152).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(148, 0).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(80, 10).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 73).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.7453F, 0.0F));

		ring11.addChild("hexadecagon_r41", ModelPartBuilder.create().uv(64, 72).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 1).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(147, 126).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(20, 152).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring11.addChild("hexadecagon_r42", ModelPartBuilder.create().uv(48, 73).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(80, 19).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(150, 117).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(49, 152).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring11.addChild("hexadecagon_r43", ModelPartBuilder.create().uv(141, 145).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(151, 145).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring11.addChild("hexadecagon_r44", ModelPartBuilder.create().uv(10, 151).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(77, 152).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring12 = main.addChild("ring12", ModelPartBuilder.create().uv(111, 145).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 72).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(0, 72).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(48, 64).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.9199F, 0.0F));

		ring12.addChild("hexadecagon_r45", ModelPartBuilder.create().uv(64, 45).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(64, 63).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(145, 63).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 108).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring12.addChild("hexadecagon_r46", ModelPartBuilder.create().uv(64, 54).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 72).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(145, 81).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(121, 145).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring12.addChild("hexadecagon_r47", ModelPartBuilder.create().uv(145, 54).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 99).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring12.addChild("hexadecagon_r48", ModelPartBuilder.create().uv(145, 90).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(131, 145).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring13 = main.addChild("ring13", ModelPartBuilder.create().uv(145, 27).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(91, 144).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(64, 27).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(64, 9).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		ring13.addChild("hexadecagon_r49", ModelPartBuilder.create().uv(64, 0).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(64, 18).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(68, 144).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 18).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring13.addChild("hexadecagon_r50", ModelPartBuilder.create().uv(16, 64).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(64, 36).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(101, 144).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 36).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring13.addChild("hexadecagon_r51", ModelPartBuilder.create().uv(58, 144).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 9).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring13.addChild("hexadecagon_r52", ModelPartBuilder.create().uv(144, 136).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(145, 45).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring14 = main.addChild("ring14", ModelPartBuilder.create().uv(81, 143).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(138, 0).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(0, 63).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 54).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.2689F, 0.0F));

		ring14.addChild("hexadecagon_r53", ModelPartBuilder.create().uv(0, 54).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(48, 55).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(137, 127).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(48, 143).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring14.addChild("hexadecagon_r54", ModelPartBuilder.create().uv(16, 55).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 63).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(140, 117).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(0, 144).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring14.addChild("hexadecagon_r55", ModelPartBuilder.create().uv(134, 136).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(20, 143).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring14.addChild("hexadecagon_r56", ModelPartBuilder.create().uv(10, 142).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(30, 144).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring15 = main.addChild("ring15", ModelPartBuilder.create().uv(135, 108).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(135, 81).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(48, 37).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(48, 10).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.4435F, 0.0F));

		ring15.addChild("hexadecagon_r57", ModelPartBuilder.create().uv(48, 1).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(48, 28).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(135, 72).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(104, 135).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring15.addChild("hexadecagon_r58", ModelPartBuilder.create().uv(48, 19).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(48, 46).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(135, 90).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(114, 136).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring15.addChild("hexadecagon_r59", ModelPartBuilder.create().uv(72, 135).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(135, 99).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring15.addChild("hexadecagon_r60", ModelPartBuilder.create().uv(94, 135).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(124, 136).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring16 = main.addChild("ring16", ModelPartBuilder.create().uv(135, 54).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(135, 27).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(32, 45).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 36).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

		ring16.addChild("hexadecagon_r61", ModelPartBuilder.create().uv(0, 36).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(0, 45).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(135, 18).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(135, 45).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring16.addChild("hexadecagon_r62", ModelPartBuilder.create().uv(16, 37).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 46).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(29, 135).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(62, 135).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring16.addChild("hexadecagon_r63", ModelPartBuilder.create().uv(135, 9).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(39, 135).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring16.addChild("hexadecagon_r64", ModelPartBuilder.create().uv(135, 36).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(135, 63).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring17 = main.addChild("ring17", ModelPartBuilder.create().uv(52, 134).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(117, 127).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(32, 18).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 28).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.7925F, 0.0F));

		ring17.addChild("hexadecagon_r65", ModelPartBuilder.create().uv(0, 27).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 9).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(107, 126).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(19, 134).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring17.addChild("hexadecagon_r66", ModelPartBuilder.create().uv(32, 0).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(32, 27).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(127, 127).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(84, 134).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring17.addChild("hexadecagon_r67", ModelPartBuilder.create().uv(97, 126).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(130, 118).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring17.addChild("hexadecagon_r68", ModelPartBuilder.create().uv(128, 0).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(0, 135).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData ring18 = main.addChild("ring18", ModelPartBuilder.create().uv(43, 126).cuboid(-2.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(55, 125).cuboid(-2.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(0, 18).cuboid(-2.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-2.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.9671F, 0.0F));

		ring18.addChild("hexadecagon_r69", ModelPartBuilder.create().uv(0, 0).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 10).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(23, 125).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(33, 126).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		ring18.addChild("hexadecagon_r70", ModelPartBuilder.create().uv(16, 1).cuboid(11.0F, -19.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(16, 19).cuboid(11.0F, 18.5F, -3.8788F, 4.0F, 1.0F, 7.7576F, new Dilation(0.0F))
		.uv(87, 125).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(65, 126).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ring18.addChild("hexadecagon_r71", ModelPartBuilder.create().uv(120, 118).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(10, 126).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ring18.addChild("hexadecagon_r72", ModelPartBuilder.create().uv(0, 126).cuboid(11.0F, -3.8788F, 18.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F))
		.uv(75, 126).cuboid(11.0F, -3.8788F, -19.5F, 4.0F, 7.7576F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(BlackHoleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}