package o1.game.entities.npc.boss

import o1.game.entities.{CombatEntity, OverworldEntity}
import o1.game.entities.npc.Mob
import o1.game.entities.player.Player
import o1.game.stages.overworldArea.OverworldArea

import scala.util.Random

val BOSS_KEYWORD = "boss"
val BOSS_NAME = "Mr. Big Boss"
val BOSS_HEALTH = 30
val BOSS_ATTACK_DAMAGE = 5

class Boss(startingArea: OverworldArea, enemy: Player) extends Mob(BOSS_KEYWORD,BOSS_NAME, enemy), OverworldEntity(startingArea), CombatEntity(BOSS_HEALTH, BOSS_ATTACK_DAMAGE):
  
  def pathFind = "no path"
  
  def attack: String =
    if schemed then
      schemed = false
      this.enemy.suffer(this.name, this.attackPower*2)
    else
      this.enemy.suffer(this.name, this.attackPower)

  private var schemed = false
  def scheme: String =
    schemed = true
    "Mr. Big Boss smiles devilishly. It seems he is planning something sinister. (Hits for twice the damage next attack.)"

  private var strongCounter = 0
  def strongAttack: String =
    strongCounter = 0
    schemed = false
    "Mr. Big Boss unleashes his Ultimate Imaginary Technique: Hollow Pur- I mean, Eyes of the Overworked!\n"+this.enemy.suffer(this.name, this.enemy.maxHealth*2)

  val rng = Random(69420) //funny hehe
  def fight() =
    if remainingHealth > 0 then
      strongCounter += 1
      val strongWarning = {
        if strongCounter == 4 then
          "Mr. Big Boss is performing a strong attack next turn. You will not be able to withstand the damage! Brace yourself!"
        else ""
      }
      val action: String = {
      if this.strongCounter == 5 then
        this.strongAttack
      else
        if rng.nextInt(4) == 0 then
          this.waitOut
        else if rng.nextInt(4) == 1 then
          this.attack
        else if rng.nextInt(4) == 2 then
          this.scheme
        else
          this.heal(7)
          "Mr. Big Boss healed himself!"
      }
      action + "\n" + strongWarning
    else
      this.die()
