package com.npcspawntimers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimerMode
{
	COUNT_UP("Count up"),
	COUNTDOWN("Countdown");

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}
}
