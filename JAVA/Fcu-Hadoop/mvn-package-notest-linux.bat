cd D:\GitHub\Program\JAVA\Fcu-Hadoop
mvn clean package -DskipTests=true -P !local,linux,!windows > mvn-package-notest-linux.txt
pause
