#!/bin/sh

if [ -z "$API_URL" ]; then
  echo "API_URL environment variable is not set. Using default value."
  export API_URL="http://localhost:8080/api/accounts"
else
  echo "Using API_URL from environment variable: $API_URL"
fi

cat <<EOF > /usr/share/nginx/html/assets/config.json
{
  "apiUrl": "${API_URL}"
}
EOF

#exec nginx -g "daemon off;"
nginx -g 'daemon off;'
