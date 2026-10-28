package com.npcspawntimers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import org.junit.Test;

public class TimerGroupTest
{
	private static TimerGroup group(String npcs, String objects)
	{
		return new TimerGroup(npcs, objects, Color.WHITE, TimerMode.COUNT_UP, 10, TimerUnit.TICKS);
	}

	@Test
	public void matchesNpcByNameAndId()
	{
		TimerGroup group = group("Goblin, 3029\n Hill giant ", "");
		assertTrue(group.matchesNpc(1, "goblin"));
		assertTrue(group.matchesNpc(3029, "Something else"));
		assertTrue(group.matchesNpc(2, "Hill Giant"));
		assertFalse(group.matchesNpc(2, "Goblin chief"));
		assertFalse(group.matchesNpc(2, null));
		assertFalse(group.matchesObject(3029, "Goblin"));
	}

	@Test
	public void wildcards()
	{
		TimerGroup group = group("", "*tree, Rune rock*");
		assertTrue(group.matchesObject(1, "Magic tree"));
		assertTrue(group.matchesObject(1, "Rune rocks"));
		assertFalse(group.matchesObject(1, "Tree stump"));
	}

	@Test
	public void emptyGroup()
	{
		assertTrue(group(" , \n", null).isEmpty());
		assertFalse(group("", "123").isEmpty());
	}
}
