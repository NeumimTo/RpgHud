package net.fabricmc.example.models.renderers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

public class DisguisedEntityRenderer extends GeoReplacedEntityRenderer {
    public Entity currentEntity;

    private static DisguisedEntityRenderer instance;

    public DisguisedEntityRenderer(EntityRendererFactory.Context context, DisguisedEntityModelProvider modelProvider) {
        super(context, modelProvider, new DisguisedEntity());
        modelProvider.renderer = this;
        instance = this;
    }

    public void render(Entity entity, IAnimatable animatable, float entityYaw, float partialTicks, MatrixStack stack,
                       VertexConsumerProvider bufferIn, int packedLightIn) {
        this.currentEntity = entity;
        super.render(entity, animatable, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    public static DisguisedEntityRenderer getInstance() {
        return instance;
    }
}
