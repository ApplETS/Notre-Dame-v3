//
//  Request.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-02.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation

class Request {
    static let shared = Request()

    private let session: URLSession

    private init() {
        self.session = URLSession(configuration: .default)
    }

    func buildCallback<DataType: Decodable>(callback: @escaping (RequestResult<DataType>) -> Void) -> ((Data?, URLResponse?, Error?) -> Void) {
        return { data, response, error in
            DispatchQueue.main.async {
                let result = RequestResult<Data>.fromValue(data: data, error: error)

                switch result {
                case .failure(let resultError):
                    callback(RequestResult<DataType>.failure(resultError))
                    return
                case .success(_):
                    do {
                        callback(RequestResult.success(try result.decoded(as: DataType.self)))
                    } catch {
                        callback(RequestResult.failure(RequestError.malformedJson))
                    }
                }
            }
        }
    }

    /// Sends an HTTP request. You should use one the helper functions instead of this one.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - method: The HTTP method to use.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func request<DataType: Decodable>(url: String,
                                      method: RequestMethod,
                                      headers: [String: String]? = nil,
                                      callback: @escaping (RequestResult<DataType>) -> Void) -> URLSessionTask? {
        let completionHandler = buildCallback(callback: callback)
        guard let request = createURLRequest(url: url, method: method) else {
            return nil
        }
        let dataTask = session.dataTask(with: request, completionHandler: completionHandler)
        dataTask.resume()
        return dataTask
    }

    /// Sends an HTTP request with data. You should use one the helper functions instead of this one.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - method: The HTTP method to use.
    ///   - data: The data to send to this URL.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func request<DataType: Decodable, SentDataType: Encodable>(url: String,
                                      method: RequestMethod,
                                      data: SentDataType? = nil,
                                      headers: [String: String]? = nil,
                                      callback: @escaping (RequestResult<DataType>) -> Void) -> URLSessionTask? {
        let completionHandler = buildCallback(callback: callback)
        guard let request = createURLRequest(url: url, method: method),
              let uploadData = try? JSONEncoder().encode(data) else {
            return nil
        }
        let dataTask = session.uploadTask(with: request, from: uploadData, completionHandler: completionHandler)
        dataTask.resume()
        return dataTask
    }

    func createURLRequest(url: String, method: RequestMethod, headers: [String: String]? = nil) -> URLRequest? {
        guard let url = URL(string: url) else {
            return nil
        }
        var request = URLRequest(url: url)
        request.httpMethod = method.rawValue
        if let headers = headers {
            for (name, value) in headers {
                request.addValue(value, forHTTPHeaderField: name)
            }
        }
        return request
    }
    
    /// Sends a GET HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func get<DataType: Decodable>(url: String,
                                  headers: [String: String]? = nil,
                                  callback: @escaping (RequestResult<DataType>) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.GET, callback: callback)
    }

    /// Sends a DELETE HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func delete<DataType: Decodable>(url: String,
                                     headers: [String: String]? = nil,
                                     callback: @escaping (RequestResult<DataType>) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.DELETE, callback: callback)
    }

    /// Sends a POST HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - data: The data to send to this URL.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func post<DataType: Decodable, SentDataType: Encodable>(url: String,
                                   data: SentDataType,
                                   headers: [String: String]? = nil,
                                   callback: @escaping (RequestResult<DataType>) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.POST, data: data, callback: callback)
    }

    /// Sends a PUT HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - data: The data to send to this URL.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func put<DataType: Decodable, SentDataType: Encodable>(url: String,
             data: SentDataType,
             headers: [String: String]? = nil,
             callback: @escaping (RequestResult<DataType>) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.PUT, data: data, callback: callback)
    }
}

enum RequestMethod: String {
    case GET = "GET"
    case POST = "POST"
    case PUT = "PUT"
    case DELETE = "DELETE"
}

enum RequestError: Error {
    case malformedJson
}
