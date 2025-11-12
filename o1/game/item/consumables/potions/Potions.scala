package o1.game.item.consumables.potions

import o1.game.entities.player.Player

class SusPotion(user: Player , potionType:String) extends Potion(user , potionType, "Suspicious Potion" , "A mysterious concoction with unknown effect. How does this end up in the workplac- I mean, the dungeon? Nobody knows. Note: Use with caution.")

class SPotion(user: Player) extends Potion(user , "s" , "Shield Potion" , "")

class HPotion(user: Player) extends Potion(user , "h" , "Healing Potion" , "")

class RPotion(user: Player) extends Potion(user , "r" , "Rage Potion" , "")

class DPotion(user: Player) extends Potion(user , "d" , "Death Potion" , "")