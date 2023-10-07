package br.com.wishlist.commons

import spock.lang.Specification
import spock.lang.Unroll

class InputSanitizerTest extends Specification {

    @Unroll
    def "Must sanitize a string with special characters or spaces" (){

        when: "I call the sanitizer"
        String result = InputSanitizer.sanitize(unsanitizeString)

        then: "The result must be sanitized and having no spaces"
        assert result == expected

        where:
        unsanitizeString                           | expected
        "aVery~Special*Str-ing"                    | "aVerySpecialString"
        "One# String With A@ Lot+ (Of Probl/ems"   | "OneStringWithALotOfProblems"
        "A string with spaces "                    | "Astringwithspaces"
        "'/*-+.@@!%&%*(*#@@!#@#%"                  | ""

    }
}
