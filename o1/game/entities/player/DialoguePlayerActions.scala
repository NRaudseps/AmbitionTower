package o1.game.entities.player

import o1.game.entities.npc.boss.Boss
import o1.game.stages.DialogueArea

class DialoguePlayerActions(input: String) extends PlayerActions:
  
  private val commandText: String = input.trim
  private val verb: String        = commandText.takeWhile(_ != ' ').toLowerCase
  private val modifiers: String   = commandText.drop(verb.length).trim
  

  def execute(actor: Player): Option[String] =
    this.verb match
      case "1"     => Some(actor.handleChoice(0))
      case "2"     => Some(actor.handleChoice(1))
      case "3"     => Some(actor.handleChoice(2))
      case "help"  => Some(actor.helpDialogue)
      case _       => None
    