# Patch Creator
A tiny Java application. It creats a patch based on two directory file differences.

# How it works
Based on two directory (old, new) some files (located in new directory) would copy to `save to` location:
* `Files exist in new directory, but not in old`.
* `Files exist in both directory (Same Name, Diffrent Data)`.

That means exact same files will be ignored.

At last a Batch (`delete_Removed_Files.bat`) file will be created to delete `Files Exist in old directory, but not in new`.

# Features
No changes will be made in old and new directories by program.

All files are copied from new directory to `save to` directory.

The Batch file will be saved in `save to` location.

# How to's

## How to use Batch file
Copy batch file to the directory that patch is going to installed. Run it and specified files (Line 11) will be deleted. After run you can delete the batch file.
This file is runable in Windows. for the other os there are some ways.
