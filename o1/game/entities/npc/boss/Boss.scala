package o1.game.entities.npc.boss

import o1.game.entities.{CombatEntity, OverworldEntity}
import o1.game.entities.npc.Mob
import o1.game.entities.player.Player
import o1.game.stages.overworldArea.OverworldArea

// TODO: add more descriptions
val BOSS_NAME = "Mr. Big Boss"
val BOSS_DESCRIPTION = "something"
val BOSS_HEALTH = 1
val BOSS_ATTACK_DAMAGE = 1

class Boss(startingArea: OverworldArea, enemy: Player) extends Mob(BOSS_NAME, enemy, BOSS_DESCRIPTION), OverworldEntity(startingArea), CombatEntity(BOSS_HEALTH, BOSS_ATTACK_DAMAGE):

  def pathFind = "no path"

  def attack: String =
    this.enemy.suffer(this.name, this.attackPower)

  def fight() =
    if remainingHealth > 0 then
      this.attack
    else
      this.die()
