// @See http://www.canoo.com/blog/2011/12/08/the-art-of-groovy-command-expressions-in-dsls/

import groovy.transform.TupleConstructor

@TupleConstructor
class Instruction {
  String action
  Location location
  Duration duration
  Vehicle vehicle

  String toString() {
    if (vehicle != null) {
      "$action $duration on $vehicle towards $location"
    }
    else {
      "$action $duration at $location"
    }
  }
}

@TupleConstructor
class Duration {
    int value
    String unit

    String toString() {
        "$value $unit"
    }
}

enum Vehicle {
    bus, tram
}

enum Location {
    busstop, Basel, tramstop, Birsfelden
}

import static Duration.*
import static Instruction.*
import static Location.*
import static Vehicle.*
import static GroovyCommandExpressionSample.*

instructions = []

def minutes(int length) { 
    new Duration(length, 'minutes') 
} 

def stand(Duration duration) {     
  [at: { Location loc ->
    instructions << new Instruction('stand', loc, duration)
  }]
}

def ride(Duration duration) {     
  [on: { Vehicle veh ->
    [towards: { Location loc ->
      instructions << new Instruction('ride', loc, duration, veh)
    }]
  }]
}

void makeInstructions() {
  stand 5.minutes at busstop
  ride 10.minutes on bus towards Basel
}

Integer.metaClass.getMinute  = { new Duration(delegate, 'minute') }
Integer.metaClass.getMinutes = { new Duration(delegate, 'minutes') }
Integer.metaClass.getStop    = { new Duration(delegate, 'stop') }
Integer.metaClass.getStops   = { new Duration(delegate, 'stops') }

stand 5.minutes at busstop
ride 10.minutes on bus towards Basel

assert instructions[0].toString() == 'stand 5 minutes at busstop'
assert instructions[1].toString() == 'ride 10 minutes on bus towards Basel'
