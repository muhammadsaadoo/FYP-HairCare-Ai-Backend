web: bash -c 'export JDBC_DATABASE_URL=$(echo $DATABASE_URL | sed -E "s|postgres://([^:]+):([^@]+)@([^:]+):([^/]+)/(.+)|jdbc:postgresql://\3:\4/\5?user=\1&password=\2|"); java -Dserver.port=$PORT -jar target/*.jar --spring.datasource.url=$JDBC_DATABASE_URL'

