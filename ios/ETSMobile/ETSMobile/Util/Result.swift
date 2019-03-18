//
//  Result.swift
//  ETSMobile
//
//  Created by Antoine Lamy on 2018-10-01.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

enum Result<Value> {
    enum ResultError: Error {
        case missingData
    }

    case success(Value)
    case failure(Error)

    static func fromValue(data: Value?, error: Error?) -> Result<Value> {
        if let data = data {
            return .success(data)
        } else if let error = error {
            return .failure(error)
        } else {
            return .failure(ResultError.missingData)
        }
    }
}

extension Result {
    func resolve() throws -> Value {
        switch self {
        case .success(let value):
            return value
        case .failure(let error):
            throw error
        }
    }
}

extension Result where Value == Data {
    func decoded<T: Decodable>(as: T.Type) throws -> T {
        let decoder = JSONDecoder()
        let data = try resolve()
        return try decoder.decode(T.self, from: data)
    }
}
