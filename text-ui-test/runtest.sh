#!/usr/bin/env bash

# Define colors
GREEN='\033[0;32m'  # Green for PASSED
RED='\033[0;31m'    # Red for FAILED
NC='\033[0m'        # No color (resets color)

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin Bartholomew < input.txt > ACTUAL.TXT

# Normalize line endings to UNIX format for consistency
if command -v dos2unix >/dev/null 2>&1; then
    dos2unix ACTUAL.TXT EXPECTED.TXT
else
    echo "Warning: dos2unix not found, skipping line-ending normalization."
fi

# Remove blank lines from both files
sed -i '' '/^$/d' ACTUAL.TXT
sed -i '' '/^$/d' EXPECTED.TXT
# Remove trailing spaces in both files (helps prevent whitespace issues)
sed -i '' 's/[[:space:]]*$//' ACTUAL.TXT EXPECTED.TXT

# Compare the output to the expected output
diff ACTUAL.TXT EXPECTED.TXT
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Test result: PASSED${NC}"
    exit 0
else
    echo -e "${RED}Test result: FAILED${NC}"
    echo "Differences:"
    diff -y --suppress-common-lines ACTUAL.TXT EXPECTED.TXT
    exit 1
fi