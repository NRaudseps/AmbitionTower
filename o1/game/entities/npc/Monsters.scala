package o1.game.entities.npc

import o1.game.entities.{CombatEntity, OverworldEntity}
import o1.game.entities.player.Player
import o1.game.stages.overworldArea.OverworldArea

class PrinterJamSlime(startingArea:OverworldArea, enemy: Player) extends Mob("Slime", enemy , ""), OverworldEntity(startingArea), CombatEntity(7,2):
  var alternate1 = false
  def pathFind: String =
    alternate1 = !alternate1
    if alternate1 then
      "south"
    else
      "north"
  
  def attack: String =
    this.enemy.suffer(this.name, this.attackPower)
  
  var alternate2 = true
  def fight() =
    if remainingHealth > 0 then
      alternate2 = !alternate2
      if alternate2 then
        this.attack
      else
        this.waitOut
    else
      this.die()

class BootlickerGolem(val id: Int, startingArea: OverworldArea, enemy: Player) extends Mob("Bootlicker Golem | Employee's ID: " + id, enemy, ""), OverworldEntity(startingArea), CombatEntity(20, 5):
  val directions =
    {
      if this.id % 2 != 0 then
      Vector[String]("north", "west", "south", "east").zipWithIndex.map((direction, index) => (index, direction)).toMap
    else
      Vector[String]("north", "east", "south", "west").zipWithIndex.map((direction, index) => (index, direction)).toMap
    }

  def attack: String =
    this.enemy.suffer(this.name, this.attackPower)

  var index = 0
  def pathFind: String =
    {
    while index < 3 && this.currentLocation.neighbor(directions(index)).isEmpty do
      index += 1
    }
    val direction: String = directions(index) //always returns last direction if the other 3 are not valid; the move method will automatically return 
                                              //if this last direction is also not possible to move to
    index = 0
    direction

  var counter = 0
  def fight() =
    if this.remainingHealth > 0 then
      if counter < 3 then
        counter += 1
        this.waitOut
      else
        counter = 0
        this.attack
    else
      this.die()

/*var index = 1
    while index <= 4 && this.location.neighbor(directions(index)).isEmpty do
      index +=1
    directions(index)*/