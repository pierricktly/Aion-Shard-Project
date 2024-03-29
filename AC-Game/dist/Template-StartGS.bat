@ECHO off
@COLOR 0C
TITLE Aion-Shard GS v4.7.5.x
:START
CLS
SET JAVAVER=1.7
SET NUMAENABLE=false
CLS
IF "%MODE%" == "" (
CALL PanelGS.bat
)

IF "%JAVAVER%" == "1.7" (
SET JAVA_OPTS=-XX:-UseSplitVerifier -XX:+TieredCompilation %JAVA_OPTS%
)
IF "%NUMAENABLE%" == "true" (
SET JAVA_OPTS=-XX:+UseNUMA %JAVA_OPTS%
)
ECHO Starting Aion-Shard GameServer in %MODE% mode.
JAVA %JAVA_OPTS% -ea -javaagent:./libs/ac-commons-1.3.jar -cp ./libs/*;AC-Game.jar com.aionemu.gameserver.GameServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO GameServer has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO GameServer is terminated!
ECHO.
PAUSE
EXIT