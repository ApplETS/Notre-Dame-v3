//
//  ETSTextView.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-24.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation
import UIKit;

class ETSTextField: UITextField {
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
    
    public func togglePasswordVisibility() {
        isSecureTextEntry = !isSecureTextEntry
        
        if let existingText = text, isSecureTextEntry {
            /* When toggling to secure text, all text will be purged if the user
             continues typing unless we intervene. This is prevented by first
             deleting the existing text and then recovering the original text. */
            deleteBackward()
            
            if let textRange = textRange(from: beginningOfDocument, to: endOfDocument) {
                replace(textRange, withText: existingText)
            }
        }
        
        /* Reset the selected text range since the cursor can end up in the wrong
         position after a toggle because the text might vary in width */
        if let existingSelectedTextRange = selectedTextRange {
            selectedTextRange = nil
            selectedTextRange = existingSelectedTextRange
        }
    }
}
