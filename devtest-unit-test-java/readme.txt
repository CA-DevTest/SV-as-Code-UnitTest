How to deploy new version : 
mvn install:install-file -DgroupId=com.ca.devtest.sv.devtools -DartifactId=devtest-unit-test-java -Dversion=1.3.1 -Dpackaging=jar -Dfile=/Users/gaspa03/git/svascode/devtest-unit-test-java/target/devtest-unit-test-java-1.3.1.jar -DpomFile=/Users/gaspa03/git/svascode/devtest-unit-test-java/pom.xml -DlocalRepositoryPath=//Users/gaspa03/git/maven
