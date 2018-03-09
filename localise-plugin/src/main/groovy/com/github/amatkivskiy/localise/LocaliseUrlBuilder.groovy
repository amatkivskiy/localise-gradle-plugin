package com.github.amatkivskiy.localise

class LocaliseUrlBuilder {
    static def buildFinalUrl(LocalisePluginExtension extension) {
        def urlBuilder = "https://localise.biz/api/export/archive/xml.zip?key=$extension.localiseKey"

        if (extension.fallbackLanguage) {
            urlBuilder += "&fallback=$extension.fallbackLanguage"
        }

        if (extension.filters) {
            urlBuilder += "&filter=${URLEncoder.encode(extension.filters.join(","))}"
        }

        urlBuilder
    }
}