package com.github.amatkivskiy.localise

import spock.lang.Specification

class LocaliseUrlBuilderTests extends Specification {
    def "localise URL is correct for all valid arguments"() {
        setup:
        def ext = new LocalisePluginExtension()

        when:
        ext.localiseKey = "key"
        ext.fallbackLanguage = "en-GB"
        ext.filters = ["Android", "France"]

        then:
        LocaliseUrlBuilder.buildFinalUrl(ext) == "https://localise.biz/api/export/archive/xml.zip?key=key&fallback=en-GB&filter=Android%2CFrance"
    }

    def "localise URL is correct for only license key"() {
        setup:
        def ext = new LocalisePluginExtension()

        when:
        ext.localiseKey = "key"

        then:
        LocaliseUrlBuilder.buildFinalUrl(ext) == "https://localise.biz/api/export/archive/xml.zip?key=key"
    }

    def "localise URL is correct for license key and filters"() {
        setup:
        def ext = new LocalisePluginExtension()

        when:
        ext.localiseKey = "key"
        ext.filters = ["Android", "France"]

        then:
        LocaliseUrlBuilder.buildFinalUrl(ext) == "https://localise.biz/api/export/archive/xml.zip?key=key&filter=Android%2CFrance"
    }
}