run: ./src/main/Main.java
	@javac -cp ".\src" .\src\main\Main.java -d bin
	@java -cp ".\bin" main.Main