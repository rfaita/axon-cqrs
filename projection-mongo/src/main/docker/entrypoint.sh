#!/bin/sh
set -e

for i in $WAIT_FOR_IT;
    do /wait-for-it.sh $i -t 3600;
done

export NGROK_URL=`curl --silent --show-error http://ngrok:4040/api/tunnels | sed -nE 's/.*public_url":"(https:..[^"]*).*/\1/p'`

echo "---------------------------------OPTS------------------------------------"
echo "JAVA_OPTS="$JAVA_OPTS
echo "WAIT_FOR_IT="$WAIT_FOR_IT
echo "NGROK_URL="$NGROK_URL
echo "-------------------------------------------------------------------------"

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar