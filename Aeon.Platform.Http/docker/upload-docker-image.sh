set -x
set -e

docker tag aeon-runner docker.mia.ulti.io/aeon/aeon-runner
docker push docker.mia.ulti.io/aeon/aeon-runner
