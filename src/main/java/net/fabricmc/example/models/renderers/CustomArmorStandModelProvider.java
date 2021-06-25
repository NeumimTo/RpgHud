package net.fabricmc.example.models.renderers;

import net.fabricmc.example.NTHudMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CustomArmorStandModelProvider extends AnimatedGeoModel {

    public CustomArmorStandRenderer renderer;

    String modelLocation = "gecko/bat.geo.json";
    String textureLocation = "gecko/bat.png";
    String animationLocation = "gecko/bat.animation.json";

    @Override
    public Identifier getModelLocation(Object object) {
        Entity currentEntity = renderer.currentEntity;
        ModelData modelData = NTHudMod.getInstance().getCustomModels().get(currentEntity.getUuidAsString());
        return new Identifier("minecraft", modelLocation);
    }

    @Override
    public Identifier getTextureLocation(Object object) {
        Entity currentEntity = renderer.currentEntity;
        ModelData modelData = NTHudMod.getInstance().getCustomModels().get(currentEntity.getUuidAsString());
        return new Identifier("minecraft", textureLocation);
    }

    @Override
    public Identifier getAnimationFileLocation(Object animatable) {
        Entity currentEntity = renderer.currentEntity;
        ModelData modelData = NTHudMod.getInstance().getCustomModels().get(currentEntity.getUuidAsString());
        return new Identifier("minecraft", animationLocation);
    }

}