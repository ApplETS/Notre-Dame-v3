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
    static let height = CGFloat(48)
    static let loadingWidth = CGFloat(100)

    private var widthConstraint: NSLayoutConstraint?
    private var loadingIndicator: ETSLoadingIndicator?

    private var _loading = false
    var loading: Bool {
        get { return self._loading }
        set {
            if self._loading != newValue {
                self._loading = newValue
                if self._loading {
                    self.startLoadingAnimation()
                } else {
                    self.stopLoadingAnimation()
                }
            }
        }
    }

    override var isEnabled: Bool {
        didSet {
            UIView.animate(withDuration: 0.2) {
                self.alpha = self.isEnabled || self.loading ? 1.0 : 0.3
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

    private func style() {
        self.layer.borderColor = UIColor.white.cgColor
        self.layer.cornerRadius = 8.0
        self.layer.borderWidth = 0.0
        // width constraint that will be used when loading
        self.widthConstraint = self.widthAnchor.constraint(equalToConstant: ETSButton.loadingWidth)
        self.heightAnchor.constraint(equalToConstant: CGFloat(ETSButton.height)).isActive = true
        self.titleLabel?.font = UIFont.systemFont(ofSize: 20.0, weight: UIFont.Weight.bold)
        self.loadingIndicator = ETSLoadingIndicator(
            frame: CGRect(
                x: 0,
                y: 0,
                width: ETSButton.loadingWidth,
                height: ETSButton.height
            )
        )
        self.addSubview(self.loadingIndicator!)
        self.loadingIndicator?.isHidden = true
    }

    private func startLoadingAnimation() {
        self.titleLabel?.textColor = self.titleLabel?.textColor.withAlphaComponent(0.0)
        self.layer.borderWidth = 2.0
        self.isEnabled = false
        UIView.animate(withDuration: 0.2) {
            self.backgroundColor = self.backgroundColor?.withAlphaComponent(0.0)
            self.widthConstraint?.isActive = true
            self.layoutIfNeeded()
        }
        self.loadingIndicator?.isHidden = false
    }

    private func stopLoadingAnimation() {
        self.titleLabel?.textColor = self.titleLabel?.textColor.withAlphaComponent(1.0)
        self.layer.borderWidth = 0.0
        self.isEnabled = true
        UIView.animate(withDuration: 0.2) {
            self.backgroundColor = self.backgroundColor?.withAlphaComponent(1.0)
            self.widthConstraint?.isActive = false
            self.layoutIfNeeded()
        }
        self.loadingIndicator?.isHidden = true
    }
}
