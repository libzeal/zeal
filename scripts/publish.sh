#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

mvn release:prepare \
	release:perform \
	javadoc:jar \
	source:jar \
	deploy \
	$@
