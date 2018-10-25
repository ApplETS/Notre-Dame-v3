//
//  ETSButton.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-25.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation
import UIKit

class ETSButton: UIButton {
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
        self.layer.cornerRadius = 15.0
        self.heightAnchor.constraint(equalToConstant: CGFloat(ETSButton.height)).isActive = true
        self.titleLabel?.font = UIFont.systemFont(ofSize: 15.0, weight: UIFont.Weight.heavy)
    }
}
