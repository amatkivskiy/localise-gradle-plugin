#!/usr/bin/env bash

../gradlew clean build generatePomFileForMavenPublication bintrayUpload -PbintrayKey=KEY -PdryRun=false --info