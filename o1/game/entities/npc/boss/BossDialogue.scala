package o1.game.entities.npc.boss

import o1.game.stages.{DialogueNode, DialogueOption}

object BossDialogue:

  // --- Third question (final node) -----------------------------
  val finalNode = DialogueNode(
    text = "Mr. Big Boss: 'Very well… knowing who you are is only the beginning.'\nHe rises slightly from his chair, the chandeliers catching the movement, casting long shadows across the floor.\nMr. Big Boss: 'Tell me this, Adventurer: why have you climbed the tower?'\nMr. Big Boss: 'Is it ambition? Curiosity? Or something darker that drives you upward?'",
    options = Vector(
      DialogueOption("200 C points.", true, None),      // correct -> end
      DialogueOption("Money.", false, None),
      DialogueOption("Power.", false, None)
    )
  )

  // --- Second question -----------------------------------------
  val secondNode = DialogueNode(
    text = "Mr. Big Boss: 'Hmm… I see.'\nHe sits straighter, fingers steepled, eyes glinting with approval and calculation.\nMr. Big Boss: 'So the rumor is true... I expected no less from the Adventurer recognized by the Tower itself.'\nMr. Big Boss: 'Very well. Names do not matter… but deeds do.'\nMr. Big Boss: 'Let us see if your steps, your choices, and your resolve are worthy of this hall...'\nMr. Big Boss: 'What's 1+1?'",
    options = Vector(
      DialogueOption("2.", true, Some(finalNode)),
      DialogueOption("11.", false, None),
      DialogueOption("Fibsh.", false, None)
    )
  )

  // --- First question (root) -----------------------------------
  val root = DialogueNode(
    text = "Mr. Big Boss: 'Ah… so you’ve finally arrived.'\nHe leans back, eyes narrowing, voice smooth but sharp, echoing slightly in the vast chamber.\nMr. Big Boss: 'Every step you’ve taken, every corridor, every decision… has led you here.'\nMr. Big Boss: 'But before we truly begin… I must know.'\nHe gestures slowly toward you, a smile that doesn’t reach his eyes.\nMr. Big Boss: 'Do you know who stands before you, what name do I bear, Adventurer?'",
    options = Vector(
      DialogueOption("Names matter not for the to-be-former-boss.", true, Some(secondNode)),
      DialogueOption("Kevin.", false, None),
      DialogueOption("I have no idea.", false, None)
    )
  )

