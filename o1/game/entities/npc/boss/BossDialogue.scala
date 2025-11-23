package o1.game.entities.npc.boss

import o1.game.stages.{DialogueNode, DialogueOption}

object BossDialogue:

  // --- Third question (final node) -----------------------------
  val finalNode = DialogueNode(
    text = "Final question! What do I guard?",
    options = Vector(
      DialogueOption("The treasure", true, None),      // correct -> end
      DialogueOption("Your ego", false, None),
      DialogueOption("Uh… potatoes?", false, None)
    )
  )

  // --- Second question -----------------------------------------
  val secondNode = DialogueNode(
    text = "Second question: What is 2 + 2?",
    options = Vector(
      DialogueOption("4", true, Some(finalNode)),
      DialogueOption("22", false, None),
      DialogueOption("Fish", false, None)
    )
  )

  // --- First question (root) -----------------------------------
  val root = DialogueNode(
    text = "You dare challenge me? Answer this: What is my name?",
    options = Vector(
      DialogueOption("The Dark Lord", true, Some(secondNode)),
      DialogueOption("Kevin", false, None),
      DialogueOption("I have no idea", false, None)
    )
  )

