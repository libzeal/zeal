#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..
LIBRARIES_DIR=${PROJECT_ROOT}/libraries
STAGING_DIR=${PROJECT_ROOT}/staging

mkdir -p $STAGING_DIR
find $LIBRARIES_DIR -name "*.jar" | xargs -I {} cp {} $STAGING_DIR
