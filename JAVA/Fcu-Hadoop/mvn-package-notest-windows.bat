cd D:\GitHub\Program\JAVA\Fcu-Hadoop
mvn clean package -DskipTests=true -P !local,!linux,windows,!gelab03 > mvn-package-notest-windows.txt
pause
