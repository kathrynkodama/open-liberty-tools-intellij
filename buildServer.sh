mkdir server

# Build Liberty Lemminx extenstion and copy over jar
cd ../liberty-language-server/lemminx-liberty && mvn clean verify
mv target/lemminx-liberty-1.0-SNAPSHOT-jar-with-dependencies.jar ../../open-liberty-tools-intellij/server/