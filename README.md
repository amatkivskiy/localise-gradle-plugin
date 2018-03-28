# Localise.biz gradle plugin for Android projects
[![CircleCI](https://circleci.com/gh/amatkivskiy/localise-gradle-plugin.svg?style=svg)](https://circleci.com/gh/amatkivskiy/localise-gradle-plugin)
[![Gitter](https://badges.gitter.im/amatkivskiy/localise-gradle-plugin.svg)](https://gitter.im/amatkivskiy/localise-gradle-plugin?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

This is a gradle plugin for Android projects that fetches localisation `strings.xml` from localise.biz web service and places it under `res/` folder. :boom: Multiple translations are also supported.

## Download

`TODO`

## Usage 
In `app/build.gradle`
```
buildscript {
    repositories {
      jcenter()
    }

    dependencies {
      classpath 'com.github.amatkivskiy:localise:0.1'
    }
}

apply plugin: 'com.android.application'
// make sure this line comes *after* you apply the Android plugin
apply plugin: 'localise'

android {
  // your Android project configuration
}

localise {
    localiseKey "LOCALISE_PROJECT_KEY" // Localise.biz project API key.
    fallbackLanguage "en-EN" // empty by default.
    filters = ["en", "ua"] // specifies list of langauges to download. Empty by default.
}
```

### Advanced usage

If you need you can link `downloadLocaliseTranslations` to any task in Android project so they can be executed sequentally:

```
task updateStringsAndBuild {
    dependsOn "downloadLocaliseTranslations"
    finalizedBy "build"
}
```

## How it works

This plugin adds gradle `downloadLocaliseTranslations` task to Android project which downloads localised `strings.xml` into `main`'s flavor `res/` folder.
 
:bangbang: Please be careful, as this task will replace all existing `strings.xml` files in that folder.
:point_up: If you store some untranslated resources in `strings.xml` it is recommended to move them into separate file like: `non-translated.xml`.
