package o1.game.entities.player

import o1.game.*
import o1.game.entities.npc.Mob
import o1.game.entities.npc.boss.Boss
import o1.game.entities.{CombatEntity, OverworldEntity}
import o1.game.item.*
import o1.game.stages.overworldArea.{Elevator2F, Elevator3F, OverworldArea}

import scala.collection.mutable.Map
import scala.util.Random

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */

class Player(startingArea: OverworldArea) extends OverworldEntity(startingArea) , CombatEntity(20 , 3):
  private var quitCommandGiven = false              // one-way flag
  private val ownedItems = Map[String,Item]()
  private var hasShield = false
  private var rageCounter = 0
  private var tempEffect : Option[String] = None
  var boss: Option[Boss] = None
  val specialString: String = "@"
  //appended at the end of a [String] return value to indicate non-turn-costing outcomes
  /** Determines if the player has indicated a desire to quit the game. */

  def hasQuit = this.quitCommandGiven
  def inCombat: Boolean = this.enemies.nonEmpty
  def inDialogue: Boolean = this.currentLocation.dialogue.nonEmpty
  def enemies: Map[String , Mob] = this.currentLocation.mobs
  def enemiesKeyword: Map[String , Mob] = this.enemies.map(  (name , mob) => (mob.keyword , mob)  )
  def clearTempEffect() = tempEffect = None

  def shieldUp() =
    hasShield = true

  def hasRage = rageCounter > 0
  def rageUp() =
    rageCounter = 5
  def rageReduce() =
    rageCounter -= 1

  def die() =
    this.currentLocation.player = None
    this.remainingHealth = 0
    this.isDead = true

  def handleChoice(index: Int) =
    this.currentLocation.dialogue.map(_.chooseOption(index)) match
      case Some("continue") => "You chose your answer wisely and satisfied Mr. Big Boss."
      case Some("combat") => {
        boss = Some(Boss(currentLocation, this))
        "Mr. Big Boss: 'How foolish! And you chose to stand against me with that kind of resolve!'"
      }
      case Some("end") => "Mr. Big Boss smiles. He looks at you with a resigned expression."
      case other => ""

  def helpOverworld =
    "AVAILABLE COMMANDS:\n• go [direction]:\nMove one step in the chosen direction (north, south, east, west, etc.). This action passes a turn. If the path is blocked, you stay where you are.\n• rest:\nTake a moment to recover. Resting passes a turn and restores a small amount of health.\n• get [item]:\nPick up an item found in the area. Passes a turn.\n• drop [item]:\nPlace an item from your inventory onto the ground. Passes a turn.\n• use [item]:\nUse an item in your possession. Effects vary depending on the item. This passes a turn.\n• examine [item]:\nTake a closer look at an item in your inventory. Does not pass a turn.\n• status:\nCheck your current condition — health, inventory, and any special states. Does not pass a turn.\n• help:\nDisplay this list of available commands. Does not pass a turn.\n• quit:\nExit the game." + specialString

  def helpCombat =
    "AVAILABLE COMMANDS:\n• attack [enemy's keyword]:\nStrike the enemy with a basic offensive move. Damage varies based on your stats and the situation. Use with the enemy's stated keyword. This action passes a turn.\n• guard:\nBrace yourself and reduce incoming damage for the next enemy action. This action passes a turn.\n• dodge:\nAttempt to avoid the next incoming attack. Success depends on PURE LUCK, PRAY TO RNG. This action passes a turn.\n• rest:\nTake a moment to recover. Resting passes a turn and restores a small amount of health.\n• use [item]:\nUse an item in your possession. Effects vary depending on the item. This passes a turn.\n• examine [item]:\nTake a closer look at an item in your inventory. Does not pass a turn.\n• status:\nCheck your current condition — health, inventory, and any special states. Does not pass a turn.\n• help:\nDisplay this list of available commands. Does not pass a turn.\n• quit:\nExit the game." + specialString

  def helpDialogue =
    "AVAILABLE COMMANDS:\nChoose between the dialogue options using the indicated numbers (1, 2, or 3).\nThe 'help' command is also available here and does not cost a turn."

  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  private def move(destination: OverworldArea)=
    this.currentLocation.player = None
    this.currentLocation = destination
    this.currentLocation.player = Some(this)

  def go(direction: String) : String =
    val destination = this.currentLocation.neighbor(direction)
    def description: String = if destination.isDefined then s"You go $direction." else s"You can't go $direction!" + specialString
    destination match
      case Some(Elevator2F) => {
        if Elevator2F.isAccessible then
          move(Elevator2F)
          description
        else "The upper floor is not accessible yet! Hint: Find an item to help you move on." + specialString
      }
      case Some(Elevator3F) => {
        if Elevator3F.isAccessible then
          move(Elevator3F)
          description
        else "The floor above is a VIP area! You do not have VIP access! Hint: Explore the current floor." + specialString
      }
      case Some(area) => {
        if area.door.forall(_.isUnlocked) then
          move(area)
          description
        else "The door is locked! Hint: Find an item to help you." + specialString
      }
      case None => description

  def get(itemName:String): String =
    val pickedUpItem = this.currentLocation.removeItem(itemName)
    if pickedUpItem.isDefined then
      pickedUpItem.map(this.ownedItems += itemName -> _)
      "You pick up the " + itemName + s".\n${this.ownedItems(itemName).description}"
    else
      s"There is no ${itemName} here to pick up." + specialString


  def has(itemName:String): Boolean=
    this.ownedItems.contains(itemName)

  def drop(itemName:String):String=
    if this.has(itemName) then
      this.ownedItems.get(itemName).foreach(this.currentLocation.addItem)
      this.ownedItems -= itemName
      "You drop the " + itemName + "."
    else "You don't have that!" + specialString

  def examine(itemName:String):String =
    if this.has(itemName) then
      s"You look closely at the ${itemName}.\n${this.ownedItems(itemName).description}"
    else "If you want to examine something, you need to pick it up first." + specialString

  def status: String =
    val status: String = s"HP: ${remainingHealth}/${maxHealth}.\nShield: ${if this.hasShield then "Yes" else "No"}.\nRage's remaining turn: ${this.rageCounter}."
    val outcome = {
      if this.ownedItems.nonEmpty then
        s"You are carrying:\n${this.ownedItems.keys.mkString("\n")}"
      else "You are empty-handed."
    }
    status + "\n\n" + outcome + specialString

  def use(itemName: String): String =
    if this.has(itemName) then
      val item = this.ownedItems(itemName)
      if item.isUseable then
        this.ownedItems -= itemName
        "You used the item!\n\n" + item.effect
      else s"${item.name} can't be used!" + specialString
    else "You don't have this item!" + specialString

  /** Causes the player to rest for a short while.
    * Returns a description of what happened. */
  def rest():String =
    val initialHealth = this.remainingHealth
    val healedAmount = this.maxHealth/10
    this.heal(healedAmount)
    s"You rest for a while and heal ${this.remainingHealth-initialHealth} HP."

  /** Signals that the player wants to quit the game. Returns a description of what happened
    * within the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  def attack(keyword: String): String=
    if this.enemiesKeyword.contains(keyword) then
      val damage = this.attackPower
      this.enemiesKeyword(keyword).remainingHealth -= damage
      s"You dealt ${damage} to ${this.enemiesKeyword(keyword).name}!"
    else
      "Please specify which enemy to attack." + specialString

  def guard(): String =
    tempEffect = Some("guarded")
    "You guarded. Damage halved for this turn!"

  def dodge(): String =
    val generator = Random.nextInt(20) + 1
    if generator >= difficultyCheck then
      tempEffect = Some("dodged")
      "You prepared to dodge incoming attacks."
    else "You tried to dodge incoming attacks, but failed! Hint: The odds are worse than a coin flip."

  def suffer(cause: String , damage: Int): String =
    val initialHP = remainingHealth
    val damageReport: String = {
      if this.tempEffect.contains("guarded") then
        remainingHealth -= damage / 2
        s"You took ${initialHP - remainingHealth} damage from $cause! The damage was cut in half by your keen guard!"
      else if this.tempEffect.contains("dodged") then
        s"You fully dodged attacks from $cause! No damage suffered!"
      else
        remainingHealth -= damage
        s"You took ${initialHP - remainingHealth} damage from $cause! Ouch."
    }
    if remainingHealth <= 0 && this.hasShield then
      this.hasShield = false
      this.remainingHealth = 1
      damageReport + "\nClose call! Your Shield protected you!"
    else
      damageReport

    /*if effect.isDefined && damage != 0 then
      effect.foreach(statusEffects += _)
      val effectTaken = effect.map(effect => s"You also got afflicted with $effect! Bummer.").getOrElse("")
      val dmgTaken = s"You took $damage damage from $cause!"
      dmgTaken + effectTaken
    else if effect.isDefined && damage == 0 then
      effect.map(effect => s"You got afflicted with $effect!").getOrElse("")
    else s"You took $damage damage from $cause!"*/


  // in the comment is a possible implementation for lingering status effects in the future;
  // for now, there's only a "rage" status caused by potions, and it will be implemented alone

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.currentLocation.name

end Player
