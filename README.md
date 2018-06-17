# Patch Creator
A tiny Java application. It creats a patch based on two directory file differences.

# How it works
Based on two directory (old, new) some files (located in new directory) would copy to `save to` location:
* `Files exist in new directory, but not in old`.
* `Files exist in both directory (Same Name, Diffrent Data)`.

That means exact same files will be ignored.

At last a Batch (`delete_Removed_Files.bat`) file will be created to delete `Files Exist in old directory, but not in new`.

# Features
No changes will be made in old or new directories by program.

All files are copied from new directory to `save to` directory.

The Batch file will be saved in `save to` location.

Currently files are compared based on the sizes.

# Requirments
* [Java Runtime Environment Version 10.0.1](http://www.oracle.com/technetwork/java/javase/downloads/jre10-downloads-4417026.html) is needed to run JAR file or run it manually.
Other versions not tested so not recommended.
* I tried to write a cross-platform application so there should be no problem on java supported os's. But i only tested in Windows 10 1803.

# How to's

## How to run the program
Simply copy and run `Patch Crator.jar` File. You need JRE pre installed.

## How to use Batch file
Copy batch file to the directory that patch is going to installed. Run it and specified files (Line 11) will be deleted. After run you can delete the batch file.
This file is runable in Windows. for the other os there are some ways.

# Screenshot
![screenshot](https://github.com/Matin-A/Patch-Creator/blob/master/Assests/PatchCreatorScreenshot.jpg)
