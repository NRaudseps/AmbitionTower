package o1.game.stages.overworldArea


import o1.game.entities.LockedDoor
import o1.game.entities.npc.Mob
import o1.game.entities.player.Player
import o1.game.item.Item
import o1.game.stages.{DialogueArea, Stage}

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an “area” can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */

class OverworldArea(name: String, val description: String) extends Stage(name):
  val neighbors = Map[String, OverworldArea]()
  val items =  Map[String, Item]()
  val mobs = Map[String , Mob]()
  var door: Option[LockedDoor] = None
  var player: Option[Player] = None
  var dialogue: Option[DialogueArea] = None
  private val directions = Vector[String]("north", "east", "south", "west")
  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)


  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: OverworldArea):Map[String,OverworldArea] =
    this.neighbors += direction -> neighbor

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, OverworldArea)]):Map[String,OverworldArea] =
    this.neighbors ++= exits

  def addItem(item: Item): Unit =
    this.items += item.name -> item

  def removeItem(itemName:String): Option[Item]=
    this.items.get(itemName).map(possibleItem =>
      {this.items -= possibleItem.name
       possibleItem})

  def addMob(mob:Mob): Unit =
    this.mobs += mob.name -> mob
    
  def removeMob(mobName:String): Option[Mob]=
    this.mobs.get(mobName).map(possibleMob =>
      {this.mobs -= possibleMob.name
       possibleMob})

  def addDialogue(dialogueArea: DialogueArea) =
    this.dialogue = Some(dialogueArea)

  def containsItem(itemName: String): Boolean=
    this.items.contains(itemName)

  def containsMob(mobName: String): Boolean=
    this.mobs.contains(mobName)

  private def noNearbyMobs: Boolean =
    this.neighbors.forall(  (direction,area) => area.mobs.isEmpty  ) && this.neighbors.forall( (direction, area) => area.neighbors.forall( (direction,area) => area.mobs.isEmpty) )

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. If there are no
    * items present, the return value has the form "DESCRIPTION\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". If there are one or more items present, the return
    * value has the form "DESCRIPTION\nYou see here: ITEMS SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The items and directions are listed in an arbitrary order. */
  def fullDescription: String =
    val itemList = s"\n\nYou see here: ${this.items.keys.mkString(", ")}"
    val exitList = "\n\nExit(s) available: " + this.neighbors.keys.mkString(", ")
    val mobsCheck = s"\n\n${if this.noNearbyMobs then "" else "You hear the rumbling of monsters nearby..."}"
    if this.items.nonEmpty then
      this.description + itemList + exitList + mobsCheck
    else
      this.description + exitList + mobsCheck


  /** Returns a single-line description of the area for debugging purposes. */
  override def toString =
    this.name + ": " + this.description.replaceAll("\n", " ").take(150)

end OverworldArea
















//Drafted idea of a continual Elevator generator until a certain max floor
/*class Elevator(val currentFloor: Int) extends Area1("Elevator - " + currentFloor + "F", currentFloor , "You are at Floor " + currentFloor + ".")
end Elevator
val elevators = LazyList.continually(LazyList.from(1).map(floor =>
  {val elevator = Elevator(floor)
   if floor == 1 then
     elevator.setNeighbor("up",elevators(floor+1))
   if floor == 3 then
     elevator.setNeighbor("down",elevators(floor-1))
   else elevator.setNeighbors(Vector(("up",elevators(floor+1)),("down",elevators(floor-1))))
   elevator}
  )
)*/
