iSpy
========

The Trigger System

This is an extensive list of every event, condition, and action that iSpy uses.

(PROPERTIES)

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

(EVENTS)

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
Example: message event.getPlayer "This is a message"
The above will be read as three arguments and the action can be executed correctly.

Assignment Operator
 '=' - This will assign the right operand to the left operand

Message
 - This will message a string to the player.
 - Usage: 'message (playerName) (string)'