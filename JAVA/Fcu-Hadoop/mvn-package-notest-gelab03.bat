cd D:\GitHub\Program\JAVA\Fcu-Hadoop
mvn clean install -DskipTests=true -P !local,!linux,!windows,gelab03 > mvn-package-notest-gelab03.txt
pause
