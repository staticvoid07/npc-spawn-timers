package com.npcspawntimers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import net.runelite.client.util.WildcardMatcher;

/**
 * A set of NPCs, objects and graphics that share a color and timer settings.
 */
@Getter
class TimerGroup
{
	private final Set<Integer> npcIds = new HashSet<>();
	private final List<String> npcNames = new ArrayList<>();
	private final Set<Integer> objectIds = new HashSet<>();
	private final List<String> objectNames = new ArrayList<>();
	private final Set<Integer> graphicIds = new HashSet<>();
	private final Color color;
	private final TimerMode mode;
	private final int start;
	private final TimerUnit unit;

	TimerGroup(String npcs, String objects, String graphics, Color color, TimerMode mode, int start, TimerUnit unit)
	{
		parseEntries(npcs, npcIds, npcNames);
		parseEntries(objects, objectIds, objectNames);
		parseEntries(graphics, graphicIds, new ArrayList<>());
		this.color = color;
		this.mode = mode;
		this.start = start;
		this.unit = unit;
	}

	static List<TimerGroup> fromConfig(NpcSpawnTimersConfig c)
	{
		List<TimerGroup> groups = Arrays.asList(
			new TimerGroup(c.group1Npcs(), c.group1Objects(), c.group1Graphics(), c.group1Color(), c.group1Mode(), c.group1Start(), c.group1Unit()),
			new TimerGroup(c.group2Npcs(), c.group2Objects(), c.group2Graphics(), c.group2Color(), c.group2Mode(), c.group2Start(), c.group2Unit()),
			new TimerGroup(c.group3Npcs(), c.group3Objects(), c.group3Graphics(), c.group3Color(), c.group3Mode(), c.group3Start(), c.group3Unit()),
			new TimerGroup(c.group4Npcs(), c.group4Objects(), c.group4Graphics(), c.group4Color(), c.group4Mode(), c.group4Start(), c.group4Unit()),
			new TimerGroup(c.group5Npcs(), c.group5Objects(), c.group5Graphics(), c.group5Color(), c.group5Mode(), c.group5Start(), c.group5Unit())
		);
		List<TimerGroup> nonEmpty = new ArrayList<>();
		for (TimerGroup group : groups)
		{
			if (!group.isEmpty())
			{
				nonEmpty.add(group);
			}
		}
		return nonEmpty;
	}

	boolean isEmpty()
	{
		return npcIds.isEmpty() && npcNames.isEmpty() && objectIds.isEmpty() && objectNames.isEmpty()
			&& graphicIds.isEmpty();
	}

	boolean matchesNpc(int id, String name)
	{
		return matches(npcIds, npcNames, id, name);
	}

	boolean matchesObject(int id, String name)
	{
		return matches(objectIds, objectNames, id, name);
	}

	boolean matchesGraphic(int id)
	{
		return graphicIds.contains(id);
	}

	private static boolean matches(Set<Integer> ids, List<String> names, int id, String name)
	{
		if (ids.contains(id))
		{
			return true;
		}
		if (name == null)
		{
			return false;
		}
		for (String pattern : names)
		{
			if (WildcardMatcher.matches(pattern, name))
			{
				return true;
			}
		}
		return false;
	}

	private static void parseEntries(String text, Set<Integer> ids, List<String> names)
	{
		if (text == null)
		{
			return;
		}
		for (String entry : text.split("[,\\n]"))
		{
			entry = entry.trim();
			if (entry.isEmpty())
			{
				continue;
			}
			if (entry.matches("\\d+"))
			{
				try
				{
					ids.add(Integer.parseInt(entry));
					continue;
				}
				catch (NumberFormatException ignored)
				{
					// too large to be an id, treat it as a name
				}
			}
			names.add(entry);
		}
	}
}
