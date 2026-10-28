package com.npcspawntimers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimerUnit
{
	TICKS("Ticks"),
	SECONDS("Seconds");

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}
}
