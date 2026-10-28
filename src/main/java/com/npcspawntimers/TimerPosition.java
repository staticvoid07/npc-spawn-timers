package com.npcspawntimers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimerPosition
{
	ABOVE("Above"),
	GROUND("On the ground");

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}
}
