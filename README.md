# FSM Compiler

FSMC is a scala application that generates a finite state machine from a file with a specific syntax and outputs a finite state machine as a nested switch case statement in C#

### Usage

The repository has a [compiler](https://github.com/adizhavo/FSM_compiler/tree/master/compiler) folder with ```fsmcompiler.jar``` and a sample ```car.fsm``` file showing the syntax of fsmc.

In the same directory run the command ```java -jar fsmcompiler.jar <language> <.fsm file path>```
- ```<language>```is only C#
- ```<.fsm file path>``` is one or multiple paths to the .fsm files
- Additional command ```gen=<generated code directory>``` which by default is the current directory
- Additional command ```json=<syntax structure directory>``` to output the data structure of the finite state machine


### Syntax
```
Actions : Key
FSM : Car
Initial : Locked
{
	Locked {
		Open	Unlocked 	unlock
		Force	Alarming	{lock notify}
	}

	Alarming >alarmOn <alarmOff {
		Reset	Locked	-
	}

	Unlocked {
		Close	Locked	lock
		Reset	Locked	lock
	}
}
```

#### Required

- ```Actions``` will generated an enum containing all the actions
- ```FSM``` will be the file name and also part of the enum containing the fsm states
- ``` Initial``` is the initial state of the finite state machine

#### Transition
```
Locked >entryFunction <exitFunction {
		Open	Unlocked 	unlock
		Force	Alarming	{lock notify}
	}
```

- ```Locked``` is the state of the fsm to execute all the subtransitions
- ```>entryFunction``` is the function that will be executed when transitioning out of the state, this is optional
- ```<exitFunction``` is the function that will be executed when transitioning out of the state, this is optional

#### Subtransitions

- ```{ action	nextState - }``` no function will be called
- ```{ action	nextState function}```
- ```{ action	nextState {function1 function2} }```
