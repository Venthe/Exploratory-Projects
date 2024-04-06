#!/usr/bin/env bash

set -o errexit
set -o pipefail

python3 -m venv --help >> /dev/null || {
  sudo apt install --assume-yes python3-venv
}

if [[ -d .venv ]]; then
  python3 -m venv .venv
fi

# source .venv/bin/activate
# 
# if [[ "$VIRTUAL_ENV" != "" ]]; then
#   echo "Virtual env sourced"
# fi

# python3 -m pip install -r requirements.txt