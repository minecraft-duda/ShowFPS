package com.minecraft_duda.showfps.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minecraft_duda.showfps.ShowFPS;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShowFPSConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("showfps.json");

	public boolean enabled = true;
	public boolean showBackground = true;
	public boolean colorByFps = true;

	public static ShowFPSConfig load() {
		ShowFPSConfig config = new ShowFPSConfig();
		if (Files.exists(CONFIG_PATH)) {
			try {
				String json = Files.readString(CONFIG_PATH);
				JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
				if (obj.has("enabled")) config.enabled = obj.get("enabled").getAsBoolean();
				if (obj.has("showBackground")) config.showBackground = obj.get("showBackground").getAsBoolean();
				if (obj.has("colorByFps")) config.colorByFps = obj.get("colorByFps").getAsBoolean();
			} catch (IOException e) {
				ShowFPS.LOGGER.error("Failed to load config", e);
			}
		} else {
			config.save();
		}
		return config;
	}

	public void save() {
		try {
			JsonObject obj = new JsonObject();
			obj.addProperty("enabled", enabled);
			obj.addProperty("showBackground", showBackground);
			obj.addProperty("colorByFps", colorByFps);
			Files.writeString(CONFIG_PATH, GSON.toJson(obj));
		} catch (IOException e) {
			ShowFPS.LOGGER.error("Failed to save config", e);
		}
	}
}