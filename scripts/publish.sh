#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

mvn -f ${PROJECT_ROOT}/libraries/pom.xml \
	javadoc:jar \
	source:jar \
	deploy \
	$@
