# Ad-Hoc-Serial-Interface

This library allows you to use Serial communication to synchronize a set of key value pairs between two endpoints. It is designed specifically for use in an FRC robot to interface between the computer vision processor and the main controller, however it can be used for other purposes. 

Most interactions should occur with each individual SerialFile object that holds a key-value pair, but special circumstances may require use of the SerialProcessor class or the Sys class as follows:

If manual connection/disconnection is required, use SerialProcessor because the library currently connects automatically and only disconnects on program exit.

If manual synchronization of one or all SerialFile objects is required, use the SerialProcessor class.

If connection status must be determined, use the Sys class.


We do what we must because we can.
