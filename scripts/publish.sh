#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

mvn -X -f ${PROJECT_ROOT}/libraries/pom.xml \
    release:prepare -DtagBase=https://github.com/libzeal/zeal/tags \
    release:perform \
	deploy \
	$@
