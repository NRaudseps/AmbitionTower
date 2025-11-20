package o1.game.entities.npc.boss

import o1.game.stages.{DialogueNode, DialogueOption}

object BossDialogue:

  // --- Third question (final node) -----------------------------
  val finalNode = new DialogueNode(
    text = "Final question! What do I guard?",
    options = Vector(
      new DialogueOption("The treasure", true, None),      // correct -> end
      new DialogueOption("Your ego", false, None),
      new DialogueOption("Uh… potatoes?", false, None)
    )
  )

  // --- Second question -----------------------------------------
  val secondNode = new DialogueNode(
    text = "Second question: What is 2 + 2?",
    options = Vector(
      new DialogueOption("4", true, Some(finalNode)),
      new DialogueOption("22", false, None),
      new DialogueOption("Fish", false, None)
    )
  )

  // --- First question (root) -----------------------------------
  val root = new DialogueNode(
    text = "You dare challenge me? Answer this: What is my name?",
    options = Vector(
      new DialogueOption("The Dark Lord", true, Some(secondNode)),
      new DialogueOption("Kevin", false, None),
      new DialogueOption("I have no idea", false, None)
    )
  )

