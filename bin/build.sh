echo "========================== Start building Iris =========================="
sh ./bin/build_app.sh
echo "========================== Building Iris done =========================="
echo "========================== Start building Docker image =========================="
sh ./bin/build_docker_image.sh
echo "========================== Building Docker image done =========================="