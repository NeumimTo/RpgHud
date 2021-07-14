package net.fabricmc.example.mixin;

import net.fabricmc.example.NTHudMod;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Inject(method = "getRenderer(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/render/entity/EntityRenderer;", at = @At(value = "HEAD"), cancellable = true)
    public <T extends Entity> void onGetRenderer(T entity, CallbackInfoReturnable<EntityRenderer> callbackInfoReturnable) {
        if (NTHudMod.getInstance().getCustomModels().containsKey(entity.getUuidAsString())) {
            callbackInfoReturnable.setReturnValue(NTHudMod.disguisedEntityRenderer);
        }
    }

}
