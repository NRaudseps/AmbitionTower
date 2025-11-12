package o1.game.stages.overworldArea


import o1.game.item.Item

import scala.collection.mutable.Map

object Elevator:
  private val sharedItems = Map[String , Item]()

sealed class Elevator(var currentFloor: Int, private var isAcc3ssible: Boolean) extends OverworldArea("Elevator" + currentFloor, s"${currentFloor}F - ELEVATOR\n\nDING!\nYou are now on Floor " + currentFloor + "."):
  override val items = Elevator.sharedItems
  def access() =
    this.isAcc3ssible = true
  def isAccessible = this.isAcc3ssible

object Elevator1F extends Elevator(1, true):
  this.setNeighbor("up", Elevator2F)
object Elevator2F extends Elevator(2, false):
  this.setNeighbors(Vector(("up",Elevator3F),("down",Elevator1F)))
object Elevator3F extends Elevator(3, false):
  this.setNeighbor("down", Elevator1F)
