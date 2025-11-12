package o1.game.entities.npc

import o1.game.entities.player.Player
import o1.game.entities.{CombatEntity, OverworldEntity}
import o1.game.stages.overworldArea.OverworldArea

trait Mob(val name: String, val enemy: Player, val description : String) extends OverworldEntity,CombatEntity:
  
  this.currentLocation.addMob(this)

  def pathFind: String //determine the direction that a specific type of mobs should move to on an overworld's turn; every species has a
                       //different pathFind method
  
  def move(destination: Option[OverworldArea]):Unit =
    //if this.isDead then ()
    //else
      destination.foreach(destination => {
        this.currentLocation.removeMob(this.name)
        this.currentLocation = destination
        this.currentLocation.addMob(this)
      }
      )
    //maybe an extra implementation so that mobs cannot go into the elevator? but unnecessary
//special moves will be made specific for each species; these are standard moves

    
  def waitOut: String =
    s"${this.name} waited and did nothing."
    
  def fight(): String
  
  def die(): String =
    this.currentLocation.removeMob(this.name)
    this.remainingHealth = 0
    this.isDead = true
    s"${this.name} is defeated!"
