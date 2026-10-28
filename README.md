# NPC Spawn Timers

A RuneLite plugin that draws a timer above NPCs and objects you choose, showing how long ago they spawned or counting down from a value you set.

## Features

- Track NPCs and objects by **name or ID**. Names are case-insensitive and support `*` wildcards (e.g. `*tree`, `Rune rock*`).
- **5 independent groups**, each with its own color, so different targets can be told apart at a glance.
- Two timer modes per group:
  - **Count up** – time elapsed since the NPC/object spawned.
  - **Countdown** – starts at a configured value and counts down to zero.
- Every group can measure time in **game ticks** or **seconds**.
- Works with game objects, wall objects, decorative objects and ground objects.

## Configuration

### Display

| Option | Description |
| --- | --- |
| Font | Font used to draw the timers. |
| Show unit suffix | Appends `t` to tick timers and `s` to second timers. |
| Show tenths of a second | Shows second timers with one decimal place (e.g. `12.4`). |
| Countdown at zero | What a countdown does when it runs out: hide the timer, stay at `0`, or keep counting into negative values. |
| NPC text height | Height of the timer above the NPC's head. |
| Object text height | Height of the timer above the top of the object. |

### Groups 1–5

| Option | Description |
| --- | --- |
| NPCs | Comma or newline separated NPC names or IDs. |
| Objects | Comma or newline separated object names or IDs. |
| Color | Color of this group's timers. |
| Mode | `Count up` or `Countdown`. |
| Countdown start | The value a countdown starts from, in the group's unit. |
| Unit | `Ticks` or `Seconds`. |

If an NPC or object matches more than one group, the lowest numbered group is used.

Timers longer than a minute are shown as `m:ss` (or `h:mm:ss`) when using seconds.

### Example

| Group | NPCs | Objects | Mode | Start | Unit |
| --- | --- | --- | --- | --- | --- |
| 1 | `Hill giant, Moss giant` | | Count up | | Seconds |
| 2 | | `Rune rocks` | Countdown | 200 | Ticks |

## Notes

- A timer starts when the game reports the spawn. Entities that load in when you log in or enter a new area count as spawning at that moment.
- Entities already loaded when the plugin is turned on get a timer the next time they spawn.
- Objects are re-sent by the game whenever the scene reloads (for example after walking far enough to load a new map region), which restarts their timers.
- Changes to NPC lists apply immediately to NPCs already on screen. Changes to object lists apply to objects that spawn after the change.

## Building

```
./gradlew build
./gradlew run   # launches RuneLite with the plugin loaded
```

## License

BSD 2-Clause, see [LICENSE](LICENSE).
