
#!/bin/bash

# Container configuration
CONTAINER_NAME="bikemarket-mariadb"
DB_ROOT_PASSWORD="rootpw"
DB_NAME="bikemarket"
DB_USER="bikemarket"
DB_PASSWORD="bikemarket"
DB_PORT="3307"

# Stop and remove existing container if it exists
podman stop $CONTAINER_NAME 2>/dev/null
podman rm $CONTAINER_NAME 2>/dev/null

# Start new MariaDB container
podman run --name $CONTAINER_NAME \
    -e MYSQL_ROOT_PASSWORD=$DB_ROOT_PASSWORD \
    -e MYSQL_DATABASE=$DB_NAME \
    -e MYSQL_USER=$DB_USER \
    -e MYSQL_PASSWORD=$DB_PASSWORD \
    -p $DB_PORT:3306 \
    -d mariadb:lts

echo "MariaDB container started. Waiting for database to initialize..."
sleep 5
