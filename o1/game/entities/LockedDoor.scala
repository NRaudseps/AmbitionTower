package o1.game.entities

import o1.game.entities.player.Player
import o1.game.item.AccessCard
import o1.game.stages.overworldArea.OverworldArea

class LockedDoor(location: OverworldArea , val player: Player, val accessCard: AccessCard) extends OverworldEntity(location):
  this.currentLocation.door = Some(this)
  def isUnlocked = this.player.has(this.accessCard.name)
