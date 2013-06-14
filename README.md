iSpy
========

The Trigger System

This is an extensive list of every event, condition, and action that iSpy uses.

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

world.playerCount
 - Returns the number of players in the trigger world

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

(CONDITIONS)

Conditional Operators
 - '==' - If both the operands match, then return true
 - '!=' - If both the operands don't match, then return true
 - 'HAS' - If the left operand contains the right operand, then return true

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