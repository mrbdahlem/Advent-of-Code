# Advent-of-Code Java Template for repl.it
This is a template to aid in solving Advent of Code puzzles on repl.it.

## To Use
Create a new repl.it project and clone this repo into it.

Look at the ```Main.java``` file. In this file, you will need to change the ```YEAR``` to be the year you are working on. If ```RUN_TODAY_ONLY```` is true,
then the day specified by ```RUN_DAY``` will be the only day that will be run. ```RUN_DAY``` should be a number from 1-25, or the automatically generated
```LocalDate.now().getDayOfMonth()```. ```DISPLAY_OUTPUT``` will allow or prevent any System.out from your code displaying when run.

During the competition, you will want to modify the class for each day in the ```days``` folder. Each day of the advent calendar has its own subfolder to
allow easier use of helper classes.

In order to download the input files, you will need to create a file named ```.env``` in the same folder as ```Main.java```. This file will have one line as
below:

```
SESSION=XXXXXXXXXXXXXXXXX
```

This is your session cookie which allows access in your name to the Advent of Code site. To get this cookie, you will need to log into the Advent of Code website
using a web browser. Using Chrome, you can go to https://adventofcode.com and press F12 to display the Developer Console. At the top of the console, is a menu with
```Elements```, ```Console```, ```Sources```, etc.  You want the ```Application``` tab, you might need to press the ```>>``` button to find it. On the Application
page, look for Cookies under the Storage item. Click ```Cookies```, then click ```https://adventofcode.com```. Find the cookie named ```session```. The Value of this
cookie is what you want. Double click the value it and press Ctrl-C to copy the value. Paste it into your .env file, replacing the Xs so that it looks like:

```
SESSION=5c4351df31414...
```

If done properly, when you run the project, it will use your session cookie to download your input files each day. A session cookie is good for about 30 days, so you
shouldn't need to repeat these steps during the competition.
