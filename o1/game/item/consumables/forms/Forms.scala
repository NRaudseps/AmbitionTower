package o1.game.item.consumables.forms

import o1.game.entities.player.Player
import o1.game.item.consumables.Consumables
import o1.game.stages.overworldArea.{Elevator2F, Elevator3F}

class CakeForm(user:Player) extends Consumables(user , "Cake Form" , "A form that can be used to request a Black Forest Cake."):
  def effect() = 
    user.die()
    "Nothing happened. Or so you thought. Shortly after beginning to wait for the cake, you start losing all motivation and feel suffocated. You remember this feeling: Work Burnout. You keep waiting and waiting, but the promised Cake never showed up, and as it dawns on you, the Cake is a lie, after all."

class AccessForm(user:Player) extends Consumables(user , "Access Form" , "A suspicious form that you cannot make out what is written on it. Can be used to access a previously inaccessible area."):
  def effect() =
    Elevator2F.access()
    "Receptionist Drone: 'Congratulations, Adventurer! Your potential has been logged. You are now officially a Senior Adventurer! Your next evaluation awaits on Floor 2.'\n\nThe drone’s eyes flicker blue for a moment too long, as if it’s not just reciting protocol but watching, recording, remembering.\n\nReceptionist Drone: 'Please enjoy your advancement. Remember: upward mobility is mandatory.'\n\nACCESS TO 2F GRANTED. PROCEED AT YOUR OWN DISCRETION."

class VIPAccessForm(user:Player) extends Consumables(user , "VIP Access Form" , "A golden shiny(!!) form that you cannot make out what is written on it. It feels like it has the authority to open up any door in the world..."):
  def effect() =
    Elevator3F.access()
    "Receptionist Drone: 'Congratulations, Senior Adventurer! Few ascend this far. Your efficiency metrics exceed projected thresholds. Your initiative has been deemed… exceptional.'\n\nThe lights in the building shift to gold, and the dungeon’s emblem flares above the elevator — a rising tower encircled by an endless loop. The air itself hums approval.\n\nReceptionist Drone: 'You have been selected for executive observation. Proceed to Floor 3. Mr. Big Boss has taken… personal interest in your performance.'\n\nThe drone leans closer towards you, as it quietly whispers into your ear.\n\nReceptionist Drone: 'Again, congratulations on your promotion. May your ascent be… permanent.'\n\nACCESS TO VIP AREA GRANTED. WELCOME, ESTEEMED ADVENTURER."