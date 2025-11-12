package o1.game.stages.combatArea


import o1.game.entities.npc.Mob
import o1.game.entities.player.Player
import o1.game.stages.Stage
import o1.game.{Game, Overworld}

import scala.collection.mutable.Map

class CombatArea(val overworld: Overworld, val currentGame: Game) extends Stage("IN COMBAT!"):

  private val player: Player = this.currentGame.player
  private val initialMobs: Map[String,Mob] = this.player.enemies.clone()
  private def mobs: Map[String , Mob] = this.player.enemies
  private var occurredTurns = 1
  private var endMessage = ""
  private var isFin1shed = false

  def isFinished = this.isFin1shed
  //everything here is written to account for multi-enemies fights, but there will be none in the game;
  //if multi-enemies fights are to occur, there will be a speed stats to determine turn order, but as mentioned, there will be
  //no such fights and hence, no such stats
  def mobsTurn(): String =
    mobs.toVector.map(  (name , mob) => mob.fight()  ).mkString("\n\n")

  def playTurn(): String =
    occurredTurns += 1
    val output = mobsTurn()
    if this.player.remainingHealth > 0 && mobs.nonEmpty then
      this.player.clearTempEffect()
    //this serves as a means for the player to clear
                                    //effects like dodge and guard, but is also
                                    //a prototype for status effects that last multiple
                                    //turns, instead of simply clearing, the effects "proc"
                                    //and reduce a number of stacks
    else if this.player.remainingHealth > 0 && mobs.isEmpty then
      isFin1shed = true
      endMessage = "You won!"
      this.overworld.removeDeadMobs()
    else
      this.player.die()
      endMessage = "You were defeated."
    output + "\n\n" + endMessage

  def fullDescription : String =
    val playerHP = s"\nYour HP: ${this.player.remainingHealth}/${this.player.maxHealth}"
    val mobsHP = s"\n${this.mobs.map(  (name,mob) => name + "'s HP: " + s"${mob.remainingHealth}/${mob.maxHealth}"  ).mkString("\n")}"
    val turnReport = s"\n\nTURN ${this.occurredTurns}" + playerHP + mobsHP
    s"YOU ARE $name\n\nYou encountered enemies!\n\nCurrently fighting: ${this.mobs.keys.mkString(", ")}.\nYour options: Attack , Rest , Guard , Dodge, Use items. Every action costs a turn." + turnReport