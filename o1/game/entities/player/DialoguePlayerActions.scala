package o1.game.entities.player

import o1.game.entities.npc.boss.Boss
import o1.game.stages.DialogueArea

class DialoguePlayerActions(input: String, dialogueArea: DialogueArea) extends PlayerActions:
  
  private val commandText: String = input.trim
  private val verb: String        = commandText.takeWhile(_ != ' ').toLowerCase
  private val modifiers: String   = commandText.drop(verb.length).trim
  
  private def handleChoice(command: String, actor: Player) =
    val index = command.toInt - 1

    dialogueArea.chooseOption(index) match
      case "continue" => dialogueArea.currentDialogueText
      case "combat" =>
        actor.currentLocation.dialogue = None
        val boss = Boss(actor.currentLocation, actor)
        "How dare you!"
      case "end" =>
        actor.currentLocation.dialogue = None
        "You Win!"

  def execute(actor: Player): Option[String] =
    this.verb match
      case "1" | "2" | "3" => Some(this.handleChoice(verb, actor)) // TODO: should this be in player???
      case _ => None
    