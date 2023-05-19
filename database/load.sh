# read username and password
read -p "Enter username: " username
read -sp "Enter password: " password

echo "Creating database and tables..."
mysql -u $username -p$password < ./sql/create.sql

echo "Creating triggerr..."
mysql -u $username -p$password < ./sql/triggers.sql

echo "Creating views..."
mysql -u $username -p$password < ./sql/views.sql

echo "Creating procedures..."
mysql -u $username -p$password < ./sql/procedures.sql


insert_directory="./sql/inserts"
for file in $insert_directory/*.sql
do
    echo "Inserting data from $file..."
    mysql -u $username -p$password < $file
done


echo "Database created successfully"