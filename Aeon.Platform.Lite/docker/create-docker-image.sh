cd ../../
./gradlew Aeon.Platform.Lite:shadowJar
cd Aeon.Platform.Lite/docker

cp ../config.yml .
cp ../build/libs/*.jar .
cp -r ../lib .
cp -r ../plugins .
docker build -t aeon-runner .
