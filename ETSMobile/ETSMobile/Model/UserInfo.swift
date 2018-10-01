//
//  LoginModel.swift
//  ETS-mobile
//
//  Created by Emmanuel Proulx on 2018-07-23.
//  Copyright © 2018 applETS. All rights reserved.
//

import Foundation

struct UserInfo: Codable {
    var username: String
    var password: String
    var userType: UserType

    enum CodingKeys: String, CodingKey {
        case username
        case password
        case userType = "userTypeId"
    }
}
