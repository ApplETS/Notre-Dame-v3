#!/bin/bash
# Modified files check script
# Terminates Travis-CI builds whether project related files hasn't been modified
#
# Club App|ETS

MODIFIED_FILES=$(git diff --name-only "$TRAVIS_COMMIT_RANGE")

if [[ ! "$MODIFIED_FILES" =~ "shared/" ]]; then
    if [[ ! "$MODIFIED_FILES" =~ "android/" && "$TRAVIS_OS_NAME" = "linux" ]] ||
    [[ ! "$MODIFIED_FILES" =~ "ios/" && "$TRAVIS_OS_NAME" = "osx" ]]; then
        # It might be possible that travis.yml has been modified. In that case, continue through the build.
        if [[ ! "$MODIFIED_FILES" =~ ".travis.yml" ]]; then
            echo "No project files or travis.yml modified. Exiting."
            exit 1
        fi
    fi
fi