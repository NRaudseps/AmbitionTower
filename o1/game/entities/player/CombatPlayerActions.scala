package o1.game.entities.player

class CombatPlayerActions(input: String) extends PlayerActions:
  
  private val commandText: String  = input.trim
  private val verb: String         = commandText.takeWhile(_ != ' ').toLowerCase
  private val modifiers: String    = commandText.drop(verb.length).trim
  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player): Option[String] =
    this.verb match
      case "attack"     => Some(actor.attack(this.modifiers))
      case "rest"       => Some(actor.rest())
      case "quit"       => Some(actor.quit())
      case "guard"      => Some(actor.guard())
      case "dodge"      => Some(actor.dodge())
      case "examine"    => Some(actor.examine(this.modifiers))
      case "status"     => Some(actor.status)
      case "use"        => Some(actor.use(this.modifiers))
      case other        => None

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (modifiers: $modifiers)"
