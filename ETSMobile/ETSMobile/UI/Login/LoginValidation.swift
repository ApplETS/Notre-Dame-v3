//
//  LoginValidation.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-26.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

class LoginValidation {
    static func validateAccessCode(_ code: String) -> Bool {
        return code =~ "^[A-Za-z]{2}\\d{5}$"
    }

    static func validatePassword(_ password: String) -> Bool {
        return !password.isEmpty
    }

    static func validate(code: String, password: String) -> Bool {
        return validateAccessCode(code) && validatePassword(password)
    }
}
