package o1.game.item.consumables.potions


import o1.game.entities.player.Player
import o1.game.item.consumables.Consumables

class Potion(user: Player , val potionType : String, name:String, description: String) extends Consumables(user, name , description):
  def effect: String =
    if potionType == "s" then
      user.shieldUp()
      "You received a favour from Mr. Big Boss! You have a one-time immunity to being fired. Gained a Shield!" //set health to 1 when the player otherwise dies. unstackable
    else if potionType == "h" then
      user.heal(5)
      "You heal 5 HP! Tastes like refridgerated energy drinks. Yucks!" //to be implemented health potion's effect
    else if potionType == "r" then
      user.rageUp()
      "The potion makes you burn with motivation (and rage against the corpo). Attack doubled for the next 5 turns."
    else
      user.die()
      "The potion isn't particularly poisonous or anything, but it tastes so bad you couldn't go to work the next day and hence, get fired."