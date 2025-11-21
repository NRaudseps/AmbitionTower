package o1.game

import o1.game.entities.npc.boss.BossDialogue
import o1.game.entities.npc.{BootlickerGolem, Mob, PrinterJamSlime}
import o1.game.entities.player.Player
import o1.game.item.consumables.forms.{AccessForm, CakeForm, VIPAccessForm}
import o1.game.item.consumables.potions.{HPotion, SusPotion}
import o1.game.stages.DialogueArea
import o1.game.stages.overworldArea.{Elevator1F, Elevator2F, Elevator3F, OverworldArea}

import scala.collection.mutable.{Buffer, Map}


/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Overworld(val currentGame: Game):
  // the names of these areas will be changed to accompany TextUI after game is finished
  private val f1Lobby = OverworldArea("1F", "1F - THE LOBBY\n\nYou step into the pristine marble lobby of Ambition Tower, the air thick with artificial cheer and recycled confidence. Fluorescent lights hum overhead, reflecting off motivational posters—“Teamwork Makes the Dream Work!”—while a receptionist drone greets you with a smile too wide to be real.\n\nThe floor stretches out like an endless waiting room, divided by velvet ropes and endless forms. Every desk demands a signature, every door requires an ID badge you don’t yet possess. Somewhere in this bureaucratic maze lies your first promotion...\n\n'No more useless thought', you say to yourself. You notice the reception desk, sitting at which is a lady drone who is diligently typing on her laptop. There is an elevator on the opposite side of the room.\n\n")
  private val f2MazeCenter = OverworldArea("2FCenter", "2F - THE CUBICLES - CENTER")
  private val f2West1 = OverworldArea("2FLocker", "2F - THE CUBICLES - WEST WING 1")
  private val f2West1South1 = OverworldArea("2FAccess", "2F - THE CUBICLES - WEST WING 2")
  private val f2South1 = OverworldArea("2FMonster", "2F - THE CUBICLES - PRINTING HALLWAY 1")
  private val f2South2 = OverworldArea("2FEmpty", "2F - THE CUBICLES - PRINTING HALLWAY 2")
  private val f2South2East1 = OverworldArea("2FTrap", "2F - THE CUBICLES - THE GOLDEN EXILE DEPARTMENT")
  private val f2East1 = OverworldArea("2FDoor", "2F - THE CUBICLES - EMPTY HALLWAY")
  private val f2East2 = OverworldArea("2FHallway", "2F - THE CUBICLES - EMPTY HALLWAY")
  private val f2East2South1 = OverworldArea("2FForm", "2F - THE CUBICLES - EMPTY HALLWAY")
  private val f3East1 = OverworldArea("3FHallway1", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East2 = OverworldArea("3FHallway2", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East2North1 = OverworldArea("3FHallway21", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East2South1 = OverworldArea("3FHallway22", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East3 = OverworldArea("3FHallway3", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East3North1 = OverworldArea("3FHallway31", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East3South1 = OverworldArea("3FHallway32", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East4 = OverworldArea("3FHallway4", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East4North1 = OverworldArea("3FHallway41", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East4South1 = OverworldArea("3FHallway42", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East5 = OverworldArea("3FHallway5", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East5North1 = OverworldArea("3FHallway51", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East5South1 = OverworldArea("3FHallway52", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East6 = OverworldArea("3FHallway6", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East6North1 = OverworldArea("3FHallway61", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East6South1 = OverworldArea("3FHallway62", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East7 = OverworldArea("3FHallway7", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East7North1 = OverworldArea("3FHallway71", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3East7South1 = OverworldArea("3FHallway72", "3F - VIP AREA - TO MR. BIG BOSS' ROOM")
  private val f3BossRoom = OverworldArea("3FBossRoom", "3F - MR. BIG BOSS' ABODE")

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
  f3East2.setNeighbors(Vector("north" -> f3East2North1, "east" -> f3East3, "south" -> f3East2South1))
  f3East2South1.setNeighbors(Vector("north" -> f3East2, "east" -> f3East3South1))

  f3East3North1.setNeighbors(Vector("east" -> f3East4North1, "south" -> f3East3, "west" -> f3East2North1))
  f3East3.setNeighbors(Vector("north" -> f3East3North1, "east" -> f3East4, "south" -> f3East3South1))
  f3East3South1.setNeighbors(Vector("north" -> f3East3, "east" -> f3East4South1, "west" -> f3East2South1))

  f3East4North1.setNeighbors(Vector("south" -> f3East4, "west" -> f3East3North1))
  f3East4.setNeighbors(Vector("north" -> f3East4North1, "east" -> f3East5, "south" -> f3East4South1))
  f3East4South1.setNeighbors(Vector("north" -> f3East4, "west" -> f3East3South1))

  f3East5North1.setNeighbors(Vector("east" -> f3East6North1, "south" -> f3East5))
  f3East5.setNeighbors(Vector("north" -> f3East5North1, "east" -> f3East6, "south" -> f3East5South1))
  f3East5South1.setNeighbors(Vector("north" -> f3East5, "east" -> f3East6South1))

  f3East6North1.setNeighbors(Vector("east" -> f3East7North1, "south" -> f3East6, "west" -> f3East5North1))
  f3East6.setNeighbors(Vector("north" -> f3East6North1, "east" -> f3East7, "south" -> f3East6South1))
  f3East6South1.setNeighbors(Vector("north" -> f3East6, "east" -> f3East7South1, "west" -> f3East5South1))

  f3East7North1.setNeighbors(Vector("south" -> f3East7, "west" -> f3East6North1))
  f3East7.setNeighbors(Vector("north" -> f3East7North1, "east" -> f3BossRoom, "south" -> f3East7South1))
  f3East7South1.setNeighbors(Vector("north" -> f3East7, "west" -> f3East6South1))


  // TODO: temp for testing
  def start = f3East7

  def destination = f2MazeCenter

  val player = Player(this.start)
  this.start.player = Some(player)


  f1Lobby.addItem(CakeForm(this.player))
  f1Lobby.addItem(AccessForm(this.player))
  f1Lobby.addItem(SusPotion(this.player, "s"))
  f2West1.addItem(HPotion(this.player))
  f2East2South1.addItem(VIPAccessForm(this.player))

  val bossDialogueArea = new DialogueArea(BossDialogue.root)
  f3BossRoom.addDialogue(bossDialogueArea)

  private var allMobs: Buffer[Mob] = Buffer(PrinterJamSlime(f2South1, this.player), BootlickerGolem(80081369, f3East4, this.player), BootlickerGolem(42000420, f3East5, this.player))

  def removeDeadMobs() =
    allMobs = allMobs.filterNot(_.isDead)
    println(allMobs)


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

