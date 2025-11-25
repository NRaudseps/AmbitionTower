package o1.game

import o1.game.entities.LockedDoor
import o1.game.entities.npc.boss.BossDialogue
import o1.game.entities.npc.{BootlickerGolem, Mob, PrinterJamSlime}
import o1.game.entities.player.Player
import o1.game.item.AccessCard
import o1.game.item.consumables.forms.{AccessForm, CakeForm, VIPAccessForm}
import o1.game.item.consumables.potions.{AHPotion, DPotion, HPotion, RPotion, SPotion, SusPotion}
import o1.game.stages.DialogueArea
import o1.game.stages.overworldArea.{Elevator1F, Elevator2F, Elevator3F, OverworldArea}

import scala.collection.mutable.Buffer


/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Overworld(val currentGame: Game):

  private val f1Lobby = OverworldArea("1F", "1F - THE LOBBY\n\nYou step into the pristine marble lobby of Ambition Tower, the air thick with artificial cheer and recycled confidence. Fluorescent lights hum overhead, reflecting off motivational posters—“Teamwork Makes the Dream Work!”—while a receptionist drone greets you with a smile too wide to be real.\n\nThe floor stretches out like an endless waiting room. Every desk demands a signature, every door requires an ID badge you don’t yet possess. Somewhere in this bureaucratic maze lies your first promotion...\n\nYou notice there is an elevator on the north side of the room.")
  private val f2MazeCenter = OverworldArea("2FCenter", "2F - THE CUBICLES - CENTER\n\nYou enter a wide, circular chamber where three corridors branch outward like veins. The East, and West wings are choked with endless cubicles, murmuring with the quiet despair of overworked employees.\n\nThe South Wing stands apart — heavy metal doors marked PRINT OPERATIONS, machinery grinding behind them, leaking the sharp smell of ink and molten plastic.\n\nA flickering map in the center shifts unpredictably, as if the floor itself refuses to stay still. The Tower watches. The Tower waits.")
  private val f2West1 = OverworldArea("2FWest1", "2F - THE CUBICLES - WEST WING 1\n\nThe corridor stretches into a monotonous grid of cubicles, the hum of fluorescent lights blending with distant typing. Walls are lined with half-empty whiteboards, scrawled with deadlines that have long passed. A faint draft carries the scent of stale coffee and paper. Somewhere further down, a light flickers — a signal, perhaps, or just another broken bulb.\n\nKeep moving. The path ahead is waiting.")
  private val f2West1South1 = OverworldArea("2FAccess", "2F - THE CUBICLES - WEST WING 2\n\nThe cubicles become denser here, corners stacking into narrow passages. Chairs sit abandoned, swivel slowly as if moved by unseen hands. The hum of machinery from the South Wing drifts faintly, distant but constant, reminding you the maze is larger than it seems. The corridor narrows slightly, and the flickering light grows steadier.\n\nDo not linger — the floor watches those who pause too long.")
  private val f2South1 = OverworldArea("2FMonster1", "2F - THE CUBICLES - PRINTING HALLWAY 1\n\nThe corridor narrows, walls lined with racks of humming printers and stacks of smudged paper. The air is heavy with the sharp scent of ink and overheated machinery.\n\nShadows shift in the corner of your vision — or maybe the floor itself is playing tricks...")
  private val f2South2 = OverworldArea("2FMonster2", "2F - THE CUBICLES - PRINTING HALLWAY 2\n\nA narrow passage filled with the steady churn of machines. Printers line both sides, spitting out endless sheets that pile on the floor like snowdrifts of unfinished work.\n\nAhead, a single red status light blinks in a slow, deliberate rhythm — almost like it’s beckoning you deeper.")
  private val f2South2East1 = OverworldArea("2FStorage", "2F - THE CUBICLES - STORAGE ROOM\n\nThe hallway opens into a cramped storage space stacked high with boxes of toner, reams of paper, and obsolete office equipment. Dust hangs in the air like a slow-falling fog. A single utility lamp flickers overhead, revealing shelves cluttered with forgotten supplies. Something glints among the clutter — a tool left behind by someone who clearly didn’t come back for it...")
  private val f2East1 = OverworldArea("2FDoor", "2F - THE CUBICLES - EAST WING 1\n\nThe East Wing stretches out in a straight, quiet line of dim overhead lights and empty cubicle walls. The air here feels heavier, as though the hallway itself is waiting for something to happen.\n\nAt the far end, a reinforced metal door blocks the way forward, its AUTHORIZED ACCESS ONLY warning dulled with age but impossible to ignore. The keypad beside it pulses with a cold, distant light, and the door’s thick frame feels unwelcoming.")
  private val f2East2 = OverworldArea("2FHallway", "2F - THE CUBICLES - EAST WING 2\n\nAs you step into this part of the corridor, the atmosphere lightens without warning. The lights glow a little steadier here, casting cleaner lines along the walls, and the air carries a faint coolness that feels unexpectedly calming. The cubicles are tidier, papers stacked neatly, chairs aligned as though someone actually cared about keeping order.\n\nSomething about this place gives the sense that you’re close to something important — either just ahead or waiting for you once you’ve found what you came for.")
  private val f2East2South1 = OverworldArea("2FForm", "2F - THE CUBICLES - EAST WING 3\n\nThe corridor opens into a small, quiet nook — the farthest point of the East Wing. The lights here are dimmer, calmer, as though this space has been waiting undisturbed for a long time. A single desk stands against the wall, drawers half-open, something important resting within reach.\n\nThe air feels still, expectant. There’s nothing left beyond this point — only the faint pull of the tower behind you, urging you to turn back.")
  private val f3East1 = OverworldArea("3FHallway1", "3F - VIP AREA - TO MR. BIG BOSS' ROOM\n\nYou step into a vast, open space, the ceiling high and polished, reflecting light from sweeping floor-to-ceiling windows. Plush seating clusters in careful arrangements, and the faint scent of expensive coffee and polished wood lingers in the air.\n\nEvery corner of the room seems curated for comfort — yet there’s a subtle, underlying tension. The carpet muffles footsteps, but the silence is heavy, almost deliberate, as if the floor itself is listening...")
  private val f3East2 = OverworldArea("3FHallway2", "3F - VIP AREA - VIP RECEPTION HALL'S ENTRANCE\n\nThe room opens into another stretch of polished floors and soft lighting. Plush chairs and low tables are arranged with precise care, the quiet hum of the tower filling the space.\n\nLuxury and vigilance coexist. Keep moving; the path ahead waits.")
  private val f3East2North1 = OverworldArea("3FHallway21", "3F - VIP AREA - VIP RECEPTION HALL'S NORTH WALL 1\n\nYou hug the edges of the hall, pressed close to the walls where shadows gather and the light dims. Chairs and tables form narrow lanes, forcing your steps along a precise path.\n\nThe hum of the tower feels heavier here, vibrating through the polished floor like a warning. Reflections in the glass twist unnaturally, as if something unseen moves just beyond your vision...")
  private val f3East2South1 = OverworldArea("3FHallway22", "3F - VIP AREA - VIP RECEPTION HALL'S SOUTH WALL 1\n\nYou hug the edges of the hall, pressed close to the walls where shadows gather and the light dims. Chairs and tables form narrow lanes, forcing your steps along a precise path.\n\nThe hum of the tower feels heavier here, vibrating through the polished floor like a warning. Reflections in the glass twist unnaturally, as if something unseen moves just beyond your vision...")
  private val f3East3 = OverworldArea("3FHallway3", "3F - VIP AREA - VIP RECEPTION HALL'S CENTER\n\nYou find yourself in the heart of the expansive hall. Light pours from overhead panels, bouncing across polished marble floors. Plush seating clusters in the center, forming islands of comfort surrounded by open space. Here, the emptiness around you emphasizes both grandeur and isolation. Every footstep echoes softly. The hall stretches endlessly, a living stage for your ascent.")
  private val f3East3North1 = OverworldArea("3FHallway31", "3F - VIP AREA - VIP RECEPTION HALL'S NORTH WALL 2\n\nThe corridor along the hall’s edge narrows as the walls rise, lined with glass panels and shadowed alcoves.\n\nThe polished floor vibrates faintly underfoot, a low, insistent rhythm that hints at movement nearby. Reflections shift in ways the light shouldn’t allow, and the quiet luxury around you seems to hold its breath.")
  private val f3East3South1 = OverworldArea("3FHallway32", "3F - VIP AREA - VIP RECEPTION HALL'S SOUTH WALL 2\n\nThe corridor along the hall’s edge narrows as the walls rise, lined with glass panels and shadowed alcoves.\n\nThe polished floor vibrates faintly underfoot, a low, insistent rhythm that hints at movement nearby. Reflections shift in ways the light shouldn’t allow, and the quiet luxury around you seems to hold its breath.")
  private val f3East4 = OverworldArea("3FHallway4", "3F - VIP AREA - VIP RECEPTION HALL'S EXIT\n\nYou step near the archway, leaving the familiar hall behind. Ahead, a grander space stretches out, shimmering with gold and marble. Shadows cling to the edges, and the air hums with quiet intent.\nSomething waits ahead — opulent, watchful, and dangerous.")
  private val f3East4North1 = OverworldArea("3FHallway41", "3F - VIP AREA - VIP RECEPTION HALL'S NORTH WALL 3\n\nThe hall narrows sharply along the wall, where tall partitions and heavy curtains block most of the light. The furniture is sparse here — a few scattered chairs and tables, but the shadows between them feel thick, almost solid.\n\nEvery reflection in the glass seems delayed, distorted, as if something is tracking you just out of sight...")
  private val f3East4South1 = OverworldArea("3FHallway42", "3F - VIP AREA - VIP RECEPTION HALL'S SOUTH WALL 3\n\nThe hall narrows sharply along the wall, where tall partitions and heavy curtains block most of the light. The furniture is sparse here — a few scattered chairs and tables, but the shadows between them feel thick, almost solid.\n\nEvery reflection in the glass seems delayed, distorted, as if something is tracking you just out of sight...")
  private val f3East5 = OverworldArea("3FHallway5", "3F - VIP AREA - AMBITION HALL'S ENTRANCE\n\nYou step into a vast chamber that dwarfs everything you’ve seen so far. Marble floors gleam like liquid gold, and towering columns rise to a vaulted ceiling veined with gilded patterns. The space feels almost too perfect.The air is heavy, scented faintly with expensive perfumes and something sharper, metallic, like power waiting to be wielded. Shadows stretch unnaturally across the hall, shifting as if the room itself breathes.\n\nThis is where the glory of the Tower lies - The Ambition Hall.")
  private val f3East5North1 = OverworldArea("3FHallway51", "3F - VIP AREA - AMBITION HALL'S NORTH WALL 1\n\nYou move along the edges of the hall, where towering columns and polished walls stretch far above. The carpets are thick, the shadows deeper here, and the space around you hums with a quiet, watchful presence.\n\nOrnate furniture is arranged sparsely along the perimeter, forcing your steps into narrow lanes that feel carefully guided. You think something is here...")
  private val f3East5South1 = OverworldArea("3FHallway52", "3F - VIP AREA - AMBITION HALL'S SOUTH WALL 1\n\nYou move along the edges of the hall, where towering columns and polished walls stretch far above. The carpets are thick, the shadows deeper here, and the space around you hums with a quiet, watchful presence.\n\nOrnate furniture is arranged sparsely along the perimeter, forcing your steps into narrow lanes that feel carefully guided. You think something is here...")
  private val f3East6 = OverworldArea("3FHallway6", "3F - VIP AREA - AMBITION HALL'S CENTER\n\nYou step into the heart of the chamber. The ceiling soars above, gilded patterns catching the light and casting shifting shadows across the polished marble floor. Plush seating clusters are scattered like islands, but the space between them feels vast, deliberate, almost unnerving in its emptiness.\n\nEvery echo of your footsteps seems magnified, as if the hall itself listens.")
  private val f3East6North1 = OverworldArea("3FHallway61", "3F - VIP AREA - AMBITION HALL'S NORTH WALL 2\n\nYou move along the hall’s towering edges, where gilded columns and polished walls stretch above. Shadows cling tightly here, and the carpeted path funnels your steps, guiding you through the perimeter.\n\nIt is still not safe here; something is still watching you. In fact, you have always been watched since who-knows-when...")
  private val f3East6South1 = OverworldArea("3FHallway62", "3F - VIP AREA - AMBITION HALL'S SOUTH WALL 2\n\nYou move along the hall’s towering edges, where gilded columns and polished walls stretch above. Shadows cling tightly here, and the carpeted path funnels your steps, guiding you through the perimeter.\n\nIt is still not safe here; something is still watching you. In fact, you have always been watched since who-knows-when...")
  private val f3East7 = OverworldArea("3FHallway7", "3F - VIP AREA - AMBITION HALL - BEFORE MR. BIG BOSS' ROOM\n\nThe hall opens into a soaring archway, gilded and immense, framed by columns that stretch beyond sight. Light from unseen sources glints off polished marble, casting long, sharp shadows that seem to reach toward you.\n\nThe air grows heavier, charged with expectation. Every echo of your footsteps resounds like a drumbeat, announcing your approach. Beyond the arch, the space narrows into darkness — or perhaps it simply swallows the light, as if something waits, watching.\n\nThis is no ordinary doorway.\n\nBeyond it stands Mr. Big Boss, and the Tower holds its breath for what comes next...")
  private val f3East7North1 = OverworldArea("3FHallway71", "3F - VIP AREA - AMBITION HALL'S NORTH WALL 3\n\nYou hug the edges of the hall, the towering columns and polished walls looming overhead. Light glints off gilded surfaces, but shadows stretch unnaturally along the floor, shifting as if alive.\n\nWhispers of movement echo faintly from the far corners, reminding you that something waits just out of sight.")
  private val f3East7South1 = OverworldArea("3FHallway72", "3F - VIP AREA - AMBITION HALL'S SOUTH WALL 3\n\nYou hug the edges of the hall, the towering columns and polished walls looming overhead. Light glints off gilded surfaces, but shadows stretch unnaturally along the floor, shifting as if alive.\n\nWhispers of movement echo faintly from the far corners, reminding you that something waits just out of sight.")
  private val f3BossRoom = OverworldArea("3FBossRoom", "3F - MR. BIG BOSS' ABODE\n\nA chamber unlike any other in the tower. The ceiling stretches impossibly high, crisscrossed with golden beams and shadowed vaults. The floor is polished obsidian, reflecting the glittering light of massive chandeliers that hang like frozen stars.\n\nAt the far end, a massive desk dominates the space, impossibly wide and cluttered with papers, ledgers, and glowing screens.\n\nThis is the heart of the Tower’s Ambition.")

  /** The character that the player controls in the game. */


  Elevator1F.setNeighbor("south", f1Lobby)

  f1Lobby.setNeighbors(Vector("north" -> Elevator1F))


  Elevator2F.setNeighbor("south", f2MazeCenter)

  f2MazeCenter.setNeighbors(Vector("north" -> Elevator2F, "east" -> f2East1, "south" -> f2South1, "west" -> f2West1))

  f2West1.setNeighbors(Vector("east" -> f2MazeCenter, "south" -> f2West1South1))
  f2West1South1.setNeighbors(Vector("north" -> f2West1))

  f2South1.setNeighbors(Vector("north" -> f2MazeCenter, "south" -> f2South2))
  f2South2.setNeighbors(Vector("north" -> f2South1, "east" -> f2South2East1))
  f2South2East1.setNeighbors(Vector("west" -> f2South2))

  f2East1.setNeighbors(Vector("east" -> f2East2, "west" -> f2MazeCenter))
  f2East2.setNeighbors(Vector("south" -> f2East2South1, "west" -> f2East1))
  f2East2South1.setNeighbors(Vector("north" -> f2East2))


  Elevator3F.setNeighbor("east", f3East1)

  f3East1.setNeighbors(Vector("east" -> f3East2, "west" -> Elevator3F))

  f3East2North1.setNeighbors(Vector("east" -> f3East3North1, "south" -> f3East2))
  f3East2.setNeighbors(Vector("north" -> f3East2North1, "east" -> f3East3, "west" -> f3East1 ,"south" -> f3East2South1))
  f3East2South1.setNeighbors(Vector("north" -> f3East2, "east" -> f3East3South1))

  f3East3North1.setNeighbors(Vector("east" -> f3East4North1, "south" -> f3East3, "west" -> f3East2North1))
  f3East3.setNeighbors(Vector("north" -> f3East3North1, "east" -> f3East4, "west" -> f3East2 , "south" -> f3East3South1))
  f3East3South1.setNeighbors(Vector("north" -> f3East3, "east" -> f3East4South1, "west" -> f3East2South1))

  f3East4North1.setNeighbors(Vector("south" -> f3East4, "west" -> f3East3North1))
  f3East4.setNeighbors(Vector("north" -> f3East4North1, "east" -> f3East5, "west" -> f3East3 , "south" -> f3East4South1))
  f3East4South1.setNeighbors(Vector("north" -> f3East4, "west" -> f3East3South1))

  f3East5North1.setNeighbors(Vector("east" -> f3East6North1, "south" -> f3East5))
  f3East5.setNeighbors(Vector("north" -> f3East5North1, "east" -> f3East6, "west" -> f3East4 , "south" -> f3East5South1))
  f3East5South1.setNeighbors(Vector("north" -> f3East5, "east" -> f3East6South1))

  f3East6North1.setNeighbors(Vector("east" -> f3East7North1, "south" -> f3East6, "west" -> f3East5North1))
  f3East6.setNeighbors(Vector("north" -> f3East6North1, "east" -> f3East7, "west" -> f3East5 , "south" -> f3East6South1))
  f3East6South1.setNeighbors(Vector("north" -> f3East6, "east" -> f3East7South1, "west" -> f3East5South1))

  f3East7North1.setNeighbors(Vector("south" -> f3East7, "west" -> f3East6North1))
  f3East7.setNeighbors(Vector("north" -> f3East7North1, "east" -> f3BossRoom, "west" -> f3East6 , "south" -> f3East7South1))
  f3East7South1.setNeighbors(Vector("north" -> f3East7, "west" -> f3East6South1))

  f3BossRoom.setNeighbor("east" , f3East7)

  def start = f1Lobby

  def destination = f3BossRoom

  val player = Player(this.start)
  this.start.player = Some(player)

  val accessCard = AccessCard(this.player)
  LockedDoor(f2East2, this.player , accessCard)


  f1Lobby.addItem(CakeForm(this.player))
  f1Lobby.addItem(AccessForm(this.player))
  f1Lobby.addItem(SPotion(this.player))
  f2West1.addItem(HPotion(this.player))
  f2West1South1.addItem(accessCard)
  f2East2.addItem(DPotion(this.player))
  f2East2South1.addItem(VIPAccessForm(this.player))
  f2South2East1.addItem(RPotion(this.player))
  f3East3North1.addItem(SusPotion(this.player , "ah"))
  f3East6South1.addItem(AHPotion(this.player))

  val bossDialogueArea = DialogueArea(BossDialogue.root, currentGame)
  f3BossRoom.addDialogue(bossDialogueArea)

  private var allMobs: Buffer[Mob] = Buffer(PrinterJamSlime(f2South1, this.player), BootlickerGolem(80081369, f3East4, this.player), BootlickerGolem(42000420, f3East5, this.player))

  def removeDeadMobs() =
    allMobs = allMobs.filterNot(_.isDead)


  def playTurn(): Unit =
    this.allMobs.foreach(mob => mob.move(mob.currentLocation.neighbor(mob.pathFind)))


  /** The number of turns that have passed since the start of the game. */

  /** The maximum number of turns that this adventure game allows before time runs out. */


  /** Determines if the adventure is complete, that is, if the player has won. */


  /** Determines whether the player has won, lost, or quit, thereby ending the game. */


  /** Returns a message that is to be displayed to the player at the beginning of the game. */


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether the player has completed their quest. */


  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */


end Overworld

