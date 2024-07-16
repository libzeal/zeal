#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

mvn clean package -f ${PROJECT_ROOT}/pom.xml $@
