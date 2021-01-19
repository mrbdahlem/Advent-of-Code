# repl.it compile script for advent of code project
set -e # exit on errors

echo preparing...

# clean up any left over temp directories from the last build
rm -rf /tmp/aoc_*
# make a temp directory to hold the build
TEMPDIR=`mktemp -dt aoc_XXXXXXXXXX`
# create a directory to hold the compiled class files
mkdir -p $TEMPDIR/build

echo compiling...

# compile all class files into the build dir
find -name "*.java" > $TEMPDIR/sources.txt
javac @$TEMPDIR/sources.txt -d $TEMPDIR/build 

echo packaging...

jar -cfe $TEMPDIR/AoC.jar Main -C $TEMPDIR/build .
rm -rf $TEMPDIR/build

echo running...

# run the packaged greenfoot project
java -jar $TEMPDIR/AoC.jar
