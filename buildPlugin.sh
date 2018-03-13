#!/usr/bin/env bash
# Delete previous artifacts of the plugin
rm -rf localRepo/*

# Run test on plugin project and deploy it to localRepo folder
./gradlew -p localise-plugin test uploadArchives