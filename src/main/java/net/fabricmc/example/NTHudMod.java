package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.models.renderers.CustomArmorStandModelProvider;
import net.fabricmc.example.models.renderers.CustomArmorStandRenderer;
import net.fabricmc.example.models.renderers.ModelData;
import net.fabricmc.example.networking.Networking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import software.bernie.example.client.renderer.entity.ReplacedCreeperRenderer;
import software.bernie.geckolib3.GeckoLib;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NTHudMod implements ModInitializer, ClientModInitializer {

	private Map<Identifier, Long> currentCooldowns = new TreeMap<>();
	private Map<String, ModelData> customModels = new HashMap<>();
	private static NTHudMod instance;
	public static CustomArmorStandRenderer customArmorStandRenderer;
	private static final Logger logger = Logger.getLogger("NTHud");

	@Override
	public void onInitialize() {
		instance = this;

		Networking.initialize();
		GeckoLib.initialize();

		System.out.println("NTHUD mod initialized!");

	}

	public static NTHudMod getInstance() {
		return instance;
	}

	public static void log(String msg) {
		logger.info(msg);
	}

	public static void err(String msg, Throwable t) {
		logger.log(Level.SEVERE, msg, t);
	}

	public static void err(String msg) {
		logger.log(Level.SEVERE, msg);
	}

	public Map<Identifier, Long> getCooldowns() {
		return currentCooldowns;
	}

	public Map<String, ModelData> getCustomModels() {
		return customModels;
	}

	public void setCustomModels(Map<String, ModelData> customModels) {
		this.customModels = customModels;
	}

	@Override
	public void onInitializeClient() {

	}
}
