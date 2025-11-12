package o1.game.entities.player

trait PlayerActions:

  def execute(actor: Player): Option[String]
