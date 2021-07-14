package net.fabricmc.example.mixin;

import net.fabricmc.example.NTHudMod;
import net.fabricmc.example.models.renderers.DisguisedEntityModelProvider;
import net.fabricmc.example.models.renderers.DisguisedEntityRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.file.AnimationFile;
import software.bernie.geckolib3.file.AnimationFileLoader;
import software.bernie.geckolib3.file.GeoModelLoader;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void MinecraftClient(CallbackInfo callbackInfo) {
        DisguisedEntityModelProvider provider = new DisguisedEntityModelProvider();

        MinecraftClient instance = MinecraftClient.getInstance();
        EntityRendererFactory.Context c = new EntityRendererFactory.Context(
                instance.getEntityRenderDispatcher(),
                instance.getItemRenderer(),
                instance.getResourceManager(),
                instance.getEntityModelLoader(),
                instance.inGameHud.getTextRenderer()
        );
        NTHudMod.disguisedEntityRenderer = new DisguisedEntityRenderer(c, provider);

        try {
            Identifier modelId = new Identifier("minecraft", "gecko/bat.geo.json");
            GeoModel geoModel = new GeoModelLoader().loadModel(instance.getResourceManager(), modelId);

            HashMap<Identifier, GeoModel> map = new HashMap<>(GeckoLibCache.getInstance().getGeoModels());
            map.put(modelId, geoModel);
            Field geoModels = GeckoLibCache.getInstance().getClass().getDeclaredField("geoModels");
            geoModels.setAccessible(true);
            geoModels.set(GeckoLibCache.getInstance(), map);

            Identifier animId = new Identifier("minecraft", "gecko/bat.animation.json");
            AnimationFile animationFile = new AnimationFileLoader().loadAllAnimations(GeckoLibCache.getInstance().parser, animId, instance.getResourceManager());
            Map<Identifier, AnimationFile> mapp = new HashMap<>(GeckoLibCache.getInstance().getAnimations());
            mapp.put(animId, animationFile);
            geoModels = GeckoLibCache.getInstance().getClass().getDeclaredField("animations");
            geoModels.setAccessible(true);
            geoModels.set(GeckoLibCache.getInstance(), mapp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
