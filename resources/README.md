Creator: Xingyao Wu
Date: 2017/2/26

Java Class:
-------------------------------------------------------------
For model:  
- INote: An interface representing a music note.  - Note: The actual implementation for the note.  
- Pitch: A enum class which contains all the pitch for the music need.  
- PitchIndex: A helper class helps determine the music level for a node.     
Two public static methods implemented in this class:     
- getIndexFromString(String note): get an index representing the note.     
- getStringFromIndex(int index): get string representation from the input index.  
- MusicCreatorOperations: An interface representing how a musicCreator is constructed.  
- MusicCreatorModel: The actual implemention for the musicCreator.

Changes: 
- Adding more getter methods to retrieve data stored in the model classes.
- Fix a bug in deleteNote() method which could't update the highest and lowest note
  for the model in the past.
- Fix a bug in getCurrentStatus() which could't print empty spaces when a certain time 
  this model contains no note.
- Adding playSimultaneously() and playConsecutively() method which supports playing music
  from two models together in different ways.
- Adding more setter methods in Note class for supporting future needs.

For controller:
-  IMusicCreatorController(Interface): Interface that has the start method to take in a starting 
beat and show the view.
-  MusicCreatorController: It implements the interface and uses note container class to get the 
information from model. 
- NotesContainer: class to get the information from the model so that the model won’t be exposed to 
the controller.
- MusicCreatorKeyHandler: Allows the users to move the beat bar for visual view.
- IViewFactory: Factory class to create three views.

For view:
-  IVew(Interface): Interface to represent the views.(Console, gui, midi)
-  Console view: It implements the IView. It is to show the music in text form. 
-  JFrame(gui) view: It implements the IView. It is to show the music in visual form with a piano 
key board below. 
-  midi view: It implements the IView. It is to play the music in audio form. 
- PianoGraph: Used for visual view to have the piano key board.
- Pianokey: Used for visual view to have each piano key.
- PitchGraph: Used for visual view to have the music score on the top.

For main:
- MusicCreator: The main class to take in the order.