#!/bin/sh
DIR="$( cd "$(dirname "$0")" ; pwd -P )"
java -cp "$DIR/../lib/*" Statique "$@"