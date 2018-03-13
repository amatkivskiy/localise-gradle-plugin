#!/usr/bin/env bash
rm -rf localRepo/*

./gradlew -p localise-plugin test uploadArchives

./gradlew -p sample clean downloadLocaliseTranslations