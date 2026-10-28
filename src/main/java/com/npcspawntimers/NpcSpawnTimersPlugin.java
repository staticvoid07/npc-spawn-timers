package com.npcspawntimers;

import com.google.inject.Provides;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.GraphicsObject;
import net.runelite.api.NPC;
import net.runelite.api.ObjectComposition;
import net.runelite.api.TileObject;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "NPC Spawn Timers",
	description = "Shows how long ago NPCs, objects and graphics spawned, or counts down from a set time",
	tags = {"npc", "object", "spawn", "respawn", "timer", "countdown", "ticks", "graphics"}
)
public class NpcSpawnTimersPlugin extends Plugin
{
	@Inject private Client client;
	@Inject private ClientThread clientThread;
	@Inject private NpcSpawnTimersConfig config;
	@Inject private OverlayManager overlayManager;
	@Inject private NpcSpawnTimersOverlay overlay;

	@Getter(AccessLevel.PACKAGE)
	private final Map<NPC, TrackedSpawn> npcs = new HashMap<>();

	@Getter(AccessLevel.PACKAGE)
	private final Map<TileObject, TrackedObject> objects = new HashMap<>();

	@Getter(AccessLevel.PACKAGE)
	private final Map<GraphicsObject, TrackedSpawn> graphics = new HashMap<>();

	@Getter(AccessLevel.PACKAGE)
	private int tickCount;

	private List<TimerGroup> groups = Collections.emptyList();

	@Override
	protected void startUp()
	{
		groups = TimerGroup.fromConfig(config);
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		npcs.clear();
		objects.clear();
		graphics.clear();
		groups = Collections.emptyList();
	}

	@Provides
	NpcSpawnTimersConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NpcSpawnTimersConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!NpcSpawnTimersConfig.GROUP.equals(event.getGroup()))
		{
			return;
		}

		clientThread.invoke(() ->
		{
			groups = TimerGroup.fromConfig(config);
			npcs.forEach((npc, spawn) -> spawn.setGroup(findNpcGroup(npc)));
			objects.values().forEach(spawn -> spawn.setGroup(findObjectGroup(spawn)));
			graphics.forEach((graphic, spawn) -> spawn.setGroup(findGraphicGroup(graphic.getId())));
		});
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		GameState state = event.getGameState();
		if (state == GameState.LOADING)
		{
			// objects are re-sent as spawns once the new scene has loaded
			objects.clear();
			graphics.clear();
		}
		else if (state == GameState.LOGIN_SCREEN || state == GameState.HOPPING)
		{
			npcs.clear();
			objects.clear();
			graphics.clear();
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		tickCount++;
		// graphics objects have no despawn event, they are only flagged as finished
		graphics.keySet().removeIf(GraphicsObject::finished);
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		NPC npc = event.getNpc();
		TrackedSpawn spawn = new TrackedSpawn(spawnTick(), System.currentTimeMillis());
		spawn.setGroup(findNpcGroup(npc));
		npcs.put(npc, spawn);
	}

	@Subscribe
	public void onNpcChanged(NpcChanged event)
	{
		NPC npc = event.getNpc();
		TrackedSpawn spawn = npcs.get(npc);
		if (spawn != null)
		{
			spawn.setGroup(findNpcGroup(npc));
		}
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		npcs.remove(event.getNpc());
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		objectSpawned(event.getGameObject());
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		objects.remove(event.getGameObject());
	}

	@Subscribe
	public void onWallObjectSpawned(WallObjectSpawned event)
	{
		objectSpawned(event.getWallObject());
	}

	@Subscribe
	public void onWallObjectDespawned(WallObjectDespawned event)
	{
		objects.remove(event.getWallObject());
	}

	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
	{
		objectSpawned(event.getDecorativeObject());
	}

	@Subscribe
	public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
	{
		objects.remove(event.getDecorativeObject());
	}

	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned event)
	{
		objectSpawned(event.getGroundObject());
	}

	@Subscribe
	public void onGroundObjectDespawned(GroundObjectDespawned event)
	{
		objects.remove(event.getGroundObject());
	}

	@Subscribe
	public void onGraphicsObjectCreated(GraphicsObjectCreated event)
	{
		GraphicsObject graphic = event.getGraphicsObject();
		TimerGroup group = findGraphicGroup(graphic.getId());
		if (group != null)
		{
			log.debug("Graphic {} created at {} on tick {}", graphic.getId(), graphic.getLocation(), spawnTick());
			TrackedSpawn spawn = new TrackedSpawn(spawnTick(), System.currentTimeMillis());
			spawn.setGroup(group);
			graphics.put(graphic, spawn);
		}
	}

	private void objectSpawned(TileObject object)
	{
		if (object == null || groups.isEmpty())
		{
			return;
		}

		ObjectComposition comp = client.getObjectDefinition(object.getId());
		if (comp == null)
		{
			return;
		}

		int impostorId = -1;
		String impostorName = null;
		if (comp.getImpostorIds() != null)
		{
			ObjectComposition impostor = comp.getImpostor();
			if (impostor != null)
			{
				impostorId = impostor.getId();
				impostorName = cleanName(impostor.getName());
			}
		}

		TrackedObject spawn = new TrackedObject(spawnTick(), System.currentTimeMillis(),
			object.getId(), cleanName(comp.getName()), impostorId, impostorName);
		TimerGroup group = findObjectGroup(spawn);
		if (group != null)
		{
			spawn.setGroup(group);
			objects.put(object, spawn);
		}
	}

	/**
	 * Spawns are received before the GameTick of the tick they belong to, so they count from the upcoming tick.
	 */
	private int spawnTick()
	{
		return tickCount + 1;
	}

	private TimerGroup findNpcGroup(NPC npc)
	{
		String name = cleanName(npc.getName());
		for (TimerGroup group : groups)
		{
			if (group.matchesNpc(npc.getId(), name))
			{
				return group;
			}
		}
		return null;
	}

	private TimerGroup findObjectGroup(TrackedObject spawn)
	{
		for (TimerGroup group : groups)
		{
			if (spawn.matches(group))
			{
				return group;
			}
		}
		return null;
	}

	private TimerGroup findGraphicGroup(int id)
	{
		for (TimerGroup group : groups)
		{
			if (group.matchesGraphic(id))
			{
				return group;
			}
		}
		return null;
	}

	private static String cleanName(String name)
	{
		return name == null ? null : Text.removeTags(name).replace(' ', ' ');
	}
}
