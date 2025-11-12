package o1.game

import o1.game.entities.player.{CombatPlayerActions, OverworldPlayerActions}
import o1.game.stages.combatArea.CombatArea

class Game:
  /** the name of the game */
  val title = "Ambition Tower: Your Dungeon Diving Adventure!! (read: Corporate Ladder Climbing Hell)"
  val overworld = Overworld(this)
  val player = this.overworld.player
  var combatArea: Option[CombatArea] = None
  var turnCount = 0
  private val timeLimit = 99



  def enterCombat() =
    combatArea = Some(CombatArea(this.overworld, this))

  def inCombat = this.combatArea.nonEmpty
  
  def currentStage =
    this.combatArea.getOrElse(this.player.currentLocation)

  def isComplete = this.player.currentLocation == this.overworld.destination //need to add other conditions
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.isDead


  def welcomeMessage = "DING.\nThe elevator doors slide open, releasing a gust of overly conditioned air that smells faintly of toner and desperation. A polished sign greets you in gold lettering:\n\n“WELCOME TO AMBITION TOWER – WHERE CAREERS ASCEND.”\n\nThe lobby stretches ahead, immaculate and sterile. Rows of reception desks line the walls, manned by identical attendants whose smiles never reach their eyes. Behind them, filing cabinets hum softly like hives. Somewhere between the chatter of phones and the click of heels, you sense the quiet dread of efficiency perfected.\n\nThe Tower calls to you, Adventurer.\nMay you learn its language of forms and approvals, and climb to the top before its cruelty ultimately swallows and corrupts you whole..."
  def goodbyeMessage =
    if this.isComplete then
      "Home at last... and phew, just in time! Well done!"
    else if this.player.isDead then
      "You fall to your knees. With no more strength left to struggle, your soul inevitably gets consumed by the Tower, becoming a puny cog in the perfect machine that turns passion into cold, hard profit. New 'Adventurers' will keep challenging the Tower and its trials, while your own story completely lost in history. Game over!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    else  // game over due to player quitting
      "Quitter!"

  def playTurn(command: String): String =
    val action = {
      if this.inCombat then
        CombatPlayerActions(command)
      else
        OverworldPlayerActions(command)
    }
    val commandReport = action.execute(this.player)

    val output = commandReport.map(playerTurn => {
      val turnPassed = !playerTurn.contains(this.player.specialString)
      if turnPassed then
        this.turnCount += 1
        val gameTurn: Option[String] = {
          if !this.inCombat && this.player.inCombat then
            this.enterCombat()
            None
          else if !this.inCombat && !this.player.inCombat then
            this.overworld.playTurn()
            if this.player.inCombat then this.enterCombat()
            None
          else
            this.combatArea.map(_.playTurn())
        }
        playerTurn + "\n\n" + gameTurn.getOrElse("")
      //if a full turn should be passed, return the outputs of both the player's command
      //and the game's response to it (playerTurn & gameTurn); the return value for
      //overworld's response is None because in later expansions of the game, there can be
      //responses that return no string or some string
      else
        playerTurn.replaceAll(this.player.specialString, "")
      //if the command is recognizable, but its output has a special string,
      //aka it does not result in a full turn, like inventory or faulty attack commands,
      //turnCount does not increase and return the output of player's command
    })
    if combatArea.nonEmpty && combatArea.forall(_.isFinished) then
      this.combatArea = None
    //after each playTurn, regardless if a turn passes or not, check to terminate an
    //existing combat area; should not randomly terminate the combat area if the state
    //of the game was unchanged
    output.getOrElse(s"""You can't do this here: "$command"!""")
//if the output is undefined, aka the command was unrecognized to begin with, no turn
//is passed, like with the above case

