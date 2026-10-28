package com.npcspawntimers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class TimerTextTest
{
	private static String build(TimerMode mode, TimerUnit unit, int start, int ticks, long millis, CountdownExpiry expiry)
	{
		return TimerText.build(mode, unit, start, ticks, millis, expiry, false, false);
	}

	@Test
	public void countUpTicks()
	{
		assertEquals("0", build(TimerMode.COUNT_UP, TimerUnit.TICKS, 0, 0, 0, CountdownExpiry.SHOW_ZERO));
		assertEquals("17", build(TimerMode.COUNT_UP, TimerUnit.TICKS, 0, 17, 0, CountdownExpiry.SHOW_ZERO));
	}

	@Test
	public void countUpSeconds()
	{
		assertEquals("4", build(TimerMode.COUNT_UP, TimerUnit.SECONDS, 0, 0, 4999, CountdownExpiry.SHOW_ZERO));
		assertEquals("1:05", build(TimerMode.COUNT_UP, TimerUnit.SECONDS, 0, 0, 65_000, CountdownExpiry.SHOW_ZERO));
		assertEquals("1:00:01", build(TimerMode.COUNT_UP, TimerUnit.SECONDS, 0, 0, 3_601_000, CountdownExpiry.SHOW_ZERO));
	}

	@Test
	public void countdownTicks()
	{
		assertEquals("10", build(TimerMode.COUNTDOWN, TimerUnit.TICKS, 10, 0, 0, CountdownExpiry.SHOW_ZERO));
		assertEquals("1", build(TimerMode.COUNTDOWN, TimerUnit.TICKS, 10, 9, 0, CountdownExpiry.SHOW_ZERO));
		assertEquals("0", build(TimerMode.COUNTDOWN, TimerUnit.TICKS, 10, 12, 0, CountdownExpiry.SHOW_ZERO));
		assertEquals("-2", build(TimerMode.COUNTDOWN, TimerUnit.TICKS, 10, 12, 0, CountdownExpiry.COUNT_NEGATIVE));
		assertNull(build(TimerMode.COUNTDOWN, TimerUnit.TICKS, 10, 10, 0, CountdownExpiry.HIDE));
	}

	@Test
	public void countdownSecondsRoundsUp()
	{
		assertEquals("30", build(TimerMode.COUNTDOWN, TimerUnit.SECONDS, 30, 0, 0, CountdownExpiry.SHOW_ZERO));
		assertEquals("30", build(TimerMode.COUNTDOWN, TimerUnit.SECONDS, 30, 0, 999, CountdownExpiry.SHOW_ZERO));
		assertEquals("29", build(TimerMode.COUNTDOWN, TimerUnit.SECONDS, 30, 0, 1000, CountdownExpiry.SHOW_ZERO));
		assertEquals("1", build(TimerMode.COUNTDOWN, TimerUnit.SECONDS, 30, 0, 29_500, CountdownExpiry.SHOW_ZERO));
		assertEquals("0", build(TimerMode.COUNTDOWN, TimerUnit.SECONDS, 30, 0, 30_000, CountdownExpiry.SHOW_ZERO));
		assertEquals("-1", build(TimerMode.COUNTDOWN, TimerUnit.SECONDS, 30, 0, 31_000, CountdownExpiry.COUNT_NEGATIVE));
	}

	@Test
	public void tenthsAndSuffix()
	{
		assertEquals("4.2s", TimerText.build(TimerMode.COUNT_UP, TimerUnit.SECONDS, 0, 0, 4250, CountdownExpiry.SHOW_ZERO, true, true));
		assertEquals("1:05.3", TimerText.build(TimerMode.COUNT_UP, TimerUnit.SECONDS, 0, 0, 65_300, CountdownExpiry.SHOW_ZERO, true, true));
		assertEquals("-0.5", TimerText.formatSeconds(-5, true, false));
		assertEquals("12t", TimerText.build(TimerMode.COUNT_UP, TimerUnit.TICKS, 0, 12, 0, CountdownExpiry.SHOW_ZERO, false, true));
	}
}
