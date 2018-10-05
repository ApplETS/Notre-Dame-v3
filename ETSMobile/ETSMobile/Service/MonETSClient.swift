//
//  MonETSClient.swift
//  ETSMobile
//
//  Created by Antoine Lamy on 2018-10-01.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

class MonETSClient {

    private enum HTTPMethod: String {
        case get = "GET"
        case head = "HEAD"
        case post = "POST"
        case put = "PUT"
        case delete = "DELETE"
    }

    enum MonETSError: Error {
        case malformedJson
    }

    static let shared = MonETSClient()

    private let baseURL = Environment.current.monETS()
    private let session = URLSession(configuration: URLSessionConfiguration.default)

    private init() { }

    func authenticate(completion: @escaping (Result<UserInfo>) -> Void) {
        let request = createRequest(url: baseURL.appendingPathComponent("authentification"), httpMethod: .post)
        let task = createDataTask(request: request, completion: completion)
        task.resume()
    }

    private func createDataTask<DataType>(request: URLRequest, completion: @escaping (Result<DataType>) -> Void) -> URLSessionDataTask where DataType: Decodable {
        return session.dataTask(with: request) { (data: Data?, response: URLResponse?, error: Error?) in
            DispatchQueue.main.async {
                let result = Result<Data>.fromValue(data: data, error: error)

                switch result {
                case .failure(let resultError):
                    completion(Result<DataType>.failure(resultError))
                    return
                case .success(_):
                    do {
                        completion(Result.success(try result.decoded(as: DataType.self)))
                    } catch {
                        completion(Result.failure(MonETSError.malformedJson))
                    }
                }
            }
        }
    }

    private func createRequest(url: URL, httpMethod: HTTPMethod) -> URLRequest {
        var request = URLRequest(url: url)
        request.httpMethod = httpMethod.rawValue
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue("ETSMobile", forHTTPHeaderField: "User-Agent")
        return request as URLRequest
    }
}
