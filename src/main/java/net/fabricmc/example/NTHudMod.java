package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.networking.Networking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NTHudMod implements ModInitializer {

	private Map<Identifier, Long> currentCooldowns = new TreeMap<>();

	private static NTHudMod instance;

	private static final Logger logger = Logger.getLogger("NTHud");

	@Override
	public void onInitialize() {
		instance = this;

		System.out.println("Hello Fabric world!");

		Networking.initialize();
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
}
