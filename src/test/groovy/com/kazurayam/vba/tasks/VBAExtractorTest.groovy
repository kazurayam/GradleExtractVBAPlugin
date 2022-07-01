package com.kazurayam.vba.tasks

import org.apache.poi.poifs.macros.VBAMacroExtractor
import spock.lang.Specification
import spock.lang.TempDir

class VBAExtractorTest extends Specification {

    @TempDir
    File testProjectDir

    def setup() {
    }

    def "Are you alright?"() {
        VBAMacroExtractor instance = new VBAMacroExtractor()
        assert instance != null
    }
}
