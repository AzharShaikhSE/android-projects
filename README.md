# Android-projects

Android academic projects:
## 1. Temperature Converter:
- The app allows the user to select either Fahrenheit-to-Celsius or Celsius-to-Fahrenheit
conversions. No other conversions is allowed, and one conversion must be selected at any
given time (no situation where no conversion is selected).
- The conversion operation, value, and calculated results are added to the “history” (most recent
conversions at the top of the history, history is scrollable when many entries are present).


## 2. QuickNotes:
- The app should act as a simple note pad, allowing the user to enter text notes.
• The note pad displays the last update date/time (which is the last save date/time – this is saved when the note is saved - not entered or editable by the user).
• Note text (and the saved time) is automatically saved when the user presses the back arrow, home
button, etc. – any time the app goes into the Stop state.
• Note text (and the saved time) is saved to the device’s internal file system i.e. JSON file.
• Note text (and the saved time) should be automatically loaded when the app goes into the Resume state.
• If no saved data file is present, the app starts with no text and the current date/time displayed as
the Last Update time.

> Uses: File System, State Saving, Alternate Landscape Layout


## 3. MultiNotes pad:
- This app allows the creation and maintenance of multiple notes. Any number of notes are allowed (including
no notes at all). Notes are made up of a Title and Note Text.
- Notes are loaded from and saved to JSON file.
- JSON file loading happens in an AsyncTask that is started in the onCreate method. Saving happens in the onPause method.

> Uses: RecyclerView, CardLayout, Multi-Activity, JSON File, Option-Menus, Async Task

