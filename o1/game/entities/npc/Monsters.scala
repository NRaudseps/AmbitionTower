package o1.game.entities.npc

import o1.game.entities.player.Player
import o1.game.entities.{CombatEntity, OverworldEntity}
import o1.game.stages.overworldArea.OverworldArea

class PrinterJamSlime(startingArea: OverworldArea, enemy: Player) extends Mob("slime", "PrinterJamSlime" , enemy), OverworldEntity(startingArea), CombatEntity(7, 2):

  var north = true
  
  def pathFind: String =
    north = !north
    if !north then
      "south"
    else
      "north"
      
  def attack: String =
    this.enemy.suffer(this.name, this.attackPower)

  var attackTurn = true

  def fight(): String =
    if remainingHealth > 0 then
      attackTurn = !attackTurn
      if attackTurn then
        this.attack
      else
        this.waitOut
    else
      this.die()

class BootlickerGolem(val id: Int, startingArea: OverworldArea, enemy: Player) extends Mob("golem","Bootlicker Golem | Employee's ID: " + id, enemy), OverworldEntity(startingArea), CombatEntity(15, 3):
  val directions = {
    if this.id % 2 != 0 then
      Vector[String]("north", "west", "south", "east").zipWithIndex.map((direction, index) => (index, direction)).toMap
    else
      Vector[String]("north", "east", "south", "west").zipWithIndex.map((direction, index) => (index, direction)).toMap
  }

  def attack: String =
    this.enemy.suffer(this.name, this.attackPower)

  var index = 0

  def pathFind: String =
    if this.currentLocation.neighbor(directions(index)).isEmpty then
      index += 1
      if index > 3 then
        index = 0
    directions(index)

  var waitCounter = 0

  def fight() =
    if this.remainingHealth > 0 then
      if waitCounter < 3 then
        waitCounter += 1
        this.waitOut
      else
        waitCounter = 0
        this.attack
    else
      this.die()