package com.github.amatkivskiy.localise

import spock.lang.Specification

class LocaliseExtensionDataCheckerTests extends Specification {
    def "extension with license key is correct"() {
        setup:
        def ext = new LocalisePluginExtension()
        ext.localiseKey = "key"

        when:
        LocaliseExtensionDataChecker.validateExtensionDataIsCorrect(ext, "test")

        then:
        noExceptionThrown()
    }

    def "extension without license key fails check"() {
        setup:
        def ext = new LocalisePluginExtension()

        when:
        LocaliseExtensionDataChecker.validateExtensionDataIsCorrect(ext, "test")

        then:
        def exception = thrown RuntimeException
        exception.message == "Missing 'localiseKey' property in 'localise' closure in test/build.gradle."
    }

    def "null extension fails check"() {
        when:
        LocaliseExtensionDataChecker.validateExtensionDataIsCorrect(null, "test")

        then:
        def exception = thrown RuntimeException
        exception.message == "Missing 'localise' closure in test/build.gradle."
    }
}