package com.npcspawntimers;

final class TimerText
{
	private TimerText()
	{
	}

	/**
	 * Builds the text shown for a timer, or returns null if the timer should be hidden.
	 */
	static String build(TimerMode mode, TimerUnit unit, int start, int elapsedTicks, long elapsedMillis,
		CountdownExpiry expiry, boolean tenths, boolean suffix)
	{
		long value;
		if (unit == TimerUnit.TICKS)
		{
			value = mode == TimerMode.COUNTDOWN ? start - elapsedTicks : elapsedTicks;
		}
		else
		{
			long step = tenths ? 100 : 1000;
			value = mode == TimerMode.COUNTDOWN
				// countdowns round up so they only read zero once the time has actually run out
				? Math.floorDiv(start * 1000L - elapsedMillis + step - 1, step)
				: Math.floorDiv(elapsedMillis, step);
		}

		if (mode == TimerMode.COUNTDOWN && value <= 0)
		{
			switch (expiry)
			{
				case HIDE:
					return null;
				case SHOW_ZERO:
					value = 0;
					break;
				default:
					break;
			}
		}

		return unit == TimerUnit.TICKS ? formatTicks(value, suffix) : formatSeconds(value, tenths, suffix);
	}

	static String formatTicks(long ticks, boolean suffix)
	{
		return suffix ? ticks + "t" : Long.toString(ticks);
	}

	/**
	 * @param value whole seconds, or tenths of a second when {@code tenths} is set
	 */
	static String formatSeconds(long value, boolean tenths, boolean suffix)
	{
		StringBuilder sb = new StringBuilder();
		if (value < 0)
		{
			sb.append('-');
		}
		long abs = Math.abs(value);
		long totalSeconds = tenths ? abs / 10 : abs;

		if (totalSeconds >= 3600)
		{
			sb.append(totalSeconds / 3600).append(':')
				.append(String.format("%02d:%02d", (totalSeconds / 60) % 60, totalSeconds % 60));
		}
		else if (totalSeconds >= 60)
		{
			sb.append(totalSeconds / 60).append(':').append(String.format("%02d", totalSeconds % 60));
		}
		else
		{
			sb.append(totalSeconds);
		}

		if (tenths)
		{
			sb.append('.').append(abs % 10);
		}
		if (suffix && totalSeconds < 60)
		{
			sb.append('s');
		}
		return sb.toString();
	}
}
