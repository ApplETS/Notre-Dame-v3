//
//  ETSButton.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-25.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation
import UIKit

@IBDesignable class ETSButton: UIButton {
    static let height: CGFloat = 48
    static let loadingWidth: CGFloat = 100

    private var widthConstraint: NSLayoutConstraint?
    private var loadingIndicator: ETSLoadingIndicator?

    var isLoading: Bool = false {
        didSet {
            if isLoading != oldValue {
                if isLoading {
                    startLoadingAnimation()
                } else {
                    stopLoadingAnimation()
                }
            }
        }
    }

    override var isEnabled: Bool {
        didSet {
            UIView.animate(withDuration: 0.2) {
                self.alpha = self.isEnabled || self.isLoading ? 1.0 : 0.3
            }
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.style()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.style()
    }

    public func style() {
        layer.borderColor = UIColor.white.cgColor
        layer.cornerRadius = 8.0
        layer.borderWidth = 0.0
        // width constraint that will be used when loading
        widthConstraint = self.widthAnchor.constraint(equalToConstant: ETSButton.loadingWidth)
        heightAnchor.constraint(equalToConstant: CGFloat(ETSButton.height)).isActive = true
        titleLabel?.font = UIFont.systemFont(ofSize: 20.0, weight: UIFont.Weight.bold)
        loadingIndicator = ETSLoadingIndicator(
            frame: CGRect(
                x: 0,
                y: 0,
                width: ETSButton.loadingWidth,
                height: ETSButton.height
            )
        )
        addSubview(self.loadingIndicator!)
        loadingIndicator?.isHidden = true
    }

    private func startLoadingAnimation() {
        setTitle("", for: .normal)
        titleLabel?.textColor = self.titleLabel?.textColor.withAlphaComponent(0.0)
        layer.borderWidth = 2.0
        isEnabled = false
        UIView.animate(withDuration: 0.2) {
            self.backgroundColor = self.backgroundColor?.withAlphaComponent(0.0)
            self.widthConstraint?.isActive = true
            self.layoutIfNeeded()
        }
        loadingIndicator?.isHidden = false
    }

    private func stopLoadingAnimation() {
        titleLabel?.textColor = self.titleLabel?.textColor.withAlphaComponent(1.0)
        layer.borderWidth = 0.0
        isEnabled = true
        UIView.animate(withDuration: 0.2) {
            self.backgroundColor = self.backgroundColor?.withAlphaComponent(1.0)
            self.widthConstraint?.isActive = false
            self.layoutIfNeeded()
        }
        loadingIndicator?.isHidden = true
    }
}

extension UIButton {
    @IBInspectable var imageContentMode: Int {
        get {
            return imageView?.contentMode.rawValue ?? 0
        }

        set {
            imageView?.contentMode = UIView.ContentMode(rawValue: newValue) ?? .scaleToFill
        }
    }
}
