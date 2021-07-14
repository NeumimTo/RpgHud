package net.fabricmc.example.models.renderers;

import net.fabricmc.example.NTHudMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DisguisedEntityModelProvider extends AnimatedGeoModel {

    public DisguisedEntityRenderer renderer;

    @Override
    public Identifier getModelLocation(Object object) {
        Entity currentEntity = renderer.currentEntity;
        ModelData modelData = NTHudMod.getInstance().getCustomModels().get(currentEntity.getUuidAsString());
        return new Identifier("minecraft", modelData.modelLocation);
    }

    @Override
    public Identifier getTextureLocation(Object object) {
        Entity currentEntity = renderer.currentEntity;
        ModelData modelData = NTHudMod.getInstance().getCustomModels().get(currentEntity.getUuidAsString());
        return new Identifier("minecraft", modelData.textureLocation);
    }

    @Override
    public Identifier getAnimationFileLocation(Object animatable) {
        Entity currentEntity = renderer.currentEntity;
        ModelData modelData = NTHudMod.getInstance().getCustomModels().get(currentEntity.getUuidAsString());
        return new Identifier("minecraft", modelData.animationFileLocation);
    }
}