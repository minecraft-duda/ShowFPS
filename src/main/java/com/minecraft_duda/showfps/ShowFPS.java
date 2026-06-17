package com.minecraft_duda.showfps;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowFPS implements ModInitializer {
	public static final String MOD_ID = "showfps";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("ShowFPS initialized!");
	}
}