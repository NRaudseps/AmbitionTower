package o1.game.stages

import o1.game.Game
import o1.game.stages.Stage

class DialogueOption(val text: String, val isCorrect: Boolean, val nextNode: Option[DialogueNode])
class DialogueNode(val text: String, val options: Vector[DialogueOption])

class DialogueArea(root: DialogueNode, currentGame : Game) extends Stage("IN DIALOGUE!"):
  private val player = currentGame.player
  private var currentNode = root
  private var isFin1shed = false
  def isFinished = isFin1shed
  def currentDialogueText: String =
    val dialogueText = currentNode.text
    val optionsText = currentNode.options.zipWithIndex.map(  (opt, i) => s"${i + 1}: ${opt.text}"  ).mkString("\n")
    s"$dialogueText\n\n$optionsText"

  def chooseOption(index: Int): String =
    val chosenOption = currentNode.options(index)
    if !chosenOption.isCorrect then
      this.isFin1shed = true
      "combat"
    else
      chosenOption.nextNode match
        case Some(next) => {
          currentNode = next
          "continue"
        }
        case None => {
          currentGame.dialogueWin()
          this.isFin1shed = true
          "end"
        }

  def fullDescription = this.name + "\n\n" + this.currentDialogueText
