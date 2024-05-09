PACKAGE_PATH := hu/elte/fi/szofttech/bomberman
PACKAGE_CLASS := hu.elte.fi.szofttech.bomberman

TEST_START_PATH := ./src/test/java
GAME_START_PATH := ./src/main/java
TEST_FOLDER_PATH := $(TEST_START_PATH)/$(PACKAGE_PATH)/model
GAME_FOLDER_PATH := $(GAME_START_PATH)/$(PACKAGE_PATH)

BIN_FOLDER_PATH := ./bin
LIB_FOLDER_PATH := ./lib

JUNIT_JAR := junit-platform-console-standalone-1.10.2.jar

run: $(GAME_FOLDER_PATH)/Main.java
	@javac -cp "$(GAME_START_PATH)" $(GAME_FOLDER_PATH)/Main.java -d bin
	@java -cp "$(BIN_FOLDER_PATH)" $(PACKAGE_CLASS).Main

test: $(TEST_FOLDER_PATH)/*.java $(LIB_FOLDER_PATH)/$(JUNIT_JAR)
	@javac -d bin -cp "$(LIB_FOLDER_PATH)/$(JUNIT_JAR);$(TEST_START_PATH);$(GAME_START_PATH)" $(TEST_FOLDER_PATH)/BombTest.java
	@java -jar "$(LIB_FOLDER_PATH)/$(JUNIT_JAR)" --class-path "$(BIN_FOLDER_PATH)" --scan-class-path
