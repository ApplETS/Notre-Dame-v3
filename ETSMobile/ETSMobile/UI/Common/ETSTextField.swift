//
//  ETSTextView.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-24.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation
import UIKit;

@IBDesignable class ETSTextField: UITextField {
    static let height = 48

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.style()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.style()
    }

    private func style() {
        self.heightAnchor.constraint(equalToConstant: CGFloat(ETSTextField.height)).isActive = true
        self.backgroundColor = UIColor.clear
        self.textColor = UIColor.white
        self.layer.borderColor = UIColor.white.withAlphaComponent(0.7).cgColor
        self.layer.borderWidth = 1.0
        self.layer.cornerRadius = 8.0
        if let placeholder = self.placeholder {
            self.attributedPlaceholder = NSAttributedString(
                string: placeholder,
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.white.withAlphaComponent(0.5)]
            )
        }
        self.tintColor = UIColor.white;
    }
}
