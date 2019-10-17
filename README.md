# Patch Creator
A tiny Java application. It creates a patch based on two directory file differences.

# How it works
Based on two directory (old, new) some files (located in new directory) would copy to `save to` location:
* `Files exist in new directory, but not in old`.
* `Files exist in both directory (Same Name, Different Data)`.

That means exact same files will be ignored.

Finally a Batch (`delete_Removed_Files.bat`) file will be created to delete `Files Exist in old directory, but not in new`.

# Features
No changes will be made in old or new directories by program.

All files are copied from new directory to `save to` directory.

The Batch file will be saved in `save to` location.

`New!` Files are compared based on Hash Codes and file sizes. If you select `Ignore Hash Code`, files will be compared only based on sizes. I suggest you not select the `Ignore Hash Code` unless you know the risks (Some files may be ignored).

# Requirements
* [Java Runtime Environment Version 10.0.1](http://www.oracle.com/technetwork/java/javase/downloads/jre10-downloads-4417026.html) is needed to run JAR file or manual run.
Other versions not tested so not recommended.
* I tried to write a cross-platform application so there should be no problem on java supported OSs. But I only tested in Windows 10 1803.

# How-to

## How to run the program
Simply copy and run `Patch Crator.jar` File. You need JRE pre-installed.

## How to use Batch file
Copy batch file to the directory that patch is going to be installed. Run it and specified files (Line 11) will be deleted. After run you can delete the batch file.
This file is runnable in Windows. For the other OSs there are some ways.

# Screenshot
![screenshot](https://github.com/Matin-A/Patch-Creator/blob/v1.1.0-releases-archived/PatchCreatorScreenshot.jpg)

# Notice
Since Java-FX removed from the newest Java, you may need to run command-line app (Console Version).
