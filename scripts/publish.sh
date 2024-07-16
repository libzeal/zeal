#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

mvn -X -f ${PROJECT_ROOT}/pom.xml \
    release:prepare \
    release:perform \
	  $@
