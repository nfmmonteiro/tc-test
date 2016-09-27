package com.travelcorporation.baskettest

import spock.lang.Specification

class HelloSpec extends Specification {

    def 'sayHi returns hi' () {
        expect:
            new Hello().sayHi() == 'hi'
    }
}