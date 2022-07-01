package com.kazurayam.vba.tasks

import spock.lang.Specification
import spock.lang.TempDir

class VBAExtractorTest extends Specification {

    @TempDir
    File testProjectDir

    def setup() {
    }

    def "Are you alright?"() {
        VBAExtractor instance = new VBAExtractor()
        assert instance != null
    }
}
