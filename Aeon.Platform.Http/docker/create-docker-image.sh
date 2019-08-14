set -x
set -e

cd ../../
./gradlew assemble
./gradlew Aeon.Platform.Http:shadowJar
cd Aeon.Platform.Http/docker

cp ../build/libs/Aeon.Platform.Http-*-all.jar aeon.jar
cp -r ../lib .
cp -r ../plugins .
docker build -t aeon-runner .
