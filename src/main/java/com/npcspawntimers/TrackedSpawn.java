package com.npcspawntimers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
class TrackedSpawn
{
	private final int spawnTick;
	private final long spawnMillis;
	@Setter
	private TimerGroup group;
}
