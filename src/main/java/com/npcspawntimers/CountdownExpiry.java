package com.npcspawntimers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CountdownExpiry
{
	HIDE("Hide timer"),
	SHOW_ZERO("Stay at zero"),
	COUNT_NEGATIVE("Keep counting (negative)");

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}
}
