package com.github.amatkivskiy.localise

class LocaliseExtensionDataChecker {
    static def validateExtensionDataIsCorrect(LocalisePluginExtension extension, String projectName) {
        if (!extension) {
            throw new RuntimeException("Missing 'localise' closure in $projectName/build.gradle.")
        }

        if (!extension.localiseKey) {
            throw new RuntimeException("Missing 'localiseKey' property in 'localise' closure in $projectName/build.gradle.")
        }
    }
}