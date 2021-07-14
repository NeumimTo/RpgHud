package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.models.renderers.DisguisedEntityRenderer;
import net.fabricmc.example.models.renderers.ModelData;
import net.fabricmc.example.networking.Networking;
import net.fabricmc.example.networking.NetworkingArgumentType;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import software.bernie.geckolib3.GeckoLib;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class NTHudMod implements ModInitializer, ClientModInitializer {

	private Map<Identifier, Long> currentCooldowns = new TreeMap<>();
	private Map<String, ModelData> customModels = new HashMap<>();
	private Map<String, String> animations = new HashMap<>();

	private static NTHudMod instance;
	public static DisguisedEntityRenderer disguisedEntityRenderer;
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

		//todo remove
		ClientCommandManager.DISPATCHER.register(
				literal("nthud")
					.then(argument("type", NetworkingArgumentType.type()).
							then(argument("data", MessageArgumentType.message())
							.executes(context -> {
								MinecraftClient client = MinecraftClient.getInstance();
								HitResult hit = client.crosshairTarget;

								if (hit == null) {
									return 0;
								}
								if (hit.getType() == HitResult.Type.ENTITY) {
									EntityHitResult entityHit = (EntityHitResult) hit;
									Entity entity = entityHit.getEntity();
									var data = context.getArgument("data", MessageArgumentType.MessageFormat.class);
									Networking.processChannelMessage(data.getContents().replaceAll("%uuid%", entity.getUuidAsString()));
									return 0;
								} else {
									return 0;
								}
							}))));

	}

	public Map<String, String> getAnimations() {
		return animations;
	}
}
