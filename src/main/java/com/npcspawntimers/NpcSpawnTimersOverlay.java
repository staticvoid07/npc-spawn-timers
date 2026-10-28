package com.npcspawntimers;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.api.Renderable;
import net.runelite.api.TileObject;
import net.runelite.api.WorldView;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class NpcSpawnTimersOverlay extends Overlay
{
	private final NpcSpawnTimersPlugin plugin;
	private final NpcSpawnTimersConfig config;

	@Inject
	NpcSpawnTimersOverlay(NpcSpawnTimersPlugin plugin, NpcSpawnTimersConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		graphics.setFont(config.fontType().getFont());
		int tick = plugin.getTickCount();
		long now = System.currentTimeMillis();

		for (Map.Entry<NPC, TrackedSpawn> entry : plugin.getNpcs().entrySet())
		{
			TrackedSpawn spawn = entry.getValue();
			String text = timerText(spawn, tick, now);
			if (text == null)
			{
				continue;
			}

			NPC npc = entry.getKey();
			Point location = npc.getCanvasTextLocation(graphics, text, npc.getLogicalHeight() + config.npcTextOffset());
			if (location != null)
			{
				OverlayUtil.renderTextLocation(graphics, location, text, spawn.getGroup().getColor());
			}
		}

		for (Map.Entry<TileObject, TrackedObject> entry : plugin.getObjects().entrySet())
		{
			TileObject object = entry.getKey();
			WorldView worldView = object.getWorldView();
			if (worldView == null || object.getPlane() != worldView.getPlane())
			{
				continue;
			}

			TrackedObject spawn = entry.getValue();
			String text = timerText(spawn, tick, now);
			if (text == null)
			{
				continue;
			}

			Point location = object.getCanvasTextLocation(graphics, text, objectHeight(object) + config.objectTextOffset());
			if (location != null)
			{
				OverlayUtil.renderTextLocation(graphics, location, text, spawn.getGroup().getColor());
			}
		}

		return null;
	}

	private String timerText(TrackedSpawn spawn, int tick, long now)
	{
		TimerGroup group = spawn.getGroup();
		if (group == null)
		{
			return null;
		}

		return TimerText.build(group.getMode(), group.getUnit(), group.getStart(),
			tick - spawn.getSpawnTick(), now - spawn.getSpawnMillis(),
			config.countdownExpiry(), config.showTenths(), config.showUnitSuffix());
	}

	private static int objectHeight(TileObject object)
	{
		if (object instanceof GameObject)
		{
			Renderable renderable = ((GameObject) object).getRenderable();
			if (renderable != null)
			{
				return renderable.getModelHeight();
			}
		}
		return 0;
	}
}
