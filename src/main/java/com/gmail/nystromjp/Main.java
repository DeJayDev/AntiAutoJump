package com.gmail.nystromjp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.Option;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() {

		System.out.println("Banning Auto-Jump...");

		ClientLifecycleEvents.CLIENT_STARTED.register(minecraftClient -> {
			MinecraftClient.getInstance().options.autoJump=false;
		});

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (MinecraftClient.getInstance().player!=null){
			}
		});

	}
}
