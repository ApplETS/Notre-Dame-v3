//
//  ETSLoadingIndicator.swift
//  ETSMobile
//
//  Created by Felix Leveille on 2018-10-25.
//  Copyright © 2018 ApplETS. All rights reserved.
//

import Foundation
import UIKit

@IBDesignable class ETSLoadingIndicator: UIView {
    static let numberOfCircles = 3
    static let color = UIColor.white
    static let jumpHeight = CGFloat(8)
    static let spacing = CGFloat(4)
    static let circleDiameter = CGFloat(8)
    static let speed = 0.3
    static let delay = 1.0

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.createCircles()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.createCircles()
    }

    func createCircles() {
        let fullWidth = (ETSLoadingIndicator.circleDiameter + ETSLoadingIndicator.spacing) *
            CGFloat(ETSLoadingIndicator.numberOfCircles) - ETSLoadingIndicator.spacing
        let x = (self.frame.width - fullWidth) / 2
        let y = (self.frame.height - ETSLoadingIndicator.circleDiameter) / 2

        for i in 0..<ETSLoadingIndicator.numberOfCircles {
            let circleRect = CGRect(
                x: CGFloat(i) * (ETSLoadingIndicator.circleDiameter + ETSLoadingIndicator.spacing) + x,
                y: self.frame.height - ETSLoadingIndicator.circleDiameter - y,
                width: ETSLoadingIndicator.circleDiameter,
                height: ETSLoadingIndicator.circleDiameter
            )

            let circle = CAShapeLayer()
            circle.fillColor = ETSLoadingIndicator.color.cgColor
            let circlePath = UIBezierPath(ovalIn: circleRect)
            circle.path = circlePath.cgPath
            self.layer.addSublayer(circle)

            let animation = CABasicAnimation(keyPath: "position")
            animation.fromValue = [0, 0]
            animation.toValue = [0, -ETSLoadingIndicator.jumpHeight]
            animation.duration = ETSLoadingIndicator.speed
            animation.autoreverses = true
            animation.timingFunction = CAMediaTimingFunction(name: .easeInEaseOut)

            // animation group with longer duration to add a delay before repeating
            let animationGroup = CAAnimationGroup()
            animationGroup.repeatCount = .infinity
            // The 0.01 adjustment is somehow needed to get the circles to actually start in order
            animationGroup.beginTime = Double(i) * ETSLoadingIndicator.speed / 2 + 0.01
            animationGroup.animations = [animation]
            animationGroup.duration = ETSLoadingIndicator.delay
            circle.add(animationGroup, forKey: "position")
        }
    }
}
