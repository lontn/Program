cd D:\Juno\workspaces\SpringAngular
mvn clean package -DskipTests=true -P !local,linux > mvn-package-notest-linux.txt
pause
