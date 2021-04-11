package net.fabricmc.example.mixin;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.example.NTHudMod;
import net.fabricmc.example.configuration.RenderLocation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    private int iconsize = 12;

    @Inject(at = @At("TAIL"), method = "render")
    public void renderCDOverlay(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        matrixStack.push();

        RenderLocation location = RenderLocation.TOP_LEFT;
        int xpos;
        int ypos;
        switch (location) {
            case TOP_LEFT:
                xpos = 5;
                ypos = 5;
                break;
            case TOP_RIGHT:
                xpos = client.getWindow().getScaledWidth() - 5;
                ypos = 60;
                break;
            case BOTTOM_LEFT:
                xpos = 5;
                ypos = client.getWindow().getScaledHeight() - 5 - client.getWindow().getHeight();
                break;
            case BOTTOM_RIGHT:
                xpos = client.getWindow().getScaledWidth() - 5;
                ypos = client.getWindow().getScaledHeight() - 5 - client.getWindow().getHeight();
                break;
            default:
                return;
        }

        Map<Identifier, Long> cooldowns = NTHudMod.getInstance().getCooldowns();
        if(!cooldowns.isEmpty()) {

            List<Runnable> list = Lists.newArrayListWithExpectedSize(cooldowns.size());
            Iterator<Map.Entry<Identifier, Long>> it = cooldowns.entrySet().iterator();
            int h = 0;
            int w = 0;
            int i = 0;
            int arrangment = 2;

            while(it.hasNext()) {
                Map.Entry<Identifier, Long> next = it.next();

                if (next.getValue() <= System.currentTimeMillis()) {
                    it.remove();
                    continue;
                }

                int length = assumeTextWidth(next);
                int x0 = xpos;
                int y0 = ypos + ((3 * i + h) * arrangment);

                list.add(() -> {
                    renderCooldownIcon(matrixStack, next, x0, y0, length);
                    renderCooldownText(matrixStack, next, x0, y0, length);
                });
                w += length;
                h += iconsize;
                i = 0;
            }
            list.forEach(Runnable::run);
        }
        matrixStack.pop();
    }


    private void renderCooldownIcon(MatrixStack matrixStack, Map.Entry<Identifier, Long> sei, int x, int y, int length){
        MinecraftClient client = MinecraftClient.getInstance();
        client.getTextureManager().bindTexture(sei.getKey());

        drawTextureTransparent(matrixStack, x, y,32,32, x, y, 27, 27, 32, 32);

    }

    private void drawTextureTransparent(MatrixStack matrices, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        drawTextureTransparent(matrices, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight);
    }

    private void drawTextureTransparent(MatrixStack matrices, int x0, int y0, int x1, int y1, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
        drawTexturedQuadTransparent(matrices.peek().getModel(), x0, y0, x1, y1, z, (u + 0.0F) / (float)textureWidth, (u + (float)regionWidth) / (float)textureWidth, (v + 0.0F) / (float)textureHeight, (v + (float)regionHeight) / (float)textureHeight);
    }


    private static void drawTexturedQuadTransparent(Matrix4f matrices, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder b = tess.getBuffer();
        b.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        b.vertex(matrices, x0, y1, z).color(1, 1, 1, 0.5F).texture(u0, v1).next();
        b.vertex(matrices, x1, y1, z).color(1, 1, 1, 0.5F).texture(u1, v1).next();
        b.vertex(matrices, x1, y0, z).color(1, 1, 1, 0.5F).texture(u1, v0).next();
        b.vertex(matrices, x0, y0, z).color(1, 1, 1, 0.5F).texture(u0, v0).next();
        tess.draw();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();

        //BufferRenderer.draw(bufferBuilder);
    }

    private void renderCooldownText(MatrixStack matrixStack, Map.Entry<Identifier, Long> sei, int x, int y, int length){
        MinecraftClient client = MinecraftClient.getInstance();

        String durationString = parseRemainingDuration(sei);
        int durationColor = 223575;

        int x0 = x + 4 + iconsize - (length / 2);
        int y0 = y + 3;
        int x1 = x0;

        x1 = x1 + client.textRenderer.getWidth(durationString + " ");
        drawStringWithShadow(matrixStack,client.textRenderer, durationString, x1, y0, durationColor);
    }

    private String parseRemainingDuration(Map.Entry<Identifier, Long> sei) {
        long millis = sei.getValue() - System.currentTimeMillis();
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    private int assumeTextWidth(Map.Entry<Identifier, Long> entry){
        MinecraftClient client = MinecraftClient.getInstance();
        String s = "  "+ parseRemainingDuration(entry);
        return Math.max(client.textRenderer.getWidth(" 0:00"), client.textRenderer.getWidth(s));
    }
}
