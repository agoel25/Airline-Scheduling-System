# Flight Scheduler ✈️

### What is it? 

This application was intended to serve an easy way for airline companies 
to schedule their flights. The schedule developed by the software 
will depend upon the number of planes they have in service and 
the routes they want to offer. 
The airline company will have to input the routes which 
it wishes to cover and the number of planes to get a schedule 
for its flights. \
However, due to the time constraint in this summer term, 
I could only include functionality of managing and viewing flight 
routes without the schedule development functionality. The algorithm involved 
in making the schedule development was quite complex, and I will continue working 
on it. 

### My Interest

Scheduling software systems have been fascinating me ever since I
learnt about their existence. The ability of a computer program 
to help decide which tasks should take place when, in order for 
us to be "efficient" has always excited me. Further research into
this topic revealed to me that airline companies often use such
software to schedule their flights as flights usually have very
specific time durations. Therefore, I decided to create one such 
software for my personal project. 

### User Stories

As an airline company, we want to be able to: 
- **add** a flight route to the existing list of routes.
- **delete** an existing flight route from the list of routes.
- **view** the list of routes we are currently serving.
- **edit** the number of planes available.
- **search** for a route using its ID. 
- **save** all the airline information to files. 
- **load** previously saved airline information from files.
- *(future) **get** an efficient flight schedule for the routes.*

### Instructions for Grader

- You can generate the first event (adding a route) by clicking on the "Add Route" button and filling the appropriate fields. 
- You can generate the second event (deleting a route) by selecting the route from the list interface and clicking the "Delete Route" button.
- You can generate the third event (searching for a route) by inputting the ID and clicking the "Search" button.
- You can locate my visual component (application logo) in the top-right corner of the window. 
- You can locate other visual components on the right side of all popup messages. 
- You can change the number of planes by inputting the new number and clicking the "Change Plane Count" button.
- You can save the state of my application by clicking the "Save Data" button.
- You can reload the state of my application by "Load Data" button.

### Phase 4: Task 2 
A representative sample of the event log which is printed to the console when the application is quit:

------------ Event Log ------------

Wed Aug 10 19:07:05 PDT 2022 \
Route with ID: 2506 was added to the airline

Wed Aug 10 19:07:24 PDT 2022 \
Route with ID: 7819 was added to the airline

Wed Aug 10 19:07:31 PDT 2022 \
Plane count was updated to: 34

Wed Aug 10 19:07:33 PDT 2022 \
Route with ID: 2506 was removed from the airline

Wed Aug 10 19:07:36 PDT 2022 \
Plane count was updated to: 12

### Phase 4: Task 3 

**UML Diagram reflection:** \
My UML diagram is pretty straightforward. The main class doesn't have any relationships or associations with any other
class, it is just used to call the AirlineApp class constructor. AirlineApp is the GUI class of the application. It 
holds associations with JsonReader, JsonWriter and Airline classes with multiplicities of 1. The AirlineAppCommandLine 
class is the command line interface class for this application. It holds the same associations as the AirlineApp class. 
The Writable interface helps make a class "Savable" as it helps create Json objects of the class. The Airline and 
Route classes implement this interface. Airline class holds a single RouteList while RouteList holds 0 to many Routes. 
This is because Routes can keep being added or removed from the List. The RouteList class also extends the ArrayList 
class and gets all its functionality from this class. Finally, coming to the event logging mechanism. 0 to many Event(s)
can be held by an EventLog since the list of Events is initialized to be empty in the constructor of EventLog and then 
Events can keep getting added. Moreover, I used an aggregation for this as no Event can exist without an EventLog in 
this application. Additionally, the Iterable<Event> interface is implemented by EvenLog which allows the client 
to iterate all the Events in the EventLog. 

**Possible refactoring:** \
If I had more time one possible refactoring which I would do is move the route addition and removal methods from the 
Airline class to the RouteList class as they seem more appropriate there. Additionally, all the event handlers for mouse 
clicks in my GUI are in the constructor of the AirlineApp class. I could make this more efficient by making a new 
method which handles this as the constructor has become really long as of now. These would improve the structure of my 
code while not changing any functionality, hence being appropriate refactoring. 