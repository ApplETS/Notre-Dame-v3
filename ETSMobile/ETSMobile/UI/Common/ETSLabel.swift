//
//  ETSLabel.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2018-10-25.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation
import UIKit;

class ETSLabel: UILabel {
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
        self.tintColor = UIColor.white;
    }
}

