package o1.game.stages

import o1.game.stages.Stage

class DialogueOption(val text: String, val isCorrect: Boolean, val nextNode: Option[DialogueNode])
class DialogueNode(val text: String, val options: Vector[DialogueOption])

class DialogueArea(root: DialogueNode) extends Stage("In Dialaogue"):
  private var currentNode = root

  def currentDialogueText: String =
    val dialogueText = currentNode.text
    val optionsText = currentNode.options.zipWithIndex.map(
      (opt, i) => s"${i + 1}: ${opt.text}"
    ).mkString("\n")
    s"$dialogueText\n\n$optionsText"

  def chooseOption(index: Int): String =
    val chosenOption = currentNode.options(index)

    if !chosenOption.isCorrect then
      "combat"
    else
      chosenOption.nextNode match
        case Some(next) =>
          currentNode = next
          "continue"
        case None => "end"

  def fullDescription = ""
