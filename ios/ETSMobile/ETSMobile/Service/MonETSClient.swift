//
//  MonETSClient.swift
//  ETSMobile
//
//  Created by Antoine Lamy on 2018-10-01.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

class MonETSClient {
    enum MonETSError: Error {
        case malformedJson
    }

    static let shared = MonETSClient()

    private let baseURL = Environment.current.monETS()
    private let session = URLSession(configuration: URLSessionConfiguration.default)

    private init() { }

    func authenticate(userInfo: UserInfo, completion: @escaping (Result<UserInfo>) -> Void) -> URLSessionTask? {
        return Request.shared.post(url: baseURL.appendingPathComponent("authentification").absoluteString,
                                   data: userInfo, callback: completion)
    }
}
