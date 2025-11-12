package o1.game.entities

trait CombatEntity(val maxHealth: Int, var attackPower: Int):
  
  var remainingHealth = maxHealth
  val difficultyCheck = 12 //a luck stats that can be changed depending on each individual in the future, 
                           // the lower, the better but 12 for now
                           // Note: the idea is based on a d20 roll, the roll is a success if
                           // it's higher than DC value
  //only used for luck-based checks for player's dodge for now
  
  
  def heal(amount: Int): Unit=
    if remainingHealth + amount > maxHealth then
      remainingHealth = maxHealth
    else
      remainingHealth += amount
      


