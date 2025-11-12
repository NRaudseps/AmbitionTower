package o1.game.item.consumables

import o1.game.entities.player.Player
import o1.game.item.Item

abstract class Consumables(user : Player, name: String, description: String) extends Item(user, true , name, description)
