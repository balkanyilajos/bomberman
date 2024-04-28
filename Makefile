run: ./src/main/Main.java
	@javac -cp ".\src" .\src\main\Main.java -d bin
	@java -cp ".\bin" main.Main

test: ./test/*.java ./lib/junit-*.jar
	@javac -d bin -cp ".\lib\junit-platform-console-standalone-1.10.2.jar;.\src" test/BombTest.java
	@java -jar ".\lib\junit-platform-console-standalone-1.10.2.jar" --class-path ".\bin" --scan-class-path

