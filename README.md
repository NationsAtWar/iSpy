iSpy
========

The Trigger System

This is an extensive list of every variable, property, event, condition, and action that iSpy uses.

Note that each trigger is dependent on a world and will not work outside that world. The world is implied by 
the directory in which the triggers folder rests in.

(TRIGGER PROPERTIES)

name
 - The name of the trigger, must be unique per world, same name as filename

active
 - True/False: Trigger won't activate if false

counter
 - How many times the trigger will activate before never triggering again. Negative means infinite.

record
 - Doesn't actually do anything, but is useful for storing Item/Block IDs and recording location coordinates

(VARIABLES)

global
 - Global variables can be read by any other trigger

local
 - Only this trigger can read these variables

(World PROPERTIES)

world.time
 - Returns the world time of the trigger world

world.weather
 - Returns whether the world is currently set to "clear", "rain", or "storm"

world.moonPhase
 - Returns the state of the moon as either: "new moon", "waxing crescent", "waxing half", "waxing gibbous", "full moon", 
 "waning gibbous", "waning half", and "waning crescent"

world.playerCount
 - Returns the number of players in the trigger world

(VARIABLE TYPES)

Custom variables can be created and used in an iSpy script, so long as they're assigned their type name 
and their specified properties

(e.g)
  someBlockLocation
    varType: Location
    x: 35
    y: 72
    z: -102

Location
 - x
 - y
 - z

Region
 - Location 1
 - Location 2

Block
 - Type
 - ID
 - Amount
 - Durability

(EVENTS)

Each event will store a number of hidden local variables that the rest of the trigger can use.

block.place
 - event.blockLocation
 - event.blockPlacer

block.break
 - event.blockLocation
 - event.blockBreaker

block.use
 - event.blockLocation
 - event.blockUser

region.enter(regionName)
 - regionName

region.leave(regionName)
 - regionName

(CONDITIONS)

Conditional Operators
 - '==' - If both the operands match, then return true
 - '!=' - If both the operands don't match, then return true
 - '>' - If both operands are integers, and the left operand is larger than the right operand, then return true
 - '<' - If both operands are integers, and the left operand is smaller than the right operand, then return true
 - '>=' - If the left operand is greater than or equal to the right operand, then return true
 - '<=' - If the left operand is less than or equal to the right operand, then return true
 - 'HAS' - If the left operand contains the right operand, then return true. This can be either a string inside 
 another string, or an ItemStack within a player inventory (specified by player name or player object)
 - 'IN' - If the left operand is inside the right operand, then return true. This can be location or a player name 
 or a player object inside a region.

(ACTIONS)

IMPORTANT NOTE

Spaces are important, especially in actions, it's the way arguments are parsed from one another.
If you want to pass a string with spaces, then put the string inside quotation marks.
 - Example: message event.getPlayer "This is a message"

The above will be read as three arguments and the action can be executed correctly.

Assignment Operator
 - '=' - This will assign the right operand to the left operand

Message
 - This will message a string to the player.
 - Usage: 'message (playerName) (string)'