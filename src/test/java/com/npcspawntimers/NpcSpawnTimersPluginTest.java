package com.npcspawntimers;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class NpcSpawnTimersPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(NpcSpawnTimersPlugin.class);
		RuneLite.main(args);
	}
}
