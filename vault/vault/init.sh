#!/usr/bin/env sh

function is_running() {
  printf "$(docker-entrypoint.sh status > /dev/null 2>&1 && printf "0" || printf $?)"
}

set -e

echo "Starting vault server..."
docker-entrypoint.sh server -dev "$@" &

echo "Waiting for vault to start..."
while [[ "$(is_running)" -ne "0" ]]; do
  sleep 1
done

echo "Adding secrets..."
docker-entrypoint.sh login "$VAULT_DEV_ROOT_TOKEN_ID"
cd /mnt/init/secrets; ls | grep json | xargs -I{} docker-entrypoint.sh kv put "secret/${APPLICATION_NAME}" @{}

wait
