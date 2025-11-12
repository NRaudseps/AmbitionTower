package o1.game.item.consumables.potions


import o1.game.entities.player.Player
import o1.game.item.consumables.Consumables
import o1.game.stages.overworldArea.{Elevator2F, Elevator3F}

class Potion(user: Player , val potionType : String, name:String, description: String) extends Consumables(user, name , description):
  def effect() =
    if potionType == "s" then
      "You received a favour from Mr. Big Boss! You have a one-time immunity to being fired." //set health to 1 when the player otherwise dies. unstackable
    else if potionType == "h" then
      "But nothing happened." //to be implemented health potion's effect
    else
      "But nothing happened." // to be implemented rage and death potion's effect