package com.npcspawntimers;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.FontType;
import net.runelite.client.config.Range;

@ConfigGroup(NpcSpawnTimersConfig.GROUP)
public interface NpcSpawnTimersConfig extends Config
{
	String GROUP = "npcspawntimers";
	int GROUP_COUNT = 5;

	@ConfigSection(
		name = "Display",
		description = "General display settings shared by all timer groups",
		position = 0
	)
	String displaySection = "display";

	@ConfigItem(
		keyName = "fontType",
		name = "Font",
		description = "Font used to draw the timers",
		section = displaySection,
		position = 0
	)
	default FontType fontType()
	{
		return FontType.BOLD;
	}

	@ConfigItem(
		keyName = "showUnitSuffix",
		name = "Show unit suffix",
		description = "Append 't' to tick timers and 's' to second timers",
		section = displaySection,
		position = 1
	)
	default boolean showUnitSuffix()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showTenths",
		name = "Show tenths of a second",
		description = "Show second timers with one decimal place",
		section = displaySection,
		position = 2
	)
	default boolean showTenths()
	{
		return false;
	}

	@ConfigItem(
		keyName = "countdownExpiry",
		name = "Countdown at zero",
		description = "What a countdown timer does once it reaches zero",
		section = displaySection,
		position = 3
	)
	default CountdownExpiry countdownExpiry()
	{
		return CountdownExpiry.SHOW_ZERO;
	}

	@Range(min = -500, max = 500)
	@ConfigItem(
		keyName = "npcTextOffset",
		name = "NPC text height",
		description = "Height of the timer above the NPC's head",
		section = displaySection,
		position = 4
	)
	default int npcTextOffset()
	{
		return 40;
	}

	@Range(min = -500, max = 500)
	@ConfigItem(
		keyName = "objectTextOffset",
		name = "Object text height",
		description = "Height of the timer above the top of the object",
		section = displaySection,
		position = 5
	)
	default int objectTextOffset()
	{
		return 0;
	}

	@ConfigSection(
		name = "Group 1",
		description = "NPCs and objects that share timer settings and a color",
		position = 1
	)
	String group1Section = "group1";

	@ConfigItem(
		keyName = "group1Npcs",
		name = "NPCs",
		description = "Comma or newline separated NPC names or IDs. Names support * wildcards",
		section = group1Section,
		position = 0
	)
	default String group1Npcs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "group1Objects",
		name = "Objects",
		description = "Comma or newline separated object names or IDs. Names support * wildcards",
		section = group1Section,
		position = 1
	)
	default String group1Objects()
	{
		return "";
	}

	@Alpha
	@ConfigItem(
		keyName = "group1Color",
		name = "Color",
		description = "Color of the timers in this group",
		section = group1Section,
		position = 2
	)
	default Color group1Color()
	{
		return Color.CYAN;
	}

	@ConfigItem(
		keyName = "group1Mode",
		name = "Mode",
		description = "Count up from the spawn, or count down from a starting value",
		section = group1Section,
		position = 3
	)
	default TimerMode group1Mode()
	{
		return TimerMode.COUNT_UP;
	}

	@Range(min = 1, max = 100000)
	@ConfigItem(
		keyName = "group1Start",
		name = "Countdown start",
		description = "Value the countdown starts from, in the unit selected below",
		section = group1Section,
		position = 4
	)
	default int group1Start()
	{
		return 100;
	}

	@ConfigItem(
		keyName = "group1Unit",
		name = "Unit",
		description = "Whether the timer is measured in game ticks or seconds",
		section = group1Section,
		position = 5
	)
	default TimerUnit group1Unit()
	{
		return TimerUnit.TICKS;
	}

	@ConfigSection(
		name = "Group 2",
		description = "NPCs and objects that share timer settings and a color",
		position = 2,
		closedByDefault = true
	)
	String group2Section = "group2";

	@ConfigItem(
		keyName = "group2Npcs",
		name = "NPCs",
		description = "Comma or newline separated NPC names or IDs. Names support * wildcards",
		section = group2Section,
		position = 0
	)
	default String group2Npcs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "group2Objects",
		name = "Objects",
		description = "Comma or newline separated object names or IDs. Names support * wildcards",
		section = group2Section,
		position = 1
	)
	default String group2Objects()
	{
		return "";
	}

	@Alpha
	@ConfigItem(
		keyName = "group2Color",
		name = "Color",
		description = "Color of the timers in this group",
		section = group2Section,
		position = 2
	)
	default Color group2Color()
	{
		return Color.GREEN;
	}

	@ConfigItem(
		keyName = "group2Mode",
		name = "Mode",
		description = "Count up from the spawn, or count down from a starting value",
		section = group2Section,
		position = 3
	)
	default TimerMode group2Mode()
	{
		return TimerMode.COUNT_UP;
	}

	@Range(min = 1, max = 100000)
	@ConfigItem(
		keyName = "group2Start",
		name = "Countdown start",
		description = "Value the countdown starts from, in the unit selected below",
		section = group2Section,
		position = 4
	)
	default int group2Start()
	{
		return 100;
	}

	@ConfigItem(
		keyName = "group2Unit",
		name = "Unit",
		description = "Whether the timer is measured in game ticks or seconds",
		section = group2Section,
		position = 5
	)
	default TimerUnit group2Unit()
	{
		return TimerUnit.TICKS;
	}

	@ConfigSection(
		name = "Group 3",
		description = "NPCs and objects that share timer settings and a color",
		position = 3,
		closedByDefault = true
	)
	String group3Section = "group3";

	@ConfigItem(
		keyName = "group3Npcs",
		name = "NPCs",
		description = "Comma or newline separated NPC names or IDs. Names support * wildcards",
		section = group3Section,
		position = 0
	)
	default String group3Npcs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "group3Objects",
		name = "Objects",
		description = "Comma or newline separated object names or IDs. Names support * wildcards",
		section = group3Section,
		position = 1
	)
	default String group3Objects()
	{
		return "";
	}

	@Alpha
	@ConfigItem(
		keyName = "group3Color",
		name = "Color",
		description = "Color of the timers in this group",
		section = group3Section,
		position = 2
	)
	default Color group3Color()
	{
		return Color.YELLOW;
	}

	@ConfigItem(
		keyName = "group3Mode",
		name = "Mode",
		description = "Count up from the spawn, or count down from a starting value",
		section = group3Section,
		position = 3
	)
	default TimerMode group3Mode()
	{
		return TimerMode.COUNT_UP;
	}

	@Range(min = 1, max = 100000)
	@ConfigItem(
		keyName = "group3Start",
		name = "Countdown start",
		description = "Value the countdown starts from, in the unit selected below",
		section = group3Section,
		position = 4
	)
	default int group3Start()
	{
		return 100;
	}

	@ConfigItem(
		keyName = "group3Unit",
		name = "Unit",
		description = "Whether the timer is measured in game ticks or seconds",
		section = group3Section,
		position = 5
	)
	default TimerUnit group3Unit()
	{
		return TimerUnit.TICKS;
	}

	@ConfigSection(
		name = "Group 4",
		description = "NPCs and objects that share timer settings and a color",
		position = 4,
		closedByDefault = true
	)
	String group4Section = "group4";

	@ConfigItem(
		keyName = "group4Npcs",
		name = "NPCs",
		description = "Comma or newline separated NPC names or IDs. Names support * wildcards",
		section = group4Section,
		position = 0
	)
	default String group4Npcs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "group4Objects",
		name = "Objects",
		description = "Comma or newline separated object names or IDs. Names support * wildcards",
		section = group4Section,
		position = 1
	)
	default String group4Objects()
	{
		return "";
	}

	@Alpha
	@ConfigItem(
		keyName = "group4Color",
		name = "Color",
		description = "Color of the timers in this group",
		section = group4Section,
		position = 2
	)
	default Color group4Color()
	{
		return Color.ORANGE;
	}

	@ConfigItem(
		keyName = "group4Mode",
		name = "Mode",
		description = "Count up from the spawn, or count down from a starting value",
		section = group4Section,
		position = 3
	)
	default TimerMode group4Mode()
	{
		return TimerMode.COUNT_UP;
	}

	@Range(min = 1, max = 100000)
	@ConfigItem(
		keyName = "group4Start",
		name = "Countdown start",
		description = "Value the countdown starts from, in the unit selected below",
		section = group4Section,
		position = 4
	)
	default int group4Start()
	{
		return 100;
	}

	@ConfigItem(
		keyName = "group4Unit",
		name = "Unit",
		description = "Whether the timer is measured in game ticks or seconds",
		section = group4Section,
		position = 5
	)
	default TimerUnit group4Unit()
	{
		return TimerUnit.TICKS;
	}

	@ConfigSection(
		name = "Group 5",
		description = "NPCs and objects that share timer settings and a color",
		position = 5,
		closedByDefault = true
	)
	String group5Section = "group5";

	@ConfigItem(
		keyName = "group5Npcs",
		name = "NPCs",
		description = "Comma or newline separated NPC names or IDs. Names support * wildcards",
		section = group5Section,
		position = 0
	)
	default String group5Npcs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "group5Objects",
		name = "Objects",
		description = "Comma or newline separated object names or IDs. Names support * wildcards",
		section = group5Section,
		position = 1
	)
	default String group5Objects()
	{
		return "";
	}

	@Alpha
	@ConfigItem(
		keyName = "group5Color",
		name = "Color",
		description = "Color of the timers in this group",
		section = group5Section,
		position = 2
	)
	default Color group5Color()
	{
		return Color.MAGENTA;
	}

	@ConfigItem(
		keyName = "group5Mode",
		name = "Mode",
		description = "Count up from the spawn, or count down from a starting value",
		section = group5Section,
		position = 3
	)
	default TimerMode group5Mode()
	{
		return TimerMode.COUNT_UP;
	}

	@Range(min = 1, max = 100000)
	@ConfigItem(
		keyName = "group5Start",
		name = "Countdown start",
		description = "Value the countdown starts from, in the unit selected below",
		section = group5Section,
		position = 4
	)
	default int group5Start()
	{
		return 100;
	}

	@ConfigItem(
		keyName = "group5Unit",
		name = "Unit",
		description = "Whether the timer is measured in game ticks or seconds",
		section = group5Section,
		position = 5
	)
	default TimerUnit group5Unit()
	{
		return TimerUnit.TICKS;
	}
}
