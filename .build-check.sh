#!/bin/bash
# Modified files check script
# Terminates Travis-CI builds whether project related files hasn't been modified
#
# Club App|ETS

if [[ ! $(git diff --name-only $TRAVIS_COMMIT_RANGE) =~ "android/" && "$TRAVIS_OS_NAME" = "linux" ]] || 
   [[ ! $(git diff --name-only $TRAVIS_COMMIT_RANGE) =~ "ios/" && "$TRAVIS_OS_NAME" = "osx" ]]; then
    # It might be possible that travis.yml has been modified. In that case, continue through the build.
    if [[ ! $(git diff --name-only $TRAVIS_COMMIT_RANGE) =~ ".travis.yml" ]]; then
        echo "No project files or travis.yml modified. Exiting."
        exit 1
    fi
fi