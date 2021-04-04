#!/usr/bin/env bash

set -o errexit

docker build . \
  --tag layered-dockerfile:latest
