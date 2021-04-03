#!/usr/bin/env bash

set -o errexit

docker run \
  --interactive \
  --tty \
  --rm \
  --publish 8080:8080 \
  layered-dockerfile:latest
