//
//  Environment.swift
//  ETSMobile
//
//  Created by Antoine Lamy on 2018-10-01.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

enum Environment {
    case production
    case preproduction

    static var current: Environment {
        // TODO: When an environment switcher is available, get current environment from user defaults
        return Environment.preproduction
    }

    func emergencyNumber() -> URL {
        return URL(string: "tel://514-396-8900")!
    }

    func clubHomepage() -> URL {
        return URL(string: "https://clubapplets.ca")!
    }

    func passwordReset() -> URL {
        return URL(string: "https://signets-ens.etsmtl.ca/Public/MotDePassePerdu.aspx")!
    }

    func monETS() -> URL {
        switch self {
        case .production:
            return URL(string: "https://portail.etsmtl.ca/api")!
        case .preproduction:
            return URL(string: "https://portail-preprod.etsmtl.ca/api")!
        }
    }
}
