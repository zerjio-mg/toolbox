
# Colours
BLUE:=			\033[0;34m
RED:=			\033[0;31m
YELLOW:=		\033[1;33m
LIGHT_RED:=		\033[1;31m
WHITE:=			\033[1;37m
LIGHT_VIOLET := \033[1;35m
NO_COLOUR := 	\033[0m

PROJECT_NAME := TOOLBOX
PROJECT_DESCRIPTION := 📦️️ A simple but handy java library with some useful code (and also to try out things 😋️ )

MSG_SEPARATOR := "==================================================================================================="
MSG_IDENT := "    "

.SILENT:

help:
	echo "${MSG_SEPARATOR}"
	echo "\n${YELLOW}${MSG_IDENT}${PROJECT_DESCRIPTION}${NO_COLOUR}\n"
	echo "=======   ❓️  HELP      ===========================================================================\n"
	echo "${MSG_IDENT}  ${LIGHT_RED}info${NO_COLOUR}       -  ℹ️️   What is this all about?"
	echo
	echo "=======   🛠️  BUILD   [ Requirements : Java 17 ]====================================================\n"
	echo "${MSG_IDENT}  ${LIGHT_RED}clean${NO_COLOUR}      -  🚮  Erase build/ folder"
	echo "${MSG_IDENT}  ${LIGHT_RED}build${NO_COLOUR}      -  ⚙️   Compile classes"
	echo "${MSG_IDENT}  ${LIGHT_RED}test${NO_COLOUR}       -  ✅  Run Unit tests"
	echo "${MSG_IDENT}  ${LIGHT_RED}jar${NO_COLOUR}        -  📦  Compile and generate final JAR"
	echo "${MSG_IDENT}  ${LIGHT_RED}all${NO_COLOUR}        -  🚀️  All-In-One: clean build test jar"
	echo
	echo "${MSG_SEPARATOR}"
	echo

###################################################################################################
#####   HELP   ####################################################################################
###################################################################################################

info:
	echo "\n${MSG_SEPARATOR}\n  ✨ INFO => ℹ️️   What is this all about?\n${MSG_SEPARATOR}"
	-cat doc/help_info.txt
	echo "\n${MSG_IDENT}✅ Done.\n"



###################################################################################################
#####   🛠️  BUILD    ##############################################################################
###################################################################################################

clean:
	echo "\n${MSG_SEPARATOR}\nCLEAN - 🚮  Erase build/ folder\n${MSG_SEPARATOR}"
	- gradle clean
	echo "\n${MSG_IDENT}✅ Done.\n"



build: clean
	echo "\n${MSG_SEPARATOR}\nBUILD -  ⚙️   Compile classes\n${MSG_SEPARATOR}"
	- gradle classes
	echo "\n${MSG_IDENT}✅ Done.\n"



test: clean
	echo "\n${MSG_SEPARATOR}\nTEST -  ✅  Run Unit tests\n${MSG_SEPARATOR}"
	- gradle test
	echo "\n${MSG_IDENT}✅ Done.\n"



jar: clean
	echo "\n${MSG_SEPARATOR}\nJAR -  📦  Compile and generate final JAR\n${MSG_SEPARATOR}"
	- gradle jar
	echo "\n${MSG_IDENT}✅ Done.\n"



all: clean
	echo "\n${MSG_SEPARATOR}\nALL -  🚀️  All-In-One: clean build test jar\n${MSG_SEPARATOR}"
	- gradle build
	echo "\n${MSG_IDENT}✅ Done.\n"

