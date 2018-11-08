cd ../../
./gradlew Aeon.Platform.Http:shadowJar
cd Aeon.Platform.Http/docker

cp ../config.yml .
cp ../build/libs/*.jar .
cp -r ../lib .
cp -r ../plugins .
docker build -t aeon-runner .
