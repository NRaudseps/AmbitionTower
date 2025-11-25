package o1.game.item

import o1.game.entities.player.Player

class AccessCard(user : Player) extends Item(user, false , "Access Card", "An access card that can grant you clearance to a certain area within the floor. Unusable."):
  def effect: String = "a"