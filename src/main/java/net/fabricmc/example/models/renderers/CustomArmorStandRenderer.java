package net.fabricmc.example.models.renderers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import software.bernie.example.client.model.entity.ReplacedCreeperModel;
import software.bernie.example.entity.ReplacedCreeperEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderer.geo.GeoReplacedEntityRenderer;

public class CustomArmorStandRenderer extends GeoReplacedEntityRenderer {
    public Entity currentEntity;

    public CustomArmorStandRenderer(EntityRenderDispatcher renderManager, CustomArmorStandModelProvider modelProvider) {
        super(renderManager, modelProvider, new CustomArmorStandEntity());
        modelProvider.renderer = this;
    }

    public void render(Entity entity, IAnimatable animatable, float entityYaw, float partialTicks, MatrixStack stack,
                       VertexConsumerProvider bufferIn, int packedLightIn) {
        this.currentEntity = entity;
        super.render(entity, animatable, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
