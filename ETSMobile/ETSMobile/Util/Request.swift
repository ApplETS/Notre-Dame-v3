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
    
    let session: URLSession
    
    private init() {
        self.session = URLSession(configuration: .default)
    }
    
    /// Sends an HTTP request. You should use one the helper functions instead of this one.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - method: The HTTP method to use.
    ///   - data: The data to send to this URL.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func request(url strUrl: String,
                 method: RequestMethod,
                 data: Data?,
                 callback: @escaping (Data?, URLResponse?, Error?) -> Void) -> URLSessionTask? {
        guard let url = URL(string: strUrl) else {
            return nil
        }
        var request = URLRequest(url: url)
        request.httpMethod = method.rawValue
        let completionHandler: (Data?, URLResponse?, Error?) -> Void = { data, response, error in
            DispatchQueue.main.async {
                callback(data, response, error)
            }
        }
        var dataTask: URLSessionTask?
        switch method {
        case .GET, .DELETE:
            dataTask = session.dataTask(with: request, completionHandler: completionHandler)
        case .POST, .PUT:
            if let realData = data {
                dataTask = session.uploadTask(with: request, from: realData, completionHandler: completionHandler)
            }
        }
        dataTask?.resume()
        return dataTask
    }
    
    /// Sends a GET HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func get(url: String, callback: @escaping (Data?, URLResponse?, Error?) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.GET, data: nil, callback: callback)
    }
    
    /// Sends a DELETE HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func delete(url: String, callback: @escaping (Data?, URLResponse?, Error?) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.DELETE, data: nil, callback: callback)
    }
    
    /// Sends a POST HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - data: The data to send to this URL.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func post(url: String, data: Data, callback: @escaping (Data?, URLResponse?, Error?) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.POST, data: data, callback: callback)
    }
    
    /// Sends a PUT HTTP request.
    ///
    /// - Parameters:
    ///   - url: The URL to request.
    ///   - data: The data to send to this URL.
    ///   - callback: The callback to execute when the request finishes.
    /// - Returns: A URLSessionTask that can be used to cancel the request.
    func put(url: String, data: Data, callback: @escaping (Data?, URLResponse?, Error?) -> Void) -> URLSessionTask? {
        return self.request(url: url, method: RequestMethod.PUT, data: data, callback: callback)
    }
}

enum RequestMethod: String {
    case GET = "GET"
    case POST = "POST"
    case PUT = "PUT"
    case DELETE = "DELETE"
}
