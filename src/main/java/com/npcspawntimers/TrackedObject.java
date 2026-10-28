package com.npcspawntimers;

import lombok.Getter;

/**
 * An object spawn, with the identities it was seen under so it can be re-matched when the config changes.
 */
@Getter
class TrackedObject extends TrackedSpawn
{
	private final int id;
	private final String name;
	private final int impostorId;
	private final String impostorName;

	TrackedObject(int spawnTick, long spawnMillis, int id, String name, int impostorId, String impostorName)
	{
		super(spawnTick, spawnMillis);
		this.id = id;
		this.name = name;
		this.impostorId = impostorId;
		this.impostorName = impostorName;
	}

	boolean matches(TimerGroup group)
	{
		return group.matchesObject(id, name) || (impostorId != -1 && group.matchesObject(impostorId, impostorName));
	}
}
