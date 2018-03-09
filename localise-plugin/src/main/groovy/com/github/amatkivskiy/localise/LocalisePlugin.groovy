package com.github.amatkivskiy.localise

import org.gradle.api.Plugin
import org.gradle.api.Project

import static LocaliseExtensionDataChecker.validateExtensionDataIsCorrect

class LocalisePluginExtension {
    String localiseKey
    String fallbackLanguage
    List<String> filters = []
}

class LocalisePlugin implements Plugin<Project> {
    final LocaliseTranslationZipName = "xml.zip"
    final LocalisationBuildDirName = "loco"

    @Override
    void apply(Project target) {
        target.extensions.add("localise", LocalisePluginExtension)

        target.afterEvaluate {
            def android = target.extensions.getByName("android")
            if (!android) {
                throw new RuntimeException("Not an Android application; did you forget `apply plugin: 'com.android.application`?")
            }

            def extension = target.extensions.findByName("localise") as LocalisePluginExtension

            validateExtensionDataIsCorrect(extension, target.name)

            def finalLocaliseUrl = LocaliseUrlBuilder.buildFinalUrl(extension)

            def downloadTask = target.tasks.create("downloadLocaliseTranslations", DownloadLocoTranslations) {
                exportUrl = finalLocaliseUrl
                fileName = LocaliseTranslationZipName
                locoBuildDirectory = LocalisationBuildDirName
                project = target
            }

            downloadTask.group = "Localization"
            downloadTask.description = "Downloads localisation archive using localise.biz API and replaces 'main' flavor strings.xml files with translations from localise.biz"
        }
    }
}
