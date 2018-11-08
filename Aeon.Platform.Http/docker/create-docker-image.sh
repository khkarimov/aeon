cd ../../
./gradlew Aeon.Platform.Http:bootJar
cd Aeon.Platform.Http/docker

cp ../build/libs/*.jar .
cp -r ../lib .
cp -r ../plugins .
docker build -t aeon-runner .
