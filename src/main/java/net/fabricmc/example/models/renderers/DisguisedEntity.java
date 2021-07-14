package net.fabricmc.example.models.renderers;

import net.fabricmc.example.NTHudMod;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

///nthud sm sm;%uuid%;gecko/bat.png;gecko/bat.animation.json;gecko/bat.geo.json

public class DisguisedEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        Entity currentEntity = DisguisedEntityRenderer.getInstance().currentEntity;
        String animation = NTHudMod.getInstance().getAnimations().get(currentEntity.getUuidAsString());
        Animation currentAnimation = event.getController().getCurrentAnimation();
        if (animation != null) { //???
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, false));
            NTHudMod.getInstance().getAnimations().remove(currentEntity.getUuidAsString());
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bat.fly", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bat.fly", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
