//
//  Regex.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-26.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

// Idea stole from https://benscheirman.com/2014/06/regex-in-swift/
class Regex {
    private let pattern: NSRegularExpression?

    init(_ pattern: String, options: NSRegularExpression.Options = []) {
        self.pattern = try? NSRegularExpression(pattern: pattern, options: [])
    }

    func test(_ str: String, options: NSRegularExpression.MatchingOptions = []) -> Bool {
        return self.pattern!.numberOfMatches(
            in: str,
            options: [],
            range: NSMakeRange(0, str.count)
        ) > 0
    }
}

infix operator =~
extension String {
    // regex test operator
    // allows cool syntax like this: "hello there" =~ "[A-Za-z]{5}"
    static func =~ (left: String, right: String) -> Bool {
        return Regex(right).test(left)
    }
}
